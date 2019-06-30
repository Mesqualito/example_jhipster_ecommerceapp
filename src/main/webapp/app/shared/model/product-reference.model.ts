export interface IProductReference {
  id?: number;
  name?: string;
  refName?: string;
  reference?: string;
  type?: string;
  productName?: string;
  productId?: number;
}

export class ProductReference implements IProductReference {
  constructor(
    public id?: number,
    public name?: string,
    public refName?: string,
    public reference?: string,
    public type?: string,
    public productName?: string,
    public productId?: number
  ) {}
}
