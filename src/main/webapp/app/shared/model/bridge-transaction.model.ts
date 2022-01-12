import dayjs from 'dayjs';
import { Currency } from 'app/shared/model/enumerations/currency.model';

export interface IBridgeTransaction {
  id?: number;
  transactionId?: string | null;
  transactionType?: string | null;
  accountId?: string | null;
  amount?: number | null;
  isoCurrencyCode?: Currency | null;
  transactionDate?: string | null;
  description?: string | null;
  isFuture?: boolean | null;
  isDeleted?: boolean | null;
  addedDate?: string | null;
  updatedAt?: string | null;
  checked?: boolean | null;
}

export const defaultValue: Readonly<IBridgeTransaction> = {
  isFuture: false,
  isDeleted: false,
  checked: false,
};
