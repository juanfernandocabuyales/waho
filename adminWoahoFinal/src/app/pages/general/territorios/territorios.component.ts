import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { TerritorioDto, TipoDto, ImagenDto } from '../../../models/general/general';
import { UtilidadesService } from '../../../services/utils/utilidades.service';
import { TipoTerritorioService } from '../../../services/rest/tipo-territorio.service';
import { TerritorioService } from '../../../services/rest/territorio.service';
import { ImagenService } from '../../../services/rest/imagen.service';
import { ConsultarImagenesRequest, ConsultarTiposRequest } from 'src/app/models/request/requests';
import { ConsultarTerritoriosRequest, CrearTerritoriosRequest, EliminarRequest } from '../../../models/request/requests';
import { forkJoin } from 'rxjs';
import { ConsultarImagenesResponse, ConsultarTerritoriosResponse } from 'src/app/models/response/reponses';
import { ConsultarTiposResponse, GeneralResponse, CrearResponse, EliminarResponse } from '../../../models/response/reponses';
import { Constantes } from 'src/app/constants/constantes';

@Component({
  selector: 'app-territorios',
  templateUrl: './territorios.component.html',
  styleUrls: ['./territorios.component.css']
})
export class TerritoriosComponent implements OnInit {

  territorioForm: FormGroup;
  submitted = false;

  listTerritorios: TerritorioDto[] = [];
  listTipos: TipoDto[] = [];
  listImagenes: ImagenDto[] = [];

  constructor(private utilidades: UtilidadesService,
              private tipoService: TipoTerritorioService,
              private territorioService: TerritorioService,
              private imagenService: ImagenService,
              private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.cargarInformacion();
    this.territorioForm = this.formBuilder.group({
      nombre: new FormControl('', Validators.required),
      padre: new FormControl(''),
      tipo: new FormControl('', Validators.required),
      codigo: new FormControl('', Validators.required),
      imagen: new FormControl('', Validators.required)
    });
  }

  private cargarInformacion(): void {
    this.utilidades.mostrarCargue();
    const requestImagen: ConsultarImagenesRequest = {
      idioma: this.utilidades.obtenerIdioma()
    };

    const requestTipos: ConsultarTiposRequest = {
      idioma: this.utilidades.obtenerIdioma()
    };

    const requestTerritorios: ConsultarTerritoriosRequest = {
      idioma: this.utilidades.obtenerIdioma()
    };

    const imagenesServicio = this.imagenService.obtenerImagenes(this.utilidades.construirPeticion(requestImagen));
    const tipoServicios = this.tipoService.obtenerTipos(this.utilidades.construirPeticion(requestTipos));
    const territorioServicios = this.territorioService.obtenerTerritorios(this.utilidades.construirPeticion(requestTerritorios));

    forkJoin([imagenesServicio, tipoServicios, territorioServicios]).subscribe(
      data => {
        this.validarRespuestas(data);
      },
      () => {
        this.utilidades.ocultarCargue();
      }
    );
  }

  private actualizarData(): void {
    this.utilidades.mostrarCargue();
    const requestTerritorios: ConsultarTerritoriosRequest = {
      idioma: this.utilidades.obtenerIdioma()
    };
    this.territorioService.obtenerTerritorios(this.utilidades.construirPeticion(requestTerritorios)).subscribe(
      data => {
        this.validarActualizacion(data);
      },
      () => {
        this.utilidades.ocultarCargue();
      }
    );
  }

  crearTerritorio(): void {
    if (this.territorioForm.invalid) {
      this.utilidades.abrirDialogo(this.utilidades.traducirTexto('general.completar_campos'), false);
    } else {
      this.procesoCreacionActualizacion();
    }
  }

  eliminarFila(territorio: TerritorioDto, index: number): void {
    this.utilidades.mostrarDialogoConfirmacion(this.utilidades.traducirTexto('territorioPage.mensaje_eliminar'))
    .then( result => {
      if (result.isConfirmed){
        this.procesoEliminar(territorio, index);
      }
    });
  }

  editarFila(territorio: TerritorioDto): void {
    if (territorio.nombre === '' || territorio.idTipo === '' ||
      territorio.codigo === '' || territorio.idImagen === '') {
      this.utilidades.abrirDialogo(this.utilidades.traducirTexto('general.completar_campos'), false);
    }
    else {
      this.procesoCreacionActualizacion(territorio);
    }
  }

  limpiar(): void {
    this.submitted = false;
    this.territorioForm.reset({
      nombre: '',
      padre: '',
      tipo: '',
      codigo: '',
      imagen: ''
    });
  }

  get f(): any {
    return this.territorioForm.controls;
  }

