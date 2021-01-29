import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { UtilidadesService } from '../../services/utilidades.service';
import { UsuarioService } from '../../services/usuario.service';
import { PeticionRequest, LoginAdminRequest } from '../../interface/request';
import { LoginAdminResponse } from '../../interface/response';
import { IfStmt } from '@angular/compiler';
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

      console.log('LoginAdminRequest', request);

      this.usuarioService.validarLoginAdmin(this.utilidades.construirPeticion(request))
      .subscribe(
        data => {
          this.loading = false;
          console.log('data', data);
          const loginAdminResponse: LoginAdminResponse = JSON.parse(data.mensaje);
          console.log('imprime objeto total', loginAdminResponse);
          if (loginAdminResponse.codigoRespuesta === Constantes.RESPUESTA_POSITIVA){
            console.log('Podemos hacer el login');
          }else{
            this.abrirDialogo(loginAdminResponse.mensajeRespuesta, true);
          }
        },
        error => {
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
    this.utilidades.abrirDialogo(pMensaje, false, pBandera);
  }
}
