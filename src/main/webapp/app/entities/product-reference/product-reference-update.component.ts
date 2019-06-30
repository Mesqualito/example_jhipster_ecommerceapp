import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProductReference, ProductReference } from 'app/shared/model/product-reference.model';
import { ProductReferenceService } from './product-reference.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product';

@Component({
  selector: 'jhi-product-reference-update',
  templateUrl: './product-reference-update.component.html'
})
export class ProductReferenceUpdateComponent implements OnInit {
  isSaving: boolean;

  products: IProduct[];

  editForm = this.fb.group({
    id: [],
    name: [],
    refName: [null, [Validators.required]],
    reference: [],
    type: [],
    product: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected productReferenceService: ProductReferenceService,
    protected productService: ProductService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ productReference }) => {
      this.updateForm(productReference);
    });
    this.productService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProduct[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProduct[]>) => response.body)
      )
      .subscribe((res: IProduct[]) => (this.products = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(productReference: IProductReference) {
    this.editForm.patchValue({
      id: productReference.id,
      name: productReference.name,
      refName: productReference.refName,
      reference: productReference.reference,
      type: productReference.type,
      product: productReference.product
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const productReference = this.createFromForm();
    if (productReference.id !== undefined) {
      this.subscribeToSaveResponse(this.productReferenceService.update(productReference));
    } else {
      this.subscribeToSaveResponse(this.productReferenceService.create(productReference));
    }
  }

  private createFromForm(): IProductReference {
    return {
      ...new ProductReference(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      refName: this.editForm.get(['refName']).value,
      reference: this.editForm.get(['reference']).value,
      type: this.editForm.get(['type']).value,
      product: this.editForm.get(['product']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductReference>>) {
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
}
