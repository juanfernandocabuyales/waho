import { Component, OnInit, HostListener } from '@angular/core';
import { UtilidadesService } from '../../../services/utils/utilidades.service';
import { OpcionesDto } from '../../../models/general/general';

@Component({
  selector: 'app-opciones',
  templateUrl: './opciones.component.html',
  styleUrls: ['./opciones.component.css']
})
export class OpcionesComponent implements OnInit {

  opciones: OpcionesDto[] = [];

  blnMostarOpciones = true;

  constructor(private utilidades: UtilidadesService) { }

  ngOnInit(): void {
    this.cargarOpciones();
  }

  @HostListener('window:popstate')
  onPopState(): void {
    this.blnMostarOpciones = true;
  }

  cargarOpciones(): void {
    this.opciones.push({
      nombre: this.utilidades.traducirTexto('general.opciones.imagenes'),
      imagen: '../../../../assets/ic_imagen.png',
      ruta: '/home/general/imagenes'
    });

    this.opciones.push({
      nombre: this.utilidades.traducirTexto('general.opciones.monedas'),
      imagen: '../../../../assets/ic_monedas.png',
      ruta: '/home/general/monedas'
    });

    this.opciones.push({
      nombre: this.utilidades.traducirTexto('general.opciones.unidad'),
      imagen: '../../../../assets/ic_medida.png',
      ruta: '/home/general/unidad'
    });

    this.opciones.push({
      nombre: this.utilidades.traducirTexto('general.opciones.pais'),
      imagen: '../../../../assets/ic_bandera.png',
      ruta: '/home/general/pais'
    });
  }

  abrirOpcion(pOpcion: OpcionesDto): void {
    console.log('esta abriendo');
    this.blnMostarOpciones = false;
    this.utilidades.navegarPagina(pOpcion.ruta, null);
  }

}
