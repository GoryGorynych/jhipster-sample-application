export interface IAuthor {
  id?: string;
  firstName?: string | null;
  secondName?: string | null;
}

export const defaultValue: Readonly<IAuthor> = {};
