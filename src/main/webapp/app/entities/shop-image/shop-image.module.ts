import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { StoreSharedModule } from 'app/shared';
import {
  ShopImageComponent,
  ShopImageDeleteDialogComponent,
  ShopImageDeletePopupComponent,
  ShopImageDetailComponent,
  shopImagePopupRoute,
  shopImageRoute,
  ShopImageUpdateComponent
} from './';

const ENTITY_STATES = [...shopImageRoute, ...shopImagePopupRoute];

@NgModule({
  imports: [StoreSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ShopImageComponent,
    ShopImageDetailComponent,
    ShopImageUpdateComponent,
    ShopImageDeleteDialogComponent,
    ShopImageDeletePopupComponent
  ],
  entryComponents: [ShopImageComponent, ShopImageUpdateComponent, ShopImageDeleteDialogComponent, ShopImageDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class StoreShopImageModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
