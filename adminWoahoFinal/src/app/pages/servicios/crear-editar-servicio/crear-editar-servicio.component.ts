import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { forkJoin } from 'rxjs';
import { Constantes } from '../../../constants/constantes';
import { ImagenService } from '../../../services/rest/imagen.service';
import { CategoriaService } from '../../../services/rest/categoria.service';
import { TerritorioService } from '../../../services/rest/territorio.service';
import { ConsultarImagenesRequest } from '../../../models/request/requests';
import { UtilidadesService } from '../../../services/utils/utilidades.service';
import { ConsultarCategoriaRequest } from 'src/app/models/request/requests';
import { ConsultarTerritorioRequest } from '../../../models/request/requests';
import { ConsultarImagenesResponse } from '../../../models/response/reponses';
import { ConsultarCategoriasResponse } from 'src/app/models/response/reponses';
import { ConsultarTerritorioResponse } from '../../../models/response/reponses';
import { MonedaDto, UnidadDto, TarifaDto, ServicioDto } from '../../../models/general/general';
import { MonedaServiceService } from '../../../services/rest/moneda.service';
import { ConsultarMonedasRequest } from '../../../models/request/requests';
import { ConsultarMonedasResponse } from '../../../models/response/reponses';
import { UnidadService } from '../../../services/rest/unidad.service';
import { ConsultarUnidadesRequest } from '../../../models/request/requests';
import { ConsultarUnidadesResponse } from '../../../models/response/reponses';
import { CrearServicioRequest } from '../../../models/request/requests';
import { ServicioService } from '../../../services/rest/servicio.service';
import { GeneralResponse } from 'src/app/models/response/reponses';
import { CrearServicioResponse } from '../../../models/response/reponses';
import { ImagenDto, CategoriaDto, PaisDTO } from '../../../models/general/general';

@Component({
  selector: 'app-crear-editar-servicio',
  templateUrl: './crear-editar-servicio.component.html',
  styleUrls: ['./crear-editar-servicio.component.css']
})
export class CrearEditarServicioComponent implements OnInit {

  servicioForm: FormGroup;

  caracteres = '';

  listTarifas: TarifaDto[] = [];

  maxCaracteres: number = Constantes.CANT_MAX_CARACTERES;

  listImagenes: ImagenDto[] = [];
  listCategorias: CategoriaDto[] = [];
  listPaises: PaisDTO[] = [];
  listMonedas: MonedaDto[] = [];
  listUnidades: UnidadDto[] = [];

  submitted = false;
  blnCreacion = false;
  servicioAux: ServicioDto;

  constructor(private formBuilder: FormBuilder,
              private utilidades: UtilidadesService,
              private imagenService: ImagenService,
              private categoriaService: CategoriaService,
              private territorioService: TerritorioService,
              private monedaServiceService: MonedaServiceService,
              private unidadService: UnidadService,
              private servicio: ServicioService) {
  }

  ngOnInit(): void {
    this.servicioAux = this.utilidades.obtenerObjetoAlmacenado() as ServicioDto;
    this.cargarInformacion();
    this.utilidades.mostrarCargue();
    if (this.servicioAux) {
      this.servicioForm = this.formBuilder.group({
        nombre: new FormControl(this.servicioAux.nombre, Validators.required),
        imagen: new FormControl(this.servicioAux.codigoImagen, Validators.required),
        categoria: new FormControl(this.servicioAux.categoria, Validators.required),
        pais: new FormControl(this.servicioAux.pais, Validators.required),
        descripcion: new FormControl(this.servicioAux.descripcion, Validators.required)
      });
      this.listTarifas = this.servicioAux.listTarifas;
    } else {
      this.blnCreacion = true;
      this.utilidades.mostrarCargue();
      this.servicioForm = this.formBuilder.group({
        nombre: new FormControl('', Validators.required),
        imagen: new FormControl('', Validators.required),
        categoria: new FormControl('', Validators.required),
        pais: new FormControl('', Validators.required),
        descripcion: new FormControl('', Validators.required)
      });
      this.agregarFila();
      this.utilidades.ocultarCargue();
    }
  }

  get f(): any {
    return this.servicioForm.controls;
  }

