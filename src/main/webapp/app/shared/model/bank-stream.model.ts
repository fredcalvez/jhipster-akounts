export interface IBankStream {
  id?: number;
  name?: string | null;
  streamType?: string | null;
}

export const defaultValue: Readonly<IBankStream> = {};
