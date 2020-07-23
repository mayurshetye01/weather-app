import {Injectable} from '@angular/core';
import {map} from 'rxjs/operators';
import {Weather} from '../_models/weather';
import {HttpClient, HttpParams} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {DatePipe} from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class WeatherService {

  private baseUrl = environment.baseUrl;
  constructor(private http: HttpClient) {}

  getCurrentWeather(city: string) {
    const params = new HttpParams()
                    .set('city', city);

    return this.http.get<Weather>(this.baseUrl + '/weather/current', {params});
  }
}
