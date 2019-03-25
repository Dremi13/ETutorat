import { Component, OnInit, ViewChild, TemplateRef } from '@angular/core';
import { CalendarEvent } from 'calendar-utils';
import { AdminSeanceService } from './admin-seance.service';
import { Seance } from '../../responseBodies/seance';
import { CalendarView, CalendarEventAction } from 'angular-calendar';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CalendarEventActionsComponent } from 'angular-calendar/modules/common/calendar-event-actions.component';
import { FormGroup, FormControl, Validators, ValidatorFn, ValidationErrors } from '@angular/forms';
import { Tuteur } from '../../responseBodies/tuteur';
import { AdminService } from '../services/admin.service';
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
  selector: 'app-admin-seance',
  templateUrl: './admin-seance.component.html',
  styleUrls: ['./admin-seance.component.css']
})
export class AdminSeanceComponent implements OnInit {

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
        this.openUpdateEvent(event);
      }
    },
    {
      label: '<i class="fas fa-times"></i>',
      onClick: ({ event }: { event: CalendarEvent }): void => {
        this.removeEvent(event);
      }
    }
  ];
  



  events: CalendarEvent[] = [];
  eventClicked : CalendarEvent;
  
  @ViewChild('updateEventModal') updateEventModal: TemplateRef<any>;
  @ViewChild('modalEvent') modalEvent: TemplateRef<any>;
  
  
  
  
  endAfterStart: ValidatorFn = (control: FormGroup): ValidationErrors | null => {
    const start = control.get('startTime').value;
    const end = control.get('endTime').value;
  
    return (start.hour > end.hour || (start.hour === end.hour && start.minute > end.minute)) ? { 'endBeforeStart': true } : null;
  };


  /*
   *  Insure than a course doesn't exceed the day hours (here 8.0 AM and 8 PM)
   */
  dayLimit: ValidatorFn = (control: FormGroup): ValidationErrors | null => {
    const start = control.get('startTime').value;
    const end = control.get('endTime').value;
  
    return (start.hour < 8 || start.hour > 20 || end.hour < 8 || end.hour > 20) ? { 'dayLimit': true } : null;
  };

  /*
   *  Insure than a course doesn't last more than two hours.
   */
  maxDuration: ValidatorFn = (control: FormGroup): ValidationErrors | null => {
    const start = control.get('startTime').value;
    const end = control.get('endTime').value;
  
    return (end.hour - start.hour > 2 || (end.hour - start.hour == 2 && end.minute - start.minute > 0)) ? { 'endBeforeStart': true } : null;
  };

  updateEventForm = new FormGroup({
    title: new FormControl('', [Validators.required]),
    date: new FormControl('', [Validators.required]),
    startTime: new FormControl('', [Validators.required]),
    endTime: new FormControl('', [Validators.required])
  }, { validators: [this.endAfterStart, this.dayLimit, this.maxDuration]});


  
  

  //Used to choose a Tuteur for a course.
  tuteur: Tuteur;
  allTuteurs : Tuteur[];


  //Used to choose the room for a course.
  salle: Salle;
  allSalles: Salle[];

  
  eventBySalle: Event[][];


  constructor(private adminSeanceService: AdminSeanceService,
              private adminService: AdminService,
              private modalService: NgbModal) {
    
  }

 

  ngOnInit() {

    this.getCurrentWeek();
    this.adminService.getAllTuteurs()
    .subscribe(
      (resp : Tuteur[]) => {
        this.allTuteurs = resp;
      },
      error => {
        if(error.status == 404){
        alert(error.message);
      }
    });

    this.adminSeanceService.getSalles()
    .subscribe(
      (resp : Salle[]) => {
        this.allSalles = resp;
      },
      error => {
        if(error.status == 404){
        alert(error.message);
      }
    });


    this.adminSeanceService.getSeances().subscribe(
      (resp : Seance[]) => {
        console.log(resp);
        var event : CalendarEvent;
        for(let seance of resp) {
          

          event = this.adminSeanceService.convertSeanceToEvent(seance);
          event.actions = this.actions;
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


  getCurrentWeek(){
    var currentDay = new Date();
    this.currentWeekStart.setDate(currentDay.getDate()-currentDay.getDay()+1);
    this.currentWeekEnd.setDate(currentDay.getDate()-currentDay.getDay()+8);

  }

  clickEvent(event){
    this.eventClicked = event;
   
    this.modalService.open(this.modalEvent).result.then(
      (result) => {
        this.eventClicked = null;
      },
      (reason) => {
        console.log("Annulation :"+reason);
        this.eventClicked = null;
      }
    
    
      );
  }

  //Même un administrateur ne peut pas modifier les informations primordiales => celle fausserait l'inscription des tutores. Par conséquent, seules le nombre maximal d'inscrits et la liste des inscrits peuvent être modifiés.
  openUpdateEvent(event: CalendarEvent){
    this.eventClicked = event;
    this.tuteur = event.meta.tuteur;
    this.salle = event.meta.salle;
    this.modalService.open(this.updateEventModal,{ size: 'lg' });
    this.updateEventForm.setValue({
      title: this.eventClicked.title,
      date: {year: this.eventClicked.start.getFullYear(), month: this.eventClicked.start.getMonth(), day: this.eventClicked.start.getDate()},
      startTime: {hour: this.eventClicked.start.getHours(), minute: this.eventClicked.start.getMinutes()},
      endTime: {hour: this.eventClicked.end.getHours(), minute: this.eventClicked.end.getMinutes()}

    });

  }

  updateEvent(){
    let start = this.createDatetime(this.updateEventForm.get("date").value,this.updateEventForm.get("startTime").value);
    let end = this.createDatetime(this.updateEventForm.get("date").value,this.updateEventForm.get("endTime").value);

    this.events = this.events.map(iEvent => {
      if (iEvent === this.eventClicked) {
        return {
          ...this.eventClicked,
          title: this.updateEventForm.get("title").value,
          start: start,
          end: end,
          meta: {
            tuteur: this.tuteur,
            outilAV: this.eventClicked.meta.outilAV,
            salle: this.salle,
            nbmax: this.eventClicked.meta.nbmax,
            tutores: this.eventClicked.meta.tutores
          }
        };
      }
      return iEvent;
    });
    this.modalService.dismissAll();
    console.log(this.updateEventForm.value);
    
  }

  createDatetime(date, time){
    return new Date(date.year,date.month,date.day,time.hour,time.minute);
  }

  chooseTuteur(tuteur: Tuteur){
    this.tuteur = tuteur;
  }

  chooseSalle(salle: Salle){
    this.salle = salle;
  }



  checkCollision(salle, datetime){
     
  }

  removeEvent(event){
    this.events = this.events.filter(iEvent => iEvent !== event);
    this.tuteur = null;
    this.salle = null;
  }


}
