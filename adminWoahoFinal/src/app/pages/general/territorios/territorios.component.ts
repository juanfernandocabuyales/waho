import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-territorios',
  templateUrl: './territorios.component.html',
  styleUrls: ['./territorios.component.css']
})
export class TerritoriosComponent implements OnInit {

  territorioForm: FormGroup;
  submitted = false;

  constructor() { }

  ngOnInit(): void {
  }

  crearTerritorio(): void {
  }

  get f(): any {
    return this.territorioForm.controls;
  }

}
