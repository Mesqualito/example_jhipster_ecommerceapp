export const enum Size {
  S = 'S',
  M = 'M',
  L = 'L'
}

export interface IShopImage {
  id?: number;
  name?: string;
  herstArtNr?: string;
  order?: number;
  size?: Size;
  description?: string;
  imageContentType?: string;
  image?: any;
  productName?: string;
  productId?: number;
}

export class ShopImage implements IShopImage {
  constructor(
    public id?: number,
    public name?: string,
    public herstArtNr?: string,
    public order?: number,
    public size?: Size,
    public description?: string,
    public imageContentType?: string,
    public image?: any,
    public productName?: string,
    public productId?: number
  ) {}
}
