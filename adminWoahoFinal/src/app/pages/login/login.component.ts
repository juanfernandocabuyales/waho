import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators} from '@angular/forms';
import { UtilidadesService } from '../../services/utilidades.service';
import { UsuarioService } from '../../services/usuario.service';
import { LoginAdminRequest } from '../../interface/request';
import { LoginAdminResponse, PeticionResponse } from '../../interface/response';
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
        error => {
          console.log('error', error);
          this.loading = false;
          this.abrirDialogo('Se ha presentado un error', false);
        }
      );
    }
  }

  get f() {
     return this.loginForm.controls;
  }

  cambioTexto(): void {
    this.blnEscribio = !this.blnEscribio;
  }

  abrirDialogo(pMensaje: string, pBandera: boolean): void{
    this.utilidades.abrirDialogo(pMensaje, pBandera);
  }

  validarRespuesta(pRespuesta: PeticionResponse): void{
    this.loading = false;
    const loginAdminResponse: LoginAdminResponse = JSON.parse(pRespuesta.mensaje);
    if (loginAdminResponse.codigoRespuesta === Constantes.RESPUESTA_POSITIVA){
      this.utilidades.navegarPagina('/home', loginAdminResponse);
    }else{
      this.abrirDialogo(loginAdminResponse.mensajeRespuesta, true);
    }
  }
}
