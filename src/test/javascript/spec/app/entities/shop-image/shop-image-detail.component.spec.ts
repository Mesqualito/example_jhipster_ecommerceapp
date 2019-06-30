/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StoreTestModule } from '../../../test.module';
import { ShopImageDetailComponent } from 'app/entities/shop-image/shop-image-detail.component';
import { ShopImage } from 'app/shared/model/shop-image.model';

describe('Component Tests', () => {
  describe('ShopImage Management Detail Component', () => {
    let comp: ShopImageDetailComponent;
    let fixture: ComponentFixture<ShopImageDetailComponent>;
    const route = ({ data: of({ shopImage: new ShopImage(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StoreTestModule],
        declarations: [ShopImageDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ShopImageDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ShopImageDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.shopImage).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
