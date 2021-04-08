import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { UtilidadesService } from 'src/app/services/utils/utilidades.service';
import { UnidadDto } from '../../../models/general/general';
import { ConsultarUnidadesRequest, CrearUnidadRequest } from '../../../models/request/requests';
import { UnidadService } from '../../../services/rest/unidad.service';
import { GeneralResponse, ConsultarUnidadesResponse, CrearUnidadResponse, EliminarResponse } from '../../../models/response/reponses';
import { Constantes } from 'src/app/constants/constantes';

@Component({
  selector: 'app-unidades',
  templateUrl: './unidades.component.html',
  styleUrls: ['./unidades.component.css']
})
export class UnidadesComponent implements OnInit {

  unidadForm: FormGroup;
  submitted = false;

  listUnidades: UnidadDto[] = [];

  constructor(private formBuilder: FormBuilder,
              private unidadService: UnidadService,
              private utilidades: UtilidadesService) { }

  ngOnInit(): void {
    this.unidadForm = this.formBuilder.group({
      nombre: new FormControl('', Validators.required)
    });
    this.cargarUnidades();
  }

  get f(): any {
    return this.unidadForm.controls;
  }

  private cargarUnidades(): void {
    this.utilidades.mostrarCargue();
    const unidadesRequest: ConsultarUnidadesRequest = {
      idioma: this.utilidades.obtenerIdioma()
    };
    this.unidadService.obtenerUnidades(this.utilidades.construirPeticion(unidadesRequest)).subscribe(
      data => {
        this.validarCargaInicial(data);
      },
      () => {
        this.utilidades.ocultarCargue();
      }
    );
  }

  crearUnidad(): void {
    this.submitted = true;
    if (this.unidadForm.invalid) {
      this.utilidades.abrirDialogo(this.utilidades.traducirTexto('general.completar_campos'), false);
    } else {
      this.procesarCreacionActualizacion(this.unidadForm.get('nombre').value, null);
    }
  }

  editarFila(pUnidad: UnidadDto): void {
    this.utilidades.mostarDialogoInput(this.utilidades.traducirTexto('unidadPage.nombre'),
      this.utilidades.traducirTexto('unidadPage.ingrese_nombre'),
      pUnidad.nombreUnidad)
      .then(result => {
        const nombre: string = result.value as string;
        if (!nombre || nombre === '') {
          this.utilidades.abrirDialogo(this.utilidades.traducirTexto('general.completar_campos'), true);
        } else {
          this.procesarCreacionActualizacion(nombre, pUnidad);
        }
      });
  }

  eliminarFila(unidad: UnidadDto): void {
    this.utilidades.mostrarDialogoConfirmacion(this.utilidades.traducirTexto('unidadPage.mensaje_eliminar'))
    .then( result => {
      if (result.isConfirmed){
        this.utilidades.mostrarCargue();
        const eliminarRequest = this.utilidades.obtenerRequestEliminar(unidad.idUnidad);
        this.unidadService.eliminarUnidades(this.utilidades.construirPeticion(eliminarRequest)).subscribe(
          data => {
            this.validarEliminacion(data);
          },
          () => {
            this.utilidades.ocultarCargue();
          }
        );
      }
    });
  }

  private procesarCreacionActualizacion(pNombre: string, pUnidad: UnidadDto): void {
    this.utilidades.mostrarCargue();
    const crearRequest: CrearUnidadRequest = {
      unidad: {
        idUnidad: !pUnidad ? '' : pUnidad.idUnidad,
        nombreUnidad: pNombre
      },
      idioma: this.utilidades.obtenerIdioma()
    };
    if (!pUnidad){
      this.unidadService.crearUnidades(this.utilidades.construirPeticion(crearRequest)).subscribe(
        data => {
          this.validarCreacionActualizacion(data);
        },
        () => {
          this.utilidades.ocultarCargue();
        }
      );
    }else{
      this.unidadService.actualizarUnidades(this.utilidades.construirPeticion(crearRequest)).subscribe(
        data => {
          this.validarCreacionActualizacion(data);
        },
        () => {
          this.utilidades.ocultarCargue();
        }
      );
    }
  }

  private limpiarCampos(): void {
    this.submitted = false;
    this.unidadForm.reset({
      nombre: ''
    });
  }

  private validarCargaInicial(request: GeneralResponse): void {
    const unidadResponse: ConsultarUnidadesResponse = JSON.parse(request.mensaje);
    if (unidadResponse.codigoRespuesta === Constantes.RESPUESTA_POSITIVA) {
      this.listUnidades = unidadResponse.listUnidades;
    } else {
      this.utilidades.abrirDialogo(unidadResponse.mensajeRespuesta, true);
    }
    this.utilidades.ocultarCargue();
  }

  private validarCreacionActualizacion(request: GeneralResponse): void {
    const crearResponse: CrearUnidadResponse = JSON.parse(request.mensaje);
    this.utilidades.ocultarCargue();
    if (crearResponse.codigoRespuesta === Constantes.RESPUESTA_POSITIVA) {
      this.utilidades.abrirDialogoExitoso(this.utilidades.traducirTexto('general.proceso_ok'))
        .then(() => {
          this.limpiarCampos();
          this.cargarUnidades();
        });
    } else {
      this.utilidades.abrirDialogo(crearResponse.mensajeRespuesta, true);
    }
  }

  private validarEliminacion(request: GeneralResponse): void {
    const eliminarResponse: EliminarResponse = JSON.parse(request.mensaje);
    this.utilidades.ocultarCargue();
    if (eliminarResponse.codigoRespuesta === Constantes.RESPUESTA_POSITIVA){
      this.utilidades.abrirDialogoExitoso(this.utilidades.traducirTexto('general.proceso_ok'))
        .then(() => {
          this.limpiarCampos();
          this.cargarUnidades();
        });
    }else {
      this.utilidades.abrirDialogo(eliminarResponse.mensajeRespuesta, true);
    }
  }
}
