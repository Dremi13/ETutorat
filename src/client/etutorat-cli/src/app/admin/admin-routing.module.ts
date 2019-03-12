import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdminGuard } from './guard/admin.guard';
import { AdminIndexComponent } from './admin-index/admin-index.component';
import { environment } from '../../environments/environment';
import { AdminSigninComponent } from './admin-signin/admin-signin.component';
import { AdminNotsigninGuard } from './guard/admin-notsignin.guard';


const routes: Routes = [
  {
    path: environment.API_ADMIN,
    component: AdminIndexComponent,
    canActivate: [AdminGuard]
  },
  {
    path: environment.API_ADMIN+"/index",
    redirectTo: environment.API_ADMIN
  },
  {
    path: environment.API_ADMIN+"/signin",
    component: AdminSigninComponent,
    canActivate: [AdminNotsigninGuard]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
