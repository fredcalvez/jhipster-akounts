import dayjs from 'dayjs';

export interface IBridgeUser {
  id?: number;
  uuid?: string | null;
  email?: string | null;
  pass?: string | null;
  lastLoginDate?: string | null;
}

export const defaultValue: Readonly<IBridgeUser> = {};
