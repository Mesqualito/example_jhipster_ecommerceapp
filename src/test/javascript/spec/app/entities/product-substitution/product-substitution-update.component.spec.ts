/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { StoreTestModule } from '../../../test.module';
import { ProductSubstitutionUpdateComponent } from 'app/entities/product-substitution/product-substitution-update.component';
import { ProductSubstitutionService } from 'app/entities/product-substitution/product-substitution.service';
import { ProductSubstitution } from 'app/shared/model/product-substitution.model';

describe('Component Tests', () => {
  describe('ProductSubstitution Management Update Component', () => {
    let comp: ProductSubstitutionUpdateComponent;
    let fixture: ComponentFixture<ProductSubstitutionUpdateComponent>;
    let service: ProductSubstitutionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StoreTestModule],
        declarations: [ProductSubstitutionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProductSubstitutionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductSubstitutionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductSubstitutionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProductSubstitution(123);
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
        const entity = new ProductSubstitution();
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
