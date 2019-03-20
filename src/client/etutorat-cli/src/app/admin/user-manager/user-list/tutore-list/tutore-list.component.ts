import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { AdminService } from '../../../services/admin.service';
import { Tutore } from '../../../../responseBodies/tutore';


@Component({
  selector: 'app-tutore-list',
  templateUrl: './tutore-list.component.html',
  styleUrls: ['./tutore-list.component.css']
})
export class TutoreListComponent implements OnInit {

  tutores: Tutore[];
  currentIndex: number;
  currentTutore: Tutore;
  isCollapsed = true;
  alertSuccessOpened = false;
  alertDangerOpened = false;
  alertTutore: Tutore;
  timeOut;

  wrongFormAlertOpened = false;
  alreadyUse = [];
  timeOutOk;
  timeOutNotOk;

  inputNewMatiere: string;

  updateTutoreForm = new FormGroup({
    nom: new FormControl('', [Validators.required]),
    prenom: new FormControl('', [Validators.required]),
    codeetu: new FormControl('', [Validators.required, Validators.pattern('[a-z][0-9]{8}')]),
    telephone: new FormControl('',[Validators.required, Validators.pattern('[0-9]{10}')]),
    filiere: new FormControl('',[Validators.required]),
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required, Validators.minLength(4), Validators.maxLength(16)])
  });

  constructor(private admS: AdminService,
              private modalService: NgbModal) {
                  this.currentTutore = null;
               }



  ngOnInit() {
    this.admS.getAllTutores()
    .subscribe(
      (resp : Tutore[]) => {
        this.tutores = resp;
        console.log(resp);
      },
      error => {
        if(error.status == 404){
        alert(error.message);
        //this.router.navigate(['/signin']);
      }
    });

    this.admS.getListTutore().subscribe((list : Tutore[]) =>
    {
      console.log(list);
      if(list == null)  this.tutores = [];
      else              this.tutores = list;
    });
  }

  onClick(index){
    this.currentIndex = index;
    this.currentTutore = this.tutores[index];
    this.isCollapsed = false;

    

  }

  openRemoveTutoreModal(content){
    console.log(this.currentTutore.id);
    this.modalService.open(content).result.then(
      (result) => {
        this.admS.removeTutore(this.currentTutore.id).subscribe((resp : Tutore[]) => {
          this.admS.setListTutore(resp);
          this.currentTutore = null;
          this.isCollapsed = true;
          
        },
        error => {
          if(error.status == 404){
          alert(error.message);
          //this.router.navigate(['/signin']);
        }
        });
      },
      (reason) => {
        console.log("Annulation :"+reason);
      });
  
  }

  openUpdateTutoreModal(content){

    this.updateTutoreForm.setValue({
      nom: this.currentTutore.nom,
      prenom: this.currentTutore.prenom,
      email: this.currentTutore.email,
      filiere: this.currentTutore.filiere,
      password: "",
      telephone: this.currentTutore.telephone,
      codeetu: this.currentTutore.codeetu
    });

    this.modalService.open(content, {size: 'lg'});
  
  }

  updateTuteur(){
    this.admS.updateTutore(this.currentTutore.id, this.updateTutoreForm.value).subscribe((resp : Tutore[]) => {
      this.admS.setListTutore(resp);
      this.currentTutore = this.tutores[this.currentIndex];
      this.modalService.dismissAll();
    },
    errorResponse => {
      if(errorResponse.status == 404){
        alert(errorResponse.message);
      //this.router.navigate(['/signin']);
      }
      if(errorResponse.status == 409){
        this.alreadyUse = errorResponse.error.message.split('/');
        this.alreadyUse.shift();
        this.alreadyUse = this.alreadyUse.map(function(x) { if(x === "email") return "Email"; if(x === "codeetu") return "Code étudiant"; });

        this.wrongFormAlertOpened = true;
        this.timeOutNotOk = setTimeout(() => this.wrongFormAlertOpened = false, 5000);
        
      }
    });
  }



  addDemandeTutore(){
    if(this.inputNewMatiere != "" && !this.currentTutore.demandes.includes(this.inputNewMatiere)){
      

      this.admS.addDemandeTutore(this.currentTutore.id,this.inputNewMatiere).subscribe((resp : Tutore[]) => {
        this.admS.setListTutore(resp);
        this.currentTutore.demandes.push(this.inputNewMatiere);
        this.inputNewMatiere = "";
        
      },
      error => {
        if(error.status == 404){
        alert(error.message);
        //this.router.navigate(['/signin']);
      }
      });

    }
    else {
      //nothing
    }
  }

  removeDemandeTutore(index){

      this.admS.removeDemandeTutore(this.currentTutore.id,this.currentTutore.demandes[index]).subscribe((resp : Tutore[]) => {
        this.admS.setListTutore(resp);
        this.currentTutore.demandes.splice(index,1);
        this.inputNewMatiere = "";
        
      },
      error => {
        if(error.status == 404){
        alert(error.message);
        //this.router.navigate(['/signin']);
      }
      });

  }
    
  

}
