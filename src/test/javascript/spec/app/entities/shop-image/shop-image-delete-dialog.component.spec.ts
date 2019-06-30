/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { StoreTestModule } from '../../../test.module';
import { ShopImageDeleteDialogComponent } from 'app/entities/shop-image/shop-image-delete-dialog.component';
import { ShopImageService } from 'app/entities/shop-image/shop-image.service';

describe('Component Tests', () => {
  describe('ShopImage Management Delete Component', () => {
    let comp: ShopImageDeleteDialogComponent;
    let fixture: ComponentFixture<ShopImageDeleteDialogComponent>;
    let service: ShopImageService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StoreTestModule],
        declarations: [ShopImageDeleteDialogComponent]
      })
        .overrideTemplate(ShopImageDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ShopImageDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ShopImageService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
