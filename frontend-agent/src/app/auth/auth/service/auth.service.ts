import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { User } from '../../../../model/user';
import { TokenStorageService } from './token-storage.service';
import { Router } from '@angular/router';
import { AuthSignupData } from '../../../../model/authSignupData';
import { LoggedUser } from 'src/model/loggedUser';
import { AuthLoginData } from 'src/model/authLoginData';
import { map, catchError } from 'rxjs/operators';

//Odgovor od servera
export interface AuthResponseData {
    accessToken: string;
    expiresIn: number;
    authorities: any;
    loggedUser: LoggedUser;
}

const httpOptions = {
    headers: new HttpHeaders({
        'Accept': 'application/json, text/plain',
        'Content-Type': 'application/json'
    })
};


@Injectable({ providedIn: 'root' })
export class AuthService {

    loggedUserSubject: BehaviorSubject<LoggedUser>;
    loggedUser: Observable<LoggedUser>;

    user = new BehaviorSubject<User>(null);
    token: string = null;

    signupUrl: string = "http://localhost:8080/api/auth/signup";
    loginUrl: string = "http://localhost:8080/api/auth/login";
    usersUrl: string = "http://localhost:8080/api/auth/users";
    whoamiUrl: string = "http://localhost:8080/api/auth/whoami";

    headers_object = new HttpHeaders().set("Authorization", "Bearer" + localStorage.getItem('token'));

    httpOptions = {
      headers: this.headers_object
    }

    constructor(private http: HttpClient,
        private tokenStorage: TokenStorageService,
        
        private router: Router) { 
        }

    registration(info: AuthSignupData): Observable<AuthResponseData> {
        return this.http.post<AuthResponseData>(this.signupUrl, info).pipe(catchError(this.handleException));
    }

    tryLogin(data: AuthLoginData): Observable<any> {
        return this.http.post<any>(this.loginUrl, data);
            // console.log(this.loggedUser)
          }

          getToken() {
            console.log(localStorage.getItem('token'))
            return localStorage.getItem('token');
          }

          whoami(token: string): Observable<User> {
            return this.http.get<User>(this.whoamiUrl, httpOptions);
          }

          getAllUsers(): Observable<AuthLoginData[]> {
            return this.http.get<AuthLoginData[]>(this.usersUrl, httpOptions);
          }

          private handleException(err: HttpErrorResponse): Observable<never> {
            return throwError(err.error);
          }
    }



    
      

     


