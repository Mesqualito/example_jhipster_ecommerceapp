import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IShopImage } from 'app/shared/model/shop-image.model';

@Component({
  selector: 'jhi-shop-image-detail',
  templateUrl: './shop-image-detail.component.html'
})
export class ShopImageDetailComponent implements OnInit {
  shopImage: IShopImage;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ shopImage }) => {
      this.shopImage = shopImage;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
