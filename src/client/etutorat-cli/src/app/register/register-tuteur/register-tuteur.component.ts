import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { AuthentificationService } from '../../services/authentification.service';
import { Router } from '@angular/router';
import { Token } from '../../responseBodies/token';

@Component({
  selector: 'app-register-tuteur',
  templateUrl: './register-tuteur.component.html',
  styleUrls: ['./register-tuteur.component.css']
})
export class RegisterTuteurComponent {

  constructor(private as : AuthentificationService,
        private router: Router) { }

  createTuteurForm = new FormGroup({
    nom: new FormControl('', [Validators.required]),
    prenom: new FormControl('', [Validators.required]),
    codeetu: new FormControl('', [Validators.required, Validators.pattern('[a-z][0-9]{8}')]),
    telephone: new FormControl('',[Validators.required, Validators.pattern('[0-9]{10}')]),
    filiere: new FormControl('',[Validators.required]),
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required, Validators.minLength(4), Validators.maxLength(16)])
  });

  get nom() { return this.createTuteurForm.get('nom'); }
  get prenom() { return this.createTuteurForm.get('prenom'); }
  get email() { return this.createTuteurForm.get('email'); }
  get password() { return this.createTuteurForm.get('password'); }
  get codeetu() { return this.createTuteurForm.get('codeetu'); }


  alertOpened = false;
  tuteurCreated = "";
  wrongFormAlertOpened = false;
  alreadyUse = [];
  timeOutOk;
  timeOutNotOk;

  register(){

    console.log("create tuteur form submitted");
    this.as.registerTuteur(this.createTuteurForm.value)
    .subscribe(
      (resp : Token) => {
          this.createTuteurForm.setValue({
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
        if(errorResponse.status == 404){
          alert(errorResponse.message);
        //this.router.navigate(['/signin']);
        }
        if(errorResponse.status == 409){
          this.alreadyUse = errorResponse.error.message.split('/');
          this.alreadyUse.shift();
          this.alreadyUse = this.alreadyUse.map(function(x) { if(x === "email") return "Email"; if(x === "codeetu") return "Code étudiant"; });

          this.wrongFormAlertOpened = true;
          this.timeOutNotOk = setTimeout(() => this.wrongFormAlertOpened = false, 5000);
        }
    });
  }

}
