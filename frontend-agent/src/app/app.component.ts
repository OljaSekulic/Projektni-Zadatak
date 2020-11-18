import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthService } from './auth/auth/service/auth.service';
import { LoggedUser } from 'src/model/loggedUser';
import { BehaviorSubject, Observable } from 'rxjs';
import { Router } from '@angular/router';

export let browserRefresh = false;
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  par: any;
  user: any;
  loggedUserSubject: BehaviorSubject<LoggedUser>;
  loggedUser: Observable<LoggedUser>;

  title = 'Wall Service';
  constructor(private authService: AuthService,
    private router: Router) { }

  ngOnInit(): void {
    this.par = this.authService.getToken();

    if (this.par != null) {
      this.authService.whoami(this.par).subscribe(
        data =>
          this.user = data
      )
    }

  }

  logout() {
    localStorage.removeItem('token');
    this.user.username = null;
    this.router.navigate(['/auth']);
  }

  getUser() {
    if (this.par != null) {
      return this.user.username;
    } else {
      return " ";
    }
  }
}
