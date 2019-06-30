/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { StoreTestModule } from '../../../test.module';
import { ProductReferenceUpdateComponent } from 'app/entities/product-reference/product-reference-update.component';
import { ProductReferenceService } from 'app/entities/product-reference/product-reference.service';
import { ProductReference } from 'app/shared/model/product-reference.model';

describe('Component Tests', () => {
  describe('ProductReference Management Update Component', () => {
    let comp: ProductReferenceUpdateComponent;
    let fixture: ComponentFixture<ProductReferenceUpdateComponent>;
    let service: ProductReferenceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StoreTestModule],
        declarations: [ProductReferenceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProductReferenceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductReferenceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductReferenceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProductReference(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProductReference();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
