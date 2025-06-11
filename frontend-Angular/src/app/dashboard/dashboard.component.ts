import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {AccountService} from "../service/account.service" ;
import {Account} from "../model/account.model";
import {Router} from "@angular/router"
import {NotificationService} from "../service/notification.service";
import {Notification} from "../model/notification.model";
@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit{
  userName = 'Michelle';
  total$: Observable<number> | undefined;
  accounts: Account[] = [];
  lastTransaction = '$120 to Mario';
  latestNotification: Notification | null = null;
  constructor(private accountService :AccountService, private router: Router, private notificationService: NotificationService) {
  }

  ngOnInit() {
    // 이게 바로 "구독"이다!
    //DashboardComponent subscribe to totalBalance$
    //전체 Account[] 한번 받아서 계산
    this.total$ = this.accountService.totalBalance$;
    this.accountService.getAccounts('user1').subscribe(data => {
      this.accounts = data;
      this.accountService.updateTotalBalance(data);
    });
    this.notificationService.getNotificationStream().subscribe(notif =>{
      if(notif){
        this.latestNotification = notif;
        setTimeout(()=>{
          this.latestNotification = null;
        }, 5000);
      }
    })

  }

  viewAccounts() {
    this.router.navigate(['/accounts']);
  }


  makeTransfer() {
    // navigate to /transfer
    this.router.navigate(['/transfer']);
  }
}
