import { Currency } from 'app/shared/model/enumerations/currency.model';

export interface IBridgeAccount {
  id?: number;
  bridgeAccountId?: string | null;
  type?: string | null;
  status?: string | null;
  balance?: number | null;
  isoCurrencyCode?: Currency | null;
  name?: string | null;
}

export const defaultValue: Readonly<IBridgeAccount> = {};
