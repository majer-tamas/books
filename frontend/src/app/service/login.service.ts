import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {LoginFormModel} from '../model/loginForm.model';
import {TokenModel} from '../model/token.model';
import {RegexListItemModel} from '../model/regexListItem.model';
import {SignupFormModel} from '../model/signupForm.model';

const BASE_URL = 'http://localhost:8080/api/users';

@Injectable({
              providedIn: 'root',
            })
export class LoginService {

  constructor(private http: HttpClient) {
  }

  test(): Observable<any>{
    return this.http.get(BASE_URL + '/test');
  }

  login(data: LoginFormModel): Observable<TokenModel> {
    return this.http.post<TokenModel>(BASE_URL + '/authenticate', data);
  }

  verify(hash: string): Observable<any> {
    return this.http.get(BASE_URL + '/verify/' + hash);
  }

  getRegexes(): Observable<RegexListItemModel[]> {
    return this.http.get<RegexListItemModel[]>(BASE_URL + '/getRegexes');
  }

  checkIfWantedUsernameIsTaken(wantedUsername: string): Observable<boolean> {
    return this.http.post<boolean>(BASE_URL + '/checkIfWantedUsernameIsTaken', wantedUsername);
  }

  checkIfWantedEmailIsTaken(wantedEmail: string): Observable<boolean> {
    return this.http.post<boolean>(BASE_URL + '/checkIfWantedEmailIsTaken', wantedEmail);
  }

  createNewUser(data: SignupFormModel): Observable<any> {
    return this.http.post<any>(BASE_URL + '/register', data);
  }

}
