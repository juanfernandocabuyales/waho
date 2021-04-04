import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { GeneralRequest } from '../../models/request/requests';
import { GeneralResponse } from '../../models/response/reponses';

@Injectable({
  providedIn: 'root'
})
export class TerritorioService {

  constructor(private httClient: HttpClient) { }

  obtenerPaises(peticion: GeneralRequest): Observable<GeneralResponse>{
    return this.httClient.post<GeneralResponse>(`${environment.territorioController}consultarPaises`, peticion);
  }

  obtenerTerritorios(peticion: GeneralRequest): Observable<GeneralResponse>{
    return this.httClient.post<GeneralResponse>(`${environment.territorioController}consultarTerritorios`, peticion);
  }

  crearTerritorios(peticion: GeneralRequest): Observable<GeneralResponse>{
    return this.httClient.post<GeneralResponse>(`${environment.territorioController}crearTerritorios`, peticion);
  }

  actualizarTerritorios(peticion: GeneralRequest): Observable<GeneralResponse>{
    return this.httClient.post<GeneralResponse>(`${environment.territorioController}actualizarTerritorios`, peticion);
  }

  eliminarTerritorios(peticion: GeneralRequest): Observable<GeneralResponse>{
    return this.httClient.post<GeneralResponse>(`${environment.territorioController}eliminarTerritorio`, peticion);
  }
}
