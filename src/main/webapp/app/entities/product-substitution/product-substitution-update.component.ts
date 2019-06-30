import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProductSubstitution, ProductSubstitution } from 'app/shared/model/product-substitution.model';
import { ProductSubstitutionService } from './product-substitution.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product';

@Component({
  selector: 'jhi-product-substitution-update',
  templateUrl: './product-substitution-update.component.html'
})
export class ProductSubstitutionUpdateComponent implements OnInit {
  isSaving: boolean;

  products: IProduct[];

  editForm = this.fb.group({
    id: [],
    productName: [],
    exchangeable: [null, [Validators.required]],
    checked: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected productSubstitutionService: ProductSubstitutionService,
    protected productService: ProductService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ productSubstitution }) => {
      this.updateForm(productSubstitution);
    });
    this.productService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProduct[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProduct[]>) => response.body)
      )
      .subscribe((res: IProduct[]) => (this.products = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(productSubstitution: IProductSubstitution) {
    this.editForm.patchValue({
      id: productSubstitution.id,
      productName: productSubstitution.productName,
      exchangeable: productSubstitution.exchangeable,
      checked: productSubstitution.checked
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const productSubstitution = this.createFromForm();
    if (productSubstitution.id !== undefined) {
      this.subscribeToSaveResponse(this.productSubstitutionService.update(productSubstitution));
    } else {
      this.subscribeToSaveResponse(this.productSubstitutionService.create(productSubstitution));
    }
  }

  private createFromForm(): IProductSubstitution {
    return {
      ...new ProductSubstitution(),
      id: this.editForm.get(['id']).value,
      productName: this.editForm.get(['productName']).value,
      exchangeable: this.editForm.get(['exchangeable']).value,
      checked: this.editForm.get(['checked']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductSubstitution>>) {
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

  trackProductById(index: number, item: IProduct) {
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
