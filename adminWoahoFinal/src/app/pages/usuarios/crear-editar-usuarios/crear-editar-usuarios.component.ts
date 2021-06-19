import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { UtilidadesService } from '../../../services/utils/utilidades.service';
import { UsuarioDto } from '../../../models/general/general';
import { CrearUsuarioRequest } from '../../../models/request/requests';
import { UsuarioService } from '../../../services/rest/usuario.service';
import { GeneralResponse } from 'src/app/models/response/reponses';
import { Observable } from 'rxjs';
import { CrearResponse } from '../../../models/response/reponses';
import { Constantes } from '../../../constants/constantes';

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
  blnTerminos = true;

  usuarioAux: UsuarioDto;

  constructor(private formBuilder: FormBuilder,
              private usuarioService: UsuarioService,
              private utilidades: UtilidadesService) { }

  ngOnInit(): void {
    this.usuarioAux = this.utilidades.obtenerObjetoAlmacenado() as UsuarioDto;
    console.log('usuarioAux', this.usuarioAux);
    if (this.usuarioAux){
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
      this.validaClave(this.usuarioAux.tipoUsuario);
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
        tipo: new FormControl('0', Validators.required)
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
    if (pTipo === '1'){
      this.blnClave = true;
      this.blnTerminos = false;
      this.usuarioForm.get('clave').setValidators(Validators.required);
    }else{
      this.blnClave = false;
      this.blnTerminos = true;
      this.usuarioForm.get('clave').setValidators(null);
      this.usuarioForm.get('clave').setValue('');
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
      if (!this.usuarioForm.get('terminos').value && this.usuarioForm.get('tipo').value === '2') {
        this.utilidades.abrirDialogo(this.utilidades.traducirTexto('usuarioPage.aceptar_terminos'), false);
      }else{
        this.ejecutarOperacion();
      }
    }
  }

  limpiarCampos(): void {
    this.blnClave = true;
    this.blnCreacion = true;
    this.submitted = false;
    this.usuarioForm.reset({
      nombre: '',
      apellido: '',
      celular: '',
      terminos: false,
      clave: '',
      dispositivo: '',
      codigo: '',
      correo : '',
      tipo: '0',
    });
  }

  private ejecutarOperacion(): void {
    this.utilidades.mostrarCargue();
    const tipo = this.usuarioForm.get('tipo').value;
    const crearRequest: CrearUsuarioRequest = {
      usuarioDto : {
        id: this.blnCreacion ? '' : this.usuarioAux.id,
        nombres: this.usuarioForm.get('nombre').value,
        apellidos: this.usuarioForm.get('apellido').value,
        celular: this.usuarioForm.get('celular').value,
        correo: this.usuarioForm.get('correo').value,
        clave: (tipo === '1') ? this.usuarioForm.get('clave').value : '',
        idSuscriptor: this.usuarioForm.get('dispositivo').value,
        referrealCode: this.usuarioForm.get('codigo').value,
        tipoUsuario : tipo,
        terminos: this.usuarioForm.get('terminos').value,
      },
      idioma: this.utilidades.obtenerIdioma()
    };
    let servicio: Observable<GeneralResponse>;
    if (this.blnCreacion){
      servicio = this.usuarioService.crearUsuario(this.utilidades.construirPeticion(crearRequest));
    }else{
      servicio = this.usuarioService.actualizarUsuario(this.utilidades.construirPeticion(crearRequest));
    }
    servicio.subscribe(data => {
      this.validarCreacionActualizacion(data);
    },
    () => {
      this.utilidades.ocultarCargue();
    });
  }

  private validarCreacionActualizacion(pRespuesta: GeneralResponse): void {
    const response: CrearResponse = JSON.parse(pRespuesta.mensaje);
    if (response.codigoRespuesta === Constantes.RESPUESTA_POSITIVA){
      this.utilidades.abrirDialogoExitoso(this.utilidades.traducirTexto('general.operacion_ok'))
        .then( () => { this.limpiarCampos(); } );
    }else{
      this.utilidades.abrirDialogo(response.mensajeRespuesta, false);
    }
    this.utilidades.ocultarCargue();
  }

}
