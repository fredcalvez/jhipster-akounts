export interface IBankProject {
  id?: number;
  name?: string | null;
  projectType?: string | null;
  initialValue?: number | null;
  currentValue?: number | null;
}

export const defaultValue: Readonly<IBankProject> = {};
