import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Token } from '../responseBodies/token';





@Injectable({
  providedIn: 'root'
})
export class AuthentificationService {

  constructor(private http : HttpClient) { }


  checkSignin(){
    return this.http.get<Token>(environment.API_URL+"/auth/check",{"withCredentials": true})
  }


  signin(signinForm){
    return this.http.post<Token>(environment.API_URL+"/auth/signin", signinForm, {withCredentials: true});
  }


  register(registerForm){
    return this.http.post<Token>(environment.API_URL+"/auth/register/tuteur", registerForm, {withCredentials: true})
  }

  addTokenToStorage(token: Token){
    localStorage.setItem("login",token.login);
    localStorage.setItem("type",token.type);
  }
}
