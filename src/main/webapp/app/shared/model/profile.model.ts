import { IUser } from 'app/shared/model/user.model';

export interface IProfile {
  id?: number;
  bio?: string | null;
  image?: string | null;
  user?: IUser;
  followers?: IProfile[] | null;
  followees?: IProfile[] | null;
}

export const defaultValue: Readonly<IProfile> = {};
