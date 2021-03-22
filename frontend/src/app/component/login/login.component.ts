import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {LoginService} from '../../service/login.service';
import {Router} from '@angular/router';
import {JWTTokenService} from '../../service/jwt-token.service';
import {getErrorMessage} from '../../util/getErrorMessage';

@Component({
             selector   : 'app-login',
             templateUrl: './login.component.html',
             styleUrls  : ['./login.component.css'],
           })
export class LoginComponent implements OnInit {

  form: FormGroup;
  errorMessage: string = '';

  constructor(private formBuilder: FormBuilder,
              private loginService: LoginService,
              private router: Router,
              private jwtTokenService: JWTTokenService) {
    this.form = this.formBuilder.group({
                                         username: [null],
                                         password: [null],
                                       });
  }

  ngOnInit(): void {
  }

  submit() {
    this.loginService.login(this.form.value).subscribe(
      (response) => {
        this.jwtTokenService.saveToken(response.token);
        this.router.navigate(['/']).then();
      }, error => this.errorMessage = getErrorMessage(error),
    );
  }
}
