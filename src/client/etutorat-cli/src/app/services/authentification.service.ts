import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Token } from '../responseBodies/token';
import { Router } from '@angular/router';




@Injectable({
  providedIn: 'root'
})
export class AuthentificationService {

  constructor(private http : HttpClient,
              private router : Router) { }


  checkSignin(){
    this.http.get<Token>(environment.API_URL+"/auth/check",{"withCredentials": true}).subscribe((resp: Token) => {
      console.log(resp);
    },
    error => {
      if(error.status == 404){
        console.log(error);
        this.router.navigate(['/login']);
      }
    });
  }


  signin(signinForm){


    console.log(signinForm);

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'withCredentials': 'true'
      })
    };

    this.http.post<Token>(environment.API_URL+"/auth/signin", signinForm, httpOptions)
      
      .subscribe(
        (resp: Token) => {
          localStorage.setItem("login",resp.login);
          localStorage.setItem("type",resp.type);

          this.router.navigate(['/']);
        },
        error => {
          if(error.status == 404){
            alert(error.message);
            this.router.navigate(['/login']);
          }
        });
  }


  register(registerForm){
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'withCredentials': 'true'
      })
    };

    this.http.post<Token>(environment.API_URL+"/auth/register/tuteur", registerForm, httpOptions).subscribe((resp: Token) => {
      var r = JSON.parse(JSON.stringify(resp));
      console.log("Token :");
      console.log(r);
      
    });
  }
}
