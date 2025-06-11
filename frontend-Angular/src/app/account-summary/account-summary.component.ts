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
  @Output() balanceUpdated = new EventEmitter<{id:number, newBalance: number}>();
  @Output() requestTransferForm = new EventEmitter<number>();
  accounts: Account[] = [];

  constructor(private accountService: AccountService) {
  }

  increaseBalance() {
    const newBalance = this.account.balance + 100;
    this.balanceUpdated.emit({ id: this.account.accountId, newBalance });

  }

  onTransferClick() {
    this.requestTransferForm.emit(this.account.accountId);
  }

}
