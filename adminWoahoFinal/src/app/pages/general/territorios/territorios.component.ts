import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, MaxLengthValidator, Validators } from '@angular/forms';
import { TerritorioDto, TipoDto, ImagenDto } from '../../../models/general/general';
import { UtilidadesService } from '../../../services/utils/utilidades.service';
import { TipoTerritorioService } from '../../../services/rest/tipo-territorio.service';
import { TerritorioService } from '../../../services/rest/territorio.service';
import { ImagenService } from '../../../services/rest/imagen.service';
import { ConsultarImagenesRequest, ConsultarTiposRequest } from 'src/app/models/request/requests';
import { ConsultarTerritoriosRequest } from '../../../models/request/requests';
import { forkJoin } from 'rxjs';
import { ConsultarImagenesResponse, ConsultarTerritoriosResponse } from 'src/app/models/response/reponses';
import { ConsultarTiposResponse } from '../../../models/response/reponses';
import { Constantes } from 'src/app/constants/constantes';

@Component({
  selector: 'app-territorios',
  templateUrl: './territorios.component.html',
  styleUrls: ['./territorios.component.css']
})
export class TerritoriosComponent implements OnInit {

  territorioForm: FormGroup;
  submitted = false;

  listTerritorios: TerritorioDto[] = [];
  listTipos: TipoDto[] = [];
  listImagenes: ImagenDto[] = [];

  constructor(private utilidades: UtilidadesService,
              private tipoService: TipoTerritorioService,
              private territorioService: TerritorioService,
              private imagenService: ImagenService,
              private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.cargarInformacion();
    this.territorioForm = this.formBuilder.group({
      nombre: new FormControl('', Validators.required),
      padre: new FormControl(''),
      tipo: new FormControl('', Validators.required),
      codigo: new FormControl('', Validators.required),
      imagen: new FormControl('', Validators.required)
    });
  }

  private cargarInformacion(): void {
    this.utilidades.mostrarCargue();
    const requestImagen: ConsultarImagenesRequest = {
      idioma: this.utilidades.obtenerIdioma()
    };

    const requestTipos: ConsultarTiposRequest = {
      idioma: this.utilidades.obtenerIdioma()
    };

    const requestTerritorios: ConsultarTerritoriosRequest = {
      idioma: this.utilidades.obtenerIdioma()
    };

    const imagenesServicio = this.imagenService.obtenerImagenes(this.utilidades.construirPeticion(requestImagen));
    const tipoServicios = this.tipoService.obtenerTipos(this.utilidades.construirPeticion(requestTipos));
    const territorioServicios = this.territorioService.obtenerTerritorios(this.utilidades.construirPeticion(requestTerritorios));

    forkJoin([imagenesServicio, tipoServicios, territorioServicios]).subscribe(
      data => {
        this.validarRespuestas(data);
      },
      () => {
        this.utilidades.ocultarCargue();
      }
    );
  }

  crearTerritorio(): void {
    console.log('territorioForm invalid: ', this.territorioForm.invalid);
  }

  eliminarFila(index: number): void {
    this.listTerritorios.splice(index, 1);
  }

  editarFila(territorio: TerritorioDto): void {
    console.log('territorio editado', territorio);
  }

  limpiar(): void {
    this.submitted = false;
    this.territorioForm.reset({
      nombre: '',
      padre: '',
      tipo: '',
      codigo: '',
      imagen: ''
    });
  }

  get f(): any {
    return this.territorioForm.controls;
  }

  private validarRespuestas(results: any): void {
    const consultarImagenesResponse: ConsultarImagenesResponse = JSON.parse(results[0].mensaje);
    const tipoServiciosResponse: ConsultarTiposResponse = JSON.parse(results[1].mensaje);
    const territoriosResponse: ConsultarTerritoriosResponse = JSON.parse(results[2].mensaje);

    if (consultarImagenesResponse.codigoRespuesta === Constantes.RESPUESTA_NEGATIVA ||
      tipoServiciosResponse.codigoRespuesta === Constantes.RESPUESTA_NEGATIVA ||
      territoriosResponse.codigoRespuesta === Constantes.RESPUESTA_NEGATIVA) {
      this.utilidades.abrirDialogo(this.utilidades.traducirTexto('general.carga_mal'), true);
    } else {
      this.listImagenes = consultarImagenesResponse.listImagenes;
      this.listTipos = tipoServiciosResponse.listTipos;
      this.listTerritorios = territoriosResponse.listTerritorios;
    }
    this.utilidades.ocultarCargue();
  }
}
