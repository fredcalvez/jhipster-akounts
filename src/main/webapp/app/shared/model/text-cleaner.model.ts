export interface ITextCleaner {
  id?: number;
  type?: string | null;
  rule?: string | null;
}

export const defaultValue: Readonly<ITextCleaner> = {};
