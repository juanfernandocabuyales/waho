import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { TipoDto } from '../../../models/general/general';

@Component({
  selector: 'app-tipo-territorio',
  templateUrl: './tipo-territorio.component.html',
  styleUrls: ['./tipo-territorio.component.css']
})
export class TipoTerritorioComponent implements OnInit {

  tipoForm: FormGroup;
  submitted = false;
  listTipos: TipoDto[] = [];

  constructor(private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.tipoForm = this.formBuilder.group({
      nombre: new FormControl('', Validators.required)
    });
  }

  crearTipo(): void {

  }

  eliminarFila(i: number): void {
  }

  get f(): any {
    return this.tipoForm.controls;
  }

}
