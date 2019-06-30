import { Moment } from 'moment';

export interface IShipment {
  id?: number;
  trackingCode?: string;
  date?: Moment;
  details?: string;
  invoiceCode?: string;
  invoiceId?: number;
}

export class Shipment implements IShipment {
  constructor(
    public id?: number,
    public trackingCode?: string,
    public date?: Moment,
    public details?: string,
    public invoiceCode?: string,
    public invoiceId?: number
  ) {}
}
