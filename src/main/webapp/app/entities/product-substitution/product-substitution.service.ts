import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProductSubstitution } from 'app/shared/model/product-substitution.model';

type EntityResponseType = HttpResponse<IProductSubstitution>;
type EntityArrayResponseType = HttpResponse<IProductSubstitution[]>;

@Injectable({ providedIn: 'root' })
export class ProductSubstitutionService {
  public resourceUrl = SERVER_API_URL + 'api/product-substitutions';

  constructor(protected http: HttpClient) {}

  create(productSubstitution: IProductSubstitution): Observable<EntityResponseType> {
    return this.http.post<IProductSubstitution>(this.resourceUrl, productSubstitution, { observe: 'response' });
  }

  update(productSubstitution: IProductSubstitution): Observable<EntityResponseType> {
    return this.http.put<IProductSubstitution>(this.resourceUrl, productSubstitution, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProductSubstitution>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProductSubstitution[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
