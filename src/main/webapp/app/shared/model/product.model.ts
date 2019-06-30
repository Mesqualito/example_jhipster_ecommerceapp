import { IShopImage } from 'app/shared/model/shop-image.model';
import { IProductCategory } from 'app/shared/model/product-category.model';

export interface IProduct {
  id?: number;
  erpId?: string;
  name?: string;
  description?: string;
  herstArtNr?: string;
  price?: number;
  shopImages?: IShopImage[];
  productCategory?: IProductCategory;
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public erpId?: string,
    public name?: string,
    public description?: string,
    public herstArtNr?: string,
    public price?: number,
    public shopImages?: IShopImage[],
    public productCategory?: IProductCategory
  ) {}
}
