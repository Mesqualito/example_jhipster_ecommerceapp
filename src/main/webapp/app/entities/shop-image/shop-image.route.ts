import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ShopImage } from 'app/shared/model/shop-image.model';
import { ShopImageService } from './shop-image.service';
import { ShopImageComponent } from './shop-image.component';
import { ShopImageDetailComponent } from './shop-image-detail.component';
import { ShopImageUpdateComponent } from './shop-image-update.component';
import { ShopImageDeletePopupComponent } from './shop-image-delete-dialog.component';
import { IShopImage } from 'app/shared/model/shop-image.model';

@Injectable({ providedIn: 'root' })
export class ShopImageResolve implements Resolve<IShopImage> {
  constructor(private service: ShopImageService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IShopImage> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ShopImage>) => response.ok),
        map((shopImage: HttpResponse<ShopImage>) => shopImage.body)
      );
    }
    return of(new ShopImage());
  }
}

export const shopImageRoute: Routes = [
  {
    path: '',
    component: ShopImageComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'storeApp.shopImage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ShopImageDetailComponent,
    resolve: {
      shopImage: ShopImageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'storeApp.shopImage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ShopImageUpdateComponent,
    resolve: {
      shopImage: ShopImageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'storeApp.shopImage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ShopImageUpdateComponent,
    resolve: {
      shopImage: ShopImageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'storeApp.shopImage.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const shopImagePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ShopImageDeletePopupComponent,
    resolve: {
      shopImage: ShopImageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'storeApp.shopImage.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
