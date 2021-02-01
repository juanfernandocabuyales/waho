import { Component, OnInit } from '@angular/core';
import { UtilidadesService } from '../../services/utilidades.service';

@Component({
  selector: 'app-servicios',
  templateUrl: './servicios.component.html',
  styleUrls: ['./servicios.component.css']
})
export class ServiciosComponent implements OnInit {

  titulos: string[] = [
    'idUsuario'
  ];

  data: any[] = [];

  constructor(private utilidades: UtilidadesService) { }

  ngOnInit(): void {
    this.data.push(this.utilidades.obtenerObjetoAlmacenado());
    console.log('date seteada ', this.data);
  }

}
