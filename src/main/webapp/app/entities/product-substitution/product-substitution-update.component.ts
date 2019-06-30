import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IProductSubstitution, ProductSubstitution } from 'app/shared/model/product-substitution.model';
import { ProductSubstitutionService } from './product-substitution.service';

@Component({
  selector: 'jhi-product-substitution-update',
  templateUrl: './product-substitution-update.component.html'
})
export class ProductSubstitutionUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [],
    exchangeable: [null, [Validators.required]],
    checked: []
  });

  constructor(
    protected productSubstitutionService: ProductSubstitutionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ productSubstitution }) => {
      this.updateForm(productSubstitution);
    });
  }

  updateForm(productSubstitution: IProductSubstitution) {
    this.editForm.patchValue({
      id: productSubstitution.id,
      name: productSubstitution.name,
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
      name: this.editForm.get(['name']).value,
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
}
