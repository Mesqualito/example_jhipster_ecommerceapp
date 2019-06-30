import { Moment } from 'moment';
import { IOrderItem } from 'app/shared/model/order-item.model';
import { IInvoice } from 'app/shared/model/invoice.model';

export const enum OrderStatus {
  COMPLETED = 'COMPLETED',
  PENDING = 'PENDING',
  CANCELLED = 'CANCELLED'
}

export interface IProductOrder {
  id?: number;
  placedDate?: Moment;
  status?: OrderStatus;
  code?: string;
  orderItems?: IOrderItem[];
  invoices?: IInvoice[];
  customerEmail?: string;
  customerId?: number;
}

export class ProductOrder implements IProductOrder {
  constructor(
    public id?: number,
    public placedDate?: Moment,
    public status?: OrderStatus,
    public code?: string,
    public orderItems?: IOrderItem[],
    public invoices?: IInvoice[],
    public customerEmail?: string,
    public customerId?: number
  ) {}
}
