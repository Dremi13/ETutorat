import { Component } from '@angular/core';

import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';


import { AdminService } from '../../services/admin.service';
import { Admin } from '../../../responseBodies/admin';

@Component({
  selector: 'app-create-admin',
  templateUrl: './create-admin.component.html',
  styleUrls: ['./create-admin.component.css']
})
export class CreateAdminComponent {

  constructor(private admS: AdminService,
              private router : Router,
              private route : ActivatedRoute) { }


  createAdminForm = new FormGroup({
    nom: new FormControl('', [Validators.required]),
    prenom: new FormControl('', [Validators.required]),
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required, Validators.minLength(4), Validators.maxLength(16)])
  });

  get nom() { return this.createAdminForm.get('nom'); }
  get prenom() { return this.createAdminForm.get('prenom'); }
  get email() { return this.createAdminForm.get('email'); }
  get password() { return this.createAdminForm.get('password'); }


  alertOpened = false;
  adminCreated = "";
  wrongFormAlertOpened = false;
  alreadyUse = [];
  timeOutOK;
  timeOuNotOK;

  onSubmit(){

    console.log("create admin form submitted");
    this.admS.createAdmin(this.createAdminForm.value)
    .subscribe(
      (resp : Admin[]) => {
        this.admS.setListAdmin(resp);
        this.adminCreated = this.email.value;
        this.alertOpened = true;
        this.createAdminForm.setValue({
          nom: "",
          prenom: "",
          email: "",
          password: ""
        });
        this.timeOutOK = setTimeout(() => this.alertOpened = false, 5000);
      },
      errorResponse => {
        if(errorResponse.status == 404){
          alert(errorResponse.message);
        //this.router.navigate(['/signin']);
        }
        if(errorResponse.status == 409){
          this.alreadyUse = errorResponse.error.message.split('/');
          this.wrongFormAlertOpened = true;
          this.timeOuNotOK = setTimeout(() => this.wrongFormAlertOpened = false, 5000);
        }
    });
    
  }

}
 