import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, Router, CanActivate, UrlTree } from '@angular/router';
import { of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

import { AuthentificationService } from '../../services/authentification.service';

@Injectable({
  providedIn: 'root'
})
export class NotSigninGuard implements CanActivate {
  
  constructor(private as: AuthentificationService, private router: Router) {}

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    
    return this.as.checkSignin().pipe(map(
      res => {
        console.log("checkSign OK, donc navigation vers Index")
        //var tree = this.router.parseUrl("");
        return this.router.parseUrl("index");
        
      }),
      catchError(error => {
        if(error.status == 404){
          return of(true);
        }
      })
    );

  }
  
}