  cargarInformacion(): void {
    const requestImagen: ConsultarImagenesRequest = {
      idioma: this.utilidades.obtenerIdioma()
    };

    const requestMonedas: ConsultarMonedasRequest = {
      idioma: this.utilidades.obtenerIdioma()
    };

    const requestUnidades: ConsultarUnidadesRequest = {
      idioma: this.utilidades.obtenerIdioma()
    };

    const requestCategoria: ConsultarCategoriaRequest = {
      idioma: this.utilidades.obtenerIdioma()
    };

    const requestPais: ConsultarTerritorioRequest = {
      idioma: this.utilidades.obtenerIdioma(),
      tipoTerritorio: Constantes.TIPO_TERRITORIO_PAIS
    };

    const imagenesServicio = this.imagenService.obtenerImagenes(this.utilidades.construirPeticion(requestImagen));
    const categoriaServicio = this.categoriaService.obtenerCategorias(this.utilidades.construirPeticion(requestCategoria));
    const territorioServicio = this.territorioService.obtenerPaises(this.utilidades.construirPeticion(requestPais));
    const monedaServicio = this.monedaServiceService.obtenerMonedas(this.utilidades.construirPeticion(requestMonedas));
    const unidadServicio = this.unidadService.obtenerUnidades(this.utilidades.construirPeticion(requestUnidades));

    forkJoin([imagenesServicio, categoriaServicio, territorioServicio, monedaServicio, unidadServicio]).subscribe(
      results => {
        this.validarRespuestas(results);
      }, () => {
        this.utilidades.ocultarCargue();
      }
    );
  }

  ejecutarOperacion(): void {
    if (this.blnCreacion) {
      this.crearServicio();
    } else {
      this.actualizarServicio();
    }
  }

  crearServicio(): void {
    this.submitted = true;
    const validacionTarifa = this.validarTarifas();

    if (this.servicioForm.invalid) {
      this.utilidades.abrirDialogo(this.utilidades.traducirTexto('general.completar_campos'), false);
    } else {
      if (!validacionTarifa) {
        this.utilidades.abrirDialogo(this.utilidades.traducirTexto('serviciosPage.tarifas_mal'), false);
      } else {
        this.utilidades.mostrarCargue();
        const requestCrear: CrearServicioRequest = {
          servicioDto: {
            codigo: '',
            nombre: this.servicioForm.get('nombre').value,
            imagen: this.servicioForm.get('imagen').value,
            codigoImagen: '',
            categoria: this.servicioForm.get('categoria').value,
            pais: this.servicioForm.get('pais').value,
            descripcion: this.servicioForm.get('descripcion').value,
            listTarifas: this.listTarifas
          },
          idioma: this.utilidades.obtenerIdioma()
        };

        this.servicio.crearServicio(this.utilidades.construirPeticion(requestCrear)).subscribe(
          data => {
            this.validarRespuestaCreacion(data);
          },
          () => {
            this.utilidades.ocultarCargue();
          }
        );
      }
    }
  }

  actualizarServicio(): void {
    this.utilidades.mostrarCargue();
    const requestCrear: CrearServicioRequest = {
      servicioDto: {
        codigo: this.servicioAux.codigo,
        nombre: this.servicioForm.get('nombre').value,
        imagen: this.servicioForm.get('imagen').value,
        codigoImagen: this.servicioForm.get('imagen').value,
        categoria: this.servicioForm.get('categoria').value,
        pais: this.servicioForm.get('pais').value,
        descripcion: this.servicioForm.get('descripcion').value,
        listTarifas: this.listTarifas
      },
      idioma: this.utilidades.obtenerIdioma()
    };
    this.servicioAux = requestCrear.servicioDto;

    this.servicio.actualizarServicio(this.utilidades.construirPeticion(requestCrear)).subscribe(
      data => {
        this.validarRespuestaActualizacion(data);
      },
      error => {
        this.utilidades.ocultarCargue();
      }
    );
  }

