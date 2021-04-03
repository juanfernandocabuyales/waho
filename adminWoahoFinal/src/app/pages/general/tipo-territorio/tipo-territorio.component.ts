import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ConsultarTiposRequest, EliminarRequest } from 'src/app/models/request/requests';
import { TipoDto } from '../../../models/general/general';
import { TipoTerritorioService } from '../../../services/rest/tipo-territorio.service';
import { UtilidadesService } from '../../../services/utils/utilidades.service';
import { GeneralResponse, ConsultarTiposResponse, CrearResponse, EliminarResponse } from '../../../models/response/reponses';
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

    if (this.tipoForm.invalid) {
      this.utilidades.abrirDialogo(this.utilidades.traducirTexto('general.completar_campos'), false);
    } else {
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

  eliminarFila(i: number, pTipo: TipoDto): void {
    this.utilidades.mostrarDialogoConfirmacion(this.utilidades.traducirTexto('tipoPage.mensaje_eliminar')).then(
      result => {
        if (result.isConfirmed) {
          this.utilidades.mostrarCargue();

          const eliminarRequest: EliminarRequest = {
            id: pTipo.id,
            idioma: this.utilidades.obtenerIdioma()
          };

          this.tipoService.eliminarTipos(this.utilidades.construirPeticion(eliminarRequest)).subscribe(
            data => {
              this.validarEliminacion(data);
              this.listTipos.splice(i, 1);
            },
            () => {
              this.utilidades.ocultarCargue();
            }
          );
        }
      }
    );
  }

  editarFila(pTipo: TipoDto): void {
    this.utilidades.mostarDialogoInput(this.utilidades.traducirTexto('tipoPage.nombre'),
      this.utilidades.traducirTexto('tipoPage.ingrese_nombre'), pTipo.nombre)
      .then(result => {
        this.utilidades.mostrarCargue();

        const crearTipo: CrearTipoRequest = {
          tipoDto: {
            id: pTipo.id,
            nombre: result.value
          },
          idioma: this.utilidades.obtenerIdioma()
        };

        this.tipoService.actualizarTipos(this.utilidades.construirPeticion(crearTipo)).subscribe(
          data => {
            this.validarActualizacion(data);
            pTipo.nombre = crearTipo.tipoDto.nombre;
          },
          () => {
            this.utilidades.ocultarCargue();
          }
        );


      });
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
    if (consultarTiposResponse.codigoRespuesta === Constantes.RESPUESTA_POSITIVA) {
      this.listTipos = consultarTiposResponse.listTipos;
    } else {
      this.utilidades.abrirDialogo(consultarTiposResponse.mensajeRespuesta, true);
    }
    this.utilidades.ocultarCargue();
  }

  validarRespuestaCreacion(respuesta: GeneralResponse, pTipo: TipoDto): void {
    const crearTiposResponse: CrearResponse = JSON.parse(respuesta.mensaje);
    this.utilidades.ocultarCargue();
    if (crearTiposResponse.codigoRespuesta === Constantes.RESPUESTA_POSITIVA) {
      this.limpiarCampos();
      this.utilidades.abrirDialogoExitoso(this.utilidades.traducirTexto('general.operacion_ok'))
        .then(() => {
          this.cargarTipos();
        });
    } else {
      this.utilidades.abrirDialogo(crearTiposResponse.mensajeRespuesta, true);
    }
  }

  validarEliminacion(data: GeneralResponse): void {
    const response: EliminarResponse = JSON.parse(data.mensaje);
    if (response.codigoRespuesta === Constantes.RESPUESTA_POSITIVA) {
      this.utilidades.abrirDialogoExitoso(this.utilidades.traducirTexto('general.proceso_ok'));
    } else {
      this.utilidades.abrirDialogo(response.mensajeRespuesta, true);
    }
    this.utilidades.ocultarCargue();
  }

  validarActualizacion(data: GeneralResponse): void {
    const response: CrearResponse = JSON.parse(data.mensaje);
    if (response.codigoRespuesta === Constantes.RESPUESTA_POSITIVA) {
      this.utilidades.abrirDialogoExitoso(this.utilidades.traducirTexto('general.proceso_ok'));
    } else {
      this.utilidades.abrirDialogo(response.mensajeRespuesta, true);
    }
    this.utilidades.ocultarCargue();
  }

}
