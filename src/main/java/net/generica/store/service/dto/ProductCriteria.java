package net.generica.store.service.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.*;

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

    private BooleanFilter refined;

    private StringFilter name;

    private StringFilter description;

    private StringFilter herstArtNr;

    private BigDecimalFilter price;

    private BooleanFilter katalogOnly;

    private LongFilter shopImageId;

    private LongFilter referenceId;

    private LongFilter substitutionId;

    private LongFilter productCategoryId;

    private LongFilter productId;

    public ProductCriteria(){
    }

    public ProductCriteria(ProductCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.erpId = other.erpId == null ? null : other.erpId.copy();
        this.refined = other.refined == null ? null : other.refined.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.herstArtNr = other.herstArtNr == null ? null : other.herstArtNr.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.katalogOnly = other.katalogOnly == null ? null : other.katalogOnly.copy();
        this.shopImageId = other.shopImageId == null ? null : other.shopImageId.copy();
        this.referenceId = other.referenceId == null ? null : other.referenceId.copy();
        this.substitutionId = other.substitutionId == null ? null : other.substitutionId.copy();
        this.productCategoryId = other.productCategoryId == null ? null : other.productCategoryId.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
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

    public BooleanFilter getRefined() {
        return refined;
    }

    public void setRefined(BooleanFilter refined) {
        this.refined = refined;
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

    public BooleanFilter getKatalogOnly() {
        return katalogOnly;
    }

    public void setKatalogOnly(BooleanFilter katalogOnly) {
        this.katalogOnly = katalogOnly;
    }

    public LongFilter getShopImageId() {
        return shopImageId;
    }

    public void setShopImageId(LongFilter shopImageId) {
        this.shopImageId = shopImageId;
    }

    public LongFilter getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(LongFilter referenceId) {
        this.referenceId = referenceId;
    }

    public LongFilter getSubstitutionId() {
        return substitutionId;
    }

    public void setSubstitutionId(LongFilter substitutionId) {
        this.substitutionId = substitutionId;
    }

    public LongFilter getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(LongFilter productCategoryId) {
        this.productCategoryId = productCategoryId;
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
        final ProductCriteria that = (ProductCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(erpId, that.erpId) &&
            Objects.equals(refined, that.refined) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(herstArtNr, that.herstArtNr) &&
            Objects.equals(price, that.price) &&
            Objects.equals(katalogOnly, that.katalogOnly) &&
            Objects.equals(shopImageId, that.shopImageId) &&
            Objects.equals(referenceId, that.referenceId) &&
            Objects.equals(substitutionId, that.substitutionId) &&
            Objects.equals(productCategoryId, that.productCategoryId) &&
            Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        erpId,
        refined,
        name,
        description,
        herstArtNr,
        price,
        katalogOnly,
        shopImageId,
        referenceId,
        substitutionId,
        productCategoryId,
        productId
        );
    }

    @Override
    public String toString() {
        return "ProductCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (erpId != null ? "erpId=" + erpId + ", " : "") +
                (refined != null ? "refined=" + refined + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (herstArtNr != null ? "herstArtNr=" + herstArtNr + ", " : "") +
                (price != null ? "price=" + price + ", " : "") +
                (katalogOnly != null ? "katalogOnly=" + katalogOnly + ", " : "") +
                (shopImageId != null ? "shopImageId=" + shopImageId + ", " : "") +
                (referenceId != null ? "referenceId=" + referenceId + ", " : "") +
                (substitutionId != null ? "substitutionId=" + substitutionId + ", " : "") +
                (productCategoryId != null ? "productCategoryId=" + productCategoryId + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
            "}";
    }

}
