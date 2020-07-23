import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AuthenticationService} from '../_services/authentication.service';
import {Router} from '@angular/router';
import {HttpErrorResponse} from "@angular/common/http";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  submitted = false;
  loginErrMsg: string;
  loginForm = new FormGroup({
    username: new FormControl('', [
      Validators.required,
      Validators.email]),
    password: new FormControl('',
      [Validators.required,
        Validators.minLength(4)])
  });

  onSubmit() {
    this.submitted = true;
    if (!this.loginForm.valid) {
      return;
    }
    const user = this.loginForm.value;
    this.authenticationService.login(this.f.username.value, this.f.password.value)
      .subscribe(
        data => {
          if (localStorage.getItem('role') === 'ADMIN') {
            this.router.navigate(['/admin']);
          } else {
            this.router.navigate(['/home']);
          }
        },
        error => {
          this.handleError(error);
        }
      );
    this.submitted = false;
  }

  get f() {
    return this.loginForm.controls;
  }

  constructor(private authenticationService: AuthenticationService, private router: Router) {
  }

  ngOnInit() {
  }

  handleError(error: HttpErrorResponse) {
    if (error.status === 403 || error.status === 401) {
      this.loginErrMsg = 'Username/password incorrect';
    } else {
      this.loginErrMsg = 'Login failed. Please try again';
    }
  }

}
