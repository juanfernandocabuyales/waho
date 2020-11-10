import { Component, Input, OnInit } from '@angular/core';
import { NavController, PopoverController, ModalController } from '@ionic/angular';
import { PopoverComponent } from '../popover/popover.component';
import { Categoria } from '../../interfaces/interfaces';
import { ModalServiciosPage } from '../../pages/modals/modal-servicios/modal-servicios.page'

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
  listCategorias: Categoria[];

  items = ['Opcion uno', 'Opcion dos', 'Opcion tres']

  constructor(private navCtrl: NavController,
    private popCtrl: PopoverController,
    private alerCtrl: ModalController) { }

  ngOnInit() {

  }


  gotoBack() {
    this.navCtrl.pop();
  }

  async mostrarOpciones(event) {
    const popOver = await this.popCtrl.create({
      component: PopoverComponent,
      event: event,
      mode: 'ios',

    })
    await popOver.present();

    const { data } = await popOver.onWillDismiss();
    this.validarOpcion(data);
  }

  validarOpcion(data) {
    if (this.titulo === 'Home') {
      console.log('validarOpcion (data) ', data);
      this.validarOpcionesHome(data.item);
    }
  }

  protected validarOpcionesHome(pOpcion: string) {
    if (pOpcion === 'Filtrar') {
      this.dialogoFiltroServicio();
    }
  }

  async dialogoFiltroServicio() {
    const modal = await this.alerCtrl.create({
      component: ModalServiciosPage,
      cssClass:'fondo-modal'
    });
    /*const modal = await this.alerCtrl.create({
      component: ModalServiciosPage,
      cssClass: 'my-custom-modal-css'
    });*/

    await modal.present();
  }

  /*protected createInputs() {
    const theNewInputs = [];
    for (let i = 0; i < this.listCategorias.length; i++) {
      theNewInputs.push(
        {
          name: 'checkbox' + (i + 1),
          type: 'checkbox',
          label: this.listCategorias[i].name,
          value: this.listCategorias[i].id,
          checked: false,
          handler: (data: any) => {
            console.log('data Elemento', data);
            for (let j = 0; j < theNewInputs.length; j++) {
              if (data.value !== theNewInputs[j].value) {
                console.log('ejecuto for');
                theNewInputs[j].checked = false;
              }
            }
          }
        }
      );
    }
    return theNewInputs;
  }*/

}
