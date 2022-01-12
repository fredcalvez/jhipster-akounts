import dayjs from 'dayjs';
import { IBankCategory } from 'app/shared/model/bank-category.model';

export interface IAutomatch {
  id?: number;
  matchstring?: string | null;
  updateTime?: string | null;
  lastUsedTime?: string | null;
  useCount?: number | null;
  defaultTag?: string | null;
  category?: IBankCategory | null;
}

export const defaultValue: Readonly<IAutomatch> = {};
