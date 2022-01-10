import dayjs from 'dayjs';
import { Currency } from 'app/shared/model/enumerations/currency.model';

export interface IRebaseHistory {
  id?: number;
  oldValue?: number | null;
  oldCurrency?: Currency | null;
  newValue?: number | null;
  newCurrency?: Currency | null;
  runDate?: string | null;
}

export const defaultValue: Readonly<IRebaseHistory> = {};
