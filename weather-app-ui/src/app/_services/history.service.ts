import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Weather} from '../_models/weather';

@Injectable({
  providedIn: 'root'
})
export class HistoryService {

  private baseUrl = environment.baseUrl;
  private historyUrl = '/history';

  constructor(private http: HttpClient) {
  }

  getAllHistory() {
    return this.http.get<Map<string, Weather[]>>(this.baseUrl + '/admin' + this.historyUrl);
  }

  getCurrentUserHistory() {
    return this.http.get<Weather[]>(this.baseUrl + this.historyUrl);
  }

  updateHistory(weatherHistory: Weather) {
    return this.http.put(this.baseUrl + this.historyUrl, weatherHistory);
  }

  deleteHistory(city: string) {
    const params = new HttpParams().set('city', city);
    return this.http.delete(this.baseUrl + this.historyUrl, {params});
  }

  bulkDeleteHistory() {
    return this.http.delete(this.baseUrl + this.historyUrl + '/bulk');
  }
}
