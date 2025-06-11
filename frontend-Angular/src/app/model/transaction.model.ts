export interface Transaction {
  userId: string;
  fromAccountId: number;
  toAccountId: number;
  amount: number;
  date: string
  type: string
}
