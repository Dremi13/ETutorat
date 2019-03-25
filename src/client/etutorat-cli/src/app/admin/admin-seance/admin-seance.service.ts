import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Seance } from '../../responseBodies/seance';
import { CalendarEvent, EventColor } from 'calendar-utils';
import { Salle } from '../../responseBodies/Salle';

@Injectable({
  providedIn: 'root'
})
export class AdminSeanceService {

  constructor(private http : HttpClient) { }

  red: EventColor = {primary: '#ad2121', secondary: '#FAE3E3'};

  getSeances(){
    return this.http.get<Seance[]>(environment.API_URL+"/"+environment.API_ADMIN+"/getSeances",{withCredentials: true});
  }

  getSalles(){
    return this.http.get<Salle[]>(environment.API_URL+"/"+environment.API_ADMIN+"/getSalles",{withCredentials: true});
  }


  convertSeanceToEvent(seance: Seance): CalendarEvent {
    return {
      start: new Date(seance.dateDebut),
      end: new Date(seance.dateFin),
      title: seance.sujet,
      color: this.red,
      //actions?: EventAction[];
      resizable: {
        beforeStart: false,
        afterEnd: false,
      },
      draggable: false,
      meta: {
        tuteur: seance.tuteur,
        outilAV: seance.outilAV,
        salle: seance.salle,
        nbmax: seance.nbmaxtutores,
        tutores: seance.tutores
      }
    }
  }
}
