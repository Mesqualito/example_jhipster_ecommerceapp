export interface IProductSubstitution {
  id?: number;
  name?: string;
  exchangeable?: boolean;
  checked?: boolean;
}

export class ProductSubstitution implements IProductSubstitution {
  constructor(public id?: number, public name?: string, public exchangeable?: boolean, public checked?: boolean) {
    this.exchangeable = this.exchangeable || false;
    this.checked = this.checked || false;
  }
}
