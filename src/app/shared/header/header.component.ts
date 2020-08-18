import { Component, OnInit } from '@angular/core';
import { Global} from '../../shared/global';
import { AuthenticationService } from '../authentication.service';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(public app:Global, public authenticationService: AuthenticationService) { }
  ngOnInit(): void {

  }

  

  

}
