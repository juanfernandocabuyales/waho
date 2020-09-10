import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { environment } from 'src/environments/environment';
import {HttpParams} from "@angular/common/http";
import { PeticionRequest,PeticionResponse } from '../interfaces/interfaces';

const apiUrl = environment.url_servicio;

@Injectable({
  providedIn: 'root'
})
export class ServiceService {

  constructor(private httClient: HttpClient) { }

  validarLogin(peticion:PeticionRequest){
    return this.httClient.post<PeticionResponse>(`${apiUrl}woaho/usuario/validarLogin`,peticion);
  }
}
