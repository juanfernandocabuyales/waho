import { Component, OnInit } from '@angular/core';
import { UtilidadesService } from '../../services/utilidades.service';
import { ServicioService } from '../../services/servicio.service';
import { ConsultarServiciosRequest } from '../../models/request/ConsultarServiciosRequest';
import { GeneralResponse } from '../../models/response/GeneralResponse';
import { ConsultarServiciosResponse, Servicio } from '../../models/response/ConsultarServiciosResponse';
import { Constantes } from 'src/app/constants/constantes';
import { NgxSpinnerService } from 'ngx-spinner';

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

  constructor(private utilidades: UtilidadesService, private servicio: ServicioService, private spinner: NgxSpinnerService)
  {}

  ngOnInit(): void {
    this.spinner.show();
    this.cargarServicios();
  }

  cargarServicios(): void {
    const request: ConsultarServiciosRequest = {
      idioma: this.utilidades.obtenerIdioma()
    };
    console.log('peticion servicio: ' , request);
    this.servicio.consultarServicios(this.utilidades.construirPeticion(request))
      .subscribe(
        data => {
          this.validarRespuesta(data);
        }, error => {
          this.spinner.hide();
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
    this.spinner.hide();
  }

}
