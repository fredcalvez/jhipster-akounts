import dayjs from 'dayjs';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IFileImport {
  id?: number;
  fileName?: string | null;
  status?: Status | null;
  reviewDate?: string | null;
  filePath?: string | null;
}

export const defaultValue: Readonly<IFileImport> = {};
