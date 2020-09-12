import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  private objeto: any;

  constructor() { }

  setObjeto(objeto: any){
    this.objeto = objeto;
  }

  getObjeto(){
    return this.objeto;
  }
}
