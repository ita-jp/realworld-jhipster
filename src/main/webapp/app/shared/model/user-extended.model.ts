export interface IUserExtended {
  id?: number;
  users?: IUserExtended[] | null;
  follows?: IUserExtended[] | null;
}

export const defaultValue: Readonly<IUserExtended> = {};
