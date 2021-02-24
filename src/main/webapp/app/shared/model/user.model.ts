export interface IUser {
  id?: string;
  nationalId?: string;
  mobile?: string;
}

export class User implements IUser {
  constructor(public id?: string, public nationalId?: string, public mobile?: string) {}
}
