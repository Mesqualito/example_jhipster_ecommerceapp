package net.generica.store.service.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link net.generica.store.domain.Product} entity. This class is used
 * in {@link net.generica.store.web.rest.ProductResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /products?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter erpId;

    private StringFilter name;

    private StringFilter description;

    private StringFilter herstArtNr;

    private BigDecimalFilter price;

    private LongFilter shopImageId;

    private LongFilter productCategoryId;

    public ProductCriteria(){
    }

    public ProductCriteria(ProductCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.erpId = other.erpId == null ? null : other.erpId.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.herstArtNr = other.herstArtNr == null ? null : other.herstArtNr.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.shopImageId = other.shopImageId == null ? null : other.shopImageId.copy();
        this.productCategoryId = other.productCategoryId == null ? null : other.productCategoryId.copy();
    }

    @Override
    public ProductCriteria copy() {
        return new ProductCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getErpId() {
        return erpId;
    }

    public void setErpId(StringFilter erpId) {
        this.erpId = erpId;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getHerstArtNr() {
        return herstArtNr;
    }

    public void setHerstArtNr(StringFilter herstArtNr) {
        this.herstArtNr = herstArtNr;
    }

    public BigDecimalFilter getPrice() {
        return price;
    }

    public void setPrice(BigDecimalFilter price) {
        this.price = price;
    }

    public LongFilter getShopImageId() {
        return shopImageId;
    }

    public void setShopImageId(LongFilter shopImageId) {
        this.shopImageId = shopImageId;
    }

    public LongFilter getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(LongFilter productCategoryId) {
        this.productCategoryId = productCategoryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductCriteria that = (ProductCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(erpId, that.erpId) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(herstArtNr, that.herstArtNr) &&
            Objects.equals(price, that.price) &&
            Objects.equals(shopImageId, that.shopImageId) &&
            Objects.equals(productCategoryId, that.productCategoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        erpId,
        name,
        description,
        herstArtNr,
        price,
        shopImageId,
        productCategoryId
        );
    }

    @Override
    public String toString() {
        return "ProductCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (erpId != null ? "erpId=" + erpId + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (herstArtNr != null ? "herstArtNr=" + herstArtNr + ", " : "") +
                (price != null ? "price=" + price + ", " : "") +
                (shopImageId != null ? "shopImageId=" + shopImageId + ", " : "") +
                (productCategoryId != null ? "productCategoryId=" + productCategoryId + ", " : "") +
            "}";
    }

}
