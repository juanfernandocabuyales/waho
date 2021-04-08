import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { GeneralRequest } from '../../models/request/requests';
import { GeneralResponse } from '../../models/response/reponses';

@Injectable({
  providedIn: 'root'
})
export class UnidadService {

  constructor(private httClient: HttpClient) { }

  obtenerUnidades(peticion: GeneralRequest): Observable<GeneralResponse>{
    return this.httClient.post<GeneralResponse>(`${environment.unidadController}consultarUnidades`, peticion);
  }

  crearUnidades(peticion: GeneralRequest): Observable<GeneralResponse>{
    return this.httClient.post<GeneralResponse>(`${environment.unidadController}crearUnidades`, peticion);
  }

  actualizarUnidades(peticion: GeneralRequest): Observable<GeneralResponse>{
    return this.httClient.post<GeneralResponse>(`${environment.unidadController}actualizarUnidades`, peticion);
  }

  eliminarUnidades(peticion: GeneralRequest): Observable<GeneralResponse>{
    return this.httClient.post<GeneralResponse>(`${environment.unidadController}eliminarUnidades`, peticion);
  }
}
