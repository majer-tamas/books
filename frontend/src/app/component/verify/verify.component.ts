import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {LoginService} from '../../service/login.service';
import {getErrorMessage} from '../../util/getErrorMessage';

@Component({
             selector   : 'app-verify',
             templateUrl: './verify.component.html',
             styleUrls  : ['./verify.component.css'],
           })
export class VerifyComponent implements OnInit {

  hash: string = '';
  errorMessage: string = '';

  constructor(private activatedRoute: ActivatedRoute,
              private loginService: LoginService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.hash = this.activatedRoute.snapshot.params['hash'];
    this.loginService.verify(this.hash).subscribe(
      (response) => {
        console.log(response);
        this.router.navigate(['/successfully-verified']).then();
      }, error => this.errorMessage = getErrorMessage(error),
    );
  }

}
