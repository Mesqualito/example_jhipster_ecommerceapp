export const enum OrderItemStatus {
  AVAILABLE = 'AVAILABLE',
  OUT_OF_STOCK = 'OUT_OF_STOCK',
  BACK_ORDER = 'BACK_ORDER'
}

export interface IOrderItem {
  id?: number;
  quantity?: number;
  totalPrice?: number;
  status?: OrderItemStatus;
  productName?: string;
  productId?: number;
  orderCode?: string;
  orderId?: number;
}

export class OrderItem implements IOrderItem {
  constructor(
    public id?: number,
    public quantity?: number,
    public totalPrice?: number,
    public status?: OrderItemStatus,
    public productName?: string,
    public productId?: number,
    public orderCode?: string,
    public orderId?: number
  ) {}
}
