import { Component, OnInit } from '@angular/core';
import { UtilidadesService } from '../../../services/utils/utilidades.service';
import { UsuarioDto } from '../../../models/general/general';
import { ConsultarUsuariosRequest } from '../../../models/request/requests';
import { UsuarioService } from '../../../services/rest/usuario.service';
import { GeneralResponse, ConsultarUsuariosResponse } from '../../../models/response/reponses';
import { Constantes } from 'src/app/constants/constantes';

@Component({
  selector: 'app-consultar-usuarios',
  templateUrl: './consultar-usuarios.component.html',
  styleUrls: ['./consultar-usuarios.component.css']
})
export class ConsultarUsuariosComponent implements OnInit {

  listUsuarios: UsuarioDto[] = [];
  titulo: string;

  constructor(private utilidades: UtilidadesService,
              private usuarioService: UsuarioService) { }

  ngOnInit(): void {
  }

  consultar(pTipo: string): void {
    if (pTipo === '0') {
      this.utilidades.abrirDialogo(this.utilidades.traducirTexto('usuarioPage.tipo_valido'), false);
    }else{
      this.cambiarTitulo(pTipo);
      this.cargarUsuarios(pTipo);
    }
  }

  editarFila(usuario: UsuarioDto): void {
    this.utilidades.navegarPagina('/home/usuarios/crear-usuarios', usuario);
  }

  eliminarFila(usuario: UsuarioDto): void {

  }

  crearUsuario(): void {
    this.utilidades.navegarPagina('/home/usuarios/crear-usuarios', null);
  }

  private cargarUsuarios(pTipo: string): void {
    this.utilidades.mostrarCargue();
    const request: ConsultarUsuariosRequest = {
      tipoUsuario: pTipo,
      idioma : this.utilidades.obtenerIdioma()
    };
    this.usuarioService.consultarUsuarios(this.utilidades.construirPeticion(request)).subscribe(
      data => {
        this.validarConsulta(data);
      },
      () => {
        this.utilidades.ocultarCargue();
      }
    );
  }



  private validarConsulta(pRespuesta: GeneralResponse): void {
    const response: ConsultarUsuariosResponse = JSON.parse(pRespuesta.mensaje);
    if (response.codigoRespuesta === Constantes.RESPUESTA_POSITIVA){
      this.listUsuarios = response.listUsuarios;
    }else{
      this.utilidades.abrirDialogo(response.mensajeRespuesta, false);
    }
    this.utilidades.ocultarCargue();
  }

  private cambiarTitulo(pTipo: string): void{
    if (pTipo === '1'){
      this.titulo = this.utilidades.traducirTexto('usuarioPage.usuarios') + ' ' + this.utilidades.traducirTexto('usuarioPage.administradores');
    }else{
      this.titulo = this.utilidades.traducirTexto('usuarioPage.usuarios') + ' ' + this.utilidades.traducirTexto('usuarioPage.clientes');
    }
  }
}
