import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IShopImage } from 'app/shared/model/shop-image.model';
import { ShopImageService } from './shop-image.service';

@Component({
  selector: 'jhi-shop-image-delete-dialog',
  templateUrl: './shop-image-delete-dialog.component.html'
})
export class ShopImageDeleteDialogComponent {
  shopImage: IShopImage;

  constructor(protected shopImageService: ShopImageService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.shopImageService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'shopImageListModification',
        content: 'Deleted an shopImage'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-shop-image-delete-popup',
  template: ''
})
export class ShopImageDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ shopImage }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ShopImageDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.shopImage = shopImage;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/shop-image', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/shop-image', { outlets: { popup: null } }]);
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
