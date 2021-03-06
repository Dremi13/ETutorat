import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Token } from '../responseBodies/token';
import { Subject, Observable } from 'rxjs';





@Injectable({
  providedIn: 'root'
})
export class AuthentificationService {

  constructor(private http : HttpClient) { }

  private token = new Subject<Token>();
  private currentToken = this.token.asObservable();
  

  checkSignin(){
    return this.http.get<Token>(environment.API_URL+"/auth/check",{withCredentials: true})
  }


  signin(signinForm){
    return this.http.post<Token>(environment.API_URL+"/auth/signin", signinForm, {withCredentials: true});
  }


  adminSignin(signinForm){
    return this.http.post<Token>(environment.API_URL+"/auth/"+environment.API_ADMIN, signinForm, {withCredentials: true})
  }


  registerTuteur(registerForm){
    return this.http.post<Token>(environment.API_URL+"/auth/register/tuteur", registerForm, {withCredentials: true})
  }

  registerTutore(registerForm){
    return this.http.post<Token>(environment.API_URL+"/auth/register/tutore", registerForm, {withCredentials: true})
  }

  

  signout(){
    return this.http.get<Token>(environment.API_URL+"/auth/signout",{withCredentials: true}) 
  }
  
  addToken(token: Token){
    if(token != null){
      localStorage.setItem("id",token.id.toString());
      localStorage.setItem("type",token.type);
      localStorage.setItem("login",token.login);
      localStorage.setItem("permission",token.permission.toString());
    }
    else {
      localStorage.removeItem("id");
      localStorage.removeItem("type");
      localStorage.removeItem("login");
      localStorage.removeItem("permission");
    }
    
    this.token.next(token);
  }

  getToken(){
    return this.currentToken;
  }
}
