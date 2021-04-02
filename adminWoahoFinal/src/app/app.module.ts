import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './pages/login/login.component';
import { HomeComponent } from './pages/home/home.component';
import { ComponentsModule } from './components/components.module';

import { ReactiveFormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { HttpClient, HttpClientModule } from '@angular/common/http';

// Translation
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { ServiciosComponent } from './pages/servicios/consultar-servicios/servicios.component';
import { CrearEditarServicioComponent } from './pages/servicios/crear-editar-servicio/crear-editar-servicio.component';

import { FormsModule } from '@angular/forms';
import { OpcionesComponent } from './pages/general/opciones/opciones.component';
import { ImagenesComponent } from './pages/general/imagenes/imagenes.component';
import { NgDropFilesDirective } from './directives/ng-drop-files.directive';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    ServiciosComponent,
    CrearEditarServicioComponent,
    OpcionesComponent,
    ImagenesComponent,
    NgDropFilesDirective
  ],
  imports: [
    ReactiveFormsModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatIconModule,
    HttpClientModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: (http: HttpClient) => {
          return new TranslateHttpLoader(http, './assets/i18n/', '.json');
        },
        deps: [ HttpClient ]
      }
    }),
    ComponentsModule,
    FormsModule
  ],
  exports: [
    MatIconModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
