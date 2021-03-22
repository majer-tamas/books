import {Component, OnInit} from '@angular/core';
import {JWTTokenService} from '../../service/jwt-token.service';

@Component({
             selector   : 'app-home',
             templateUrl: './home.component.html',
             styleUrls  : ['./home.component.css'],
           })
export class HomeComponent implements OnInit {

  username: string = '';

  constructor(private jwtTokenService: JWTTokenService) {
  }

  ngOnInit(): void {
    this.username = this.jwtTokenService.getUsername();
    this.jwtTokenService.isLoggedIn();
  }

}
