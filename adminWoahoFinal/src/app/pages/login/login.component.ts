import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { DialogComponent } from '../dialog/dialog.component';
import { DataDialog } from '../../interface/interfaces';

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


  constructor( private formBuilder: FormBuilder, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
  });
  }

  validarLogin(){
    console.log('valor', this.f.username.value);
    this.abrirDialogo();
  }

  get f() { return this.loginForm.controls; }

  cambioTexto(): void {
    this.blnEscribio = !this.blnEscribio;
  }

  abrirDialogo(){
    const dataDialog: DataDialog = {
      blnBotonCancelar : true,
      strInformacion: 'Este mensaje es de prueba',
      strTitulo: 'Informacion'
    };
    console.log('alerta', dataDialog);
    const dialogRef = this.dialog.open(DialogComponent, {
      width: '250px',
      data: dataDialog
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }
}
