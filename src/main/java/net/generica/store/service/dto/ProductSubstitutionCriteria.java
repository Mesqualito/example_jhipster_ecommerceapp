package net.generica.store.service.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;

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

    private StringFilter name;

    private BooleanFilter exchangeable;

    private BooleanFilter checked;

    public ProductSubstitutionCriteria(){
    }

    public ProductSubstitutionCriteria(ProductSubstitutionCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.exchangeable = other.exchangeable == null ? null : other.exchangeable.copy();
        this.checked = other.checked == null ? null : other.checked.copy();
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

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
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
            Objects.equals(name, that.name) &&
            Objects.equals(exchangeable, that.exchangeable) &&
            Objects.equals(checked, that.checked);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        exchangeable,
        checked
        );
    }

    @Override
    public String toString() {
        return "ProductSubstitutionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (exchangeable != null ? "exchangeable=" + exchangeable + ", " : "") +
                (checked != null ? "checked=" + checked + ", " : "") +
            "}";
    }

}
