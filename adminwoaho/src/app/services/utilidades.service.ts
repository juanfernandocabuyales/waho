import { Injectable } from '@angular/core';
import { LoadingController, AlertController } from '@ionic/angular';

@Injectable({
  providedIn: 'root'
})
export class UtilidadesService {

  loading: any;

  constructor(private loadingController: LoadingController,
    public alertController: AlertController) { }

  async presentLoading() {

    if (this.loading) {
      this.loading = null;
    }

    this.loading = await this.loadingController.create({
      message: 'Por favor espere ...'
    });
    return await this.loading.present();
  }

  async dismissDialog() {
    return await this.loading.dismiss();
  }

  async presentarAlerta(pTitulo: string, pMensaje: string) {
    const alert = await this.alertController.create({
      header: pTitulo,
      message: pMensaje,
      buttons: [
        {
          text: 'Aceptar',
          handler: () => {
           alert.dismiss();
          }
        }
      ],
      backdropDismiss: false
    });
    await alert.present();
  }
}
