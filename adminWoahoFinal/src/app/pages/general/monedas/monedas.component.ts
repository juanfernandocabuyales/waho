import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MonedaDto, PaisDTO } from 'src/app/models/general/general';
import { TerritorioService } from 'src/app/services/rest/territorio.service';
import { UtilidadesService } from 'src/app/services/utils/utilidades.service';
import { ConsultarTerritorioRequest, ConsultarMonedasRequest } from '../../../models/request/requests';
import { Constantes } from '../../../constants/constantes';
import { ConsultarTerritorioResponse, GeneralResponse, ConsultarMonedasResponse } from '../../../models/response/reponses';
import { MonedaServiceService } from '../../../services/rest/moneda.service';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-monedas',
  templateUrl: './monedas.component.html',
  styleUrls: ['./monedas.component.css']
})
export class MonedasComponent implements OnInit {

  monedaForm: FormGroup;
  submitted = false;

  listTerritorios: PaisDTO[] = [];
  listMonedas: MonedaDto[] = [];

  constructor(private formBuilder: FormBuilder,
              private utilidades: UtilidadesService,
              private monedaService: MonedaServiceService,
              private territorioService: TerritorioService) { }

  ngOnInit(): void {
    this.cargarInformacion();
    this.monedaForm = this.formBuilder.group({
      nombre: new FormControl('', Validators.required),
      pais: new FormControl('')
    });
  }

  get f(): any {
    return this.monedaForm.controls;
  }

  crearMoneda(): void {}

  limpiar(): void {}

  editarFila(pMoneda: MonedaDto): void {}

  eliminarFila(pMoneda: MonedaDto): void {}

  private cargarInformacion(): void {
    this.utilidades.mostrarCargue();
    const territorioRequest: ConsultarTerritorioRequest = {
      idioma: this.utilidades.obtenerIdioma(),
      tipoTerritorio: Constantes.TIPO_TERRITORIO_PAIS
    };
    const monedasRequest: ConsultarMonedasRequest = {
      idioma: this.utilidades.obtenerIdioma()
    };

    const paises = this.territorioService.obtenerPaises(this.utilidades.construirPeticion(territorioRequest));
    const monedas = this.monedaService.obtenerMonedas(this.utilidades.construirPeticion(monedasRequest));

    forkJoin([paises, monedas]).subscribe(
      data => {
        this.validarRespuesta(data);
      },
      () => {
        this.utilidades.ocultarCargue();
      }
    );
  }




  private validarRespuesta(results: any): void {
    const territorioResponse: ConsultarTerritorioResponse = JSON.parse(results[0].mensaje);
    const monedasResponse: ConsultarMonedasResponse = JSON.parse(results[1].mensaje);
    if (territorioResponse.codigoRespuesta === Constantes.RESPUESTA_POSITIVA &&
      monedasResponse.codigoRespuesta === Constantes.RESPUESTA_POSITIVA){
      this.listTerritorios = territorioResponse.lisPaisesDto;
      this.listMonedas = monedasResponse.listMonedas;
    }else{
      this.utilidades.abrirDialogo(this.utilidades.traducirTexto('general.carga_mal'), true);
    }
    this.utilidades.ocultarCargue();
  }
}
