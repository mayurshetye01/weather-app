import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {UserService} from '../_services/user.service';
import {Router} from '@angular/router';
import {HttpErrorResponse} from '@angular/common/http';
import {delay} from 'rxjs/operators';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  submitted = false;
  registerSuccessMsg: string;
  registerErrMsg: string;
  registrationForm = new FormGroup({
    username: new FormControl('', [
      Validators.required,
      Validators.email]),
    password: new FormControl('', [
      Validators.required,
      Validators.minLength(4)]),
    dateOfBirth: new FormControl('', [
      Validators.required])
  });

  onSubmit() {
    this.submitted = true;
    if (!this.registrationForm.valid) {
      return;
    }
    const user = this.registrationForm.value;
    this.userService.addUser(user)
      .subscribe(
        data => {
		  this.registerErrMsg = '';
          this.registerSuccessMsg = 'Registration successful. Navigating to login page';
          setTimeout(() => {
            this.router.navigate(['/login']);
          }, 3000);
        },
        error => {
          this.handleError(error);
        }
      );
    this.submitted = false;
  }

  constructor(private userService: UserService, private router: Router) {
  }

  ngOnInit() {
  }

  get f() {
    return this.registrationForm.controls;
  }

  handleError(error: HttpErrorResponse) {
    this.registerErrMsg = 'Registration failed';
  }

}
