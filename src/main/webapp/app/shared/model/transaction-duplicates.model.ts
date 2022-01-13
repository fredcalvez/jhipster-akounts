import dayjs from 'dayjs';
import { IBankTransaction } from 'app/shared/model/bank-transaction.model';

export interface ITransactionDuplicates {
  id?: number;
  isduplicate?: boolean | null;
  dateAdd?: string | null;
  action?: string | null;
  checked?: boolean | null;
  parentTransactionId?: IBankTransaction | null;
  childTransactionId?: IBankTransaction | null;
}

export const defaultValue: Readonly<ITransactionDuplicates> = {
  isduplicate: false,
  checked: false,
};
