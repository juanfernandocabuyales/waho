import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css']
})
export class TableComponent implements OnInit {

  @Input()
  cabecera: string[];

  @Input()
  data: any[];

  constructor() { }

  ngOnInit(): void {
    console.log('me llego cabecera', this.cabecera);
    console.log('me llego data', this.data);
  }

}
