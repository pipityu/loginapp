import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthenticationService } from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private authenticationService: AuthenticationService, private router: Router){}
  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    let url: string = state.url;
      return this.checkRole(next, url);
  }
  checkRole(route: ActivatedRouteSnapshot, url: any):boolean{
    console.log(url);
      if(localStorage.getItem('loggedIn')==='true'){
        const userRole = localStorage.getItem('role');
        if(userRole === 'ROLE_ADMIN'){ //admin akkor mehet
          return true;
        }
        else if(route.data.role !== userRole){//ha sima user de a védelem admin akkor stop
          //this.router.navigateByUrl('/login');
          return false;
        }
        return true;
      }else{
        this.router.navigateByUrl('/login');
        return false;
      }
    }
      
}
  
