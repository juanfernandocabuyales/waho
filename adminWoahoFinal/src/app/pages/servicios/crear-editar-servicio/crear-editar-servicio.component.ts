import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Constantes } from '../../../constants/constantes';

@Component({
  selector: 'app-crear-editar-servicio',
  templateUrl: './crear-editar-servicio.component.html',
  styleUrls: ['./crear-editar-servicio.component.css']
})
export class CrearEditarServicioComponent implements OnInit {

  servicioForm: FormGroup;

  caracteres: string = '';

  max_caracteres: number = Constantes.CANT_MAX_CARACTERES;

  constructor(private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.servicioForm = this.formBuilder.group({
      nombre: ['', Validators.required],
      imagen: ['', Validators.required],
      categoria: ['', Validators.required],
      pais: ['', Validators.required],
      descripcion: ['', Validators.required]
    });
  }

  get f(): any {
    return this.servicioForm.controls;
 }

  crearServicio(): void {
    console.log('Submit para crear servicios: ', this.f);
  }

}
