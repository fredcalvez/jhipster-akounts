export interface IFileConfig {
  id?: number;
  filename?: string | null;
  description?: string | null;
  amount?: number | null;
  amountIn?: number | null;
  amountOut?: number | null;
  accountNum?: number | null;
  transactionDate?: number | null;
  dateFormat?: string | null;
  fieldSeparator?: string | null;
  hasHeader?: number | null;
  note?: string | null;
  locale?: number | null;
  multipleAccount?: number | null;
  encoding?: string | null;
  sign?: number | null;
}

export const defaultValue: Readonly<IFileConfig> = {};
