import { Component, OnInit } from '@angular/core';
import { UtilidadesService } from '../../../services/utils/utilidades.service';
import { FileDto, ImagenDto } from '../../../models/general/general';
import { CrearImagenRequest, ConsultarImagenesRequest } from '../../../models/request/requests';
import { ImagenService } from '../../../services/rest/imagen.service';
import { GeneralResponse, ConsultarImagenesResponse } from '../../../models/response/reponses';
import { Constantes } from 'src/app/constants/constantes';

@Component({
  selector: 'app-imagenes',
  templateUrl: './imagenes.component.html',
  styleUrls: ['./imagenes.component.css']
})
export class ImagenesComponent implements OnInit {

  archivos: FileDto[] = [];

  listImagenes: ImagenDto[] = [];

  blnBanderaListar = true;

  estaSobreElemento = false;

  constructor(private utilidades: UtilidadesService,
    private imagenService: ImagenService) { }

  ngOnInit(): void {
  }

  cargarArchivos(): void {
    if (this.archivos.length >= 1) {
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
      this.utilidades.mostrarCargue();
      setTimeout(() => {
        this.utilidades.ocultarCargue();
        this.utilidades.abrirDialogoExitoso('Proceso exitoso').
          then(result => {
            this.limpiar();
          });
      }, 5000);
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

  listarImagenes(): void {
    this.blnBanderaListar = !this.blnBanderaListar;
    if (!this.blnBanderaListar) {
      this.utilidades.mostrarCargue();
      const imagenesReques: ConsultarImagenesRequest = {
        idioma: this.utilidades.obtenerIdioma()
      };

      this.imagenService.obtenerImagenes(this.utilidades.construirPeticion(imagenesReques))
        .subscribe(data => {
          this.validarRespuesta(data);
        },
          error => {
            this.utilidades.ocultarCargue();
            console.log('error creacion', error);
          }
        );
    }
  }

  validarRespuesta(data: GeneralResponse): void {
    const response: ConsultarImagenesResponse = JSON.parse(data.mensaje);
    if (response.codigoRespuesta === Constantes.RESPUESTA_POSITIVA) {
      this.listImagenes = response.listImagenes;
    } else {
      this.utilidades.abrirDialogo(this.utilidades.traducirTexto('general.carga_mal'), true);
    }
    setTimeout(() => {
      this.utilidades.ocultarCargue();
    }, 5000);
  }
}
