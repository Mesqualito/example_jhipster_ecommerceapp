import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils, JhiEventManager } from 'ng-jhipster';

import { IShopImage } from 'app/shared/model/shop-image.model';
import { AccountService } from 'app/core';
import { ShopImageService } from './shop-image.service';

@Component({
  selector: 'jhi-shop-image',
  templateUrl: './shop-image.component.html'
})
export class ShopImageComponent implements OnInit, OnDestroy {
  shopImages: IShopImage[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected shopImageService: ShopImageService,
    protected jhiAlertService: JhiAlertService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.shopImageService
      .query()
      .pipe(
        filter((res: HttpResponse<IShopImage[]>) => res.ok),
        map((res: HttpResponse<IShopImage[]>) => res.body)
      )
      .subscribe(
        (res: IShopImage[]) => {
          this.shopImages = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInShopImages();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IShopImage) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInShopImages() {
    this.eventSubscriber = this.eventManager.subscribe('shopImageListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
