import { NgModule }              from '@angular/core';
import { RouterModule, Routes }  from '@angular/router';

import { LoginComponent } from './login/login.component';
import { PageNotFoundComponent } from './pagenotfound/pagenotfound.component';
import { RegisterComponent } from './register/register.component';
import { IndexComponent } from './index/index.component';

import { SigninGuard } from './services/guard/signin.guard';
import { NotSigninGuard } from './services/guard/not-signin.guard';
import { SeanceComponent } from './seance/seance.component';


const appRoutes: Routes = [

    
    { path: '',
      component: IndexComponent,
      canActivate: [SigninGuard]
    },
    {
      path: 'index',
      redirectTo: '',
      pathMatch: 'full'
    },
    {
      path: 'signin',
      component: LoginComponent,
      canActivate: [NotSigninGuard]
    },
    
    { path: 'register',
      component: RegisterComponent,
      canActivate: [NotSigninGuard]
    },
    { path: 'seance',
      component: SeanceComponent,
      canActivate: [SigninGuard]
    },
    

    { path: '**', component: PageNotFoundComponent }
  ];
  
  @NgModule({
    imports: [
        RouterModule.forRoot(
            appRoutes
          ),
    ],
    exports: [
      RouterModule
    ]
  })
  export class AppRoutingModule {}