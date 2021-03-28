import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl, FormArray } from '@angular/forms';
import { forkJoin } from 'rxjs';
import { Constantes } from '../../../constants/constantes';
import { ImagenService } from '../../../services/rest/imagen.service';
import { CategoriaService } from '../../../services/rest/categoria.service';
import { TerritorioService } from '../../../services/rest/territorio.service';
import { ConsultarImagenesRequest } from '../../../models/request/ConsultarImagenesRequest';
import { UtilidadesService } from '../../../services/utils/utilidades.service';
import { ConsultarCategoriaRequest } from 'src/app/models/request/ConsultarCategoriaRequest';
import { ConsultarTerritorioRequest } from '../../../models/request/ConsultarTerritorioRequest';
import { ConsultarImagenesResponse, ImagenDto } from '../../../models/response/ConsultarImagenesResponse';
import { ConsultarCategoriasResponse, Categoria } from 'src/app/models/response/ConsultarCategoriasResponse';
import { ConsultarTerritorioResponse, PaisDTO } from '../../../models/response/ConsultarTerritorioResponse';
import { MonedaDto } from '../../../models/general/general';
import { MonedaServiceService } from '../../../services/rest/moneda.service';
import { ConsultarMonedasRequest } from '../../../models/request/ConsultarMonedasRequest';
import { ConsultarMonedasResponse } from '../../../models/response/ConsultarMonedasResponse';

@Component({
  selector: 'app-crear-editar-servicio',
  templateUrl: './crear-editar-servicio.component.html',
  styleUrls: ['./crear-editar-servicio.component.css']
})
export class CrearEditarServicioComponent implements OnInit {

  servicioForm: FormGroup;

  caracteres = '';

  maxCaracteres: number = Constantes.CANT_MAX_CARACTERES;

  listImagenes: ImagenDto[] = [];
  listCategorias: Categoria[] = [];
  listPaises: PaisDTO[] = [];
  listMonedas: MonedaDto[] = [];

  submitted = false;

  constructor(private formBuilder: FormBuilder,
              private utilidades: UtilidadesService,
              private imagenService: ImagenService,
              private categoriaService: CategoriaService,
              private territorioService: TerritorioService,
              private monedaServiceService: MonedaServiceService) {
  }

  ngOnInit(): void {
    this.utilidades.mostrarCargue();
    this.cargarInformacion();
    this.servicioForm = this.formBuilder.group({
      nombre: new FormControl('', Validators.required),
      imagen: new FormControl('', Validators.required),
      categoria: new FormControl('', Validators.required),
      pais: new FormControl('', Validators.required),
      descripcion: new FormControl('', Validators.required)
    });
  }

  get f(): any {
    return this.servicioForm.controls;
  }

  crearServicio(): void {
    this.submitted = true;
  }

  cargarInformacion(): void {
    const requestImagen: ConsultarImagenesRequest = {
      idioma: this.utilidades.obtenerIdioma()
    };

    const requestMonedas: ConsultarMonedasRequest = {
      idioma: this.utilidades.obtenerIdioma()
    };

    const requestCategoria: ConsultarCategoriaRequest = {
      idioma: this.utilidades.obtenerIdioma()
    };

    const requestPais: ConsultarTerritorioRequest = {
      idioma: this.utilidades.obtenerIdioma(),
      tipoTerritorio: Constantes.TIPO_TERRITORIO_PAIS
    };

    const imagenesServicio = this.imagenService.obtenerImagenes(this.utilidades.construirPeticion(requestImagen));
    const categoriaServicio = this.categoriaService.obtenerCategorias(this.utilidades.construirPeticion(requestCategoria));
    const territorioServicio = this.territorioService.obtenerPaises(this.utilidades.construirPeticion(requestPais));
    const monedaServicio = this.monedaServiceService.obtenerMonedas(this.utilidades.construirPeticion(requestMonedas));

    forkJoin([imagenesServicio, categoriaServicio, territorioServicio, monedaServicio]).subscribe(
      results => {
        console.log('result Join', results);
        this.validarRespuestas(results);
      }, error => {
        this.utilidades.ocultarCargue();
        console.log('error', error);
        this.utilidades.abrirDialogo('', false);
      }
    );
  }

  validarRespuestas(results: any): void {
    const consultarImagenesResponse: ConsultarImagenesResponse = JSON.parse(results[0].mensaje);
    const consultarCategoriaResponse: ConsultarCategoriasResponse = JSON.parse(results[1].mensaje);
    const consultarTerritoriosResponse: ConsultarTerritorioResponse = JSON.parse(results[2].mensaje);
    const consultarMonedasResponse: ConsultarMonedasResponse = JSON.parse(results[3].mensaje);

    if (consultarImagenesResponse.codigoRespuesta === Constantes.RESPUESTA_NEGATIVA ||
      consultarCategoriaResponse.codigoRespuesta === Constantes.RESPUESTA_NEGATIVA ||
      consultarTerritoriosResponse.codigoRespuesta === Constantes.RESPUESTA_NEGATIVA ||
      consultarMonedasResponse.codigoRespuesta === Constantes.RESPUESTA_NEGATIVA ) {
      this.utilidades.abrirDialogo(this.utilidades.traducirTexto('general.carga_mal'), true);
    } else {
      this.listImagenes = consultarImagenesResponse.listImagenes;
      this.listCategorias = consultarCategoriaResponse.listCategorias;
      this.listPaises = consultarTerritoriosResponse.lisPaisesDto;
      this.listMonedas = consultarMonedasResponse.listMonedas;
    }
    this.utilidades.ocultarCargue();
  }

  agregarFila(): void{
    
  }
}