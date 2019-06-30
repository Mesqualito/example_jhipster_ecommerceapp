/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, inject, TestBed, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { StoreTestModule } from '../../../test.module';
import { ProductSubstitutionDeleteDialogComponent } from 'app/entities/product-substitution/product-substitution-delete-dialog.component';
import { ProductSubstitutionService } from 'app/entities/product-substitution/product-substitution.service';

describe('Component Tests', () => {
  describe('ProductSubstitution Management Delete Component', () => {
    let comp: ProductSubstitutionDeleteDialogComponent;
    let fixture: ComponentFixture<ProductSubstitutionDeleteDialogComponent>;
    let service: ProductSubstitutionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StoreTestModule],
        declarations: [ProductSubstitutionDeleteDialogComponent]
      })
        .overrideTemplate(ProductSubstitutionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductSubstitutionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductSubstitutionService);
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
