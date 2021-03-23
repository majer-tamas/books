import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {LoginService} from '../../service/login.service';
import {RegexListItemModel} from '../../model/regexListItem.model';
import {Router} from '@angular/router';

@Component({
             selector   : 'app-sign-up',
             templateUrl: './sign-up.component.html',
             styleUrls  : ['./sign-up.component.css'],
           })
export class SignUpComponent implements OnInit {

  form: FormGroup;
  regexes: RegexListItemModel[] = [];
  usernameInvalidMessage: string | null = '';
  emailInvalidMessage: string | null = '';
  passwordInvalidMessage: string | null = '';

  constructor(private formBuilder: FormBuilder,
              private loginService: LoginService,
              private router: Router) {
    this.form = this.formBuilder.group({
                                         username: [null],
                                         password: [null],
                                         email   : [null],
                                       });
  }

  ngOnInit(): void {
    this.loginService.getRegexes().subscribe(
      (response) => {
        this.regexes = response;
        this.checkInputs(this.regexes);
      }, error => console.warn(error),
    );
  }

  checkInputs(regexes: RegexListItemModel[]) {
    const inputs = document.querySelectorAll('input');
    inputs.forEach((input) => {
      input.addEventListener('keyup', (e) => {
        this.regexes.forEach((regex) => {
          if (input.name === regex.name) {
            let regexForInput = new RegExp(regex.regex);
            let field = (e.target as HTMLInputElement);
            if (regexForInput.test(field.value)) {
              if (field.name === 'username') {
                this.usernameInvalidMessage = null;
                field.className = 'form-control valid';
                this.checkIfWantedUsernameIsTaken(field, field.value);
              }
              if (field.name === 'email') {
                this.emailInvalidMessage = null;
                field.className = 'form-control valid';
                this.checkIfWantedEmailIsTaken(field, field.value);
              }
              if (field.name === 'password') {
                this.passwordInvalidMessage = null;
                field.className = 'form-control valid';
              }
            } else {
              field.className = 'form-control invalid';
              if (field.name === 'username') {
                this.usernameInvalidMessage = 'Your username must be between 3 and 30 characters (English letters, digit).';
              }
              if (field.name === 'email') {
                this.emailInvalidMessage = 'Invalid email address.';
              }
              if (field.name === 'password') {
                this.passwordInvalidMessage = 'Your password must be between 8 and 20 characters, must contain at least one uppercase letter, must contain at least one lowercase letter, must contain at least one digit.';
              }
            }
          }
        });
      });
    });
  }

  checkIfWantedUsernameIsTaken(field: HTMLInputElement, wantedUsername: string) {
    this.loginService.checkIfWantedUsernameIsTaken(wantedUsername).subscribe(
      (taken) => {
        if (taken) {
          this.usernameInvalidMessage = 'That username is taken.';
          field.className = 'form-control invalid';
        } else {
          this.usernameInvalidMessage = null;
          field.className = 'form-control valid';
        }
      },
    );
  }

  checkIfWantedEmailIsTaken(field: HTMLInputElement, wantedEmail: string) {
    this.loginService.checkIfWantedEmailIsTaken(wantedEmail).subscribe(
      (taken) => {
        if (taken) {
          this.emailInvalidMessage = 'That email is taken.';
          field.className = 'form-control invalid';
        } else {
          this.emailInvalidMessage = null;
          field.className = 'form-control valid';
        }
      },
    );
  }

  allInputValid() {
    return this.usernameInvalidMessage === null &&
           this.passwordInvalidMessage === null &&
           this.emailInvalidMessage === null;
  }

  submit() {
    this.loginService.createNewUser(this.form.value).subscribe(
      (response) => {
        this.router.navigate(['successfully-registered']);
      }, error => console.warn(error),
    );
  }

}
