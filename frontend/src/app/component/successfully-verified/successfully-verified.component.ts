import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

@Component({
             selector   : 'app-successfully-verified',
             templateUrl: './successfully-verified.component.html',
             styleUrls  : ['./successfully-verified.component.css'],
           })
export class SuccessfullyVerifiedComponent implements OnInit {

  constructor(private router: Router) {
  }

  ngOnInit(): void {
    setTimeout(() => {
      this.router.navigate(['login']).then();
    }, 5000);
  }

}
