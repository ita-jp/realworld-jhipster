import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { IArticle } from 'app/shared/model/article.model';

export interface IComment {
  id?: number;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs;
  body?: string;
  user?: IUser;
  article?: IArticle;
}

export const defaultValue: Readonly<IComment> = {};
