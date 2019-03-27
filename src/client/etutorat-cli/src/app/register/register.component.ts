import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';


import { AuthentificationService } from '../services/authentification.service';
import { Token } from '../responseBodies/token';
import { FormGroup, FormControl, Validators } from '@angular/forms';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  constructor(private as : AuthentificationService,
              private router: Router)
  { 
              
  }


  
}
