import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';

import { AuthentificationService } from '../../services/authentification.service';
import { Token } from '../../responseBodies/token';


@Component({
  selector: 'app-admin-signin',
  templateUrl: './admin-signin.component.html',
  styleUrls: ['./admin-signin.component.css']
})
export class AdminSigninComponent {

  constructor ( private as: AuthentificationService,
                private router : Router,
                private route : ActivatedRoute) {}

  signinForm = new FormGroup({
    login: new FormControl('', [Validators.required, Validators.pattern(new RegExp(/([A-Za-z0]+\.+[A-Za-z0]+)+@([A-Za-z0]*\.[A-Za-z0]*)*/))]),
    password: new FormControl('', [Validators.required, Validators.minLength(4), Validators.maxLength(16)])
  });


  get login() { return this.signinForm.get('login'); }
  get password() { return this.signinForm.get('password'); }

  onSubmit(){

    this.as.adminSignin(this.signinForm.value)
    .subscribe(
      (resp: Token) => {
        this.as.addToken(resp);
        this.router.navigate(['../'],{relativeTo: this.route});
      },
      error => {
        if(error.status == 404){
        alert(error.message);
        //this.router.navigate(['/signin']);
      }
    });;
  }
}
