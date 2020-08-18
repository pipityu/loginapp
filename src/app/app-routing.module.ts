import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { AuthGuard } from './shared/auth.guard';
import { HomeComponent } from './user/home/home.component';
import { MyindexComponent } from './myindex/myindex.component';
import { ProfileComponent } from './user/profile/profile.component';
import { UserlistComponent } from './user/admin/userlist/userlist.component';


const routes: Routes = [
  {path: '', component: MyindexComponent},
  {path: 'login', component: LoginComponent},
  {path: 'user', 
    children: [
      {path: 'home', component: HomeComponent},
      {path: 'profile', component: ProfileComponent}
    ],
    canActivate:[AuthGuard],data:{role:'ROLE_USER'}
  },
  {path: 'user/admin',
    children:[
      {path: 'userlist', component: UserlistComponent}
    ],
    canActivate:[AuthGuard],data:{role:'ROLE_ADMIN'}
  },
  {path: '**',redirectTo:'/user/profile', canActivate:[AuthGuard]}//localStorage.getItem('redirect')
  
  // user/profile
  // admin/users

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }
