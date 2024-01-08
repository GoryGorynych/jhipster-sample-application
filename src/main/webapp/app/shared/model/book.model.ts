import { IComment } from 'app/shared/model/comment.model';
import { IAuthor } from 'app/shared/model/author.model';
import { IGenre } from 'app/shared/model/genre.model';

export interface IBook {
  id?: string;
  name?: string | null;
  description?: string | null;
  comments?: IComment[] | null;
  author1?: IAuthor | null;
  genre?: IGenre | null;
}

export const defaultValue: Readonly<IBook> = {};
