import { Injectable } from '@angular/core';
import { GeneralRequest } from '../../models/request/requests';
import { Router } from '@angular/router';

import { TranslateService } from '@ngx-translate/core';
import Swal, { SweetAlertResult } from 'sweetalert2';
import { NgxSpinnerService } from 'ngx-spinner';


@Injectable({
  providedIn: 'root'
})
export class UtilidadesService {

  objetoAlmacenar: any;

  constructor(private traslation: TranslateService,
              private router: Router,
              private spinner: NgxSpinnerService) {

  }

  abrirDialogo(pMensaje: string, pBandera: boolean): void {
    Swal.fire({
      icon: pBandera ? 'info' : 'warning',
      title: pBandera ? this.traducirTexto('general.informacion') : this.traducirTexto('general.advertencia'),
      text: pMensaje,
      allowOutsideClick: false,
      width: '350px',
    });
  }

  construirPeticion(pObjeto: any): GeneralRequest {
    const peticion: GeneralRequest = {
      strMensaje: JSON.stringify(pObjeto)
    };
    return peticion;
  }

  obtenerIdioma(): string {
    const idioma = this.traslation.getLangs()[0];
    return idioma ? idioma : 'es';
  }

  traducirTexto(pTexto: string): string {
    return this.traslation.instant(pTexto);
  }

  navegarPagina(pPagina: string, pObjeto: any): void {
    if (pObjeto){
      this.objetoAlmacenar = pObjeto;
    }
    this.router.navigate([pPagina]);
  }

  obtenerObjetoAlmacenado(): any {
    return this.objetoAlmacenar;
  }

  cambiarValorAlmacenado(pObjeto: any): void {
    this.objetoAlmacenar = pObjeto;
  }

  mostrarCargue(): void {
    this.spinner.show();
  }

  ocultarCargue(): void {
    this.spinner.hide();
  }

  mostarDialogoOpciones(pTitulo: string, pTexto: string, pOpciones: string[]): Promise<SweetAlertResult<any>>{
    return Swal.fire({
      title: pTitulo,
      text: pTexto,
      showDenyButton: true,
      showCancelButton: true,
      confirmButtonColor: 'rgba(249, 25, 84, 1)',
      confirmButtonText: `<i class="fa fa-pencil"></i> ${pOpciones[0]}`,
      denyButtonColor: 'rgba(250, 0, 0, 1)',
      denyButtonText: `<i class="fa fa-trash"></i> ${pOpciones[1]}`,
      cancelButtonText: `<i class="fa fa-window-close"></i> ${pOpciones[2]}`,
      allowOutsideClick: false,
    });
  }
}
