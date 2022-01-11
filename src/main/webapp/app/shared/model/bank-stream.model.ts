import { IBankAccount } from 'app/shared/model/bank-account.model';

export interface IBankStream {
  id?: number;
  name?: string | null;
  streamType?: string | null;
  mainAccountId?: IBankAccount | null;
}

export const defaultValue: Readonly<IBankStream> = {};
