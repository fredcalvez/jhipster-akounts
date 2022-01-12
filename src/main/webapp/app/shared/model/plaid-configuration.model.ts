export interface IPlaidConfiguration {
  id?: number;
  environement?: string | null;
  key?: string | null;
  value?: string | null;
}

export const defaultValue: Readonly<IPlaidConfiguration> = {};
