import { Component, OnInit } from '@angular/core';
import { AuthentificationService } from '../../services/authentification.service';
import { Router } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Token } from '../../responseBodies/token';

@Component({
  selector: 'app-register-tutore',
  templateUrl: './register-tutore.component.html',
  styleUrls: ['./register-tutore.component.css']
})
export class RegisterTutoreComponent implements OnInit {

  constructor(private as : AuthentificationService,
              private router: Router) { }

  createTutoreForm = new FormGroup({
    nom: new FormControl('', [Validators.required]),
    prenom: new FormControl('', [Validators.required]),
    codeetu: new FormControl('', [Validators.required, Validators.pattern('[a-z][0-9]{8}')]),
    telephone: new FormControl('',[Validators.required, Validators.pattern('[0-9]{10}')]),
    filiere: new FormControl('',[Validators.required]),
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required, Validators.minLength(4), Validators.maxLength(16)])
  });

  get nom() { return this.createTutoreForm.get('nom'); }
  get prenom() { return this.createTutoreForm.get('prenom'); }
  get email() { return this.createTutoreForm.get('email'); }
  get password() { return this.createTutoreForm.get('password'); }


  alertOpened = false;
  tutoreCreated = "";
  wrongFormAlertOpened = false;
  alreadyUse = [];
  timeOutOK;
  timeOutNotOK;
              
  
  ngOnInit() {

    
  }
  
  register(){


    this.as.registerTutore(this.createTutoreForm.value).subscribe(
      (resp: Token) => {
        this.createTutoreForm.setValue({
          nom: "",
          prenom: "",
          email: "",
          password: "",
          filiere: "",
          telephone: "",
          codeetu: ""
        });
        this.as.addToken(resp);
        this.router.navigate(['/']);
      },
      errorResponse => {
        if(errorResponse.status == 409){
          this.alreadyUse = errorResponse.error.message.split('/');
          this.alreadyUse.shift();
          this.alreadyUse = this.alreadyUse.map(function(x) { if(x === "email") return "Email"; if(x === "codeetu") return "Code étudiant"; });

          this.wrongFormAlertOpened = true;
          this.timeOutNotOK = setTimeout(() => this.wrongFormAlertOpened = false, 5000);
        }
      });;;
  }
  

}
