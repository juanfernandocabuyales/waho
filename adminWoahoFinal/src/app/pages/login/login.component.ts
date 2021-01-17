import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DataDialog } from '../../interface/interfaces';
import { UtilidadesService } from '../../services/utilidades.service';

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


  constructor( private formBuilder: FormBuilder, private utilidades: UtilidadesService) { }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
  });
  }

  validarLogin(): void{
    console.log('valor', this.f.username.value);
    this.abrirDialogo();
  }

  get f() { return this.loginForm.controls; }

  cambioTexto(): void {
    this.blnEscribio = !this.blnEscribio;
  }

  abrirDialogo(): void{
    this.utilidades.abrirDialogo('Este mensaje llega desde el login component', false, 'Titulo');
  }
}
