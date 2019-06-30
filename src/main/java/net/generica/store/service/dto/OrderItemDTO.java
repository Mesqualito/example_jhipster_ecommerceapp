package net.generica.store.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import net.generica.store.domain.enumeration.OrderItemStatus;

/**
 * A DTO for the {@link net.generica.store.domain.OrderItem} entity.
 */
public class OrderItemDTO implements Serializable {

    private Long id;

    @NotNull
    @Min(value = 0)
    private Integer quantity;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal totalPrice;

    @NotNull
    private OrderItemStatus status;


    private Long productId;

    private String productName;

    private Long orderId;

    private String orderCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderItemStatus getStatus() {
        return status;
    }

    public void setStatus(OrderItemStatus status) {
        this.status = status;
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long productOrderId) {
        this.orderId = productOrderId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String productOrderCode) {
        this.orderCode = productOrderCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderItemDTO orderItemDTO = (OrderItemDTO) o;
        if (orderItemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderItemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderItemDTO{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", totalPrice=" + getTotalPrice() +
            ", status='" + getStatus() + "'" +
            ", product=" + getProductId() +
            ", product='" + getProductName() + "'" +
            ", order=" + getOrderId() +
            ", order='" + getOrderCode() + "'" +
            "}";
    }
}
