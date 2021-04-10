import { Component, OnInit } from '@angular/core';
import { UtilidadesService } from '../../../services/utils/utilidades.service';

@Component({
  selector: 'app-consultar-usuarios',
  templateUrl: './consultar-usuarios.component.html',
  styleUrls: ['./consultar-usuarios.component.css']
})
export class ConsultarUsuariosComponent implements OnInit {

  constructor(private utilidades: UtilidadesService) { }

  ngOnInit(): void {
  }

  consultar(pTipo: string): void {
    if (pTipo === '0') {
      this.utilidades.abrirDialogo(this.utilidades.traducirTexto('usuarioPage.tipo_valido'), false);
    }
  }

}
