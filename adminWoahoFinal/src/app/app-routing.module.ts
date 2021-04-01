import { NgModule, Component } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { HomeComponent } from './pages/home/home.component';
import { ServiciosComponent } from './pages/servicios/consultar-servicios/servicios.component';
import { CrearEditarServicioComponent } from './pages/servicios/crear-editar-servicio/crear-editar-servicio.component';
import { OpcionesComponent } from './pages/general/opciones/opciones.component';
import { ImagenesComponent } from './pages/general/imagenes/imagenes.component';

const routes: Routes = [
  {
    path: '',
    component: LoginComponent
  },
  {
    path: 'home',
    component: HomeComponent,
    children: [
      {
        path: 'servicios',
        component: ServiciosComponent,
      },
      {
        path: 'servicios/crear-servicios',
        component: CrearEditarServicioComponent
      },
      {
        path: 'opciones',
        component: OpcionesComponent,
        children: [
          {
            path: 'imagenes',
            component: ImagenesComponent
          }
        ]
      }
    ],
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
