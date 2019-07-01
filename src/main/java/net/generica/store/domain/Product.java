package net.generica.store.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Product sold by the online-store
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "erp_id", nullable = false)
    private String erpId;

    @NotNull
    @Column(name = "refined", nullable = false)
    private Boolean refined;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "herst_art_nr", nullable = false)
    private String herstArtNr;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "price", precision = 21, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "katalog_only")
    private Boolean katalogOnly;

    @OneToMany(mappedBy = "product")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ShopImage> shopImages = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProductReference> references = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "product_product_substitution",
               joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "product_substitution_id", referencedColumnName = "id"))
    private Set<ProductSubstitution> productSubstitutions = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("products")
    private ProductCategory productCategory;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getErpId() {
        return erpId;
    }

    public Product erpId(String erpId) {
        this.erpId = erpId;
        return this;
    }

    public void setErpId(String erpId) {
        this.erpId = erpId;
    }

    public Boolean isRefined() {
        return refined;
    }

    public Product refined(Boolean refined) {
        this.refined = refined;
        return this;
    }

    public void setRefined(Boolean refined) {
        this.refined = refined;
    }

    public String getName() {
        return name;
    }

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Product description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHerstArtNr() {
        return herstArtNr;
    }

    public Product herstArtNr(String herstArtNr) {
        this.herstArtNr = herstArtNr;
        return this;
    }

    public void setHerstArtNr(String herstArtNr) {
        this.herstArtNr = herstArtNr;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Product price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean isKatalogOnly() {
        return katalogOnly;
    }

    public Product katalogOnly(Boolean katalogOnly) {
        this.katalogOnly = katalogOnly;
        return this;
    }

    public void setKatalogOnly(Boolean katalogOnly) {
        this.katalogOnly = katalogOnly;
    }

    public Set<ShopImage> getShopImages() {
        return shopImages;
    }

    public Product shopImages(Set<ShopImage> shopImages) {
        this.shopImages = shopImages;
        return this;
    }

    public Product addShopImage(ShopImage shopImage) {
        this.shopImages.add(shopImage);
        shopImage.setProduct(this);
        return this;
    }

    public Product removeShopImage(ShopImage shopImage) {
        this.shopImages.remove(shopImage);
        shopImage.setProduct(null);
        return this;
    }

    public void setShopImages(Set<ShopImage> shopImages) {
        this.shopImages = shopImages;
    }

    public Set<ProductReference> getReferences() {
        return references;
    }

    public Product references(Set<ProductReference> productReferences) {
        this.references = productReferences;
        return this;
    }

    public Product addReference(ProductReference productReference) {
        this.references.add(productReference);
        productReference.setProduct(this);
        return this;
    }

    public Product removeReference(ProductReference productReference) {
        this.references.remove(productReference);
        productReference.setProduct(null);
        return this;
    }

    public void setReferences(Set<ProductReference> productReferences) {
        this.references = productReferences;
    }

    public Set<ProductSubstitution> getProductSubstitutions() {
        return productSubstitutions;
    }

    public Product productSubstitutions(Set<ProductSubstitution> productSubstitutions) {
        this.productSubstitutions = productSubstitutions;
        return this;
    }

    public Product addProductSubstitution(ProductSubstitution productSubstitution) {
        this.productSubstitutions.add(productSubstitution);
        productSubstitution.getProducts().add(this);
        return this;
    }

    public Product removeProductSubstitution(ProductSubstitution productSubstitution) {
        this.productSubstitutions.remove(productSubstitution);
        productSubstitution.getProducts().remove(this);
        return this;
    }

    public void setProductSubstitutions(Set<ProductSubstitution> productSubstitutions) {
        this.productSubstitutions = productSubstitutions;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public Product productCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
        return this;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", erpId='" + getErpId() + "'" +
            ", refined='" + isRefined() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", herstArtNr='" + getHerstArtNr() + "'" +
            ", price=" + getPrice() +
            ", katalogOnly='" + isKatalogOnly() + "'" +
            "}";
    }
}
