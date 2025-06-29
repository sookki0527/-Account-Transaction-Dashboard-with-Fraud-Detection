import {Component, OnInit} from '@angular/core';
import {Account} from "../model/account.model";
import {NotificationService} from "../service/notification.service";
import {AccountService} from "../service/account.service";
import {Notification} from "../model/notification.model";
@Component({
  selector: 'app-account-list',
  templateUrl: './account-list.component.html',
  styleUrls: ['./account-list.component.css']
})
export class AccountListComponent implements OnInit{


  accounts: Account[] = [];
  latestNotification: Notification | null = null;
  constructor(private accountService :AccountService, private notificationService: NotificationService) {
  }
  ngOnInit(){
      this.accountService.getAccounts("user1").subscribe(data =>{
        this.accounts = data;
      })
    this.notificationService.getNotificationStream().subscribe(notif =>{
      if(notif){
        this.latestNotification = notif;
        console.log('🔔 Notification:', notif);
        setTimeout(()=>{
          this.latestNotification = null;
        }, 5000);
      }
    })
    }


  handleIncrease(event: { id: number, newBalance: number }) {
    this.accountService.increaseBalance(event.id, event.newBalance).subscribe({
      next: () => console.log('Balance increased.'),
      error: err => console.error('Error:', err)
    });
  }

  handleDecrease(event: { id: number, newBalance: number }) {
    this.accountService.decreaseBalance(event.id, event.newBalance).subscribe({
      next: () => console.log('Balance decreased.'),
      error: err => console.error('Error:', err)
    });
  }

  selectedAccountId: number | null = null;

  toggleForm(accountId: number) {
    if (this.selectedAccountId === accountId) {
      this.selectedAccountId = null;  // 같은 걸 다시 누르면 닫기
    } else {
      this.selectedAccountId = accountId;
    }
  }
  refreshAccounts() {
    this.accountService.getAccounts('user1').subscribe(accounts => {
      this.accounts = accounts;
    });
  }


  protected readonly String = String;
}
