import dayjs from 'dayjs';

export interface IAutomatch {
  id?: number;
  matchstring?: string | null;
  updateTime?: string | null;
  lastUsedTime?: string | null;
  useCount?: number | null;
  defaultTag?: string | null;
}

export const defaultValue: Readonly<IAutomatch> = {};
