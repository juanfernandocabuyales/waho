import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { GeneralRequest } from '../../models/request/requests';
import { GeneralResponse } from '../../models/response/reponses';

@Injectable({
  providedIn: 'root'
})
export class ServicioService {

  constructor(private httClient: HttpClient) { }

  consultarServicios(peticion: GeneralRequest): Observable<GeneralResponse>{
    return this.httClient.post<GeneralResponse>(`${environment.servicioController}consultarServicios`, peticion);
  }

  crearServicio(peticion: GeneralRequest): Observable<GeneralResponse>{
    return this.httClient.post<GeneralResponse>(`${environment.servicioController}crearServicios`, peticion);
  }
}
