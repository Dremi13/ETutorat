import { Component, OnInit } from '@angular/core';
import { CalendarEvent } from 'calendar-utils';
import { AdminSeanceService } from './admin-seance.service';
import { Seance } from '../../responseBodies/seance';

@Component({
  selector: 'app-admin-seance',
  templateUrl: './admin-seance.component.html',
  styleUrls: ['./admin-seance.component.css']
})
export class AdminSeanceComponent implements OnInit {

  viewDate: Date = new Date();
  events : CalendarEvent[] = [
    {
      start: new Date(2019,2,18,15,20),
      end: new Date(2019,2,18,16,20),
      title: "TESTONS CAAAA",
      color: {primary: '#ad2121', secondary: '#FAE3E3'}
    },
    {
      start: new Date(2019,2,18,15,40),
      end: new Date(2019,2,18,16,40),
      title: "TESTONS CAAAA",
      color: {
        primary: '#1e90ff',
        secondary: '#D1E8FF'
      }
    },
    {
      start: new Date(2019,2,18,15,20),
      end: new Date(2019,2,18,16,20),
      title: "TESTONS CAAAA",
      color: {primary: '#ad2121', secondary: '#FAE3E3'}
    },
    {
      start: new Date(2019,2,18,15,40),
      end: new Date(2019,2,18,16,40),
      title: "TESTONS CAAAA",
      color: {
        primary: '#1e90ff',
        secondary: '#D1E8FF'
      }
    }

];
  

  

  constructor(private adminSeanceService: AdminSeanceService) {
    
  }

  ngOnInit() {
    this.adminSeanceService.getSeances().subscribe(
      (resp : Seance[]) => {
        console.log(resp);
        var event : CalendarEvent;
        for(let seance of resp) {
          console.log(seance.dateDebut);
          console.log(new Date(seance.dateDebut));

          event = this.adminSeanceService.convertSeanceToEvent(seance);
          this.events = [...this.events,
          event];
          console.log(this.events);
        }


      },
      errorResponse => {
        if(errorResponse.status == 404){
          alert(errorResponse.message);
        //this.router.navigate(['/signin']);
        }
        
    });
  }

}
