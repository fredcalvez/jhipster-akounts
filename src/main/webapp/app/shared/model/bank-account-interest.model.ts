import dayjs from 'dayjs';

export interface IBankAccountInterest {
  id?: number;
  interest?: number | null;
  period?: string | null;
  interestType?: string | null;
  units?: number | null;
  startDate?: string | null;
  endDate?: string | null;
  scrappingURL?: string | null;
  scrappingTag?: string | null;
  scrappingTagBis?: string | null;
}

export const defaultValue: Readonly<IBankAccountInterest> = {};
