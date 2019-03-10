import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';


import { AuthentificationService } from '../services/authentification.service';
import { Token } from '../responseBodies/token';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  constructor(private as : AuthentificationService,
              private router: Router) { }

  ngOnInit() {

    var registerForm = {
      nom : "Test",
      prenom: "test",
      email: "test@test.com",
      password: "azer",
      codeetu: "a12345678",
      telephone: "0123456789",
      filiere: "Master 2 Informatique"
    }
    this.as.register(registerForm).subscribe(
      (resp: Token) => {
        this.as.addTokenToStorage(resp);
        this.router.navigate(['/']);
      },
      error => {
        if(error.status == 404){
          
          alert(error.message);
          
        }
      });;;
  }

}
