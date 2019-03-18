import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ListeseanceService } from '../services/listeseance.service';
import { Token } from '../responseBodies/token';


@Component({
  selector: 'app-listeseance',
  templateUrl: './listeseance.component.html',
  styleUrls: ['./listeseance.component.css']
})
export class ListeseanceComponent implements OnInit {
  listSeanceForm: FormGroup;

  constructor(private as: ListeseanceService,
    private router : Router) { }

  ngOnInit() {
    
  }

}
