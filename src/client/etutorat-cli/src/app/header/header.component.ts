import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { AuthentificationService } from '../services/authentification.service';
import { Token } from '../responseBodies/token';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  isAdmin : boolean;
  isEtudiant : boolean;

  constructor(private as: AuthentificationService,
              private router: Router) {
    
  }

  ngOnInit() {
    
    //Ne s'exécute qu'à l'initialisation. Permet de retrouver le token en cas de chargement forcé.
    this.as.checkSignin().subscribe((token : Token) => {
      if(token == null){
        this.isAdmin = false;
        this.isEtudiant = false;
      }
      else if(token.type === "admin" || token.type === "superAdmin"){
        this.isAdmin = true;
        this.isEtudiant = false;
      }
      else {
        this.isAdmin = false;
        this.isEtudiant = true;
      }
    });

    //Permet de traçer le token côté client sans faire d'appel au serveur. (Plus ou moins du data binding)
    this.as.getToken().subscribe((token : Token) =>
    {
      if(token == null){
        this.isAdmin = false;
        this.isEtudiant = false;
      }
      else if(token.type === "admin" || token.type === "superAdmin"){
        this.isAdmin = true;
        this.isEtudiant = false;
      }
      else {
        this.isAdmin = false;
        this.isEtudiant = true;
      }
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
