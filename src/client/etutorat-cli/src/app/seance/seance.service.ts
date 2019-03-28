import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { EventColor, CalendarEvent } from 'calendar-utils';
import { environment } from '../../environments/environment';
import { Seance } from '../responseBodies/seance';
import { Salle } from '../responseBodies/Salle';
import { Tuteur } from '../responseBodies/tuteur';

@Injectable({
  providedIn: 'root'
})
export class SeanceService {

  constructor(private http : HttpClient) { }

  red: EventColor = {primary: '#ad2121', secondary: '#FAE3E3'};

  getSeances(){
    return this.http.get<Seance[]>(environment.API_URL+"/getSeances",{withCredentials: true});
  }

  getSalles(){
    return this.http.get<Salle[]>(environment.API_URL+"/getSalles",{withCredentials: true});
  }

  createSeance(form){
    return this.http.post(environment.API_URL+"/createSeance",form,{withCredentials: true});
  }

  updateSeance(idSeance,form){
    return this.http.post(environment.API_URL+"/updateSeance",{id: idSeance, form: form},{withCredentials: true});
  }

  join(idSeance){
    return this.http.post(environment.API_URL+"/joinSeance",idSeance,{withCredentials: true});
  }

  unjoin(idSeance){
    return this.http.post(environment.API_URL+"/unjoinSeance",idSeance,{withCredentials: true});
  }

  getCurrentTuteur(){
    return this.http.get<Tuteur>(environment.API_URL+"/getCurrentTuteur",{withCredentials: true});
  }

  removeSeance(idSeance){
    return this.http.post(environment.API_URL+"/removeSeance",idSeance,{withCredentials: true});
  }
  

  convertSeanceToEvent(seance: Seance): CalendarEvent {

    return {
      start: new Date(seance.dateDebut),
      end: new Date(seance.dateFin),
      title: seance.sujet,
      color: this.red,
      resizable: {
        beforeStart: false,
        afterEnd: false,
      },
      draggable: false,
      meta: {
        id: seance.id,
        tuteur: seance.tuteur,
        outilAV: seance.outilAV,
        salle: seance.salle,
        nbmaxtutores: seance.nbmaxtutores,
        tutores: seance.tutores
      }
    }
  }
}
