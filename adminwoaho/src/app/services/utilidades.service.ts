import { Injectable } from '@angular/core';
import { LoadingController } from '@ionic/angular';

@Injectable({
  providedIn: 'root'
})
export class UtilidadesService {

  loading:any;

  constructor(private loadingController: LoadingController) { }

  async presentLoading() {
    
    if(this.loading){
      this.loading = null;
    }

    this.loading = await this.loadingController.create({
      message: 'Por favor espere ...'
    });
    return await this.loading.present();
  }

  async dismissDialog(){
    return await this.loading.dismiss();
  }
}
