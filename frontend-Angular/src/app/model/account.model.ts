
export interface Account {
  accountId: number;
  userId: string;
  accountType: string;
  accountNumber: string;
  balance: number;
  currency: string;
  recentTransaction?: string;
}
