import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/shared/user.service';
import { User } from '../user';
import { HttpClient } from '@angular/common/http';
import { AuthenticationService } from 'src/app/shared/authentication.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  user: User;
  successUpdate: string;
  errors=[];
  constructor(private authenticationService: AuthenticationService, public userService:UserService, private http: HttpClient) { }

  ngOnInit(): void {
    this.userService.getProfile().subscribe(user=>{
      this.user = user as User;
      //localStorage.setItem('id','');
    });
  }

  async userUpdate(){
    this.errors=[];
    let errors = this.userService.updateCheck();
    let credentials={username:localStorage.getItem('user'), password:this.userService.updateUserData.password}
    if(await this.authenticationService.pwCheck(credentials)){
      if(errors.length<1){
        this.userService.update().subscribe(response=>{
          if(response === 'OK'){
            this.userService.clear();
            window.location.reload();
          }
        });
      }else{
        this.errors=this.userService.errors;
        
      }
    }else{
      this.errors.push("Rossz jelszó");
    }
    this.userService.errors=[];
  }
  

}
