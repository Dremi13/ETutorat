import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';

import { AdminService } from '../../../services/admin.service';
import { Tuteur } from '../../../../responseBodies/tuteur';

@Component({
  selector: 'app-create-tuteur',
  templateUrl: './create-tuteur.component.html',
  styleUrls: ['./create-tuteur.component.css']
})
export class CreateTuteurComponent {

  constructor(private admS: AdminService,
              private router : Router,
              private route : ActivatedRoute) { }


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

  onSubmit(){

    console.log("create tuteur form submitted");
    this.admS.createTuteur(this.createTuteurForm.value)
    .subscribe(
      (resp : Tuteur[]) => {
        this.admS.setListTuteur(resp);
        this.tuteurCreated = this.email.value;
        this.createTuteurForm.setValue({
          nom: "",
          prenom: "",
          email: "",
          password: "",
          filiere: "",
          telephone: "",
          codeetu: ""
        });
        this.alertOpened = true;
        this.timeOutOk = setTimeout(() => this.alertOpened = false, 5000);
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
