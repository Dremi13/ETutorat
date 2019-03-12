
import {Input, Component, OnInit } from '@angular/core';
import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { HttpClient,HttpClientModule } from '@angular/common/http';
import { Router } from '@angular/router';

import { Token } from '../responseBodies/token';

import { Registration } from './models/user.models';
import { AuthentificationService } from '../services/authentification.service';
import {RegistrationService} from './services/registration.service'
import { SharedService } from './services/shared.service';



@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  providers:[RegistrationService]
})
export class RegisterComponent implements OnInit {
  closeResult: string;
  registrationForm: FormGroup;
  loginForm:FormGroup;
  registrationInputs: Registration[];
  currentUser: Registration[];
  isLoggedIn:boolean=false;

  cartItemCount:number=0;
  approvalText:string="";

  @Input()
  public alerts: Array<IAlert> = [];

  message = "";
  public globalResponse: any;

  constructor(private sharedService:SharedService, private modalService: NgbModal,private fb: FormBuilder,private regService:RegistrationService ,private authService : AuthenticationService, private router: Router ) { }


  ngOnInit() {

    this.sharedService.currentMessage.subscribe(msg => this.cartItemCount = msg);
    this.registrationForm = this.fb.group({
      nom:  ['', Validators.compose([Validators.required, Validators.minLength(3),Validators.maxLength(50)])],
      prenom:  ['', Validators.compose([Validators.required, Validators.minLength(3),Validators.maxLength(50)])],
      email:['',Validators.compose([Validators.required,Validators.email])],
      Password:['',Validators.compose([Validators.required, Validators.minLength(3),Validators.maxLength(50)])],
      codeetu:['', Validators.compose([Validators.required, Validators.minLength(3),Validators.maxLength(50)])],
      telephone:['',Validators.required],
      filiere:['',Validators.required],
    });
    this.authService.register(registerForm).subscribe(
      (resp: Token) => {
        this.authService.addTokenToStorage(resp);
        this.router.navigate(['/']);
      },
      error => {
        if(error.status == 404){
          
          alert(error.message);
          
        }
      });;;
  }

  open(content) {
    this.alerts=[];
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title',size: 'lg'}).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      //this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return  `with: ${reason}`;
    }
  }

  OnRegister()
  {
    this.registrationInputs=this.registrationForm.value;

    console.log(this.registrationInputs);
        this.regService.RegisterUser(this.registrationInputs)
            .subscribe((result) => {
              this.globalResponse = result;              
            },
            error => { // erreur part
              this.alerts.push({
                id: 2,
                type: 'danger',
                message: 'Erreur d'enregistrement avec:'+error,
              });
            },
            () => {
                // Success part
                this.alerts.push({
                  id: 1,
                  type: 'success',
                  message: 'Enregistrement avec succes.',
                });
                
                }
              )
    }
  public closeAlert(alert: IAlert) {
    const index: number = this.alerts.indexOf(alert);
    this.alerts.splice(index, 1);
  }

  GetClaims()
  {
        this.authService.getClaims()
            .subscribe((result) => {
              this.globalResponse = result;              
            },
            error => { //Erreur part
              console.log(error.message);
            },
            () => {
                //  Success part
                let a=this.globalResponse;
                this.currentUser=this.globalResponse;
                this.authService.storeRole(this.currentUser);
                }
              )
            
  } 
  LogOut()
  {
    this.isLoggedIn=false;
    this.authService.removeToken();
  }

  export interface IAlert {
    id: number;
    type: string;
    message: string;
  }

}










