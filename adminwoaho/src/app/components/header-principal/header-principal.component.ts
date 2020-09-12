import { Component, Input, OnInit } from '@angular/core';
import { NavController, PopoverController } from '@ionic/angular';
import { PopoverComponent } from '../popover/popover.component';

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

  items = ['Opcion uno','Opcion dos','Opcion tres']

  constructor(private navCtrl: NavController, private popCtrl: PopoverController) { }

  ngOnInit() { }


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
  }

}
