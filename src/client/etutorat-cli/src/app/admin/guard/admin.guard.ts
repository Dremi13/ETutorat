import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, Router, CanActivate } from '@angular/router';
import { of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

import { Token } from '../../responseBodies/token';
import { AuthentificationService } from '../../services/authentification.service';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {
  
  constructor(private as: AuthentificationService, private router: Router) {}

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    var tree;

    return this.as.checkSignin().pipe(map(
      (res: Token)  => {
        if(res.type == "admin" || res.type == "superAdmin") return true;
        else {
          alert("What did you try just now ?");
          tree = this.router.parseUrl('index');
          return tree;
        }
      }),
      catchError(error => {
          
        console.log("Not connected")
        if(error.status == 404){
          tree = this.router.parseUrl(environment.API_ADMIN+'/signin');
          console.log(tree.toString())
          return of(tree);
        }
      })
    );

  }




}
