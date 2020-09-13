import { Component, OnInit } from '@angular/core';
import { SessionService } from '../../services/session.service';
import { ServiceService } from '../../services/service.service';
import { Categoria} from '../../interfaces/interfaces';
import { UtilidadesService} from '../../services/utilidades.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss'],
})
export class HomePage implements OnInit {

  blnOpciones:boolean = true;

  listCategorias : Categoria[] = [];

  constructor(private sessionService: SessionService,
              private service:ServiceService,
              private utilidades: UtilidadesService) { }

  ngOnInit() {
    console.log('Objeto Servicio: ', this.sessionService.getObjeto());
    this.consultarCategorias();    
  }

  consultarCategorias(){
    this.service.consultarCategorias().subscribe(
      (data) =>{
        console.log('data bien', JSON.stringify(data.mensaje));
        let objeto = JSON.parse(data.mensaje);
        if (objeto.codigoRespuesta === '0') {
          this.listCategorias = objeto.listCategorias;
          console.log('Listado Consultado',this.listCategorias);         
        } else {
          console.log(objeto.mensajeRespuesta);
          this.utilidades.presentarAlerta('Informacion',objeto.mensajeRespuesta);         
        }
      },
      (fail) => {
        console.log('data mal', JSON.stringify(fail));
        this.utilidades.presentarAlerta('Informacion', 'Se ha prensentado un problema');
      }
    );
  }
}