  private procesoCreacionActualizacion(territorio ?: TerritorioDto): void {
    this.utilidades.mostrarCargue();
    const crearRequest: CrearTerritoriosRequest = {
      territorioDto: {
        id: territorio ? territorio.id : '',
        nombre: territorio ? territorio.nombre : this.territorioForm.get('nombre').value,
        idPadre: territorio ? territorio.idPadre : this.territorioForm.get('padre').value,
        idTipo: territorio ? territorio.idTipo : this.territorioForm.get('tipo').value,
        codigo: territorio ? territorio.codigo : Constantes.SIGNO_MAS + this.territorioForm.get('codigo').value,
        idImagen: territorio ? territorio.idImagen : this.territorioForm.get('imagen').value,
      },
      idioma: this.utilidades.obtenerIdioma()
    };
    if (territorio){
      this.territorioService.actualizarTerritorios(this.utilidades.construirPeticion(crearRequest)).subscribe(
        data => {
          this.validarCreacionActualizacion(data, crearRequest.territorioDto);
        },
        () => {
          this.utilidades.ocultarCargue();
        }
      );
    }else{
      this.territorioService.crearTerritorios(this.utilidades.construirPeticion(crearRequest)).subscribe(
        data => {
          this.validarCreacionActualizacion(data, crearRequest.territorioDto);
        },
        () => {
          this.utilidades.ocultarCargue();
        }
      );
    }
  }

  private procesoEliminar(territorio: TerritorioDto, index: number): void {
    this.utilidades.mostrarCargue();

    const eliminarRequest: EliminarRequest = {
      id: territorio.id,
      idioma: this.utilidades.obtenerIdioma()
    };

    this.territorioService.eliminarTerritorios(this.utilidades.construirPeticion(eliminarRequest)).subscribe(
      data => {
        this.validarEliminacion(data, index);
      },
      () => {
        this.utilidades.ocultarCargue();
      }
    );
  }

  private validarRespuestas(results: any): void {
    const consultarImagenesResponse: ConsultarImagenesResponse = JSON.parse(results[0].mensaje);
    const tipoServiciosResponse: ConsultarTiposResponse = JSON.parse(results[1].mensaje);
    const territoriosResponse: ConsultarTerritoriosResponse = JSON.parse(results[2].mensaje);

    if (consultarImagenesResponse.codigoRespuesta === Constantes.RESPUESTA_NEGATIVA ||
      tipoServiciosResponse.codigoRespuesta === Constantes.RESPUESTA_NEGATIVA ||
      territoriosResponse.codigoRespuesta === Constantes.RESPUESTA_NEGATIVA) {
      this.utilidades.abrirDialogo(this.utilidades.traducirTexto('general.carga_mal'), true);
    } else {
      this.listImagenes = consultarImagenesResponse.listImagenes;
      this.listTipos = tipoServiciosResponse.listTipos;
      this.listTerritorios = territoriosResponse.listTerritorios;
    }
    this.utilidades.ocultarCargue();
  }

  private validarCreacionActualizacion(respuesta: GeneralResponse, territorio?: TerritorioDto): void {
    const crearResponse: CrearResponse = JSON.parse(respuesta.mensaje);
    if (crearResponse.codigoRespuesta === Constantes.RESPUESTA_POSITIVA) {
      this.utilidades.abrirDialogoExitoso(this.utilidades.traducirTexto('general.proceso_ok'))
        .then(() => {
          this.actualizarData();
        });
    } else {
      this.utilidades.abrirDialogo(crearResponse.mensajeRespuesta, false);
    }
    this.utilidades.ocultarCargue();
  }

  private validarEliminacion(respuesta: GeneralResponse, index: number): void {
    const eliminarResponse: EliminarResponse = JSON.parse(respuesta.mensaje);
    if (eliminarResponse.codigoRespuesta === Constantes.RESPUESTA_POSITIVA){
      this.utilidades.abrirDialogoExitoso(this.utilidades.traducirTexto('general.proceso_ok'))
      .then(() => {
          this.listTerritorios.splice(index, 1);
      });
    }else {
      this.utilidades.abrirDialogo(eliminarResponse.mensajeRespuesta, false);
    }
    this.utilidades.ocultarCargue();
  }

  private validarActualizacion(respuesta: GeneralResponse): void {
    const territoriosResponse: ConsultarTerritoriosResponse = JSON.parse(respuesta.mensaje);
    if (territoriosResponse.codigoRespuesta === Constantes.RESPUESTA_NEGATIVA){
      this.utilidades.abrirDialogo(this.utilidades.traducirTexto('general.carga_mal'), true);
    }else{
      this.limpiar();
      this.listTerritorios = territoriosResponse.listTerritorios;
    }
    this.utilidades.ocultarCargue();
  }
}
