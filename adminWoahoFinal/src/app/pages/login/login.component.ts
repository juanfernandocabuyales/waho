import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators} from '@angular/forms';
import { UtilidadesService } from '../../services/utils/utilidades.service';
import { UsuarioService } from '../../services/rest/usuario.service';
import { LoginAdminRequest } from '../../models/request/requests';
import { LoginAdminResponse } from '../../models/response/reponses';
import { GeneralResponse } from '../../models/response/reponses';
import { Constantes } from '../../constants/constantes';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  loading = false;
  submitted = false;
  returnUrl: string;
  blnEscribio = false;


  constructor( private formBuilder: FormBuilder, private utilidades: UtilidadesService, private usuarioService: UsuarioService) { }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
  });
  }

  validarLogin(): void{
    this.submitted = true;

    if (this.loginForm.invalid){
      this.abrirDialogo('Por favor revisar los datos ingresados', true);
    }else{
      this.loading = true;

      const request: LoginAdminRequest = {
        usuario : this.f.username.value,
        llave: this.f.password.value,
        idioma: this.utilidades.obtenerIdioma()
      };

      this.usuarioService.validarLoginAdmin(this.utilidades.construirPeticion(request))
      .subscribe(
        data => {
          this.validarRespuesta(data);
        },
        () => {
          this.loading = false;
          this.abrirDialogo('Se ha presentado un error', false);
        }
      );
    }
  }

  get f(): any {
     return this.loginForm.controls;
  }

  cambioTexto(): void {
    this.blnEscribio = !this.blnEscribio;
  }

  abrirDialogo(pMensaje: string, pBandera: boolean): void{
    this.utilidades.abrirDialogo(pMensaje, pBandera);
  }

  validarRespuesta(pRespuesta: GeneralResponse): void{
    this.loading = false;
    const loginAdminResponse: LoginAdminResponse = JSON.parse(pRespuesta.mensaje);
    if (loginAdminResponse.codigoRespuesta === Constantes.RESPUESTA_POSITIVA){
      this.utilidades.navegarPagina('/home/servicios', loginAdminResponse);
    }else{
      this.abrirDialogo(loginAdminResponse.mensajeRespuesta, true);
    }
  }
}
