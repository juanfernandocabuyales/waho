import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { GeneralRequest } from '../../models/request/requests';
import { GeneralResponse } from '../../models/response/reponses';

@Injectable({
  providedIn: 'root'
})
export class TipoTerritorioService {

  constructor(private httClient: HttpClient) { }

  obtenerTipos(peticion: GeneralRequest): Observable<GeneralResponse>{
    return this.httClient.post<GeneralResponse>(`${environment.tipoTerritorioController}consultarTipos`, peticion);
  }

  crearTipos(peticion: GeneralRequest): Observable<GeneralResponse>{
    return this.httClient.post<GeneralResponse>(`${environment.tipoTerritorioController}crearTipos`, peticion);
  }

  actualizarTipos(peticion: GeneralRequest): Observable<GeneralResponse>{
    return this.httClient.post<GeneralResponse>(`${environment.tipoTerritorioController}actualizarTipos`, peticion);
  }

  eliminarTipos(peticion: GeneralRequest): Observable<GeneralResponse>{
    return this.httClient.post<GeneralResponse>(`${environment.tipoTerritorioController}eliminarTipos`, peticion);
  }
}
