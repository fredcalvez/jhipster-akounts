import dayjs from 'dayjs';
import { IBankAccount } from 'app/shared/model/bank-account.model';

export interface IBankAccountInterest {
  id?: number;
  interest?: number | null;
  period?: string | null;
  interestType?: string | null;
  units?: number | null;
  startDate?: string | null;
  endDate?: string | null;
  scrappingURL?: string | null;
  scrappingTag?: string | null;
  scrappingTagBis?: string | null;
  creditedAccountId?: IBankAccount | null;
}

export const defaultValue: Readonly<IBankAccountInterest> = {};
