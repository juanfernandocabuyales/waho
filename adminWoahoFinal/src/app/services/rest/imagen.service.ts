import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { GeneralRequest } from '../../models/request/requests';
import { GeneralResponse } from '../../models/response/reponses';

@Injectable({
  providedIn: 'root'
})
export class ImagenService {

  constructor(private httClient: HttpClient) { }

  obtenerImagenes(peticion: GeneralRequest): Observable<GeneralResponse>{
    return this.httClient.post<GeneralResponse>(`${environment.imagenController}consultarImages`, peticion);
  }
}
