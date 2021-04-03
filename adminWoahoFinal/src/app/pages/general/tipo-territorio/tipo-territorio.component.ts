import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ConsultarTiposRequest } from 'src/app/models/request/requests';
import { TipoDto } from '../../../models/general/general';
import { TipoTerritorioService } from '../../../services/rest/tipo-territorio.service';
import { UtilidadesService } from '../../../services/utils/utilidades.service';
import { GeneralResponse, ConsultarTiposResponse, CrearResponse } from '../../../models/response/reponses';
import { Constantes } from 'src/app/constants/constantes';
import { CrearTipoRequest } from '../../../models/request/requests';

@Component({
  selector: 'app-tipo-territorio',
  templateUrl: './tipo-territorio.component.html',
  styleUrls: ['./tipo-territorio.component.css']
})
export class TipoTerritorioComponent implements OnInit {

  tipoForm: FormGroup;
  submitted = false;
  listTipos: TipoDto[] = [];

  constructor(private formBuilder: FormBuilder,
              private tipoService: TipoTerritorioService,
              private utilidades: UtilidadesService) { }

  ngOnInit(): void {
    this.tipoForm = this.formBuilder.group({
      nombre: new FormControl('', Validators.required)
    });
    this.utilidades.mostrarCargue();
    this.cargarTipos();
  }

  cargarTipos(): void {
    const consultarRequest: ConsultarTiposRequest = {
      idioma: this.utilidades.obtenerIdioma()
    };

    this.tipoService.obtenerTipos(this.utilidades.construirPeticion(consultarRequest)).subscribe(
      data => {
        this.validarRespuestaConsulta(data);
      },
      () => {
        this.utilidades.ocultarCargue();
      }
    );
  }

  crearTipo(): void {
    this.submitted = true;

    if (this.tipoForm.invalid){
      this.utilidades.abrirDialogo(this.utilidades.traducirTexto('general.completar_campos'), false);
    }else {
      this.utilidades.mostrarCargue();
      const crearTipo: CrearTipoRequest = {
        tipoDto: {
          id: '',
          nombre: this.tipoForm.get('nombre').value
        },
        idioma: this.utilidades.obtenerIdioma()
      };
      this.tipoService.crearTipos(this.utilidades.construirPeticion(crearTipo)).subscribe(
        data => {
          this.validarRespuestaCreacion(data, crearTipo.tipoDto);
        },
        () => {
          this.utilidades.ocultarCargue();
        }
      );
    }
  }

  eliminarFila(i: number): void {
  }

  get f(): any {
    return this.tipoForm.controls;
  }

  limpiarCampos(): void {
    this.submitted = false;
    this.tipoForm.reset({
      nombre: ''
    });
  }

  validarRespuestaConsulta(respuesta: GeneralResponse): void {
    const consultarTiposResponse: ConsultarTiposResponse = JSON.parse(respuesta.mensaje);
    if (consultarTiposResponse.codigoRespuesta === Constantes.RESPUESTA_POSITIVA){
      this.listTipos = consultarTiposResponse.listTipos;
    }else{
      this.utilidades.abrirDialogo(consultarTiposResponse.mensajeRespuesta, true);
    }
    this.utilidades.ocultarCargue();
  }

  validarRespuestaCreacion(respuesta: GeneralResponse, pTipo: TipoDto): void {
    const crearTiposResponse: CrearResponse = JSON.parse(respuesta.mensaje);
    this.utilidades.ocultarCargue();
    if (crearTiposResponse.codigoRespuesta === Constantes.RESPUESTA_POSITIVA){
      this.limpiarCampos();
      this.utilidades.abrirDialogoExitoso(this.utilidades.traducirTexto('general.operacion_ok'))
      .then( () => {
        this.cargarTipos();
      });
    }else{
      this.utilidades.abrirDialogo(crearTiposResponse.mensajeRespuesta, true);
    }
  }

}
