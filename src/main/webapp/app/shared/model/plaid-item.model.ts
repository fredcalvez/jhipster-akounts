import dayjs from 'dayjs';

export interface IPlaidItem {
  id?: number;
  itemId?: string | null;
  institutionId?: string | null;
  accessToken?: string | null;
  addedDate?: string | null;
  updateDate?: string | null;
}

export const defaultValue: Readonly<IPlaidItem> = {};
