import { IBankVendor } from 'app/shared/model/bank-vendor.model';

export interface IBankTag {
  id?: number;
  tag?: string | null;
  useCount?: number | null;
  vendor?: IBankVendor | null;
}

export const defaultValue: Readonly<IBankTag> = {};
