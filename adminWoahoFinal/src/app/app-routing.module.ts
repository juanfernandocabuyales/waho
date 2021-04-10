import { NgModule} from '@angular/core';
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
import { MonedasComponent } from './pages/general/monedas/monedas.component';
import { ConsultarUsuariosComponent } from './pages/usuarios/consultar-usuarios/consultar-usuarios.component';
import { CrearEditarUsuariosComponent } from './pages/usuarios/crear-editar-usuarios/crear-editar-usuarios.component';

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
        path: 'usuarios',
        component: ConsultarUsuariosComponent
      },
      {
        path: 'usuarios/crear-usuarios',
        component: CrearEditarUsuariosComponent
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
          },
          {
            path: 'monedas',
            component: MonedasComponent
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
