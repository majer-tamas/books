import {Injectable} from '@angular/core';
import jwt_decode from 'jwt-decode';
import {DecodedTokenModel} from '../model/decodedToken.model';

@Injectable({
              providedIn: 'root',
            })
export class JWTTokenService {

  constructor() {
  }

  decode(token: string): DecodedTokenModel {
    return jwt_decode(token);
  }

  saveToken(token: string) {
    localStorage.removeItem('token');
    localStorage.setItem('token', token);
  }

  removeToken() {
    localStorage.removeItem('token');
  }

  isLoggedIn(): boolean {
    let token = localStorage.getItem('token');
    return !!token;
  }

  getUsername(): string {
    let token = localStorage.getItem('token');
    return (token) ? '' + this.decode(token).sub : 'John Doe';
  }

  getAuthorities(): string[] {
    let token = localStorage.getItem('token');
    return (token) ? this.decode(token).authorities : [];
  }

  isUser(): boolean {
    return this.getAuthorities().includes('ROLE_USER');
  }

  isAdmin(): boolean {
    return this.getAuthorities().includes('ROLE_ADMIN');
  }

}
