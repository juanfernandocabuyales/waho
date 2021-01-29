import { Injectable } from '@angular/core';
import { PeticionRequest } from '../interface/request';
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

  abrirDialogo( pMensaje: string, pBandera: boolean): void{
    Swal.fire({
      icon: pBandera ? 'info' : 'warning',
      title: pBandera ? this.traducirTexto('general.informacion') : this.traducirTexto('general.advertencia'),
      text: pMensaje,
      allowOutsideClick: false,
      width: '350px',
    });
  }

  construirPeticion(pObjeto: any): PeticionRequest{
    const peticion: PeticionRequest = {
      strMensaje : JSON.stringify(pObjeto)
    };
    console.log('Peticion Generada ', peticion);
    return peticion;
  }

  obtenerIdioma(): string{
    return this.traslation.getLangs()[0];
  }

  traducirTexto(pTexto: string): string{
    return this.traslation.instant(pTexto);
  }

  navegarPagina(pPagina: string, pObjeto: any): void{
    this.objetoAlmacenar = pObjeto;
    this.router.navigate([pPagina]);
  }

  obtenerObjetoAlmacenado(): any{
    return this.objetoAlmacenar;
  }
}
