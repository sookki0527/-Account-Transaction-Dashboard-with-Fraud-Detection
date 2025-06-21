import {Component, Input, Output, EventEmitter} from '@angular/core';
import {Account} from "../model/account.model";
import {AccountService} from "../service/account.service";

@Component({
  selector: 'app-account-summary',
  templateUrl: './account-summary.component.html',
  styleUrls: ['./account-summary.component.css']
})
export class AccountSummaryComponent {
  @Input() account !: Account;

  @Output() balanceIncreased = new EventEmitter<{ id: number; newBalance: number }>();
  @Output() balanceDecreased = new EventEmitter<{ id: number; newBalance: number }>();
  @Output() requestTransferForm = new EventEmitter<number>();
  accounts: Account[] = [];
  amountToAdd: number = 0;
  amountToSubtract: number = 0;

  constructor(private accountService: AccountService) {
  }

  increaseBalance() {
    const newBalance = this.account.balance  + this.amountToAdd;
    this.balanceIncreased.emit({
      id: this.account.accountId,
      newBalance
    });
  }

  decreaseBalance() {
    const newBalance = this.account.balance -this.amountToSubtract;
    this.balanceDecreased.emit({
      id: this.account.accountId,
      newBalance
    });
  }

  onTransferClick() {
    this.requestTransferForm.emit(this.account.accountId);
  }

}
