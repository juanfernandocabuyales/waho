import { Component, OnInit, HostListener, OnDestroy } from '@angular/core';
import { UtilidadesService } from '../../../services/utils/utilidades.service';
import { OpcionesDto } from '../../../models/general/general';
import { Subscription } from 'rxjs';
import { NavigationEnd, Router } from '@angular/router';

@Component({
  selector: 'app-opciones',
  templateUrl: './opciones.component.html',
  styleUrls: ['./opciones.component.css']
})
export class OpcionesComponent implements OnInit, OnDestroy {

  opciones: OpcionesDto[] = [];

  blnMostarOpciones = true;

  constructor(private utilidades: UtilidadesService, private router: Router) {
    router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        if (event.url === '/home/general') {
          this.blnMostarOpciones = true;
        } else {
          this.blnMostarOpciones = false;
        }
      }
    });
  }

  ngOnDestroy(): void {
    this.blnMostarOpciones = true;
  }

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
      ruta: '/home/general/territorios'
    });

    this.opciones.push({
      nombre: this.utilidades.traducirTexto('general.opciones.tipo'),
      imagen: '../../../../assets/ic_bandera.png',
      ruta: '/home/general/tipoTerritorios'
    });
  }

  abrirOpcion(pOpcion: OpcionesDto): void {
    this.blnMostarOpciones = !this.blnMostarOpciones;
    this.utilidades.navegarPagina(pOpcion.ruta, null);
  }

}