  validarRespuestas(results: any): void {
    const consultarImagenesResponse: ConsultarImagenesResponse = JSON.parse(results[0].mensaje);
    const consultarCategoriaResponse: ConsultarCategoriasResponse = JSON.parse(results[1].mensaje);
    const consultarTerritoriosResponse: ConsultarTerritorioResponse = JSON.parse(results[2].mensaje);
    const consultarMonedasResponse: ConsultarMonedasResponse = JSON.parse(results[3].mensaje);
    const consultarUnidadesResponse: ConsultarUnidadesResponse = JSON.parse(results[4].mensaje);

    if (consultarImagenesResponse.codigoRespuesta === Constantes.RESPUESTA_NEGATIVA ||
      consultarCategoriaResponse.codigoRespuesta === Constantes.RESPUESTA_NEGATIVA ||
      consultarTerritoriosResponse.codigoRespuesta === Constantes.RESPUESTA_NEGATIVA ||
      consultarMonedasResponse.codigoRespuesta === Constantes.RESPUESTA_NEGATIVA ||
      consultarUnidadesResponse.codigoRespuesta === Constantes.RESPUESTA_NEGATIVA) {
      this.utilidades.abrirDialogo(this.utilidades.traducirTexto('general.carga_mal'), true);
    } else {
      this.listImagenes = consultarImagenesResponse.listImagenes;
      this.listCategorias = consultarCategoriaResponse.listCategorias;
      this.listPaises = consultarTerritoriosResponse.lisPaisesDto;
      this.listMonedas = consultarMonedasResponse.listMonedas;
      this.listUnidades = consultarUnidadesResponse.listUnidades;
    }
    this.utilidades.ocultarCargue();
  }

  validarRespuestaCreacion(pRespuesta: GeneralResponse): void {
    const crearServiciosResponse: CrearServicioResponse = JSON.parse(pRespuesta.mensaje);
    if (crearServiciosResponse.codigoRespuesta === Constantes.RESPUESTA_POSITIVA) {
      this.limpiarCampos();
      this.utilidades.abrirDialogo(this.utilidades.traducirTexto('serviciosPage.creacion_ok'), true);
    } else {
      this.utilidades.abrirDialogo(crearServiciosResponse.mensajeRespuesta, false);
    }
    this.utilidades.ocultarCargue();
  }

  validarRespuestaActualizacion(pRespuesta: GeneralResponse): void {
    const crearServiciosResponse: CrearServicioResponse = JSON.parse(pRespuesta.mensaje);
    if (crearServiciosResponse.codigoRespuesta === Constantes.RESPUESTA_POSITIVA) {
      this.limpiarCampos();
      this.utilidades.abrirDialogoExitoso(this.utilidades.traducirTexto('general.operacion_ok'));
    } else {
      this.utilidades.abrirDialogo(crearServiciosResponse.mensajeRespuesta, false);
    }
    this.utilidades.ocultarCargue();
  }

  agregarFila(): void {
    this.listTarifas.push({
      codigo: '',
      pais: '0',
      unidad: '0',
      valor: null,
      moneda: '0'
    });
  }

  eliminarFila(i: number): void {
    if (this.listTarifas.length === 1) {
      this.listTarifas = [];
      this.agregarFila();
    } else {
      this.listTarifas.splice(i, 1);
    }
  }

  validarTarifas(): boolean {
    let blnValidacion = true;
    this.listTarifas.forEach(item => {
      if (item.moneda === '0' || item.pais === '0' ||
        item.unidad === '0' || !item.valor) {
        blnValidacion = false;
      }
    });
    return blnValidacion;
  }

  limpiarCampos(): void {
    this.maxCaracteres = Constantes.CANT_MAX_CARACTERES;
    if (!this.blnCreacion) {
      this.listTarifas = this.servicioAux.listTarifas;
      this.servicioForm.reset({
        nombre: this.servicioAux.nombre,
        imagen: this.servicioAux.codigoImagen,
        categoria: this.servicioAux.categoria,
        pais: this.servicioAux.pais,
        descripcion: this.servicioAux.descripcion
      });
    } else {
      this.listTarifas = [];
      this.agregarFila();
      this.servicioForm.reset({
        nombre: '',
        imagen: '',
        categoria: '',
        pais: '',
        descripcion: ''
      });
    }
  }
}
