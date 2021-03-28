import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HomeComponent} from './component/home/home.component';
import {MenuComponent} from './component/menu/menu.component';
import {LoginComponent} from './component/login/login.component';
import {ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {AuthInterceptor} from './util/authInterceptor';
import {VerifyComponent} from './component/verify/verify.component';
import {SignUpComponent} from './component/sign-up/sign-up.component';
import {SuccessfullyRegisteredComponent} from './component/successfully-registered/successfully-registered.component';
import {SuccessfullyVerifiedComponent} from './component/successfully-verified/successfully-verified.component';
import {LogoutComponent} from './component/logout/logout.component';
import { BookListComponent } from './component/book-list/book-list.component';

@NgModule({
            declarations: [
              AppComponent,
              HomeComponent,
              MenuComponent,
              LoginComponent,
              VerifyComponent,
              SignUpComponent,
              SuccessfullyRegisteredComponent,
              SuccessfullyVerifiedComponent,
              LogoutComponent,
              BookListComponent,
            ],
            imports     : [
              BrowserModule,
              AppRoutingModule,
              HttpClientModule,
              ReactiveFormsModule,
            ],
            providers   : [
              {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true},
            ],
            bootstrap   : [AppComponent],
          })
export class AppModule {
}
