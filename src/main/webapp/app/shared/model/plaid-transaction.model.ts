import dayjs from 'dayjs';

export interface IPlaidTransaction {
  id?: number;
  transactionId?: string | null;
  transactionType?: string | null;
  accountId?: string | null;
  amount?: number | null;
  isoCurrencyCode?: string | null;
  transactionDate?: string | null;
  name?: string | null;
  originalDescription?: string | null;
  pending?: boolean | null;
  pendingTransactionId?: string | null;
  addedDate?: string | null;
  checked?: boolean | null;
}

export const defaultValue: Readonly<IPlaidTransaction> = {
  pending: false,
  checked: false,
};
