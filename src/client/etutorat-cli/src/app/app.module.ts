import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule }    from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';


import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { HeaderComponent } from './header/header.component';
import { PageNotFoundComponent } from './pagenotfound/pagenotfound.component';
import { RegisterComponent } from './register/register.component';
import { IndexComponent } from './index/index.component';
import { AdminIndexComponent } from './admin/admin-index/admin-index.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AdminModule } from './admin/admin.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { RegisterTuteurComponent } from './register/register-tuteur/register-tuteur.component';
import { RegisterTutoreComponent } from './register/register-tutore/register-tutore.component';
import { SeanceComponent } from './seance/seance.component';
import { SeanceTuteurComponent } from './seance/seance-tuteur/seance-tuteur.component';
import { SeanceTutoreComponent } from './seance/seance-tutore/seance-tutore.component';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';






@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HeaderComponent,
    PageNotFoundComponent,
    RegisterComponent,
    IndexComponent,
    AdminIndexComponent,
    RegisterTuteurComponent,
    RegisterTutoreComponent,
    SeanceComponent,
    SeanceTuteurComponent,
    SeanceTutoreComponent
  ],
  imports: [
    NgbModule,
    ReactiveFormsModule,
    AdminModule,
    FormsModule, 
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    CalendarModule.forRoot({
      provide: DateAdapter,
      useFactory: adapterFactory
    }),
    
    
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
