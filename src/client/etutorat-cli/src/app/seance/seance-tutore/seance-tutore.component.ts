import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { CalendarView, CalendarEventAction } from 'angular-calendar';
import { CalendarEvent } from 'calendar-utils';
import { Salle } from '../../responseBodies/Salle';
import { FormGroup, FormControl, Validators, ValidatorFn, ValidationErrors } from '@angular/forms';
import { SeanceService } from '../seance.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Seance } from '../../responseBodies/seance';
import { Tuteur } from '../../responseBodies/tuteur';


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
  selector: 'app-seance-tutore',
  templateUrl: './seance-tutore.component.html',
  styleUrls: ['./seance-tutore.component.css']
})
export class SeanceTutoreComponent implements OnInit {


  view : CalendarView = CalendarView.Week;
  viewDate: Date = new Date();
  currentWeekStart : Date = new Date();
  currentWeekEnd : Date = new Date();

  maxDate : Date = new Date(2019,8);
  minDate : Date = new Date(2018,8);

  locale : string = 'fr';

  
  events: CalendarEvent[] = [];
  eventClicked : CalendarEvent;

  allOutilsAV: string[] = [
    "Skype",
    "Discord"
  ];


  allSalles: Salle[];


  eventBySalle: CalendarEvent[][] = [];

  
  @ViewChild('modalEvent') modalEvent: TemplateRef<any>;


  constructor(private seanceService: SeanceService,
    private modalService: NgbModal) { }

  ngOnInit() {
    
    this.getCurrentWeek();
    
    this.seanceService.getSalles()
    .subscribe(
      (resp : Salle[]) => {
        this.allSalles = resp;

        for(var i = 0; i < this.allSalles.length; i++){
          this.eventBySalle[i] = new Array<CalendarEvent>();
        }

        this.seanceService.getSeances().subscribe(
              (resp : Seance[]) => {
                var event : CalendarEvent;
                var currentId = Number(localStorage.getItem("id"));
                for(let seance of resp) {
                  

                  event = this.seanceService.convertSeanceToEvent(seance);
                  if(seance.tutores.find(tut => tut.id == currentId)) event.color = colors.blue;
                  this.eventBySalle[seance.salle.id-1].push(event);
                  this.events = [...this.events,
                  event];
                  
                }
              },
              errorResponse => {
                if(errorResponse.status == 404){
                  alert(errorResponse.message);
                //this.router.navigate(['/signin']);
                }
                
            });
        
        
      },
      error => {
        if(error.status == 404){
        alert(error.message);
      }
    });
    
    
  }

  getCurrentWeek(){
    var currentDay = new Date();
    this.currentWeekStart.setDate(currentDay.getDate()-currentDay.getDay()+1);
    this.currentWeekEnd.setDate(currentDay.getDate()-currentDay.getDay()+8);

  }

  clickEvent(event){
    this.eventClicked = event;
   
    this.modalService.open(this.modalEvent,{size: "sm", centered: true }).result.then(
      (result) => {
        this.eventClicked = null;
      },
      (reason) => {
        this.eventClicked = null;
      }
      );
  }

  join(){

    if(this.eventClicked.meta.nbmaxtutores >= this.eventClicked.meta.tutores.length+1){
      this.seanceService.join(this.eventClicked.meta.id).subscribe(
        () => {
          var newEvent = this.eventClicked;
          newEvent.color = colors.blue;

          this.events = this.events.map(iEvent => {
            if (iEvent === this.eventClicked) {
              return newEvent;
            }
            return iEvent;
          });
          this.modalService.dismissAll();
        }
      );

      
    }
    
  }

}
