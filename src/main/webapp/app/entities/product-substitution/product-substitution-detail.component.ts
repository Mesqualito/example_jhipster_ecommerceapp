import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductSubstitution } from 'app/shared/model/product-substitution.model';

@Component({
  selector: 'jhi-product-substitution-detail',
  templateUrl: './product-substitution-detail.component.html'
})
export class ProductSubstitutionDetailComponent implements OnInit {
  productSubstitution: IProductSubstitution;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productSubstitution }) => {
      this.productSubstitution = productSubstitution;
    });
  }

  previousState() {
    window.history.back();
  }
}
