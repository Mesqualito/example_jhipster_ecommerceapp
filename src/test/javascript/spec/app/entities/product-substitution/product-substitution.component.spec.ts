/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { StoreTestModule } from '../../../test.module';
import { ProductSubstitutionComponent } from 'app/entities/product-substitution/product-substitution.component';
import { ProductSubstitutionService } from 'app/entities/product-substitution/product-substitution.service';
import { ProductSubstitution } from 'app/shared/model/product-substitution.model';

describe('Component Tests', () => {
  describe('ProductSubstitution Management Component', () => {
    let comp: ProductSubstitutionComponent;
    let fixture: ComponentFixture<ProductSubstitutionComponent>;
    let service: ProductSubstitutionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StoreTestModule],
        declarations: [ProductSubstitutionComponent],
        providers: []
      })
        .overrideTemplate(ProductSubstitutionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductSubstitutionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductSubstitutionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProductSubstitution(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.productSubstitutions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
