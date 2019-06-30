import { IShopImage } from 'app/shared/model/shop-image.model';
import { IProductReference } from 'app/shared/model/product-reference.model';
import { IProductSubstitution } from 'app/shared/model/product-substitution.model';

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
  substitutions?: IProductSubstitution[];
  productCategoryName?: string;
  productCategoryId?: number;
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
    public substitutions?: IProductSubstitution[],
    public productCategoryName?: string,
    public productCategoryId?: number
  ) {
    this.refined = this.refined || false;
    this.katalogOnly = this.katalogOnly || false;
  }
}
