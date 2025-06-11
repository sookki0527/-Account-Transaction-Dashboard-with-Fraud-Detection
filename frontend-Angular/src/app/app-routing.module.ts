import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AccountListComponent} from "./account-list/account-list.component"
import {DashboardComponent} from "./dashboard/dashboard.component"
import {TransactionListComponent} from "./transaction-list/transaction-list.component";
const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'accounts', component: AccountListComponent },
  { path: 'transfer', component: TransactionListComponent}
  // 다른 경로들...
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
