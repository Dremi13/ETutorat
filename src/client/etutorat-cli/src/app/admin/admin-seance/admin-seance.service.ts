import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Seance } from '../../responseBodies/seance';
import { CalendarEvent, EventColor } from 'calendar-utils';

@Injectable({
  providedIn: 'root'
})
export class AdminSeanceService {

  constructor(private http : HttpClient) { }

  red: EventColor = {primary: '#ad2121', secondary: '#FAE3E3'};

  getSeances(){
    return this.http.get<Seance[]>(environment.API_URL+"/"+environment.API_ADMIN+"/getSeances",{withCredentials: true});
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
      draggable: false
    }
  }
}
