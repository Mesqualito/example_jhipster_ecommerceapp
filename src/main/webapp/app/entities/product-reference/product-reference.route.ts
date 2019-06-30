import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProductReference } from 'app/shared/model/product-reference.model';
import { ProductReferenceService } from './product-reference.service';
import { ProductReferenceComponent } from './product-reference.component';
import { ProductReferenceDetailComponent } from './product-reference-detail.component';
import { ProductReferenceUpdateComponent } from './product-reference-update.component';
import { ProductReferenceDeletePopupComponent } from './product-reference-delete-dialog.component';
import { IProductReference } from 'app/shared/model/product-reference.model';

@Injectable({ providedIn: 'root' })
export class ProductReferenceResolve implements Resolve<IProductReference> {
  constructor(private service: ProductReferenceService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProductReference> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProductReference>) => response.ok),
        map((productReference: HttpResponse<ProductReference>) => productReference.body)
      );
    }
    return of(new ProductReference());
  }
}

export const productReferenceRoute: Routes = [
  {
    path: '',
    component: ProductReferenceComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'storeApp.productReference.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProductReferenceDetailComponent,
    resolve: {
      productReference: ProductReferenceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'storeApp.productReference.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProductReferenceUpdateComponent,
    resolve: {
      productReference: ProductReferenceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'storeApp.productReference.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProductReferenceUpdateComponent,
    resolve: {
      productReference: ProductReferenceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'storeApp.productReference.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const productReferencePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProductReferenceDeletePopupComponent,
    resolve: {
      productReference: ProductReferenceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'storeApp.productReference.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
