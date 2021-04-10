import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MonedaDto, PaisDTO } from 'src/app/models/general/general';
import { TerritorioService } from 'src/app/services/rest/territorio.service';
import { UtilidadesService } from 'src/app/services/utils/utilidades.service';
import { ConsultarTerritorioRequest, ConsultarMonedasRequest, CrearMonedaRequest, EliminarRequest } from '../../../models/request/requests';
import { Constantes } from '../../../constants/constantes';
import { ConsultarTerritorioResponse, GeneralResponse, ConsultarMonedasResponse, CrearResponse, EliminarResponse } from '../../../models/response/reponses';
import { MonedaServiceService } from '../../../services/rest/moneda.service';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-monedas',
  templateUrl: './monedas.component.html',
  styleUrls: ['./monedas.component.css']
})
export class MonedasComponent implements OnInit {

  monedaForm: FormGroup;
  submitted = false;

  listTerritorios: PaisDTO[] = [];
  listMonedas: MonedaDto[] = [];

  constructor(private formBuilder: FormBuilder,
              private utilidades: UtilidadesService,
              private monedaService: MonedaServiceService,
              private territorioService: TerritorioService) { }

  ngOnInit(): void {
    this.cargarInformacion();
    this.monedaForm = this.formBuilder.group({
      nombre: new FormControl('', Validators.required),
      pais: new FormControl('')
    });
  }

  get f(): any {
    return this.monedaForm.controls;
  }

  crearMoneda(): void {
    this.crearActualizarMoneda(null);
  }

  limpiar(): void {
    this.submitted = false;
    this.monedaForm.reset({
      nombre: '',
      pais: ''
    });
  }

  editarFila(pMoneda: MonedaDto): void {
    if (pMoneda.nombreMoneda === '' || pMoneda.idTerritorio === ''){
      this.utilidades.abrirDialogo(this.utilidades.traducirTexto('general.completar_campos'), false);
    }else{
      this.crearActualizarMoneda(pMoneda);
    }
  }

  eliminarFila(pMoneda: MonedaDto): void {
    this.utilidades.mostrarDialogoConfirmacion(this.utilidades.traducirTexto('monedaPage.mensaje_eliminar')).then(
      result => {
        if (result.isConfirmed){
          this.utilidades.mostrarCargue();
          const eliminarRequest: EliminarRequest = {
            id: pMoneda.idMoneda,
            idioma: this.utilidades.obtenerIdioma()
          };
          this.monedaService.eliminarMonedas(this.utilidades.construirPeticion(eliminarRequest)).subscribe(
            data => {
              this.validarEliminacion(data);
            },
            () => {
              this.utilidades.ocultarCargue();
            }
          );
        }
      }
    );
  }

  private cargarInformacion(): void {
    this.utilidades.mostrarCargue();
    const territorioRequest: ConsultarTerritorioRequest = {
      idioma: this.utilidades.obtenerIdioma(),
      tipoTerritorio: Constantes.TIPO_TERRITORIO_PAIS
    };
    const monedasRequest: ConsultarMonedasRequest = {
      idioma: this.utilidades.obtenerIdioma()
    };

    const paises = this.territorioService.obtenerPaises(this.utilidades.construirPeticion(territorioRequest));
    const monedas = this.monedaService.obtenerMonedas(this.utilidades.construirPeticion(monedasRequest));

    forkJoin([paises, monedas]).subscribe(
      data => {
        this.validarRespuesta(data);
      },
      () => {
        this.utilidades.ocultarCargue();
      }
    );
  }

  private crearActualizarMoneda(pMoneda: MonedaDto): void{
    this.utilidades.mostrarCargue();
    const crearMonedaRequest: CrearMonedaRequest = {
      moneda : {
        idMoneda: !pMoneda ? '' : pMoneda.idMoneda,
        idTerritorio : !pMoneda ? this.monedaForm.get('pais').value : pMoneda.idTerritorio,
        nombreMoneda: !pMoneda ? this.monedaForm.get('nombre').value : pMoneda.nombreMoneda
      },
      idioma: this.utilidades.obtenerIdioma()
    };
    if (!pMoneda){
      this.monedaService.crearMonedas(this.utilidades.construirPeticion(crearMonedaRequest)).subscribe(
        data => {
          this.validarCreacionActualizacion(data);
        },
        () => {
          this.utilidades.ocultarCargue();
        }
      );
    }else{
      this.monedaService.actualizarMonedas(this.utilidades.construirPeticion(crearMonedaRequest)).subscribe(
        data => {
          this.validarCreacionActualizacion(data);
        },
        () => {
          this.utilidades.ocultarCargue();
        }
      );
    }
  }

  private cargarMonedas(): void {
    this.utilidades.mostrarCargue();
    const monedasRequest: ConsultarMonedasRequest = {
      idioma: this.utilidades.obtenerIdioma()
    };
    this.monedaService.obtenerMonedas(this.utilidades.construirPeticion(monedasRequest)).subscribe(
      data => {
        this.validarConsulta(data);
      },
      () => {
        this.utilidades.ocultarCargue();
      }
    );
  }

  private validarRespuesta(results: any): void {
    const territorioResponse: ConsultarTerritorioResponse = JSON.parse(results[0].mensaje);
    const monedasResponse: ConsultarMonedasResponse = JSON.parse(results[1].mensaje);
    if (territorioResponse.codigoRespuesta === Constantes.RESPUESTA_POSITIVA &&
      monedasResponse.codigoRespuesta === Constantes.RESPUESTA_POSITIVA){
      this.listTerritorios = territorioResponse.lisPaisesDto;
      this.listMonedas = monedasResponse.listMonedas;
    }else{
      this.utilidades.abrirDialogo(this.utilidades.traducirTexto('general.carga_mal'), true);
    }
    this.utilidades.ocultarCargue();
  }

  private validarCreacionActualizacion(pRespuesta: GeneralResponse): void {
    const crearResponse: CrearResponse = JSON.parse(pRespuesta.mensaje);
    if (crearResponse.codigoRespuesta === Constantes.RESPUESTA_POSITIVA){
      this.utilidades.abrirDialogoExitoso(this.utilidades.traducirTexto('general.proceso_ok'))
      .then( () => {
        this.limpiar();
        this.cargarMonedas();
      });
    }else{
      this.utilidades.abrirDialogo(crearResponse.mensajeRespuesta, false);
    }
    this.utilidades.ocultarCargue();
  }

  private validarConsulta(pRespuesta: GeneralResponse): void {
    const monedasResponse: ConsultarMonedasResponse = JSON.parse(pRespuesta.mensaje);
    if (monedasResponse.codigoRespuesta === Constantes.RESPUESTA_POSITIVA){
      this.listMonedas = monedasResponse.listMonedas;
    }else{
      this.utilidades.abrirDialogo(this.utilidades.traducirTexto('general.carga_mal'), true);
    }
    this.utilidades.ocultarCargue();
  }

  private validarEliminacion(pRespuesta: GeneralResponse): void {
    const eliminarResponse: EliminarResponse = JSON.parse(pRespuesta.mensaje);
    if (eliminarResponse.codigoRespuesta === Constantes.RESPUESTA_POSITIVA){
      this.utilidades.abrirDialogoExitoso(this.utilidades.traducirTexto('general.proceso_ok'))
      .then( () => {
        this.limpiar();
        this.cargarMonedas();
      });
    }else{
      this.utilidades.abrirDialogo(this.utilidades.traducirTexto('general.carga_mal'), true);
    }
    this.utilidades.ocultarCargue();
  }
}
