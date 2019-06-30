import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProductReference } from 'app/shared/model/product-reference.model';
import { AccountService } from 'app/core';
import { ProductReferenceService } from './product-reference.service';

@Component({
  selector: 'jhi-product-reference',
  templateUrl: './product-reference.component.html'
})
export class ProductReferenceComponent implements OnInit, OnDestroy {
  productReferences: IProductReference[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected productReferenceService: ProductReferenceService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.productReferenceService
      .query()
      .pipe(
        filter((res: HttpResponse<IProductReference[]>) => res.ok),
        map((res: HttpResponse<IProductReference[]>) => res.body)
      )
      .subscribe(
        (res: IProductReference[]) => {
          this.productReferences = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProductReferences();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProductReference) {
    return item.id;
  }

  registerChangeInProductReferences() {
    this.eventSubscriber = this.eventManager.subscribe('productReferenceListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
