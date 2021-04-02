import { Component, OnInit } from '@angular/core';
import { UtilidadesService } from '../../../services/utils/utilidades.service';
import { FileDto, ImagenDto } from '../../../models/general/general';
import { CrearImagenRequest, ConsultarImagenesRequest, EliminarRequest } from '../../../models/request/requests';
import { ImagenService } from '../../../services/rest/imagen.service';
import { GeneralResponse, ConsultarImagenesResponse, EliminarResponse } from '../../../models/response/reponses';
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
        this.utilidades.abrirDialogoExitoso(this.utilidades.traducirTexto('general.proceso_ok')).
          then(result => {
            this.limpiar();
          });
      }, 5000);
    } else {
      this.utilidades.abrirDialogo(this.utilidades.traducirTexto('imagenesPage.no_archivos'), false);
    }
  }

  limpiar(): void {
    this.archivos = [];
    this.listImagenes = [];
    this.blnBanderaListar = true;
  }

  limpiarArchivos(): void {
    console.log('file componente', this.archivos);
  }

  eliminarFila(i: number): void {
    this.archivos.splice(i, 1);
  }

  editarFila(pArchivo: FileDto): void {
    this.utilidades.mostarDialogoInput(this.utilidades.traducirTexto('imagenesPage.nombre_archivo'),
      this.utilidades.traducirTexto('imagenesPage.ingrese_nombre'))
      .then(result => {
        pArchivo.nombre = this.utilidades.obtenerNombreExtension(pArchivo.file, result.value);
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
          () => {
            this.utilidades.ocultarCargue();
          }
        );
    }
  }

  eliminarImagen(pIndex: number, pImagen: ImagenDto): void {
    this.utilidades.mostrarDialogoConfirmacion(this.utilidades.traducirTexto('imagenesPage.mensaje_eliminar'))
      .then(result => {
        if (result.isConfirmed) {
          this.utilidades.mostrarCargue();

          const eliminarRequest: EliminarRequest = {
            id: pImagen.idImagen,
            idioma: this.utilidades.obtenerIdioma()
          };

          this.imagenService.eliminarImagen(this.utilidades.construirPeticion(eliminarRequest)).subscribe(
            data => {
              this.validarEliminacion(data);
              this.listImagenes.splice(pIndex, 1);
            },
            () => {
              this.utilidades.ocultarCargue();
            }
          );
        }
      });
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

  validarEliminacion(data: GeneralResponse): void {
    const response: EliminarResponse = JSON.parse(data.mensaje);
    if (response.codigoRespuesta === Constantes.RESPUESTA_POSITIVA) {
      this.utilidades.abrirDialogoExitoso(this.utilidades.traducirTexto('general.proceso_ok'));
    } else {
      this.utilidades.abrirDialogo(this.utilidades.traducirTexto('general.carga_mal'), true);
    }
    this.utilidades.ocultarCargue();
  }
}
