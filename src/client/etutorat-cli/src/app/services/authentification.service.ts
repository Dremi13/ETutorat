import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class AuthentificationService {

  constructor(private http : HttpClient) { }


  checkSignin(){
    this.http.get(environment.API_URL+"/auth/check").subscribe(resp => {
      //console.log(resp);
    });
  }


  signin(signinForm){


    console.log(signinForm);

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json'
      })
    };

    this.http.post(environment.API_URL+"/auth/signin", signinForm, httpOptions).subscribe(resp => {
      console.log(resp);
    });
  }


  register(registerForm){
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json'
      })
    };

    this.http.post(environment.API_URL+"/auth/register/tuteur", registerForm, httpOptions).subscribe(resp => {
      console.log(resp);
    });
  }
}
