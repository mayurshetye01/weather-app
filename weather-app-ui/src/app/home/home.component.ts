import {Component, OnInit} from '@angular/core';
import {Weather} from '../_models/weather';
import {WeatherService} from '../_services/weather.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {HistoryService} from '../_services/history.service';
import {AuthenticationService} from '../_services/authentication.service';
import {HttpErrorResponse} from '@angular/common/http';
import {Router} from '@angular/router';
import {DatePipe} from '@angular/common';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})

export class HomeComponent implements OnInit {
  datePipe = new DatePipe('en-US');
  dateFormat = 'short';
  submitted = false;
  errorMsg: string;
  currentWeatherData: Weather[];
  userSearchHistory: Weather[];
  enableEdit = false;
  currActiveRow: number;
  updateErrMsgs: string[];

  searchForm = new FormGroup({
    searchCity: new FormControl('', [Validators.required])
  });

  onSearch() {
    this.submitted = true;
    if (!this.searchForm.valid) {
      return;
    }
    const searchCity = this.searchForm.controls.searchCity.value;
    this.weatherService.getCurrentWeather(searchCity)
      .subscribe((data: Weather) => {
          this.currentWeatherData = [];
          this.currentWeatherData.push(data);
          this.errorMsg = '';
          this.getUserSearchHistory();
        },
        error => {
          this.handleError(error);
        }
      );
    this.submitted = false;
    this.updateErrMsgs = [];
  }

  onLogout() {
    this.authenticationService.logout();
  }

  getUserSearchHistory() {
    this.historyService.getCurrentUserHistory()
      .subscribe((dataArr: Weather[]) => {
          this.userSearchHistory = dataArr;
        },
        error => {
          this.handleError(error);
        }
      );
  }

  delete(city: string) {
    this.historyService.deleteHistory(city)
      .subscribe(() => {
          this.getUserSearchHistory();
        },
        error => {
          this.handleError(error);
        }
      );
  }

  update(history: Weather) {
    this.enableEdit = false;
    if (!this.valid(history)) {
      this.getUserSearchHistory();
      return;
    }

    history.sunset = new Date(this.datePipe.transform(history.sunset, this.dateFormat));
    history.sunrise = new Date(this.datePipe.transform(history.sunrise, this.dateFormat));

    this.historyService.updateHistory(history)
      .subscribe(() => {
          this.getUserSearchHistory();
        },
        error => {
          this.handleError(error);
        }
      );
  }

  bulkDelete() {
    this.historyService.bulkDeleteHistory()
      .subscribe(() => {
          this.getUserSearchHistory();
        },
        error => {
          this.handleError(error);
        });
  }

  constructor(private weatherService: WeatherService,
              private historyService: HistoryService,
              private authenticationService: AuthenticationService,
              private router: Router) {
  }

  ngOnInit() {
    this.getUserSearchHistory();
  }

  onRowClick(rowNum: number) {
    this.enableEdit = true;
    this.currActiveRow = rowNum;
    this.updateErrMsgs = [];
  }

  isEditable(rowNum: number) {
    return this.enableEdit && (rowNum === this.currActiveRow);
  }

  get f() {
    return this.searchForm.controls;
  }

  handleError(error: HttpErrorResponse) {
    if (error.status === 403 || error.status === 401) {
      this.router.navigate(['/login']);
    } else if (error.status === 400) {
      this.errorMsg = 'No weather data found for this city';
    } else {
      this.errorMsg = 'Some error occurred. Please try again';
    }
  }

  valid(history: Weather) {
    this.updateErrMsgs = [];
    if (history.temp === null || isNaN(history.temp)) {
      this.updateErrMsgs.push('Invalid temperature value');
    }
    if (history.minTemp === null || isNaN(history.minTemp)) {
      this.updateErrMsgs.push('Invalid min temperature value');
    }
    if (history.maxTemp === null || isNaN(history.maxTemp)) {
      this.updateErrMsgs.push('Invalid max temperature value');
    }

    const sunrise = new Date(history.sunrise);
    const sunset = new Date(history.sunset);

    if (isNaN(sunrise.getTime())) {
      this.updateErrMsgs.push('Invalid sunrise value');
    }

    if (isNaN(sunset.getTime())) {
      this.updateErrMsgs.push('Invalid sunset value');
    }

    return this.updateErrMsgs.length === 0;
  }
}
