import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { PeticionRequest,PeticionResponse } from '../interfaces/interfaces';

const apiUrl = environment.url_servicio;

@Injectable({
  providedIn: 'root'
})
export class ServiceService {

  constructor(private httClient: HttpClient) { }

  validarLogin(peticion:PeticionRequest){
    return this.httClient.post<PeticionResponse>(`${apiUrl}usuario/validarLogin`,peticion);
  }

  consultarServicios(){
    return this.httClient.post<PeticionResponse>(`${apiUrl}servicio/consultarServicios`,null);
  }
}

