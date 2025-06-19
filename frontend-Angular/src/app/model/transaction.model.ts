export interface Transaction {
  userId: string;
  fromAccountId: number;
  toAccountId: number;
  amount: number;
  date: Date
  type: string
}
