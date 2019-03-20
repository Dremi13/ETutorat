import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, Router, CanActivate, UrlTree } from '@angular/router';
import { of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

import { AuthentificationService } from '../../services/authentification.service';

@Injectable({
  providedIn: 'root'
})
export class SigninGuard implements CanActivate {
  
  constructor(private as: AuthentificationService, private router: Router) {}

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot) {

    return this.as.checkSignin().pipe(map(
      res => {
        return true;
      }),
      catchError(error => {
          
        console.log("checkSign fail, donc navigation vers Signin")
        if(error.status == 404){
          var tree = this.router.parseUrl('/signin');
          return of(tree);
        }
      })
    );

  }

}
