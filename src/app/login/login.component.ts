import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../shared/authentication.service';




@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  title = "Login Page";
  

  constructor(public authenticationService:AuthenticationService) { }

  ngOnInit(): void {
    
  }  

}
