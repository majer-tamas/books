import {Component, OnInit} from '@angular/core';
import {JWTTokenService} from '../../service/jwt-token.service';
import {LoginService} from '../../service/login.service';

@Component({
             selector   : 'app-home',
             templateUrl: './home.component.html',
             styleUrls  : ['./home.component.css'],
           })
export class HomeComponent implements OnInit {

  username: string = '';

  constructor(private jwtTokenService: JWTTokenService,
              private loginService: LoginService) {
  }

  ngOnInit(): void {
    this.username = this.jwtTokenService.getUsername();
  }

}
