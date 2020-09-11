import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HomePage } from './home.page';

const routes: Routes = [
  {
    path:'',
    redirectTo : 'servicios'
  },
  {
    path: '',
    component: HomePage,
    children : [
      {
        path : 'servicios',
        loadChildren: () => import('../servicios/servicios.module').then(m => m.ServiciosPageModule)
      },
      {
        path : 'categorias',
        loadChildren: () => import('../categorias/categorias.module').then(m => m.CategoriasPageModule)
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class HomePageRoutingModule {}
