import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductReference } from 'app/shared/model/product-reference.model';

@Component({
  selector: 'jhi-product-reference-detail',
  templateUrl: './product-reference-detail.component.html'
})
export class ProductReferenceDetailComponent implements OnInit {
  productReference: IProductReference;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productReference }) => {
      this.productReference = productReference;
    });
  }

  previousState() {
    window.history.back();
  }
}
