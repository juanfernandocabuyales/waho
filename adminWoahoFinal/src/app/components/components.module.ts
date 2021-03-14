import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TableComponent } from './table/table.component';
import { LoadingComponent } from './loading/loading.component';

import { NgxSpinnerModule } from 'ngx-spinner';

import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';

@NgModule({
  declarations: [
    TableComponent,
    LoadingComponent
  ],
  imports: [
    CommonModule,
    NgxSpinnerModule,
    MatTableModule,
    MatPaginatorModule
  ],
  exports: [
    TableComponent,
    LoadingComponent
  ]
})
export class ComponentsModule { }
