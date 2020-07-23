import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private baseUrl = environment.baseUrl;
  private token = 'token';
  private role = 'role';
  constructor(private http: HttpClient) { }

  login(username: string, password: string) {
    return this.http.post<any>(this.baseUrl + '/login', { username, password })
      .pipe(map(res => {
        if (res && res.token) {
          localStorage.setItem(this.token, res.token);
          localStorage.setItem(this.role, res.role);
        }
        return res;
      }));
  }

  logout() {
    localStorage.removeItem(this.token);
    localStorage.removeItem(this.role);
  }

}
