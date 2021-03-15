import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TableComponent } from './table/table.component';
import { LoadingComponent } from './loading/loading.component';

import { NgxSpinnerModule } from 'ngx-spinner';

import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';

import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';

@NgModule({
  declarations: [
    TableComponent,
    LoadingComponent
  ],
  imports: [
    CommonModule,
    NgxSpinnerModule,
    MatTableModule,
    MatPaginatorModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: (http: HttpClient) => {
          return new TranslateHttpLoader(http, './assets/i18n/', '.json');
        },
        deps: [ HttpClient ]
      }
    })
  ],
  exports: [
    TableComponent,
    LoadingComponent
  ]
})
export class ComponentsModule { }
