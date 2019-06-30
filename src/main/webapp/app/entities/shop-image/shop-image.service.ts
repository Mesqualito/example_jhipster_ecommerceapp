import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IShopImage } from 'app/shared/model/shop-image.model';

type EntityResponseType = HttpResponse<IShopImage>;
type EntityArrayResponseType = HttpResponse<IShopImage[]>;

@Injectable({ providedIn: 'root' })
export class ShopImageService {
  public resourceUrl = SERVER_API_URL + 'api/shop-images';

  constructor(protected http: HttpClient) {}

  create(shopImage: IShopImage): Observable<EntityResponseType> {
    return this.http.post<IShopImage>(this.resourceUrl, shopImage, { observe: 'response' });
  }

  update(shopImage: IShopImage): Observable<EntityResponseType> {
    return this.http.put<IShopImage>(this.resourceUrl, shopImage, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IShopImage>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IShopImage[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
