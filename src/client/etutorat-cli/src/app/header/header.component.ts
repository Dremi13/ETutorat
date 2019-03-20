import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { AuthentificationService } from '../services/authentification.service';
import { Token } from '../responseBodies/token';
import { environment } from '../../environments/environment';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  API_admin: string;
  type: string;


  constructor(private as: AuthentificationService,
              private router: Router) {
    this.API_admin = environment.API_ADMIN;
  }


  ngOnInit() {
    
    //Ne s'exécute qu'à l'initialisation. Permet de retrouver le token en cas de chargement forcé.
    this.as.checkSignin().subscribe((token : Token) => {
      
      console.log(token);
      if(token === null) this.type = "signout";
      else              this.type = token.type;
    },
    error => {
      this.type = "signout";
    });

    //Permet de traçer le token côté client sans faire d'appel au serveur. (Plus ou moins du data binding)
    this.as.getToken().subscribe((token : Token) =>
    {

      if(token == null) this.type = "signout";
      else              this.type = token.type;
    });

    
  }


  signout(){
    this.as.signout().subscribe((resp: Token) => {
      console.log("body : " +resp);
      
      this.as.addToken(null);
      this.router.navigate(['/signin']);
    },
    error => {
      if(error.status == 404){
        console.log(error);
      }
    });;
  }

}
