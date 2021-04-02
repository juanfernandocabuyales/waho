import { NgModule, Component } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { HomeComponent } from './pages/home/home.component';
import { ServiciosComponent } from './pages/servicios/consultar-servicios/servicios.component';
import { CrearEditarServicioComponent } from './pages/servicios/crear-editar-servicio/crear-editar-servicio.component';
import { OpcionesComponent } from './pages/general/opciones/opciones.component';
import { ImagenesComponent } from './pages/general/imagenes/imagenes.component';
import { TerritoriosComponent } from './pages/general/territorios/territorios.component';
import { UnidadesComponent } from './pages/general/unidades/unidades.component';
import { TipoTerritorioComponent } from './pages/general/tipo-territorio/tipo-territorio.component';

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
        path: 'general',
        component: OpcionesComponent,
        children: [
          {
            path: 'imagenes',
            component: ImagenesComponent
          },
          {
            path: 'territorios',
            component: TerritoriosComponent
          },
          {
            path: 'tipoTerritorios',
            component: TipoTerritorioComponent
          },
          {
            path: 'unidades',
            component: UnidadesComponent
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
