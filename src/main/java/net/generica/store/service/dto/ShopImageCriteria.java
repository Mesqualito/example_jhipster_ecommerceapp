package net.generica.store.service.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import net.generica.store.domain.enumeration.Size;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link net.generica.store.domain.ShopImage} entity. This class is used
 * in {@link net.generica.store.web.rest.ShopImageResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /shop-images?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ShopImageCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Size
     */
    public static class SizeFilter extends Filter<Size> {

        public SizeFilter() {
        }

        public SizeFilter(SizeFilter filter) {
            super(filter);
        }

        @Override
        public SizeFilter copy() {
            return new SizeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter herstArtNr;

    private IntegerFilter order;

    private SizeFilter size;

    private StringFilter description;

    private LongFilter productId;

    public ShopImageCriteria(){
    }

    public ShopImageCriteria(ShopImageCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.herstArtNr = other.herstArtNr == null ? null : other.herstArtNr.copy();
        this.order = other.order == null ? null : other.order.copy();
        this.size = other.size == null ? null : other.size.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
    }

    @Override
    public ShopImageCriteria copy() {
        return new ShopImageCriteria(this);
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

    public StringFilter getHerstArtNr() {
        return herstArtNr;
    }

    public void setHerstArtNr(StringFilter herstArtNr) {
        this.herstArtNr = herstArtNr;
    }

    public IntegerFilter getOrder() {
        return order;
    }

    public void setOrder(IntegerFilter order) {
        this.order = order;
    }

    public SizeFilter getSize() {
        return size;
    }

    public void setSize(SizeFilter size) {
        this.size = size;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
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
        final ShopImageCriteria that = (ShopImageCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(herstArtNr, that.herstArtNr) &&
            Objects.equals(order, that.order) &&
            Objects.equals(size, that.size) &&
            Objects.equals(description, that.description) &&
            Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        herstArtNr,
        order,
        size,
        description,
        productId
        );
    }

    @Override
    public String toString() {
        return "ShopImageCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (herstArtNr != null ? "herstArtNr=" + herstArtNr + ", " : "") +
                (order != null ? "order=" + order + ", " : "") +
                (size != null ? "size=" + size + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
            "}";
    }

}
