import { Injectable, EventEmitter } from '@angular/core';

const TOKEN_KEY = 'token';
const USERNAME_KEY = 'username';
const AUTHORITIES_KEY = 'authorities';
const EXPIRE_KEY = 'date';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {
  private roles: Array<string> = [];
  eventEmitter = new EventEmitter(true);

  constructor() {}

  public saveToken(token: string) {
    window.localStorage.removeItem(TOKEN_KEY);
    window.localStorage.setItem(TOKEN_KEY, token);
    this.eventEmitter.emit();
  }

  public getToken(): string {
      return localStorage.getItem(TOKEN_KEY);
  }

  public saveUsername(username: string) {
    window.localStorage.removeItem(USERNAME_KEY);
    window.localStorage.setItem(USERNAME_KEY, username);
  }

  public getUsername(): string {
    return localStorage.getItem(USERNAME_KEY);
  }

  public saveAuthorities(authorities: string[]) {
    window.localStorage.removeItem(AUTHORITIES_KEY);
    window.localStorage.setItem(AUTHORITIES_KEY, JSON.stringify(authorities));
  }

  public getAuthorities(): string[] {
    this.roles = [];
    this.roles.push(this.getAuthority());
    return this.roles;
  }

  public getAuthority(): string {
    if (this.getToken()) {
      const roles = window.localStorage.getItem(AUTHORITIES_KEY);
      return roles.length === 0 ? null : JSON.parse(roles)['authority'];
    }

    return null;
  }

  public signOut() {
    window.localStorage.clear();
    this.eventEmitter.emit();
    
  }
}
 