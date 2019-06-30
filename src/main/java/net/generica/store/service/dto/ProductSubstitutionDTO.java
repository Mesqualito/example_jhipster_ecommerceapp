package net.generica.store.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link net.generica.store.domain.ProductSubstitution} entity.
 */
public class ProductSubstitutionDTO implements Serializable {

    private Long id;

    private String productName;

    @NotNull
    private Boolean exchangeable;

    private Boolean checked;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Boolean isExchangeable() {
        return exchangeable;
    }

    public void setExchangeable(Boolean exchangeable) {
        this.exchangeable = exchangeable;
    }

    public Boolean isChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
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

        ProductSubstitutionDTO productSubstitutionDTO = (ProductSubstitutionDTO) o;
        if (productSubstitutionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productSubstitutionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductSubstitutionDTO{" +
            "id=" + getId() +
            ", productName='" + getProductName() + "'" +
            ", exchangeable='" + isExchangeable() + "'" +
            ", checked='" + isChecked() + "'" +
            "}";
    }
}
