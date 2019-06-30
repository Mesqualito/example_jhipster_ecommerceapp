import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductSubstitution } from 'app/shared/model/product-substitution.model';
import { ProductSubstitutionService } from './product-substitution.service';

@Component({
  selector: 'jhi-product-substitution-delete-dialog',
  templateUrl: './product-substitution-delete-dialog.component.html'
})
export class ProductSubstitutionDeleteDialogComponent {
  productSubstitution: IProductSubstitution;

  constructor(
    protected productSubstitutionService: ProductSubstitutionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.productSubstitutionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'productSubstitutionListModification',
        content: 'Deleted an productSubstitution'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-product-substitution-delete-popup',
  template: ''
})
export class ProductSubstitutionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productSubstitution }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProductSubstitutionDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.productSubstitution = productSubstitution;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/product-substitution', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/product-substitution', { outlets: { popup: null } }]);
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
