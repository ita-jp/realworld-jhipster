import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { ITag } from 'app/shared/model/tag.model';

export interface IArticle {
  id?: number;
  slug?: string;
  title?: string;
  description?: string;
  body?: string;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs;
  user?: IUser | null;
  tags?: ITag[] | null;
}

export const defaultValue: Readonly<IArticle> = {};
