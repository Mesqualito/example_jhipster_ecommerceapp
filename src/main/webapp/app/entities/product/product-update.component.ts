import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProduct, Product } from 'app/shared/model/product.model';
import { ProductService } from './product.service';
import { IProductSubstitution } from 'app/shared/model/product-substitution.model';
import { ProductSubstitutionService } from 'app/entities/product-substitution';
import { IProductCategory } from 'app/shared/model/product-category.model';
import { ProductCategoryService } from 'app/entities/product-category';

@Component({
  selector: 'jhi-product-update',
  templateUrl: './product-update.component.html'
})
export class ProductUpdateComponent implements OnInit {
  isSaving: boolean;

  productsubstitutions: IProductSubstitution[];

  productcategories: IProductCategory[];

  editForm = this.fb.group({
    id: [],
    erpId: [null, [Validators.required]],
    refined: [null, [Validators.required]],
    name: [null, [Validators.required]],
    description: [],
    herstArtNr: [null, [Validators.required]],
    price: [null, [Validators.required, Validators.min(0)]],
    katalogOnly: [],
    substitutions: [],
    productCategoryId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected productService: ProductService,
    protected productSubstitutionService: ProductSubstitutionService,
    protected productCategoryService: ProductCategoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ product }) => {
      this.updateForm(product);
    });
    this.productSubstitutionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProductSubstitution[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProductSubstitution[]>) => response.body)
      )
      .subscribe((res: IProductSubstitution[]) => (this.productsubstitutions = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.productCategoryService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProductCategory[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProductCategory[]>) => response.body)
      )
      .subscribe((res: IProductCategory[]) => (this.productcategories = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(product: IProduct) {
    this.editForm.patchValue({
      id: product.id,
      erpId: product.erpId,
      refined: product.refined,
      name: product.name,
      description: product.description,
      herstArtNr: product.herstArtNr,
      price: product.price,
      katalogOnly: product.katalogOnly,
      substitutions: product.substitutions,
      productCategoryId: product.productCategoryId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const product = this.createFromForm();
    if (product.id !== undefined) {
      this.subscribeToSaveResponse(this.productService.update(product));
    } else {
      this.subscribeToSaveResponse(this.productService.create(product));
    }
  }

  private createFromForm(): IProduct {
    return {
      ...new Product(),
      id: this.editForm.get(['id']).value,
      erpId: this.editForm.get(['erpId']).value,
      refined: this.editForm.get(['refined']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value,
      herstArtNr: this.editForm.get(['herstArtNr']).value,
      price: this.editForm.get(['price']).value,
      katalogOnly: this.editForm.get(['katalogOnly']).value,
      substitutions: this.editForm.get(['substitutions']).value,
      productCategoryId: this.editForm.get(['productCategoryId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduct>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackProductSubstitutionById(index: number, item: IProductSubstitution) {
    return item.id;
  }

  trackProductCategoryById(index: number, item: IProductCategory) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
