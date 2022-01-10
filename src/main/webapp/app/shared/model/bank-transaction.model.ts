import dayjs from 'dayjs';
import { IBankCategory } from 'app/shared/model/bank-category.model';
import { IBankAccount } from 'app/shared/model/bank-account.model';
import { Currency } from 'app/shared/model/enumerations/currency.model';

export interface IBankTransaction {
  id?: number;
  transactionId?: string | null;
  transactionDate?: string | null;
  description?: string | null;
  localAmount?: number | null;
  localCurrency?: Currency | null;
  amount?: number | null;
  currency?: Currency | null;
  note?: string | null;
  year?: number | null;
  month?: number | null;
  week?: number | null;
  categorizedDate?: string | null;
  addDate?: string | null;
  checked?: boolean | null;
  rebasedDate?: string | null;
  deleted?: boolean | null;
  tag?: string | null;
  createdOn?: string | null;
  updatedOn?: string | null;
  version?: number | null;
  catId?: IBankCategory | null;
  accountId?: IBankAccount | null;
}

export const defaultValue: Readonly<IBankTransaction> = {
  checked: false,
  deleted: false,
};
