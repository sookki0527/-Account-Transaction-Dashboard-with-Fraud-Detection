import {Component, EventEmitter, Input, Output} from '@angular/core';
import {error} from "@angular/compiler-cli/src/transformers/util"
import {Transaction} from "../model/transaction.model";
import {TransactionService} from "../service/transaction.service";

@Component({
  selector: 'app-transaction-form',
  templateUrl: './transaction-form.component.html',
  styleUrls: ['./transaction-form.component.css']
})
export class TransactionFormComponent {
  constructor(private transactionService: TransactionService) {
  }
  model: Partial<Transaction> = {
    toAccountId: 0,
    amount: 0,
    type: ''
  };

  @Input() fromAccountId!: number;
  @Output() transferCompleted = new EventEmitter<void>();

  onSubmit() {
    const request: Transaction = {
      userId: "user1",
      fromAccountId: this.fromAccountId,
      toAccountId: this.model.toAccountId!,
      amount: this.model.amount!,
      date: new Date().toISOString(),
      type: "TRANSFER_OUT"
    };

    this.transactionService.sendTransaction(request).subscribe(
      {
        next: () => {
          console.log('Transferred successfully');
          this.transferCompleted.emit();
        },
        error: (err) => console.error('Failed to transfer', err)
      }
    )};


}
