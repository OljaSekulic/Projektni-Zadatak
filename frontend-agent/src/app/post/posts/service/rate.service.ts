import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { Router } from '@angular/router';
import { Post } from '../../../../model/post';
import { User } from 'src/model/user';
import { Comment } from '../../../../model/comment';
import { Rate } from 'src/model/rate';


@Injectable({ providedIn: 'root' })
export class RateService {

    loggedUser = new BehaviorSubject<any>(null);

    user = new BehaviorSubject<User>(null);
    token: string = null;

    rateUrl: string = "http://localhost:8080/api/rate/add";
    avgUrl: string = "http://localhost:8080/api/rate/avg";

    constructor(private http: HttpClient,
        private router: Router) { }

    add(id: number, rate: Rate): Observable<Rate> {
        return this.http.post<Rate>(`${this.rateUrl}/${id}`, rate);
    }

    getAvg(id: number): Observable<any> {
        return this.http.get(`${this.avgUrl}/${id}`);
    }


}