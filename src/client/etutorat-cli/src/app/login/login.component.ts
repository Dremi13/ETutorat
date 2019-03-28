import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { AuthentificationService } from '../services/authentification.service';
import { Token } from '../responseBodies/token';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  signinForm = new FormGroup({
      login: new FormControl('', [Validators.required, Validators.pattern('[a-z][0-9]{8}')]),
      password: new FormControl('', [Validators.required, Validators.minLength(4), Validators.maxLength(16)])
    });

  wrongFormAlertOpened: boolean;
  timeOutNotOK;

  constructor ( private as: AuthentificationService,
                private router : Router) {}

  
  
 
  get login() { return this.signinForm.get('login'); }
  get password() { return this.signinForm.get('password'); }
  
  onSubmit(){

    this.as.signin(this.signinForm.value)
    .subscribe(
      (resp: Token) => {
        this.as.addToken(resp);
        this.router.navigate(['/']);
      },
      error => {
        if(error.status == 404){
          
          this.wrongFormAlertOpened = true;
          this.timeOutNotOK = setTimeout(() => this.wrongFormAlertOpened = false, 3000);
          
        }
      });;
  }

  
  

}
