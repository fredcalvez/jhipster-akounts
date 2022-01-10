import { IBankTransaction } from 'app/shared/model/bank-transaction.model';
import { IBankProject } from 'app/shared/model/bank-project.model';

export interface IBankProjectTransaction {
  id?: number;
  transactionId?: IBankTransaction | null;
  projectId?: IBankProject | null;
}

export const defaultValue: Readonly<IBankProjectTransaction> = {};
