import { IBankTransaction } from 'app/shared/model/bank-transaction.model';
import { IBankStream } from 'app/shared/model/bank-stream.model';

export interface IBankStreamTransaction {
  id?: number;
  transactionId?: IBankTransaction | null;
  streamId?: IBankStream | null;
}

export const defaultValue: Readonly<IBankStreamTransaction> = {};
