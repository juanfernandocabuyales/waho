import { Component, OnInit,ViewChild} from '@angular/core';
import { ServiceService } from '../../services/service.service'
import { UtilidadesService } from 'src/app/services/utilidades.service';
import { Validators, FormGroup, FormBuilder } from '@angular/forms';
import { IonInput } from '@ionic/angular';
import { Keyboard } from '@ionic-native/keyboard/ngx';
import { Router  } from '@angular/router';
import { PeticionRequest,ResponseGeneral} from '../../interfaces/interfaces';
import { SessionService } from '../../services/session.service'

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {

  loginForm: FormGroup;

  response : ResponseGeneral;

  @ViewChild('inputLogin',{static:true}) 
  inputLogin:IonInput;

  constructor(private formBuilder: FormBuilder,
              private servicio: ServiceService,
              private utilidades: UtilidadesService,
              private keyboard: Keyboard,
              private navController: Router,
              private sessionService: SessionService) {
    this.loginForm = this.formBuilder.group({
      login: ['', Validators.compose([
        Validators.required,
        Validators.pattern(/^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,3})$/)
      ])],
      clave: ['', Validators.compose([
        Validators.required,
        Validators.pattern('^[a-zA-Z0-9_.-]*$')
      ])]
    });
   }

  ngOnInit() {
  }

  ngAfterViewInit() {
    setTimeout(() => {
      this.inputLogin.setFocus();
    }, 400);
  }

  hiddenKeyboard() {
    this.keyboard.hide();
  }


  validarLogin(){
    let objeto = {
      'correo':this.loginForm.value.login,
      'clave':this.loginForm.value.clave
    };
     
    let peticion: PeticionRequest = {strMensaje : JSON.stringify(objeto)};
    this.utilidades.presentLoading();
    this.servicio.validarLogin(peticion).subscribe( (data) =>{
      console.log('data bien',JSON.stringify(data.mensaje));
      this.response = JSON.parse(data.mensaje);
      console.log('response',this.response);
      this.utilidades.dismissDialog();
      if(this.response){
        this.gotoHome();
      }
    },(fail) =>{
      console.log('data mal',JSON.stringify(fail));
      this.utilidades.dismissDialog();
    } );

    console.log(peticion);
  }

  gotoHome() {
    this.sessionService.setObjeto(this.response);
    this.navController.navigate(['/home']);
  }

}
