export interface IBranch {
  id?: string;
  name?: string;
  address?: string;
  vaccineAllowed?: boolean;
  vaccineStock?: string;
}

export class Branch implements IBranch {
  constructor(
    public id?: string,
    public name?: string,
    public address?: string,
    public vaccineAllowed?: boolean,
    public vaccineStock?: string
  ) {
    this.vaccineAllowed = this.vaccineAllowed || false;
  }
}
