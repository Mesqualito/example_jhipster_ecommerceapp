package net.generica.store.service.dto;
import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link net.generica.store.domain.Product} entity.
 */
@ApiModel(description = "Product sold by the online-store")
public class ProductDTO implements Serializable {

    private Long id;

    @NotNull
    private String erpId;

    @NotNull
    private Boolean refined;

    @NotNull
    private String name;

    private String description;

    @NotNull
    private String herstArtNr;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal price;

    private Boolean katalogOnly;


    private Set<ProductSubstitutionDTO> substitutions = new HashSet<>();

    private Long productCategoryId;

    private String productCategoryName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getErpId() {
        return erpId;
    }

    public void setErpId(String erpId) {
        this.erpId = erpId;
    }

    public Boolean isRefined() {
        return refined;
    }

    public void setRefined(Boolean refined) {
        this.refined = refined;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHerstArtNr() {
        return herstArtNr;
    }

    public void setHerstArtNr(String herstArtNr) {
        this.herstArtNr = herstArtNr;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean isKatalogOnly() {
        return katalogOnly;
    }

    public void setKatalogOnly(Boolean katalogOnly) {
        this.katalogOnly = katalogOnly;
    }

    public Set<ProductSubstitutionDTO> getSubstitutions() {
        return substitutions;
    }

    public void setSubstitutions(Set<ProductSubstitutionDTO> productSubstitutions) {
        this.substitutions = productSubstitutions;
    }

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductDTO productDTO = (ProductDTO) o;
        if (productDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
            "id=" + getId() +
            ", erpId='" + getErpId() + "'" +
            ", refined='" + isRefined() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", herstArtNr='" + getHerstArtNr() + "'" +
            ", price=" + getPrice() +
            ", katalogOnly='" + isKatalogOnly() + "'" +
            ", productCategory=" + getProductCategoryId() +
            ", productCategory='" + getProductCategoryName() + "'" +
            "}";
    }
}
