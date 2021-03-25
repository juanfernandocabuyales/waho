import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { GeneralRequest } from '../../models/request/GeneralRequest';
import { GeneralResponse } from '../../models/response/GeneralResponse';

@Injectable({
  providedIn: 'root'
})
export class TerritorioService {

  constructor(private httClient: HttpClient) { }

  obtenerPaises(peticion: GeneralRequest): Observable<GeneralResponse>{
    return this.httClient.post<GeneralResponse>(`${environment.territorioController}consultarPaises`, peticion);
  }
}
