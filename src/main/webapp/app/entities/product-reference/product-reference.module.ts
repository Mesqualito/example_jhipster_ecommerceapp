import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { StoreSharedModule } from 'app/shared';
import {
  ProductReferenceComponent,
  ProductReferenceDeleteDialogComponent,
  ProductReferenceDeletePopupComponent,
  ProductReferenceDetailComponent,
  productReferencePopupRoute,
  productReferenceRoute,
  ProductReferenceUpdateComponent
} from './';

const ENTITY_STATES = [...productReferenceRoute, ...productReferencePopupRoute];

@NgModule({
  imports: [StoreSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProductReferenceComponent,
    ProductReferenceDetailComponent,
    ProductReferenceUpdateComponent,
    ProductReferenceDeleteDialogComponent,
    ProductReferenceDeletePopupComponent
  ],
  entryComponents: [
    ProductReferenceComponent,
    ProductReferenceUpdateComponent,
    ProductReferenceDeleteDialogComponent,
    ProductReferenceDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class StoreProductReferenceModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
