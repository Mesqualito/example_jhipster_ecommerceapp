package net.generica.store.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link net.generica.store.domain.ProductReference} entity.
 */
public class ProductReferenceDTO implements Serializable {

    private Long id;

    private String name;

    @NotNull
    private String refName;

    private String reference;

    private String type;


    private Long productId;

    private String productName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRefName() {
        return refName;
    }

    public void setRefName(String refName) {
        this.refName = refName;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductReferenceDTO productReferenceDTO = (ProductReferenceDTO) o;
        if (productReferenceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productReferenceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductReferenceDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", refName='" + getRefName() + "'" +
            ", reference='" + getReference() + "'" +
            ", type='" + getType() + "'" +
            ", product=" + getProductId() +
            ", product='" + getProductName() + "'" +
            "}";
    }
}
