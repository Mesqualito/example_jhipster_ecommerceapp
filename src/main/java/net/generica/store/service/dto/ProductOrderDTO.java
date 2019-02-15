package net.generica.store.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import net.generica.store.domain.enumeration.OrderStatus;

/**
 * A DTO for the ProductOrder entity.
 */
public class ProductOrderDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant placedDate;

    @NotNull
    private OrderStatus status;

    @NotNull
    private String code;


    private Long customerId;

    private String customerEmail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getPlacedDate() {
        return placedDate;
    }

    public void setPlacedDate(Instant placedDate) {
        this.placedDate = placedDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductOrderDTO productOrderDTO = (ProductOrderDTO) o;
        if (productOrderDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productOrderDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductOrderDTO{" +
            "id=" + getId() +
            ", placedDate='" + getPlacedDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", code='" + getCode() + "'" +
            ", customer=" + getCustomerId() +
            ", customer='" + getCustomerEmail() + "'" +
            "}";
    }
}
