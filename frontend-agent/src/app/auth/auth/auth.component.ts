import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { AuthService } from './service/auth.service';
import { NavigationStart, Router } from '@angular/router';
import { TokenStorageService } from './service/token-storage.service';
import { AuthSignupData } from '../../../model/authSignupData';
import { AuthLoginData } from 'src/model/authLoginData';
import { Subscription } from 'rxjs';
import { MatchingPassword } from './validator/matching-password.validator';
import { ToastrService } from 'ngx-toastr';

export let browserRefresh = false;
@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent implements OnInit {
  subscription: Subscription;
  isLoginMode = true;
  signupForm: FormGroup;
  loginForm: FormGroup;
  isSubmitted = false;
  isLoading = false;
  error: string = null;
  authRegistration: AuthSignupData;
  authLogin: AuthLoginData;
  isSignedUp = false;
  isSignUpFailed = false;
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  login: boolean = true;
  roles: string[] = [];


  constructor(private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private tokenStorage: TokenStorageService,
    private toastr: ToastrService) {

  }

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.router.navigate(['home']);
    }

    this.loginForm = this.formBuilder.group({
      'username': new FormControl(null, Validators.required),
      'password': new FormControl(null, Validators.required)
    });

    this.signupForm = this.formBuilder.group({
      'username': new FormControl(null, Validators.required),
      'password': new FormControl(null, Validators.required),
      'passwordConfirm': new FormControl(null, [Validators.required])

    },
      {
        validator: MatchingPassword('password', 'passwordConfirm')
      });
  }

  get f() { return this.signupForm.controls; }

  onSwitchMode() {
    this.isLoginMode = !this.isLoginMode;
  }


  signupSubmit() {

    const username = this.signupForm.value.username;
    const password = this.signupForm.value.password;

    this.authRegistration = new AuthSignupData(username, password);

    this.isLoading = true;

    this.authService.registration(this.authRegistration).subscribe(
      data => {
        this.isLoading = false;
        this.isSignedUp = true;
        this.isSignUpFailed = false;
        this.signupForm.reset();
        location.reload();
        this.toastr.success('Success!', 'Success');
      },
      error => {
        this.isLoading = false;
        this.isSignUpFailed = true;
        location.reload();
        this.toastr.error('Username is already taken!', 'Error');
      }
    );
    
  }


  onSubmitLogin() {

    const request = new AuthLoginData(this.loginForm.value.username, this.loginForm.value.password)


    this.authService.tryLogin(request).subscribe(
      data => {
        this.tokenStorage.saveToken(data.accessToken);
        this.toastr.success('You are logged in!', 'Success');
        location.assign("/posts");

      },
      error => {
        this.toastr.error('Error', 'Error');
      }
    );

  }

  switchForms() {
    this.login = !this.login;
    this.loginForm.reset();
    this.signupForm.reset();
  }


}
