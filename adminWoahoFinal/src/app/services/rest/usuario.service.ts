import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { GeneralRequest } from '../../models/request/GeneralRequest';
import { Observable } from 'rxjs';
import { GeneralResponse } from '../../models/response/GeneralResponse';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  constructor(private httClient: HttpClient) { }

  validarLoginAdmin(peticion: GeneralRequest): Observable<GeneralResponse>{
    return this.httClient.post<GeneralResponse>(`${environment.usuarioController}validarLoginAdmin`, peticion);
  }
}
