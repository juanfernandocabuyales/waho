import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { UtilidadesService } from '../../services/utilidades.service';
import { UsuarioService } from '../../services/usuario.service';
import { PeticionRequest, LoginAdminRequest } from '../../interface/request';

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
      this.abrirDialogo('Por favor revisar los datos ingresados', 'Informacion');
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
        },
        error => {
          this.loading = false;
          console.log('error', error);
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

  abrirDialogo(pMensaje: string, pTitulo: string): void{
    this.utilidades.abrirDialogo(pMensaje, false, pTitulo);
  }
}
