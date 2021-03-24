import { Component, OnInit } from '@angular/core';
import { UtilidadesService } from '../../../services/utilidades.service';
import { ServicioService } from '../../../services/servicio.service';
import { ConsultarServiciosRequest } from '../../../models/request/ConsultarServiciosRequest';
import { GeneralResponse } from '../../../models/response/GeneralResponse';
import { ConsultarServiciosResponse, Servicio } from '../../../models/response/ConsultarServiciosResponse';
import { Constantes } from 'src/app/constants/constantes';

@Component({
  selector: 'app-servicios',
  templateUrl: './servicios.component.html',
  styleUrls: ['./servicios.component.css']
})
export class ServiciosComponent implements OnInit {

  titulos: string[] = [
    'codigo',
    'name',
    'description'
  ];

  servicios: Servicio[] = [];

  constructor(private utilidades: UtilidadesService,
              private servicio: ServicioService) { }

  ngOnInit(): void {
    this.utilidades.mostrarCargue();
    this.cargarServicios();
  }

  cargarServicios(): void {
    const request: ConsultarServiciosRequest = {
      idioma: this.utilidades.obtenerIdioma()
    };
    console.log('peticion servicio: ', request);
    this.servicio.consultarServicios(this.utilidades.construirPeticion(request))
      .subscribe(
        data => {
          this.validarRespuesta(data);
        }, error => {
          this.utilidades.ocultarCargue();
          console.log('error', error);
          this.utilidades.abrirDialogo('', false);
        }
      );
  }

  validarRespuesta(pRespuesta: GeneralResponse): void {
    const consultarServiciosResponse: ConsultarServiciosResponse = JSON.parse(pRespuesta.mensaje);
    if (consultarServiciosResponse.codigoRespuesta === Constantes.RESPUESTA_POSITIVA) {
      this.servicios = consultarServiciosResponse.listServicios;
    } else {
      this.utilidades.abrirDialogo(consultarServiciosResponse.mensajeRespuesta, true);
    }
    this.utilidades.ocultarCargue();
  }

  mostarOpciones(pCodigo: string): void {
    const servicioAux = this.servicios.find(servicio => servicio.codigo === pCodigo);
    this.utilidades.mostarDialogoOpciones(servicioAux.name, this.utilidades.traducirTexto('serviciosPage.operaciones_servicio'),
                                          [this.utilidades.traducirTexto('serviciosPage.editar'),
                                          this.utilidades.traducirTexto('serviciosPage.eliminar'),
                                          this.utilidades.traducirTexto('serviciosPage.cerrar')])
      .then((result) => {
        if (result.isConfirmed) {
          console.log('Fue editado: ' + servicioAux.name);
        } else if (result.isDenied) {
          console.log('Fue eliminado: ' + servicioAux.name);
        }
      });
  }

  navegarCrearServicio(): void{
    this.utilidades.navegarPagina('/home/servicios/crear-servicios', null);
  }

}
