import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DataDialog } from '../interface/interfaces';
import { DialogComponent } from '../pages/dialog/dialog.component';
import { PeticionRequest } from '../interface/request';

import { TranslateService } from '@ngx-translate/core';

@Injectable({
  providedIn: 'root'
})
export class UtilidadesService {

  constructor(private dialog: MatDialog, private traslation: TranslateService) { }

  abrirDialogo( pMensaje: string, pBotonCancelar: boolean, pTitulo: string): void{
    const dataDialog: DataDialog = {
      blnBotonCancelar : pBotonCancelar,
      strInformacion: pMensaje,
      strTitulo: pTitulo
    };
    console.log('alerta', dataDialog);
    const dialogRef = this.dialog.open(DialogComponent, {
      width: '250px',
      data: dataDialog,
      disableClose: true
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
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
}
