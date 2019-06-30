/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { StoreTestModule } from '../../../test.module';
import { ShopImageComponent } from 'app/entities/shop-image/shop-image.component';
import { ShopImageService } from 'app/entities/shop-image/shop-image.service';
import { ShopImage } from 'app/shared/model/shop-image.model';

describe('Component Tests', () => {
  describe('ShopImage Management Component', () => {
    let comp: ShopImageComponent;
    let fixture: ComponentFixture<ShopImageComponent>;
    let service: ShopImageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StoreTestModule],
        declarations: [ShopImageComponent],
        providers: []
      })
        .overrideTemplate(ShopImageComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ShopImageComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ShopImageService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ShopImage(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.shopImages[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
