import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { Router } from '@angular/router';
import { Post } from '../../../../model/post';
import { User } from 'src/model/user';
import { Comment } from '../../../../model/comment';


@Injectable({ providedIn: 'root' })
export class CommentService {

    loggedUser = new BehaviorSubject<any>(null);

    user = new BehaviorSubject<User>(null);
    token: string = null;

    addcommentUrl: string = "http://localhost:8080/api/comment/add";
    editcommentUrl: string = "http://localhost:8080/api/comment";
    deletecommentUrl: string = "http://localhost:8080/api/comment";

    constructor(private http: HttpClient,
        private router: Router) { }

    add(id: number, comment: Comment): Observable<Comment> {
        return this.http.post<Post>(`${this.addcommentUrl}/${id}`, comment);
    }

    update(id: number, comment: Comment) {
        return this.http.put(`${this.editcommentUrl}/${id}`, comment);
    }

    delete(id: number): Observable<any> {
        return this.http.delete(`${this.deletecommentUrl}/${id}`);
    }


}