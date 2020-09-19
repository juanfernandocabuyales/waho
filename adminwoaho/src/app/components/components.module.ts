import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { FooterPrincipalComponent } from './footer-principal/footer-principal.component';
import { HeaderPrincipalComponent } from './header-principal/header-principal.component';
import { PopoverComponent } from './popover/popover.component';

@NgModule({
  declarations: [
    FooterPrincipalComponent,
    HeaderPrincipalComponent,
    PopoverComponent
  ],
  exports: [
    FooterPrincipalComponent,
    HeaderPrincipalComponent,
    PopoverComponent
  ],
  imports: [
    CommonModule,
    IonicModule,
    RouterModule,
    FormsModule
  ]
})
export class ComponentsModule { }
