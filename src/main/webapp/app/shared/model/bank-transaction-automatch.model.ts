import { IBankTransaction } from 'app/shared/model/bank-transaction.model';
import { IAutomatch } from 'app/shared/model/automatch.model';

export interface IBankTransactionAutomatch {
  id?: number;
  transactionId?: IBankTransaction | null;
  automatchId?: IAutomatch | null;
}

export const defaultValue: Readonly<IBankTransactionAutomatch> = {};
