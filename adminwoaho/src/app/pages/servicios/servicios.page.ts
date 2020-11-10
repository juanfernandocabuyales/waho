import { Component, OnInit } from '@angular/core';
import { Servicio } from '../../interfaces/interfaces';
import { ServiceService } from '../../services/service.service';
import { ActionSheetController, LoadingController } from '@ionic/angular';
import { UtilidadesService } from '../../services/utilidades.service';


@Component({
  selector: 'app-servicios',
  templateUrl: './servicios.page.html',
  styleUrls: ['./servicios.page.scss'],
})
export class ServiciosPage implements OnInit {

  listServicios: Servicio[];

  loading: any;

  constructor(private servicio: ServiceService,
    private utilidades: UtilidadesService,
    private actionSheetCtrl: ActionSheetController,
    private loadingController: LoadingController) { }

  ngOnInit() {
    this.cargarServicios();
  }

  cargarServicios() {
    this.mostrarProgress();
    this.servicio.consultarServicios().subscribe(
      (data) => {
        console.log('data bien', JSON.stringify(data.mensaje));
        let objeto = JSON.parse(data.mensaje);
        if (objeto.codigoRespuesta === '0') {
          this.listServicios = objeto.listServicios;
          this.ocultarProgress();
        } else {
          console.log(objeto.mensajeRespuesta);
          this.ocultarProgress();
        }
      },
      (fail) => {
        this.ocultarProgress();
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

  onClick() {
    console.log('presiono la tarjeta');
  }

  protected async mostrarProgress() {
    if (this.loading == undefined) {
      this.loading = this.loadingController.create({
        message: 'Realizando petición, por favor espera...',
      });
      this.loading.present();
    }
  }

  protected async ocultarProgress() {
    if (this.loading != undefined) {
      this.loading.dismiss();
      this.loading = undefined;
    }
  }
}
