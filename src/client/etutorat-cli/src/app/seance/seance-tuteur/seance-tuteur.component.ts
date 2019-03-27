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
  selector: 'app-seance-tuteur',
  templateUrl: './seance-tuteur.component.html',
  styleUrls: ['./seance-tuteur.component.css']
})
export class SeanceTuteurComponent implements OnInit {



  currentUser: Tuteur;

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

  allOutilsAV: string[] = [
    "Skype",
    "Discord"
  ];


  //Used to choose the room for a course.
  allSalles: Salle[];


  eventBySalle: CalendarEvent[][] = [];


  endAfterStart: ValidatorFn = (control: FormGroup): ValidationErrors | null => {
    const start = control.get('startTime').value;
    const end = control.get('endTime').value;
  
    return (start.hour > end.hour || (start.hour === end.hour && start.minute >= end.minute)) ? { 'endBeforeStart': true } : null;
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
    endTime: new FormControl('', [Validators.required]),
    checkAV: new FormControl(false),
    outilAV: new FormControl(this.allOutilsAV[0]),
    salle: new FormControl(),
    tuteur: new FormControl()
  }, { validators: [this.endAfterStart, this.dayLimit, this.maxDuration]});


  createEventForm = new FormGroup({
    title: new FormControl('', [Validators.required]),
    date: new FormControl('', [Validators.required]),
    startTime: new FormControl('', [Validators.required]),
    endTime: new FormControl('', [Validators.required]),
    checkAV: new FormControl(false),
    outilAV: new FormControl(this.allOutilsAV[0]),
    salle: new FormControl(),
    tuteur: new FormControl()
  }, { validators: [this.endAfterStart, this.dayLimit, this.maxDuration]});

  compareFn(c1: any, c2:any): boolean {     
    return c1 && c2 ? c1.id === c2.id : c1 === c2; 
  }
  
  

  
  @ViewChild('updateEventModal') updateEventModal: TemplateRef<any>;
  @ViewChild('modalEvent') modalEvent: TemplateRef<any>;

  
  
  alertDangerOpened = false;
  timeOut;
  errorMessage: String;

  constructor(private seanceService: SeanceService,
    private modalService: NgbModal) { }

  ngOnInit() {

    this.getCurrentWeek();
    this.seanceService.getCurrentTuteur().subscribe(
      (resp : Tuteur) => {
        this.currentUser = resp;
        console.log(this.currentUser);
      }
    );


    this.seanceService.getSalles()
    .subscribe(
      (resp : Salle[]) => {
        this.allSalles = resp;

        for(var i = 0; i < this.allSalles.length; i++){
          this.eventBySalle[i] = new Array<CalendarEvent>();
        }

        
        this.createEventForm.get("salle").setValue(this.allSalles[0]);
        var today = new Date();
        this.createEventForm.get("date").setValue({year: today.getFullYear(), month: today.getMonth()+1, day: today.getDate()});
        
        if(today.getHours() > 20){
          this.createEventForm.get("startTime").setValue({hour: 19, minute: 45});
          this.createEventForm.get("endTime").setValue({hour: 20, minute: 0});
        }
        else if(today.getHours() < 8){
          this.createEventForm.get("startTime").setValue({hour: 8, minute: 0});
          this.createEventForm.get("endTime").setValue({hour: 8, minute: 15});
        }

        else {
          this.createEventForm.get("startTime").setValue({hour: today.getHours(), minute: today.getMinutes()});
          this.createEventForm.get("endTime").setValue({hour: today.getHours(), minute: today.getMinutes()+15});
        }
        
        

        console.log(this.createEventForm.get("date").value.year);


        this.seanceService.getSeances().subscribe(
              (resp : Seance[]) => {
                var event : CalendarEvent;
                for(let seance of resp) {
                  

                  event = this.seanceService.convertSeanceToEvent(seance);
                  if(seance.tuteur.id === this.currentUser.id)  event.actions = this.actions;
                  this.eventBySalle[seance.salle.id-1].push(event);
                  this.events = [...this.events,
                  event];
                  
                }
                this.createEventForm.get("tuteur").setValue(this.currentUser);
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

  //Même un administrateur ne peut pas modifier les informations primordiales => celle fausserait l'inscription des tutores. Par conséquent, seules le nombre maximal d'inscrits et la liste des inscrits peuvent être modifiés.
  openUpdateEvent(event: CalendarEvent){
    if(event.meta.tuteur.id !== this.currentUser.id) return;
    this.eventClicked = event;
    
    var salle = this.allSalles.find(s => s.id === this.eventClicked.meta.salle.id);

    this.updateEventForm.setValue({
      title: this.eventClicked.title,
      date: {year: this.eventClicked.start.getFullYear(), month: this.eventClicked.start.getMonth()+1, day: this.eventClicked.start.getDate()},
      startTime: {hour: this.eventClicked.start.getHours(), minute: this.eventClicked.start.getMinutes()},
      endTime: {hour: this.eventClicked.end.getHours(), minute: this.eventClicked.end.getMinutes()},
      checkAV: this.eventClicked.meta.outilAV !== "",
      outilAV: this.eventClicked.meta.outilAV === "" ? this.allOutilsAV[0] : this.eventClicked.meta.outilAV,
      tuteur: this.eventClicked.meta.tuteur,
      salle: salle
    });

    this.modalService.open(this.updateEventModal,{ size: 'lg' });

  }

  updateEvent(){
    if(this.eventClicked.meta.tuteur.id !== this.currentUser.id) return;

    let start = this.createDatetime(this.updateEventForm.get("date").value,this.updateEventForm.get("startTime").value);
    let end = this.createDatetime(this.updateEventForm.get("date").value,this.updateEventForm.get("endTime").value);

    var newEvent: CalendarEvent = {
      title: this.updateEventForm.get("title").value,
      actions: this.actions,
      color: colors.red,
      start: start,
      end: end,
      meta: {
          id: this.eventClicked.meta.id,
          tuteur: this.updateEventForm.get("tuteur").value,
          outilAV: this.updateEventForm.get("checkAV").value ? this.updateEventForm.get("outilAV").value : "",
          salle: this.updateEventForm.get("salle").value,
          nbmaxtutores: this.eventClicked.meta.nbmaxtutores,
          tutores: this.eventClicked.meta.tutores
        }
    };
    if(this.checkCollision(newEvent, this.eventClicked,this.updateEventForm)){
      return;
    }

    

    this.seanceService.updateSeance(newEvent.meta.id,{
      sujet: newEvent.title,
      start: newEvent.start,
      end: newEvent.end,
      outilAV: newEvent.meta.outilAV,
      tuteur: newEvent.meta.tuteur,
      salle: newEvent.meta.salle,
      nbmaxtutores: newEvent.meta.nbmaxtutores
    }).subscribe(
      resp => {
        this.events = this.events.map(iEvent => {
          if (iEvent === this.eventClicked) {
            return newEvent;
          }
          return iEvent;
        });

        this.eventBySalle[this.eventClicked.meta.salle.id-1] = this.eventBySalle[this.eventClicked.meta.salle.id-1].filter(iEvent => iEvent !== this.eventClicked);
        if(newEvent.meta.outilAV == "") this.eventBySalle[newEvent.meta.salle.id-1].push(newEvent);
        
        this.eventClicked = newEvent;
        this.modalService.dismissAll();
      },
      error => {
        console.log(error);
        if(error.status == 403){
          this.updateEventForm.setErrors({'tooManyHours': true});
          this.errorMessage = error.error.message;
          this.alertDangerOpened = true;
          this.timeOut = setTimeout(() => {this.alertDangerOpened = false; this.updateEventForm.setErrors({'tooManyHours': null})}, 10000);
        }
    });

    
    
  }


  createEvent(){
    let start = this.createDatetime(this.createEventForm.get("date").value,this.createEventForm.get("startTime").value);
    let end = this.createDatetime(this.createEventForm.get("date").value,this.createEventForm.get("endTime").value);

    var newSeance = {
      sujet: this.createEventForm.get("title").value,
      start: start,
      end: end,
      outilAV: this.createEventForm.get("checkAV").value ? this.createEventForm.get("outilAV").value : "",
      tuteur: this.createEventForm.get("tuteur").value,
      salle: this.createEventForm.get("salle").value,
      nbmaxtutores: 10
    }


    var newEvent: CalendarEvent = {
      title: this.createEventForm.get("title").value,
      actions: this.actions,
      color: colors.red,
      start: start,
      end: end,
      meta: {
          id: 0,
          tuteur: this.createEventForm.get("tuteur").value,
          outilAV: this.createEventForm.get("checkAV").value ? this.createEventForm.get("outilAV").value : "",
          salle: this.createEventForm.get("salle").value,
          nbmaxtutores: 10,
          tutores: []
        }
    };

    console.log(newSeance);
    if(this.checkCollision(newEvent,null,this.createEventForm)) return;

    this.seanceService.createSeance(newSeance).subscribe(
    (resp : Seance) => {
      newEvent.meta.id = resp.id;
      this.events = [...this.events,
        newEvent];
      if(newEvent.meta.outilAV === "") this.eventBySalle[newEvent.meta.salle.id-1].push(newEvent);
    },
    error => {
      if(error.status == 403){
        this.createEventForm.setErrors({'tooManyHours': true});
        this.errorMessage = error.error.message;
        this.alertDangerOpened = true;
        this.timeOut = setTimeout(() => {this.alertDangerOpened = false; this.createEventForm.setErrors({'tooManyHours': null})}, 10000);
      }
    });
  }



  //Minus 1 because every library choose another way to start a year. Noice.
  createDatetime(date, time){
    return new Date(date.year,date.month-1,date.day,time.hour,time.minute);
  }





  checkCollision(event: CalendarEvent, initialEvent: CalendarEvent, form: FormGroup) : boolean {
    
    if(event.meta.outilAV == "") {
      for(var ev of this.eventBySalle[event.meta.salle.id-1]){
        
        if(ev !== initialEvent){
          if( (event.start >= ev.start && event.start < ev.end) || (event.end <= ev.end && event.end > ev.start) || (event.start <= ev.start && event.end >= ev.end) ){
            form.setErrors({'collision' : true});
            this.alertDangerOpened = true;
            this.timeOut = setTimeout(() => {this.alertDangerOpened = false; form.setErrors({'collision': null})}, 3000);
            return true;
          }
        }
      }
    }
    return false;
  }

  removeEvent(event){
    if(event.meta.tuteur.id !== this.currentUser.id) return;
    this.seanceService.removeSeance(event.meta.id).subscribe(()=>{
      this.events = this.events.filter(iEvent => iEvent !== event);
      this.eventBySalle[event.meta.salle.id-1] = this.eventBySalle[event.meta.salle.id-1].filter(iEvent => iEvent !== event);
    });
    

  }

}
