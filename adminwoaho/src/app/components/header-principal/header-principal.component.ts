import { Component, Input, OnInit } from '@angular/core';
import { NavController, PopoverController,AlertController} from '@ionic/angular';
import { PopoverComponent } from '../popover/popover.component';
import { Categoria } from '../../interfaces/interfaces';

@Component({
  selector: 'app-header-principal',
  templateUrl: './header-principal.component.html',
  styleUrls: ['./header-principal.component.scss'],
})
export class HeaderPrincipalComponent implements OnInit {

  @Input('titulo')
  titulo: string;

  @Input('blnOpciones')
  blnOpciones: boolean;

  @Input('listCategorias')
  listCategorias : Categoria [];

  items = ['Opcion uno','Opcion dos','Opcion tres']

  constructor(private navCtrl: NavController,
              private popCtrl: PopoverController,
              private alerCtrl: AlertController) { }

  ngOnInit() {
    
   }


  gotoBack() {
    this.navCtrl.pop();
  }

  async mostrarOpciones( event ) {
    const popOver = await this.popCtrl.create({
      component : PopoverComponent,
      event : event,
      mode : 'ios',
      
    })
    await popOver.present();

    const { data } = await popOver.onWillDismiss();
    this.validarOpcion(data);
  }

  validarOpcion(data){
    if(this.titulo === 'Home'){
      console.log('validarOpcion (data) ',data);
      this.validarOpcionesHome(data.item);
    }
  }

  protected validarOpcionesHome(pOpcion:string){
    if(pOpcion === 'Filtrar'){
      this.dialogoFiltroServicio();
    }
  }

  async dialogoFiltroServicio(){
    const alert = await this.alerCtrl.create({  
      header: 'Seleccione una categoria',
      mode:'ios',
      inputs: this.createInputs(),
      buttons: [
        {
          text: 'Cancel',
          role: 'cancel',
          cssClass: 'secondary',
          handler: () => {
            console.log('Confirm Cancel');
          }
        }, {
          text: 'Aceptar',
          handler: () => {
            console.log('Confirm Ok');
          }
        }
      ]
    });
    
    await alert.present();
  }

  protected createInputs() {
    const theNewInputs = [];
    for (let i = 0; i < this.listCategorias.length; i++) {
      theNewInputs.push(
        {
          type: 'checkbox',
          label: this.listCategorias[i].name,
          value: this.listCategorias[i].id,
          checked: false
        }
      );
    }
    return theNewInputs;
  }

}
