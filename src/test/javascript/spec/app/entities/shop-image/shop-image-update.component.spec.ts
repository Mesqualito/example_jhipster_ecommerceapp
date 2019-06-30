/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { StoreTestModule } from '../../../test.module';
import { ShopImageUpdateComponent } from 'app/entities/shop-image/shop-image-update.component';
import { ShopImageService } from 'app/entities/shop-image/shop-image.service';
import { ShopImage } from 'app/shared/model/shop-image.model';

describe('Component Tests', () => {
  describe('ShopImage Management Update Component', () => {
    let comp: ShopImageUpdateComponent;
    let fixture: ComponentFixture<ShopImageUpdateComponent>;
    let service: ShopImageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StoreTestModule],
        declarations: [ShopImageUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ShopImageUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ShopImageUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ShopImageService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ShopImage(123);
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
        const entity = new ShopImage();
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
