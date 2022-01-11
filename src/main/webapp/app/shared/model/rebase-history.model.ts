import dayjs from 'dayjs';
import { IBankTransaction } from 'app/shared/model/bank-transaction.model';
import { Currency } from 'app/shared/model/enumerations/currency.model';

export interface IRebaseHistory {
  id?: number;
  oldValue?: number | null;
  oldCurrency?: Currency | null;
  newValue?: number | null;
  newCurrency?: Currency | null;
  runDate?: string | null;
  transactionId?: IBankTransaction | null;
}

export const defaultValue: Readonly<IRebaseHistory> = {};
