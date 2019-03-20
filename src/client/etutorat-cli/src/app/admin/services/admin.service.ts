import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Admin } from '../../responseBodies/admin';
import { Subject } from 'rxjs';
import { Tuteur } from '../../responseBodies/tuteur';
import { Tutore } from '../../responseBodies/tutore';


@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http : HttpClient) { }


  private listAdmin = new Subject<Admin[]>();
  private currentListAdmin = this.listAdmin.asObservable();

  

  /*    Administrateurs
   */

  createAdmin(createAdminForm){
    return this.http.post<Admin[]>(environment.API_URL+"/"+environment.API_ADMIN+"/createAdmin",createAdminForm,{withCredentials: true});
  }


  getAllAdmins(){
    return this.http.get<Admin[]>(environment.API_URL+"/"+environment.API_ADMIN+"/getAllAdmins",{withCredentials: true});
  }

  updateAdmin(id, updateAdminForm){
    return this.http.post<Admin[]>(environment.API_URL+"/"+environment.API_ADMIN+"/updateAdmin",{id: id, form: updateAdminForm},{withCredentials: true});
  }

  removeAdmin(id){
    return this.http.post<Admin[]>(environment.API_URL+"/"+environment.API_ADMIN+"/removeAdmin",id,{withCredentials: true});
  }

  setListAdmin(list: Admin[]){
    this.listAdmin.next(list);
  }
  getListAdmin(){
    return this.currentListAdmin;
  }





  

  /*   Tuteurs
   */

  private listTuteur = new Subject<Tuteur[]>();
  private currentListTuteur = this.listTuteur.asObservable();

  createTuteur(createTuteurForm){
    return this.http.post<Tuteur[]>(environment.API_URL+"/"+environment.API_ADMIN+"/createTuteur",createTuteurForm,{withCredentials: true});
  }


  getAllTuteurs(){
    return this.http.get<Tuteur[]>(environment.API_URL+"/"+environment.API_ADMIN+"/getAllTuteurs",{withCredentials: true});
  }

  updateTuteur(id, updateTuteurForm){
    return this.http.post<Admin[]>(environment.API_URL+"/"+environment.API_ADMIN+"/updateTuteur",{id: id, form: updateTuteurForm},{withCredentials: true});
  }

  validationTuteur(id, bool){
    return this.http.post<Tuteur[]>(environment.API_URL+"/"+environment.API_ADMIN+"/validationTuteur",{id: id, validation: bool},{withCredentials: true});
  }

  addCompetenceTuteur(id, comp){
    return this.http.post<Tuteur[]>(environment.API_URL+"/"+environment.API_ADMIN+"/addCompetenceTuteur",{id: id, matiere: comp},{withCredentials: true});
  }

  removeCompetenceTuteur(id, comp){
    return this.http.post<Tuteur[]>(environment.API_URL+"/"+environment.API_ADMIN+"/removeCompetenceTuteur",{id: id, matiere: comp},{withCredentials: true});
  }

  removeTuteur(id){
    return this.http.post<Tuteur[]>(environment.API_URL+"/"+environment.API_ADMIN+"/removeTuteur",id,{withCredentials: true});
  }

  setListTuteur(list: Tuteur[]){
    this.listTuteur.next(list);
  }
  getListTuteur(){
    return this.currentListTuteur;
  }





  /*   Tutores
   */

  private listTutore = new Subject<Tutore[]>();
  private currentListTutore = this.listTutore.asObservable();

  createTutore(createTutoreForm){
    return this.http.post<Tutore[]>(environment.API_URL+"/"+environment.API_ADMIN+"/createTutore",createTutoreForm,{withCredentials: true});
  }


  getAllTutores(){
    return this.http.get<Tutore[]>(environment.API_URL+"/"+environment.API_ADMIN+"/getAllTutores",{withCredentials: true});
  }

  updateTutore(id, updateTutoreForm){
    return this.http.post<Tutore[]>(environment.API_URL+"/"+environment.API_ADMIN+"/updateTutore",{id: id, form: updateTutoreForm},{withCredentials: true});
  }

  addDemandeTutore(id, matiere){
    return this.http.post<Tutore[]>(environment.API_URL+"/"+environment.API_ADMIN+"/addDemandeTutore",{id: id, matiere: matiere},{withCredentials: true});
  }

  removeDemandeTutore(id, matiere){
    return this.http.post<Tutore[]>(environment.API_URL+"/"+environment.API_ADMIN+"/removeDemandeTutore",{id: id, matiere: matiere},{withCredentials: true});
  }

  removeTutore(id){
    return this.http.post<Tutore[]>(environment.API_URL+"/"+environment.API_ADMIN+"/removeTutore",id,{withCredentials: true});
  }

  setListTutore(list: Tutore[]){
    this.listTutore.next(list);
  }
  getListTutore(){
    return this.currentListTutore;
  }
  
}
