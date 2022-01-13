import dayjs from 'dayjs';

export interface ITriggerRun {
  id?: number;
  runType?: number | null;
  status?: number | null;
  addDate?: string | null;
  startDate?: string | null;
  endDate?: string | null;
}

export const defaultValue: Readonly<ITriggerRun> = {};
