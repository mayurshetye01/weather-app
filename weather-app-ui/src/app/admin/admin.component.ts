import {Component, OnInit} from '@angular/core';
import {Weather} from '../_models/weather';
import {HistoryService} from '../_services/history.service';
import {AuthenticationService} from '../_services/authentication.service';
import {Router} from '@angular/router';
import {HttpErrorResponse} from '@angular/common/http';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})

export class AdminComponent implements OnInit {
  errorMsg: string;
  searchText: string;
  userHistoryMap = new Map<string, Weather[]>();

  onLogout() {
    this.authenticationService.logout();
  }

  constructor(private historyService: HistoryService,
              private authenticationService: AuthenticationService,
              private router: Router) {
  }

  ngOnInit() {
    this.getAllHistory();
  }

  getAllHistory() {
    this.historyService.getAllHistory()
      .subscribe((dataMap: Map<string, Weather[]>) => {
          this.userHistoryMap = dataMap;
        },
        error => {
          this.handleError(error);
        }
      );
  }

  handleError(error: HttpErrorResponse) {
    if (error.status === 403 || error.status === 401) {
      this.router.navigate(['/login']);
    } else {
      this.errorMsg = 'Some error occurred. Please try again';
    }
  }
}
