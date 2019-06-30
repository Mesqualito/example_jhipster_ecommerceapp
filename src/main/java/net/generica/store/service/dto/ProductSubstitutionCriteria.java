package net.generica.store.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link net.generica.store.domain.ProductSubstitution} entity. This class is used
 * in {@link net.generica.store.web.rest.ProductSubstitutionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /product-substitutions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductSubstitutionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter productName;

    private BooleanFilter exchangeable;

    private BooleanFilter checked;

    private LongFilter productId;

    public ProductSubstitutionCriteria(){
    }

    public ProductSubstitutionCriteria(ProductSubstitutionCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.productName = other.productName == null ? null : other.productName.copy();
        this.exchangeable = other.exchangeable == null ? null : other.exchangeable.copy();
        this.checked = other.checked == null ? null : other.checked.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
    }

    @Override
    public ProductSubstitutionCriteria copy() {
        return new ProductSubstitutionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getProductName() {
        return productName;
    }

    public void setProductName(StringFilter productName) {
        this.productName = productName;
    }

    public BooleanFilter getExchangeable() {
        return exchangeable;
    }

    public void setExchangeable(BooleanFilter exchangeable) {
        this.exchangeable = exchangeable;
    }

    public BooleanFilter getChecked() {
        return checked;
    }

    public void setChecked(BooleanFilter checked) {
        this.checked = checked;
    }

    public LongFilter getProductId() {
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductSubstitutionCriteria that = (ProductSubstitutionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(productName, that.productName) &&
            Objects.equals(exchangeable, that.exchangeable) &&
            Objects.equals(checked, that.checked) &&
            Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        productName,
        exchangeable,
        checked,
        productId
        );
    }

    @Override
    public String toString() {
        return "ProductSubstitutionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (productName != null ? "productName=" + productName + ", " : "") +
                (exchangeable != null ? "exchangeable=" + exchangeable + ", " : "") +
                (checked != null ? "checked=" + checked + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
            "}";
    }

}
