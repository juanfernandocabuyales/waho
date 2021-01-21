import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { PeticionRequest, PeticionResponse } from '../interface/interfaces';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  constructor(private httClient: HttpClient) { }

  validarLoginAdmin(peticion: PeticionRequest): Observable<PeticionResponse>{
    return this.httClient.post<PeticionResponse>(`${environment.usuarioController}/validarLoginAdmin`, peticion);
  }
}
