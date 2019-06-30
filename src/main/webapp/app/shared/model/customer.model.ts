import { IProductOrder } from 'app/shared/model/product-order.model';

export interface ICustomer {
  id?: number;
  erpId?: string;
  name1?: string;
  name2?: string;
  name3?: string;
  email?: string;
  phone?: string;
  addressLine1?: string;
  addressLine2?: string;
  addressLine3?: string;
  plz?: string;
  city?: string;
  country?: string;
  userLogin?: string;
  userId?: number;
  orders?: IProductOrder[];
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public erpId?: string,
    public name1?: string,
    public name2?: string,
    public name3?: string,
    public email?: string,
    public phone?: string,
    public addressLine1?: string,
    public addressLine2?: string,
    public addressLine3?: string,
    public plz?: string,
    public city?: string,
    public country?: string,
    public userLogin?: string,
    public userId?: number,
    public orders?: IProductOrder[]
  ) {}
}
