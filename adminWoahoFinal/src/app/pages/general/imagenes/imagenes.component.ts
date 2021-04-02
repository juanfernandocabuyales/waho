import { Component, OnInit } from '@angular/core';
import { UtilidadesService } from '../../../services/utils/utilidades.service';
import { FileDto } from '../../../models/general/general';
import { CrearImagenRequest, GeneralRequest } from '../../../models/request/requests';
import { ImagenService } from '../../../services/rest/imagen.service';
import { GeneralResponse } from 'src/app/models/response/reponses';
import { CrearImagenResponse } from '../../../models/response/reponses';
import { Constantes } from 'src/app/constants/constantes';

@Component({
  selector: 'app-imagenes',
  templateUrl: './imagenes.component.html',
  styleUrls: ['./imagenes.component.css']
})
export class ImagenesComponent implements OnInit {

  archivos: FileDto[] = [];

  estaSobreElemento = false;

  constructor(private utilidades: UtilidadesService,
              private imagenService: ImagenService) { }

  ngOnInit(): void {
  }

  cargarArchivos(): void {
    if (this.archivos.length >= 1) {
      this.utilidades.mostrarCargue();
      for (const file of this.archivos) {

        const imagenRequest: CrearImagenRequest = {
          idImagen: '',
          nombreImagen: file.nombre,
          alto: '400',
          ancho: '400',
          idioma: this.utilidades.obtenerIdioma()
        };

        const generalRequest = this.utilidades.construirPeticion(imagenRequest);
        const peticion: string = JSON.stringify(generalRequest);
        this.imagenService.guardarImagen(file.file, peticion).subscribe(
          resp => {
            console.log('resp', resp);
          },
          error => {
            this.utilidades.ocultarCargue();
            console.log('error', error);
          }
        );
      }
      this.utilidades.abrirDialogoExitoso('Proceso exitoso').
      then( result => {
        this.limpiar();
      });
      this.utilidades.ocultarCargue();
    } else {
      this.utilidades.abrirDialogo('No se han cargado archivos.', false);
    }
  }

  limpiar(): void {
    this.archivos = [];
  }

  limpiarArchivos(): void {
    console.log('file componente', this.archivos);
  }

  eliminarFila(i: number): void {
    this.archivos.splice(i, 1);
  }

  editarFila(pArchivo: FileDto): void {
    this.utilidades.mostarDialogoInput('Nombre archivo', 'ingrese el nombre')
      .then(result => {
        console.log('result input', result);
        pArchivo.nombre = this.utilidades.obtenerNombreExtension(pArchivo.file, result.value);
        console.log('Nombre editado', pArchivo.nombre);
      });
  }
}
