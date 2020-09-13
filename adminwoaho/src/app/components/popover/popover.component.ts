import { Component,OnInit } from '@angular/core';
import { PopoverController } from '@ionic/angular';
import { SessionService } from '../../services/session.service';

@Component({
  selector: 'app-popover',
  templateUrl: './popover.component.html',
  styleUrls: ['./popover.component.scss'],
})
export class PopoverComponent implements OnInit {

  opciones:string[];

  constructor(private session: SessionService,private popoverController: PopoverController) { }

  ngOnInit() {
    this.opciones = this.session.getOpciones(1);
    console.log(this.opciones);
  }

  onClick(opcion:string){
    this.popoverController.dismiss({
      item: opcion
    });
    console.log(opcion);
  }

}
