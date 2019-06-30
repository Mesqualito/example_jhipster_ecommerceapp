import { IProduct } from 'app/shared/model/product.model';

export interface IProductSubstitution {
  id?: number;
  productName?: string;
  exchangeable?: boolean;
  checked?: boolean;
  products?: IProduct[];
}

export class ProductSubstitution implements IProductSubstitution {
  constructor(
    public id?: number,
    public productName?: string,
    public exchangeable?: boolean,
    public checked?: boolean,
    public products?: IProduct[]
  ) {
    this.exchangeable = this.exchangeable || false;
    this.checked = this.checked || false;
  }
}
