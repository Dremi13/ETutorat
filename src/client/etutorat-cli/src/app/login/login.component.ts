import { Component } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { AuthentificationService } from '../services/authentification.service';
import { Token } from '../responseBodies/token';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  constructor ( private as: AuthentificationService,
                private router : Router) {}

  signinForm = new FormGroup({
    login: new FormControl('', [Validators.required, Validators.minLength(9), Validators.maxLength(9)]),
    password: new FormControl('', [Validators.required, Validators.minLength(4), Validators.maxLength(16)])
  })
 
  get login() { return this.signinForm.get('login'); }
  get password() { return this.signinForm.get('password'); }
  
  onSubmit(){

    this.as.signin(this.signinForm.value)
    .subscribe(
      (resp: Token) => {
        this.as.addTokenToStorage(resp);
        this.router.navigate(['/']);
      },
      error => {
        if(error.status == 404){
          
          alert(error.message);
          
          //this.router.navigate(['/login']);
        }
      });;
  }

  
  

}
