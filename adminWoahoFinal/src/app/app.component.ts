import { Component } from '@angular/core';

import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'adminWoahoFinal';

  constructor(private traslation: TranslateService) {
    this.inicializarIdioma();
  }

  inicializarIdioma(): void {
    this.traslation.use(this.traslation.getBrowserLang());
    console.log('Lenguaje', this.traslation.getLangs());
  }
}
