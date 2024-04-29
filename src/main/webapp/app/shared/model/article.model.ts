import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';

export interface IArticle {
  id?: number;
  slug?: string;
  title?: string;
  description?: string;
  body?: string;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs;
  author?: IUser | null;
}

export const defaultValue: Readonly<IArticle> = {};
