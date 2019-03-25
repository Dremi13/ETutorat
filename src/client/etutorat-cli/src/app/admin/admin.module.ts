import { NgModule, LOCALE_ID } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';

import { registerLocaleData } from '@angular/common';
import localeFr from '@angular/common/locales/fr';
import localeFrExtra from '@angular/common/locales/extra/fr';



import { AdminRoutingModule } from './admin-routing.module';


import { AdminSigninComponent } from './admin-signin/admin-signin.component';
import { ReactiveFormsModule } from '@angular/forms';
import { CreateAdminComponent } from './admin-manager/create-admin/create-admin.component';
import { AdminListComponent } from './admin-manager/admin-list/admin-list.component';
import { AdminManagerComponent } from './admin-manager/admin-manager.component';
import { UserManagerComponent } from './user-manager/user-manager.component';
import { CreateUserComponent } from './user-manager/create-user/create-user.component';
import { UserListComponent } from './user-manager/user-list/user-list.component';
import { TuteurListComponent } from './user-manager/user-list/tuteur-list/tuteur-list.component';
import { TutoreListComponent } from './user-manager/user-list/tutore-list/tutore-list.component';
import { CreateTuteurComponent } from './user-manager/create-user/create-tuteur/create-tuteur.component';
import { CreateTutoreComponent } from './user-manager/create-user/create-tutore/create-tutore.component';
import { AdminSeanceComponent } from './admin-seance/admin-seance.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

registerLocaleData(localeFr, 'fr', localeFrExtra);

@NgModule({
  declarations: [AdminSigninComponent, CreateAdminComponent, AdminListComponent, AdminManagerComponent, UserManagerComponent, CreateUserComponent, UserListComponent, TuteurListComponent, TutoreListComponent, CreateTuteurComponent, CreateTutoreComponent, AdminSeanceComponent],
  imports: [
    BrowserAnimationsModule,
    CalendarModule.forRoot({
      provide: DateAdapter,
      useFactory: adapterFactory
    }),
    NgbModule,
    ReactiveFormsModule,
    CommonModule,
    FormsModule,
    AdminRoutingModule
  ],
  providers: [
    { provide: LOCALE_ID, useValue: "fr" }
  ]
 
})
export class AdminModule { }
