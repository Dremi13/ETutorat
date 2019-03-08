import { Component, OnInit } from '@angular/core';
import { AuthentificationService } from '../services/authentification.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  constructor(private as : AuthentificationService) { }

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
    this.as.register(registerForm);
  }

}
