import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/shared/user.service';
import { User } from '../../user';
import { Observable } from 'rxjs';
import { AuthenticationService } from 'src/app/shared/authentication.service';

@Component({
  selector: 'app-userlist',
  templateUrl: './userlist.component.html',
  styleUrls: ['./userlist.component.css']
})
export class UserlistComponent implements OnInit {
  successUpdate: string;
  users: User[];
  errors: String[];
  constructor(public userService:UserService, private authenticationService: AuthenticationService) { }

  ngOnInit(): void {
    this.userService.getAllUser().subscribe(users=>{
      this.users = users as User[];
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
            this.successUpdate = response;
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

  async newUser(){
    this.errors=[];
    let errors = this.userService.updateCheck();
    let credentials={username:localStorage.getItem('user'), password:this.userService.updateUserData.password}
    if(await this.authenticationService.pwCheck(credentials)){
      if(errors.length<1){
        this.userService.newUser().subscribe(response=>{
          if(response === 'OK'){
            this.userService.clear();
            this.successUpdate = response;
            this.userService.newUserEmail='';
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

  deleteUser(){
    this.userService.deleteUser().subscribe(response=>{
      if(response){
        window.location.reload();
      }
    });
  }

}
