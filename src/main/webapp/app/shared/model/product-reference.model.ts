import { IProduct } from 'app/shared/model/product.model';

export interface IProductReference {
  id?: number;
  name?: string;
  refName?: string;
  reference?: string;
  type?: string;
  product?: IProduct;
}

export class ProductReference implements IProductReference {
  constructor(
    public id?: number,
    public name?: string,
    public refName?: string,
    public reference?: string,
    public type?: string,
    public product?: IProduct
  ) {}
}
