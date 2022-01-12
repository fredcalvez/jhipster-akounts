import dayjs from 'dayjs';
import { IBankCategory } from 'app/shared/model/bank-category.model';

export interface IBudget {
  id?: number;
  budgetDate?: string | null;
  categorieLabel?: string | null;
  amount?: number | null;
  category?: IBankCategory | null;
}

export const defaultValue: Readonly<IBudget> = {};
