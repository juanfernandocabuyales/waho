import { Injectable } from '@angular/core';
import { AlertController } from '@ionic/angular';

@Injectable({
  providedIn: 'root'
})
export class UtilidadesService {

  loading: any;

  constructor(public alertController: AlertController) { }

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
