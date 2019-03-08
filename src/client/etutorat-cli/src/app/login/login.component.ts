import { Component } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Validators } from '@angular/forms';

import { AuthentificationService } from '../services/authentification.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  constructor (private as: AuthentificationService) {}

  signinForm = new FormGroup({
    login: new FormControl('', [Validators.required, Validators.minLength(9), Validators.maxLength(9)]),
    password: new FormControl('', [Validators.required, Validators.minLength(4), Validators.maxLength(16)])
  })
 
  get login() { return this.signinForm.get('login'); }
  get password() { return this.signinForm.get('password'); }
  
  onSubmit(){
    console.warn(this.signinForm.value);
    this.as.signin(this.signinForm.value);
  }

  
  

}
