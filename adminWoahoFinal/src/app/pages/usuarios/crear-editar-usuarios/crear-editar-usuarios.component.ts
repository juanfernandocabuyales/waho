import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { UtilidadesService } from '../../../services/utils/utilidades.service';
import { UsuarioDto } from '../../../models/general/general';

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
  blnClave = true;
  blnCreacion = true;

  usuarioAux: UsuarioDto;

  constructor(private formBuilder: FormBuilder,
              private utilidades: UtilidadesService) { }

  ngOnInit(): void {
    this.usuarioAux = this.utilidades.obtenerObjetoAlmacenado() as UsuarioDto;
    if(this.usuarioAux){
      this.blnCreacion = false;
      this.usuarioForm = this.formBuilder.group({
        nombre: new FormControl(this.usuarioAux.nombres, Validators.required),
        apellido: new FormControl(this.usuarioAux.apellidos),
        celular: new FormControl(this.usuarioAux.celular, Validators.required),
        terminos: new FormControl(this.usuarioAux.terminos, Validators.required),
        clave: new FormControl(this.usuarioAux.clave, Validators.required),
        dispositivo: new FormControl(this.usuarioAux.idSuscriptor),
        codigo: new FormControl(this.usuarioAux.referrealCode),
        correo : new FormControl(this.usuarioAux.correo, Validators.required),
        tipo: new FormControl(this.usuarioAux.tipoUsuario, Validators.required)
      });
      this.etiqueta = this.usuarioAux.terminos ? this.utilidades.traducirTexto('usuarioPage.si') : this.utilidades.traducirTexto('usuarioPage.no');
    }else{
      this.etiqueta = this.utilidades.traducirTexto('usuarioPage.no');
      this.usuarioForm = this.formBuilder.group({
        nombre: new FormControl('', Validators.required),
        apellido: new FormControl(''),
        celular: new FormControl('', Validators.required),
        terminos: new FormControl(false, Validators.required),
        clave: new FormControl('', Validators.required),
        dispositivo: new FormControl(''),
        codigo: new FormControl(''),
        correo : new FormControl('', Validators.required),
        tipo: new FormControl('', Validators.required)
      });
    }    
  }

  get f(): any {
    return this.usuarioForm.controls;
  }

  cambioTexto(): void{
    this.blnEscribio = !this.blnEscribio;
  }

  validaClave(pTipo: string): void {
    if(pTipo === '1'){
      this.blnClave = true;
    }else{
      this.blnClave = false;
    }
  }

  cambio( pValor: boolean): void {
    if (pValor){
      this.etiqueta = this.utilidades.traducirTexto('usuarioPage.si');
    }else {
      this.etiqueta = this.utilidades.traducirTexto('usuarioPage.no');
    }
  }

  validarOperacion(): void {
    this.submitted = true;
    if (this.usuarioForm.invalid) {
      this.utilidades.abrirDialogo(this.utilidades.traducirTexto('general.completar_campos'), false);
    } else {
      if (!this.usuarioForm.get('terminos').value) {
        this.utilidades.abrirDialogo(this.utilidades.traducirTexto('usuarioPage.aceptar_terminos'), false);
      }else{
        this.ejecutarOperacion();
      }
    }
  }

  limpiarCampos(): void {
    this.blnClave = true;
    this.blnCreacion = true;
  }

  private ejecutarOperacion(): void {
    if (this.blnCreacion) {
      
    }else {

    }
  }

}
