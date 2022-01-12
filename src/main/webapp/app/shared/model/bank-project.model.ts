import { IBankAccount } from 'app/shared/model/bank-account.model';

export interface IBankProject {
  id?: number;
  name?: string | null;
  projectType?: string | null;
  initialValue?: number | null;
  currentValue?: number | null;
  mainAccountId?: IBankAccount | null;
}

export const defaultValue: Readonly<IBankProject> = {};
