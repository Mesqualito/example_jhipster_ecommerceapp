import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { StoreSharedModule } from 'app/shared';
import {
  ProductSubstitutionComponent,
  ProductSubstitutionDeleteDialogComponent,
  ProductSubstitutionDeletePopupComponent,
  ProductSubstitutionDetailComponent,
  productSubstitutionPopupRoute,
  productSubstitutionRoute,
  ProductSubstitutionUpdateComponent
} from './';

const ENTITY_STATES = [...productSubstitutionRoute, ...productSubstitutionPopupRoute];

@NgModule({
  imports: [StoreSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProductSubstitutionComponent,
    ProductSubstitutionDetailComponent,
    ProductSubstitutionUpdateComponent,
    ProductSubstitutionDeleteDialogComponent,
    ProductSubstitutionDeletePopupComponent
  ],
  entryComponents: [
    ProductSubstitutionComponent,
    ProductSubstitutionUpdateComponent,
    ProductSubstitutionDeleteDialogComponent,
    ProductSubstitutionDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class StoreProductSubstitutionModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
