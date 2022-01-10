export interface IBankCategory {
  id?: number;
  parent?: number | null;
  categorieLabel?: string | null;
  categorieDesc?: string | null;
  income?: boolean | null;
  isexpense?: boolean | null;
}

export const defaultValue: Readonly<IBankCategory> = {
  income: false,
  isexpense: false,
};
