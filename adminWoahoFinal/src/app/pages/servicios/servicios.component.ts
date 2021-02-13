import { Component, OnInit } from '@angular/core';
import { UtilidadesService } from '../../services/utilidades.service';
import { ServicioService } from '../../services/servicio.service';
import { ConsultarServiciosRequest } from '../../interface/request';
import { PeticionResponse } from 'src/app/interface/response';
import { ConsultarServiciosResponse } from '../../interface/response';
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

  data: any[] = [];

  constructor(private utilidades: UtilidadesService, private servicio: ServicioService) { }

  ngOnInit(): void {
    this.cargarServicios();
  }

  cargarServicios(): void {
    const request: ConsultarServiciosRequest = {
      idioma: this.utilidades.obtenerIdioma()
    };
    this.servicio.consultarServicios(this.utilidades.construirPeticion(request))
      .subscribe(
        data => {
          this.validarRespuesta(data);
        }, error => {
          console.log('error', error);
          this.utilidades.abrirDialogo('', false);
        }
      );
  }

  validarRespuesta(pRespuesta: PeticionResponse): void {
    const consultarServiciosResponse: ConsultarServiciosResponse = JSON.parse(pRespuesta.mensaje);
    if (consultarServiciosResponse.codigoRespuesta === Constantes.RESPUESTA_POSITIVA) {
      this.data = consultarServiciosResponse.listServicios;
    } else {
      this.utilidades.abrirDialogo(consultarServiciosResponse.mensajeRespuesta, true);
    }
  }

}
