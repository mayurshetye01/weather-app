import { Injectable } from '@angular/core';
import { User } from '../_models/user';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {environment} from '../../environments/environment';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})

export class UserService {

  private baseUrl = environment.baseUrl;

  constructor(private http: HttpClient) { }

  addUser(user: User): Observable<User> {
    return this.http.post<User>(this.baseUrl + '/user', user, httpOptions);
  }

  getUser(user: User): Observable<User> {
    return this.http.post<User>(this.baseUrl + '/user', user, httpOptions);
  }

}
