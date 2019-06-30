import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductReference } from 'app/shared/model/product-reference.model';
import { ProductReferenceService } from './product-reference.service';

@Component({
  selector: 'jhi-product-reference-delete-dialog',
  templateUrl: './product-reference-delete-dialog.component.html'
})
export class ProductReferenceDeleteDialogComponent {
  productReference: IProductReference;

  constructor(
    protected productReferenceService: ProductReferenceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.productReferenceService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'productReferenceListModification',
        content: 'Deleted an productReference'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-product-reference-delete-popup',
  template: ''
})
export class ProductReferenceDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productReference }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProductReferenceDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.productReference = productReference;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/product-reference', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/product-reference', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
