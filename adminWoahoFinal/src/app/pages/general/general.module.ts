import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OpcionesComponent } from './opciones/opciones.component';
import { ImagenesComponent } from './imagenes/imagenes.component';
import { TerritoriosComponent } from './territorios/territorios.component';

import { NgxSpinnerModule } from 'ngx-spinner';
import { AppRoutingModule } from '../../app-routing.module';

import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';

import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';
import { UnidadesComponent } from './unidades/unidades.component';
import { ComponentsModule } from 'src/app/components/components.module';
import { NgDropFilesDirective } from 'src/app/directives/ng-drop-files.directive';
import { ReactiveFormsModule } from '@angular/forms';
import { TipoTerritorioComponent } from './tipo-territorio/tipo-territorio.component';
import { FormsModule } from '@angular/forms';
import { MonedasComponent } from './monedas/monedas.component';

@NgModule({
  declarations: [
    OpcionesComponent,
    ImagenesComponent,
    TerritoriosComponent,
    UnidadesComponent,
    NgDropFilesDirective,
    TipoTerritorioComponent,
    MonedasComponent
  ],
  imports: [
    CommonModule,
    NgxSpinnerModule,
    MatTableModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    MatPaginatorModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: (http: HttpClient) => {
          return new TranslateHttpLoader(http, './assets/i18n/', '.json');
        },
        deps: [ HttpClient ]
      }
    }),
    ComponentsModule
  ],
  exports: [
    OpcionesComponent,
    ImagenesComponent,
    TerritoriosComponent,
    UnidadesComponent,
    NgDropFilesDirective
  ]
})
export class GeneralModule { }
