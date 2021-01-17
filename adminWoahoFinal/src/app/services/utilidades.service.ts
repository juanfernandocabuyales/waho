import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DataDialog } from '../interface/interfaces';
import { DialogComponent } from '../pages/dialog/dialog.component';

@Injectable({
  providedIn: 'root'
})
export class UtilidadesService {

  constructor(private dialog: MatDialog) { }

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
}
