import dayjs from 'dayjs';

export interface IPlaidRun {
  id?: number;
  runType?: string | null;
  runStatus?: string | null;
  runItemId?: string | null;
  runStart?: string | null;
  runEnd?: string | null;
}

export const defaultValue: Readonly<IPlaidRun> = {};
