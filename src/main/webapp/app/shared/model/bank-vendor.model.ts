import { IBankTag } from 'app/shared/model/bank-tag.model';

export interface IBankVendor {
  id?: number;
  vendorName?: string | null;
  useCount?: number | null;
  bankTags?: IBankTag[] | null;
}

export const defaultValue: Readonly<IBankVendor> = {};
