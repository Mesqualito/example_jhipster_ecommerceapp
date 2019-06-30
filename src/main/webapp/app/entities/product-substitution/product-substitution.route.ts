import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IProductSubstitution, ProductSubstitution } from 'app/shared/model/product-substitution.model';
import { ProductSubstitutionService } from './product-substitution.service';
import { ProductSubstitutionComponent } from './product-substitution.component';
import { ProductSubstitutionDetailComponent } from './product-substitution-detail.component';
import { ProductSubstitutionUpdateComponent } from './product-substitution-update.component';
import { ProductSubstitutionDeletePopupComponent } from './product-substitution-delete-dialog.component';

@Injectable({ providedIn: 'root' })
export class ProductSubstitutionResolve implements Resolve<IProductSubstitution> {
  constructor(private service: ProductSubstitutionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProductSubstitution> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProductSubstitution>) => response.ok),
        map((productSubstitution: HttpResponse<ProductSubstitution>) => productSubstitution.body)
      );
    }
    return of(new ProductSubstitution());
  }
}

export const productSubstitutionRoute: Routes = [
  {
    path: '',
    component: ProductSubstitutionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'storeApp.productSubstitution.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProductSubstitutionDetailComponent,
    resolve: {
      productSubstitution: ProductSubstitutionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'storeApp.productSubstitution.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProductSubstitutionUpdateComponent,
    resolve: {
      productSubstitution: ProductSubstitutionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'storeApp.productSubstitution.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProductSubstitutionUpdateComponent,
    resolve: {
      productSubstitution: ProductSubstitutionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'storeApp.productSubstitution.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const productSubstitutionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProductSubstitutionDeletePopupComponent,
    resolve: {
      productSubstitution: ProductSubstitutionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'storeApp.productSubstitution.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
