import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ModalServiciosPage } from './modal-servicios.page';

const routes: Routes = [
  {
    path: '',
    component: ModalServiciosPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ModalServiciosPageRoutingModule {}
