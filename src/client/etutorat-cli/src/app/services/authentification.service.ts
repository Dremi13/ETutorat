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


  register(registerForm){
    return this.http.post<Token>(environment.API_URL+"/auth/register/tuteur", registerForm, {withCredentials: true})
  }

  

  signout(){
    return this.http.get<Token>(environment.API_URL+"/auth/signout",{withCredentials: true}) 
  }
  
  addToken(token: Token){
    console.log("TOKEN ENVOYE")
    this.token.next(token);
  }

  getToken(){
    return this.currentToken;
  }
}
