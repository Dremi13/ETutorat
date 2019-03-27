import { Component, OnInit } from '@angular/core';
import { CalendarView, CalendarEventAction } from 'angular-calendar';
import { CalendarEvent } from 'calendar-utils';

@Component({
  selector: 'app-seance',
  templateUrl: './seance.component.html',
  styleUrls: ['./seance.component.css']
})
export class SeanceComponent implements OnInit {

  type: string;

  constructor() { }

  ngOnInit() {
   
    this.type = localStorage.getItem("type");
  }

}
