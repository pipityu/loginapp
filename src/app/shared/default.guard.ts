import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DefaultGuard implements CanActivate {
  constructor(private router: Router){}
  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    let url: string = state.url;
    return this.checkRoute(next, url);
  }

  checkRoute(route: ActivatedRouteSnapshot, url: any):boolean{
      if(localStorage.getItem('loggedIn')==='true'){
        this.router.navigateByUrl('/user/profile');
        return false;
      }else{
        this.router.navigateByUrl('/login');
        return false;
      }   
  }
  
}
