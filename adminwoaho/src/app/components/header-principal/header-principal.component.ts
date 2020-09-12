import { Component, Input, OnInit } from '@angular/core';
import { NavController } from '@ionic/angular';

@Component({
  selector: 'app-header-principal',
  templateUrl: './header-principal.component.html',
  styleUrls: ['./header-principal.component.scss'],
})
export class HeaderPrincipalComponent implements OnInit {

  @Input('titulo')
  titulo:string;

  @Input('blnOpciones')
  blnOpciones:boolean;

  constructor(private navCtrl: NavController) { }

  ngOnInit() {}


  gotoBack() {
    this.navCtrl.pop();
  }

}
