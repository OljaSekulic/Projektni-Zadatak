import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { User } from '../../../../model/user';
import { Router } from '@angular/router';
import { AuthSignupData } from '../../../../model/authSignupData';
import { Post } from '../../../../model/post';



@Injectable({ providedIn: 'root' })
export class PostService {

    loggedUser = new BehaviorSubject<any>(null);

    user = new BehaviorSubject<User>(null);
    token: string = null;

    postUrl: string = "http://localhost:8080/api/post";
    allcommentsUrl: string = "http://localhost:8080/api/comment/all";
    newPostsUrl: string = "http://localhost:8080/api/post/newPosts";
    newCommentsUrl: string = "http://localhost:8080/api/post/newComments";

    constructor(private http: HttpClient,
        private router: Router) { }

    add(post: Post): Observable<Post> {
        return this.http.post<Post>(this.postUrl + "/add", post);
    }

    allPosts(): Observable<any> {
        return this.http.get(this.postUrl + '/all');
    }

    getUser(post: Post): Observable<any> {
        return this.http.get(this.postUrl + '/getUser');
    }

    update(id: number, post: Post) {
        return this.http.put(`${this.postUrl}/${id}`, post);
    }

    getId(post: Post): Observable<any> {
        return this.http.get(this.postUrl + '/getId');
    }

    delete(id: number): Observable<any> {
        return this.http.delete(`${this.postUrl}/${id}`);
    }

    getComments(id: number): Observable<any> {
        return this.http.get(`${this.allcommentsUrl}/${id}`);
    }

    newPosts(id: number): Observable<any> {
        return this.http.get(`${this.newPostsUrl}/${id}`);
    }

    newComments(id: number): Observable<any> {
        return this.http.get(`${this.newCommentsUrl}/${id}`);
    }
}