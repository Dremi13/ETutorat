import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';

import { AdminService } from '../../../services/admin.service';
import { Tutore } from '../../../../responseBodies/tutore';

@Component({
  selector: 'app-create-tutore',
  templateUrl: './create-tutore.component.html',
  styleUrls: ['./create-tutore.component.css']
})
export class CreateTutoreComponent {

  constructor(private admS: AdminService,
              private router : Router,
              private route : ActivatedRoute) { }


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

  onSubmit(){

  console.log("create tutore form submitted");
  this.admS.createTutore(this.createTutoreForm.value)
    .subscribe(
      (resp : Tutore[]) => {
        this.admS.setListTutore(resp);
        this.tutoreCreated = this.email.value;
        this.createTutoreForm.setValue({
          nom: "",
          prenom: "",
          email: "",
          password: "",
          filiere: "",
          telephone: "",
          codeetu: ""
        });
        this.alertOpened = true;
        this.timeOutOK = setTimeout(() => this.alertOpened = false, 5000);
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
          this.timeOutNotOK = setTimeout(() => this.wrongFormAlertOpened = false, 5000);
        }
    });
    
  }

}
