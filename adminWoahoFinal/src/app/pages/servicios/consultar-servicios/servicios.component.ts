import { Component, OnInit } from '@angular/core';
import { UtilidadesService } from '../../../services/utils/utilidades.service';
import { ServicioService } from '../../../services/rest/servicio.service';
import { ConsultarServiciosRequest } from '../../../models/request/requests';
import { GeneralResponse } from '../../../models/response/reponses';
import { ConsultarServiciosResponse } from '../../../models/response/reponses';
import { Constantes } from 'src/app/constants/constantes';
import { ServicioDto } from '../../../models/general/general';

@Component({
  selector: 'app-servicios',
  templateUrl: './servicios.component.html',
  styleUrls: ['./servicios.component.css']
})
export class ServiciosComponent implements OnInit {

  servicios: ServicioDto[] = [];

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
    this.utilidades.cambiarValorAlmacenado(servicioAux);
    this.utilidades.mostarDialogoOpciones(servicioAux.nombre, this.utilidades.traducirTexto('serviciosPage.operaciones_servicio'),
                                          [this.utilidades.traducirTexto('serviciosPage.editar'),
                                          this.utilidades.traducirTexto('serviciosPage.eliminar'),
                                          this.utilidades.traducirTexto('serviciosPage.cerrar')])
      .then((result) => {
        if (result.isConfirmed) {
          this.utilidades.navegarPagina('/home/servicios/crear-servicios', servicioAux);
        } else if (result.isDenied) {
          console.log('Fue eliminado: ' + servicioAux.nombre);
        }
      });
  }

  navegarCrearServicio(): void{
    this.utilidades.navegarPagina('/home/servicios/crear-servicios', null);
  }

}
