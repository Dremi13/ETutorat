import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, Router, CanActivate } from '@angular/router';


import { Token } from '../../responseBodies/token';
import { AuthentificationService } from '../../services/authentification.service';

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {
  
  constructor(private as: AuthentificationService, private router: Router) {}

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    let r: boolean;

    this.as.checkSignin().subscribe(
      (resp: Token) => {
        if(resp.type == "admin" || resp.type == "superAdmin") r = true;

        else {
          this.router.navigate(['/']);

          //Remplaçable par un message d'erreur + plus sécurisation (requête au serveur pour ban d'ip, etc)
          alert("What are you doing here ?")

          
          r = false;
        } 
      },
      error => {
        if(error.status == 404){
          
          alert(error.message);
          
          this.router.navigate(['/signin']);
        }
      });
      return r;

  }




}
