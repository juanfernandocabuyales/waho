import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { UtilidadesService } from '../../../services/utils/utilidades.service';

@Component({
  selector: 'app-crear-editar-usuarios',
  templateUrl: './crear-editar-usuarios.component.html',
  styleUrls: ['./crear-editar-usuarios.component.css']
})
export class CrearEditarUsuariosComponent implements OnInit {

  usuarioForm: FormGroup;
  submitted = false;
  blnEscribio = false;
  etiqueta = '';

  constructor(private formBuilder: FormBuilder,
              private utilidades: UtilidadesService) { }

  ngOnInit(): void {
    this.etiqueta = this.utilidades.traducirTexto('usuarioPage.no');
    this.usuarioForm = this.formBuilder.group({
      nombre: new FormControl('', Validators.required),
      apellido: new FormControl('', Validators.required),
      celular: new FormControl('', Validators.required),
      terminos: new FormControl('', Validators.required),
      clave: new FormControl('', Validators.required),
      dispositivo: new FormControl('', Validators.required),
      codigo: new FormControl('', Validators.required),
      tipo: new FormControl('', Validators.required)
    });
  }

  get f(): any {
    return this.usuarioForm.controls;
  }

  cambioTexto(): void{
    this.blnEscribio = !this.blnEscribio;
  }

  cambio( pValor: boolean): void {
    if (pValor){
      this.etiqueta = this.utilidades.traducirTexto('usuarioPage.si');
    }else {
      this.etiqueta = this.utilidades.traducirTexto('usuarioPage.no');
    }
  }

}
