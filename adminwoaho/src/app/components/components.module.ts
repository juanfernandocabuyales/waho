import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import {FooterPrincipalComponent} from './footer-principal/footer-principal.component';

@NgModule({
  declarations: [
    FooterPrincipalComponent
  ],
  exports: [
    FooterPrincipalComponent
  ],
  imports: [
    CommonModule,
    IonicModule,
    RouterModule,
    FormsModule
  ]
})
export class ComponentsModule { }
