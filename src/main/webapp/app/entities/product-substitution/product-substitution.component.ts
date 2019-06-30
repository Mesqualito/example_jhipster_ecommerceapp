import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProductSubstitution } from 'app/shared/model/product-substitution.model';
import { AccountService } from 'app/core';
import { ProductSubstitutionService } from './product-substitution.service';

@Component({
  selector: 'jhi-product-substitution',
  templateUrl: './product-substitution.component.html'
})
export class ProductSubstitutionComponent implements OnInit, OnDestroy {
  productSubstitutions: IProductSubstitution[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected productSubstitutionService: ProductSubstitutionService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.productSubstitutionService
      .query()
      .pipe(
        filter((res: HttpResponse<IProductSubstitution[]>) => res.ok),
        map((res: HttpResponse<IProductSubstitution[]>) => res.body)
      )
      .subscribe(
        (res: IProductSubstitution[]) => {
          this.productSubstitutions = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProductSubstitutions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProductSubstitution) {
    return item.id;
  }

  registerChangeInProductSubstitutions() {
    this.eventSubscriber = this.eventManager.subscribe('productSubstitutionListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
