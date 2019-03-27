import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { Router } from '@angular/router';


import { AuthentificationService } from '../services/authentification.service';
import { Token } from '../responseBodies/token';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {

  
  type: string;


  constructor(private as: AuthentificationService,
              private router: Router) { }

  ngOnInit() {
    this.type = localStorage.getItem("type");
  }

}
