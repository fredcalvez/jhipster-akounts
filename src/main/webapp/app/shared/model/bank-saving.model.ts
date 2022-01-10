import dayjs from 'dayjs';

export interface IBankSaving {
  id?: number;
  summaryDate?: string | null;
  amount?: number | null;
  goal?: number | null;
  reach?: number | null;
}

export const defaultValue: Readonly<IBankSaving> = {};
