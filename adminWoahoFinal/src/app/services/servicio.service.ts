import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { PeticionRequest } from '../interface/request';
import { Observable } from 'rxjs';
import { PeticionResponse } from '../interface/response';

@Injectable({
  providedIn: 'root'
})
export class ServicioService {

  constructor(private httClient: HttpClient) { }

  consultarServicios(peticion: PeticionRequest): Observable<PeticionResponse>{
    return this.httClient.post<PeticionResponse>(`${environment.servicioController}consultarServicios`, peticion);
  }
}
