import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProductReference } from 'app/shared/model/product-reference.model';

type EntityResponseType = HttpResponse<IProductReference>;
type EntityArrayResponseType = HttpResponse<IProductReference[]>;

@Injectable({ providedIn: 'root' })
export class ProductReferenceService {
  public resourceUrl = SERVER_API_URL + 'api/product-references';

  constructor(protected http: HttpClient) {}

  create(productReference: IProductReference): Observable<EntityResponseType> {
    return this.http.post<IProductReference>(this.resourceUrl, productReference, { observe: 'response' });
  }

  update(productReference: IProductReference): Observable<EntityResponseType> {
    return this.http.put<IProductReference>(this.resourceUrl, productReference, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProductReference>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProductReference[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
