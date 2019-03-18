import { NgModule }              from '@angular/core';
import { RouterModule, Routes }  from '@angular/router';

import { LoginComponent } from './login/login.component';
import { PageNotFoundComponent } from './pagenotfound/pagenotfound.component';
import { RegisterComponent } from './register/register.component';
import { IndexComponent } from './index/index.component';
import { ListeseanceComponent } from './listeseance/listeseance.component';


const appRoutes: Routes = [
    {
      path: 'login',
      component: LoginComponent
    },
    { path: 'lister mes séances',
      component: ListeseanceComponent
    },
    { path: '',
      component: IndexComponent
    },
    { path: 'register',
      component: RegisterComponent
    },
    { path: '**', component: PageNotFoundComponent }
  ];
  
  @NgModule({
    imports: [
        RouterModule.forRoot(
            appRoutes,
            { enableTracing: true } // <-- debugging purposes only
          ),
    ],
    exports: [
      RouterModule
    ]
  })
  export class AppRoutingModule {}