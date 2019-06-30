/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { StoreTestModule } from '../../../test.module';
import { ProductReferenceDeleteDialogComponent } from 'app/entities/product-reference/product-reference-delete-dialog.component';
import { ProductReferenceService } from 'app/entities/product-reference/product-reference.service';

describe('Component Tests', () => {
  describe('ProductReference Management Delete Component', () => {
    let comp: ProductReferenceDeleteDialogComponent;
    let fixture: ComponentFixture<ProductReferenceDeleteDialogComponent>;
    let service: ProductReferenceService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StoreTestModule],
        declarations: [ProductReferenceDeleteDialogComponent]
      })
        .overrideTemplate(ProductReferenceDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductReferenceDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductReferenceService);
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
