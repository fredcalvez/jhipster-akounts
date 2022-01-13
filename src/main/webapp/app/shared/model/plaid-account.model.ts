import { Currency } from 'app/shared/model/enumerations/currency.model';

export interface IPlaidAccount {
  id?: number;
  plaidAccountId?: string | null;
  itemId?: string | null;
  type?: string | null;
  subtype?: string | null;
  balanceAvailable?: number | null;
  balanceCurrent?: number | null;
  isoCurrencyCode?: Currency | null;
  name?: string | null;
  officialName?: string | null;
  iban?: string | null;
  bic?: string | null;
}

export const defaultValue: Readonly<IPlaidAccount> = {};
