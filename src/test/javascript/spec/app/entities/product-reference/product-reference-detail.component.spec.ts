/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StoreTestModule } from '../../../test.module';
import { ProductReferenceDetailComponent } from 'app/entities/product-reference/product-reference-detail.component';
import { ProductReference } from 'app/shared/model/product-reference.model';

describe('Component Tests', () => {
  describe('ProductReference Management Detail Component', () => {
    let comp: ProductReferenceDetailComponent;
    let fixture: ComponentFixture<ProductReferenceDetailComponent>;
    const route = ({ data: of({ productReference: new ProductReference(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StoreTestModule],
        declarations: [ProductReferenceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProductReferenceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductReferenceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.productReference).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
