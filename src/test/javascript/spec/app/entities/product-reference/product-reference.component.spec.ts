/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { StoreTestModule } from '../../../test.module';
import { ProductReferenceComponent } from 'app/entities/product-reference/product-reference.component';
import { ProductReferenceService } from 'app/entities/product-reference/product-reference.service';
import { ProductReference } from 'app/shared/model/product-reference.model';

describe('Component Tests', () => {
  describe('ProductReference Management Component', () => {
    let comp: ProductReferenceComponent;
    let fixture: ComponentFixture<ProductReferenceComponent>;
    let service: ProductReferenceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StoreTestModule],
        declarations: [ProductReferenceComponent],
        providers: []
      })
        .overrideTemplate(ProductReferenceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductReferenceComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductReferenceService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProductReference(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.productReferences[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
