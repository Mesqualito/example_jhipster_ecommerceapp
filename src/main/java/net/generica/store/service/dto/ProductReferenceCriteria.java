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
 * Criteria class for the {@link net.generica.store.domain.ProductReference} entity. This class is used
 * in {@link net.generica.store.web.rest.ProductReferenceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /product-references?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductReferenceCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter refName;

    private StringFilter reference;

    private StringFilter type;

    private LongFilter productId;

    public ProductReferenceCriteria(){
    }

    public ProductReferenceCriteria(ProductReferenceCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.refName = other.refName == null ? null : other.refName.copy();
        this.reference = other.reference == null ? null : other.reference.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
    }

    @Override
    public ProductReferenceCriteria copy() {
        return new ProductReferenceCriteria(this);
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

    public StringFilter getRefName() {
        return refName;
    }

    public void setRefName(StringFilter refName) {
        this.refName = refName;
    }

    public StringFilter getReference() {
        return reference;
    }

    public void setReference(StringFilter reference) {
        this.reference = reference;
    }

    public StringFilter getType() {
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
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
        final ProductReferenceCriteria that = (ProductReferenceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(refName, that.refName) &&
            Objects.equals(reference, that.reference) &&
            Objects.equals(type, that.type) &&
            Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        refName,
        reference,
        type,
        productId
        );
    }

    @Override
    public String toString() {
        return "ProductReferenceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (refName != null ? "refName=" + refName + ", " : "") +
                (reference != null ? "reference=" + reference + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
            "}";
    }

}
