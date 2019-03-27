import { Component, OnInit } from '@angular/core';
import { CalendarView, CalendarEventAction } from 'angular-calendar';
import { CalendarEvent } from 'calendar-utils';
import { Salle } from '../../responseBodies/Salle';

const colors: any = {
  red: {
    primary: '#ad2121',
    secondary: '#FAE3E3'
  },
  blue: {
    primary: '#1e90ff',
    secondary: '#D1E8FF'
  },
  yellow: {
    primary: '#e3bc08',
    secondary: '#FDF1BA'
  }
};

@Component({
  selector: 'app-seance-tuteur',
  templateUrl: './seance-tuteur.component.html',
  styleUrls: ['./seance-tuteur.component.css']
})
export class SeanceTuteurComponent implements OnInit {

  view : CalendarView = CalendarView.Week;
  viewDate: Date = new Date();
  currentWeekStart : Date = new Date();
  currentWeekEnd : Date = new Date();

  maxDate : Date = new Date(2019,8);
  minDate : Date = new Date(2018,8);

  locale : string = 'fr';

  actions: CalendarEventAction[] = [
    {
      label: '<i class="fas fa-edit"></i>',
      onClick: ({ event }: { event: CalendarEvent }): void => {
        //this.openUpdateEvent(event);
      }
    },
    {
      label: '<i class="fas fa-times"></i>',
      onClick: ({ event }: { event: CalendarEvent }): void => {
        //this.removeEvent(event);
      }
    }
  ];
  events: CalendarEvent[] = [];
  eventClicked : CalendarEvent;

  


  //Used to choose the room for a course.
  allSalles: Salle[];

  allOutilsAV: string[] = [
    "Skype",
    "Discord"
  ];
  
  eventBySalle: CalendarEvent[][] = [];


  constructor() { }

  ngOnInit() {
  }

}
