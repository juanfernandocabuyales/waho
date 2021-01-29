import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PeticionRequest } from '../interface/request';

import { TranslateService } from '@ngx-translate/core';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class UtilidadesService {

  constructor(private dialog: MatDialog, private traslation: TranslateService) { }

  abrirDialogo( pMensaje: string, pBotonCancelar: boolean, pBandera: boolean): void{
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
}
