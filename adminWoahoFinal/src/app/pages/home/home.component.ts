import { Component, OnInit } from '@angular/core';
import { UtilidadesService } from '../../services/utils/utilidades.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private utilidades: UtilidadesService) {

  }

  ngOnInit(): void {
    console.log('home objetoAlmacenado', this.utilidades.obtenerObjetoAlmacenado());
  }

  salir(): void{
    this.utilidades.mostrarDialogoConfirmacion(this.utilidades.traducirTexto('general.salir')).then(
      result => {
        if (result.isConfirmed){
          this.utilidades.navegarPagina('', null);
        }
      }
    );
  }

}
