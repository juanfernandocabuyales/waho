import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ModalServiciosPageRoutingModule } from './modal-servicios-routing.module';

import { ModalServiciosPage } from './modal-servicios.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ModalServiciosPageRoutingModule
  ],
  declarations: [ModalServiciosPage]
})
export class ModalServiciosPageModule {}
