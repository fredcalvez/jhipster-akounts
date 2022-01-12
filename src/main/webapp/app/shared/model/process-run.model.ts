import dayjs from 'dayjs';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IProcessRun {
  id?: number;
  process?: string | null;
  parentId?: number | null;
  status?: Status | null;
  returns?: string | null;
  error?: string | null;
  startTime?: string | null;
  endTime?: string | null;
}

export const defaultValue: Readonly<IProcessRun> = {};
