import { Component, OnInit } from '@angular/core';
import { Tuteur } from '../../../../responseBodies/tuteur';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AdminService } from '../../../services/admin.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-tuteur-list',
  templateUrl: './tuteur-list.component.html',
  styleUrls: ['./tuteur-list.component.css']
})
export class TuteurListComponent implements OnInit {

  tuteurs: Tuteur[];
  currentIndex: number;
  currentTuteur: Tuteur;
  isCollapsed = true;
  alertSuccessOpened = false;
  alertDangerOpened = false;
  alertTuteur: Tuteur;
  timeOut;

  
  wrongFormAlertOpened = false;
  alreadyUse = [];
  timeOutOk;
  timeOutNotOk;

  inputNewMatiere: string;

  updateTuteurForm = new FormGroup({
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
                  this.currentTuteur = null;
               }



  ngOnInit() {
    this.admS.getAllTuteurs()
    .subscribe(
      (resp : Tuteur[]) => {
        this.tuteurs = resp;
        
      },
      error => {
        if(error.status == 404){
        alert(error.message);
        //this.router.navigate(['/signin']);
      }
    });

    this.admS.getListTuteur().subscribe((list : Tuteur[]) =>
    {

      if(list == null)  this.tuteurs = [];
      else              this.tuteurs = list;
    });
  }

  onClick(index){
    this.currentIndex = index;
    this.currentTuteur = this.tuteurs[index];
    this.isCollapsed = false;

    

  }

  openRemoveTuteurModal(content){
    this.modalService.open(content).result.then(
      (result) => {
        this.admS.removeTuteur(this.currentTuteur.id).subscribe((resp : Tuteur[]) => {
          this.admS.setListTuteur(resp);
          this.currentTuteur = null;
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

  openUpdateTuteurModal(content){

    this.updateTuteurForm.setValue({
      nom: this.currentTuteur.nom,
      prenom: this.currentTuteur.prenom,
      email: this.currentTuteur.email,
      filiere: this.currentTuteur.filiere,
      password: "",
      telephone: this.currentTuteur.telephone,
      codeetu: this.currentTuteur.codeetu
    });

    this.modalService.open(content, {size: 'lg'});
  
  }

  updateTuteur(){
    this.admS.updateTuteur(this.currentTuteur.id, this.updateTuteurForm.value).subscribe((resp : Tuteur[]) => {
      this.admS.setListTuteur(resp);
      this.currentTuteur = this.tuteurs[this.currentIndex];
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


  validerTuteur(bool: boolean){
    
    this.alertSuccessOpened = false;
    this.alertDangerOpened = false;
    this.alertTuteur = null;
    clearTimeout(this.timeOut);
    

    //Surement mauvais
    this.admS.validationTuteur(this.currentTuteur.id,bool).subscribe((resp : Tuteur[]) => {
      this.admS.setListTuteur(resp);
      this.currentTuteur.validationcompte = bool;
      this.alertTuteur = this.currentTuteur;
      if(bool){
        this.alertSuccessOpened = true;
        this.timeOut = setTimeout(() => {this.alertSuccessOpened = false; this.alertTuteur = null}, 3000);
      }
      else {
        this.alertDangerOpened = true;
        this.timeOut = setTimeout(() => {this.alertDangerOpened = false; this.alertTuteur = null}, 3000);
      }
    });
  }


  addCompetenceTuteur(){
    if(this.inputNewMatiere != "" && !this.currentTuteur.domaineDeCompetences.includes(this.inputNewMatiere)){
      

      this.admS.addCompetenceTuteur(this.currentTuteur.id,this.inputNewMatiere).subscribe((resp : Tuteur[]) => {
        this.admS.setListTuteur(resp);
        this.currentTuteur.domaineDeCompetences.push(this.inputNewMatiere);
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

  removeCompetenceTuteur(index){

      this.admS.removeCompetenceTuteur(this.currentTuteur.id,this.currentTuteur.domaineDeCompetences[index]).subscribe((resp : Tuteur[]) => {
        this.admS.setListTuteur(resp);
        this.currentTuteur.domaineDeCompetences.splice(index,1);
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
