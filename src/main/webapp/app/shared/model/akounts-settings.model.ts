export interface IAkountsSettings {
  id?: number;
  settingKey?: string | null;
  settingVal?: string | null;
}

export const defaultValue: Readonly<IAkountsSettings> = {};
