import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {HomeComponent} from './component/home/home.component';
import {LoginComponent} from './component/login/login.component';
import {VerifyComponent} from './component/verify/verify.component';
import {SignUpComponent} from './component/sign-up/sign-up.component';
import {SuccessfullyRegisteredComponent} from './component/successfully-registered/successfully-registered.component';
import {SuccessfullyVerifiedComponent} from './component/successfully-verified/successfully-verified.component';
import {LogoutComponent} from './component/logout/logout.component';
import {BookListComponent} from './component/book-list/book-list.component';
import {BookFormComponent} from './component/book-form/book-form.component';
import {BookDetailsComponent} from './component/book-details/book-details.component';

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'logout', component: LogoutComponent},
  {path: 'verify/:hash', component: VerifyComponent},
  {path: 'signup', component: SignUpComponent},
  {path: 'successfully-registered', component: SuccessfullyRegisteredComponent},
  {path: 'successfully-verified', component: SuccessfullyVerifiedComponent},
  {path: 'book-list', component: BookListComponent},
  {path: 'add-book', component: BookFormComponent},
  {path: 'book-details/:id', component: BookDetailsComponent},
];

@NgModule({
            imports: [RouterModule.forRoot(routes)],
            exports: [RouterModule],
          })
export class AppRoutingModule {
}
