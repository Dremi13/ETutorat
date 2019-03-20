import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';


import { AuthentificationService } from '../../services/authentification.service';
import { Token } from '../../responseBodies/token';

import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AdminNotsigninGuard implements CanActivate {
  
  constructor(private as: AuthentificationService, private router: Router) {}

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
  

    return this.as.checkSignin().pipe(map(
      (res: Token)  => {
        if(res.type == "admin" || res.type == "superAdmin") return this.router.parseUrl(environment.API_ADMIN+"/index");
        else {
          alert("What did you try just now ?");
          return this.router.parseUrl('index');
        }
      }),
      catchError(error => {
          
        console.log("Not connected")
        if(error.status == 404){

          return of(true);
        }
      })
    );

  }
}
