package net.generica.store.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import net.generica.store.domain.enumeration.InvoiceStatus;
import net.generica.store.domain.enumeration.PaymentMethod;

/**
 * A DTO for the Invoice entity.
 */
public class InvoiceDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant date;

    private String details;

    @NotNull
    private InvoiceStatus status;

    @NotNull
    private PaymentMethod paymentMethod;

    @NotNull
    private Instant paymentDate;

    @NotNull
    private BigDecimal paymentAmount;

    @NotNull
    private String code;


    private Long orderId;

    private String orderCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Instant getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Instant paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

        InvoiceDTO invoiceDTO = (InvoiceDTO) o;
        if (invoiceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), invoiceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InvoiceDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", details='" + getDetails() + "'" +
            ", status='" + getStatus() + "'" +
            ", paymentMethod='" + getPaymentMethod() + "'" +
            ", paymentDate='" + getPaymentDate() + "'" +
            ", paymentAmount=" + getPaymentAmount() +
            ", code='" + getCode() + "'" +
            ", order=" + getOrderId() +
            ", order='" + getOrderCode() + "'" +
            "}";
    }
}
