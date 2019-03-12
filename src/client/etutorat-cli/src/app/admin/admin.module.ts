import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';

import { AdminSigninComponent } from './admin-signin/admin-signin.component';
import { ReactiveFormsModule } from '@angular/forms';
import { CreateAdminComponent } from './create-admin/create-admin.component';


@NgModule({
  declarations: [AdminSigninComponent, CreateAdminComponent],
  imports: [
    ReactiveFormsModule,
    CommonModule,
    AdminRoutingModule
  ]
})
export class AdminModule { }
