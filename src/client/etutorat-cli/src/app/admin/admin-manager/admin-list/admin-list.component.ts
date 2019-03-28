import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { Admin } from '../../../responseBodies/admin';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-admin-list',
  templateUrl: './admin-list.component.html',
  styleUrls: ['./admin-list.component.css']
})
export class AdminListComponent implements OnInit {
  
  admins: Admin[];
  currentIndex: number;
  currentAdmin: Admin;
  public isCollapsed = true;
  
  updateAdminForm = new FormGroup({
    nom: new FormControl('', [Validators.required]),
    prenom: new FormControl('', [Validators.required]),
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required, Validators.minLength(4), Validators.maxLength(16)])
  });

  get nom() { return this.updateAdminForm.get('nom'); }
  get prenom() { return this.updateAdminForm.get('prenom'); }
  get email() { return this.updateAdminForm.get('email'); }
  get password() { return this.updateAdminForm.get('password'); }




  constructor(private admS: AdminService, private modalService: NgbModal) {
    this.currentAdmin = null;
  }



  ngOnInit() {
    this.admS.getAllAdmins()
    .subscribe(
      (resp : Admin[]) => {
        this.admins = resp;
        
      },
      error => {
        if(error.status == 404){
        alert(error.message);
        //this.router.navigate(['/signin']);
      }
    });

    this.admS.getListAdmin().subscribe((list : Admin[]) =>
    {

      if(list == null)  this.admins = [];
      else              this.admins = list;
    });
  }

  onClick(index){
    this.currentIndex = index;
    this.currentAdmin = this.admins[index];
    this.isCollapsed = false;

    this.updateAdminForm.setValue({
      nom: this.currentAdmin.nom,
      prenom: this.currentAdmin.prenom,
      email: this.currentAdmin.email,
      password: ""
    });

  }

  openRemoveAdminModal(content){
    this.modalService.open(content).result.then(
      (result) => {
        this.admS.removeAdmin(this.currentAdmin.id).subscribe((resp : Admin[]) => {
          this.admS.setListAdmin(resp);
          this.currentAdmin = null;
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
        
      });
  
  }

  openUpdateAdminModal(content){
    this.modalService.open(content, {size: 'lg'}).result.then(
      (result) => {
        this.admS.updateAdmin(this.currentAdmin.id, this.updateAdminForm.value).subscribe((resp : Admin[]) => {
          this.admS.setListAdmin(resp);
          this.currentAdmin = this.admins[this.currentIndex];
        },
        error => {
          if(error.status == 404){
          alert(error.message);
          //this.router.navigate(['/signin']);
        }
        });
      },
      (reason) => {

      });
  
  }


}
