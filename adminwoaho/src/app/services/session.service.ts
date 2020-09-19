import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  private objeto: any;

  private opcionesUno : string[] = ['Crear Servicio','Filtrar','Salir'];

  private opcionesDos : string [] = ['Crear Tarifa','Salir'];

  constructor() { }

  setObjeto(objeto: any){
    this.objeto = objeto;
  }

  getObjeto(){
    return this.objeto;
  }

  getOpciones(bandera:number){
    if(bandera === 1){
      return this.opcionesUno;
    }else if (bandera == 2){
      return this.opcionesDos;
    }
  }
}
