/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StoreTestModule } from '../../../test.module';
import { ProductSubstitutionDetailComponent } from 'app/entities/product-substitution/product-substitution-detail.component';
import { ProductSubstitution } from 'app/shared/model/product-substitution.model';

describe('Component Tests', () => {
  describe('ProductSubstitution Management Detail Component', () => {
    let comp: ProductSubstitutionDetailComponent;
    let fixture: ComponentFixture<ProductSubstitutionDetailComponent>;
    const route = ({ data: of({ productSubstitution: new ProductSubstitution(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StoreTestModule],
        declarations: [ProductSubstitutionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProductSubstitutionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductSubstitutionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.productSubstitution).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
