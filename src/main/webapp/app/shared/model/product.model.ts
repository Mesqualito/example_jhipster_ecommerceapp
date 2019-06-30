import { IShopImage } from 'app/shared/model/shop-image.model';
import { IProductReference } from 'app/shared/model/product-reference.model';
import { IProduct } from 'app/shared/model/product.model';
import { IProductCategory } from 'app/shared/model/product-category.model';

export interface IProduct {
  id?: number;
  erpId?: string;
  refined?: boolean;
  name?: string;
  description?: string;
  herstArtNr?: string;
  price?: number;
  katalogOnly?: boolean;
  shopImages?: IShopImage[];
  references?: IProductReference[];
  substitutions?: IProduct[];
  productCategory?: IProductCategory;
  products?: IProduct[];
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public erpId?: string,
    public refined?: boolean,
    public name?: string,
    public description?: string,
    public herstArtNr?: string,
    public price?: number,
    public katalogOnly?: boolean,
    public shopImages?: IShopImage[],
    public references?: IProductReference[],
    public substitutions?: IProduct[],
    public productCategory?: IProductCategory,
    public products?: IProduct[]
  ) {
    this.refined = this.refined || false;
    this.katalogOnly = this.katalogOnly || false;
  }
}
