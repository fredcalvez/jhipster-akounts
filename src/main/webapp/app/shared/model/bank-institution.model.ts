import { Currency } from 'app/shared/model/enumerations/currency.model';

export interface IBankInstitution {
  id?: number;
  institutionLabel?: string | null;
  code?: string | null;
  active?: boolean | null;
  currency?: Currency | null;
  isoCountryCode?: string | null;
}

export const defaultValue: Readonly<IBankInstitution> = {
  active: false,
};
