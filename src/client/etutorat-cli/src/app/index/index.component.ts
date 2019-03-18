import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';


import { AuthentificationService } from '../services/authentification.service';
import { Token } from '../responseBodies/token';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {

  constructor(private as: AuthentificationService,
              private router: Router) { }

  ngOnInit() {
    this.as.checkSignin()
    .subscribe((resp: Token) => {
      console.log(resp);
    },
    error => {
      if(error.status == 404){
        console.log(error);
        this.router.navigate(['/login']);
      }
    });;
  }

}
