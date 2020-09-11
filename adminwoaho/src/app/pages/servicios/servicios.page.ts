import { Component, OnInit } from '@angular/core';
import { Servicio } from '../../interfaces/interfaces';
import { ServiceService } from '../../services/service.service';
import { UtilidadesService } from '../../services/utilidades.service';
import { ActionSheetController } from '@ionic/angular';

@Component({
  selector: 'app-servicios',
  templateUrl: './servicios.page.html',
  styleUrls: ['./servicios.page.scss'],
})
export class ServiciosPage implements OnInit {

  listServicios: Servicio[];

  constructor(private servicio: ServiceService,
    private actionSheetCtrl: ActionSheetController,
    private utilidades: UtilidadesService) { }

  ngOnInit() {
    this.cargarServicios();
  }

  cargarServicios() {
    this.utilidades.presentLoading();
    this.servicio.consultarServicios().subscribe(
      (data) => {
        console.log('data bien', JSON.stringify(data.mensaje));
        let objeto = JSON.parse(data.mensaje);
        if (objeto.codigoRespuesta === '0') {
          this.listServicios = objeto.listServicios;
          this.utilidades.dismissDialog();
        } else {
          console.log(objeto.mensajeRespuesta);
          this.utilidades.dismissDialog();
        }
      },
      (fail) => {
        this.utilidades.dismissDialog();
        console.log('data mal', JSON.stringify(fail));
        this.utilidades.presentarAlerta('Informacion', 'Se ha prensentado un problema');
      }
    );
  }

  async mostrarOpciones() {
    const actionSheet = await this.actionSheetCtrl.create({
      buttons: [{
        text: 'Editar',
        icon: 'build-outline',
        handler: () => {
          console.log('Share clicked');
        }
      }, {
        text: 'Eliminar',
        icon: 'trash-outline',
        handler: () => {
          console.log('Favorito');
        }
      }
      ]
    });
    await actionSheet.present();
  }

}
