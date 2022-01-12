import dayjs from 'dayjs';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IBridgeRun {
  id?: number;
  runType?: string | null;
  runStatus?: Status | null;
  runStart?: string | null;
  runEnd?: string | null;
}

export const defaultValue: Readonly<IBridgeRun> = {};
