import { IBook } from 'app/shared/model/book.model';

export interface IComment {
  id?: string;
  comment?: string | null;
  book?: IBook | null;
}

export const defaultValue: Readonly<IComment> = {};
