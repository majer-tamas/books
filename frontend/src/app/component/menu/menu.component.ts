import {Component, OnInit} from '@angular/core';
import {JWTTokenService} from '../../service/jwt-token.service';
import {Router} from '@angular/router';

@Component({
             selector   : 'app-menu',
             templateUrl: './menu.component.html',
             styleUrls  : ['./menu.component.css'],
           })
export class MenuComponent implements OnInit {

  isLoggedIn: boolean = false;

  constructor(private jwtTokenService: JWTTokenService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.isLoggedIn = this.jwtTokenService.isLoggedIn();
  }

  logout() {
    this.jwtTokenService.removeToken();
    this.router.navigate(['/logout']);
  }

}
