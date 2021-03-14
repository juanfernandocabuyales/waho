import { Injectable } from '@angular/core';
import { GeneralRequest } from '../models/request/GeneralRequest';
import { Router } from '@angular/router';

import { TranslateService } from '@ngx-translate/core';
import Swal from 'sweetalert2';


@Injectable({
  providedIn: 'root'
})
export class UtilidadesService {

  objetoAlmacenar: any;

  constructor(private traslation: TranslateService,
              private router: Router) {

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
    console.log('Peticion Generada ', peticion);
    return peticion;
  }

  obtenerIdioma(): string {
    console.log('Lenguaje utilidades getLangs', this.traslation.getLangs()[0]);
    const idioma = this.traslation.getLangs()[0];
    return idioma ? idioma : 'es';
  }

  traducirTexto(pTexto: string): string {
    return this.traslation.instant(pTexto);
  }

  navegarPagina(pPagina: string, pObjeto: any): void {
    this.objetoAlmacenar = pObjeto;
    this.router.navigate([pPagina]);
  }

  obtenerObjetoAlmacenado(): any {
    return this.objetoAlmacenar;
  }
}
