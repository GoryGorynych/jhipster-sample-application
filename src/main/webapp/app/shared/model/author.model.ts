import { IBook } from 'app/shared/model/book.model';

export interface IAuthor {
  id?: string;
  firstName?: string | null;
  secondName?: string | null;
  books?: IBook[] | null;
}

export const defaultValue: Readonly<IAuthor> = {};
