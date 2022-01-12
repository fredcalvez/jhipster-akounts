import { IBankInstitution } from 'app/shared/model/bank-institution.model';
import { Currency } from 'app/shared/model/enumerations/currency.model';
import { AccountType } from 'app/shared/model/enumerations/account-type.model';

export interface IBankAccount {
  id?: number;
  accountLabel?: string | null;
  accountNumber?: string | null;
  active?: boolean | null;
  currency?: Currency | null;
  initialAmount?: number | null;
  initialAmountLocal?: number | null;
  accountType?: AccountType | null;
  interest?: number | null;
  nickname?: string | null;
  institution?: IBankInstitution | null;
}

export const defaultValue: Readonly<IBankAccount> = {
  active: false,
};
