package net.generica.store.service.dto;

import java.io.Serializable;
import java.util.Objects;
import net.generica.store.domain.enumeration.InvoiceStatus;
import net.generica.store.domain.enumeration.PaymentMethod;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the Invoice entity. This class is used in InvoiceResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /invoices?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class InvoiceCriteria implements Serializable {
    /**
     * Class for filtering InvoiceStatus
     */
    public static class InvoiceStatusFilter extends Filter<InvoiceStatus> {
    }
    /**
     * Class for filtering PaymentMethod
     */
    public static class PaymentMethodFilter extends Filter<PaymentMethod> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter date;

    private StringFilter details;

    private InvoiceStatusFilter status;

    private PaymentMethodFilter paymentMethod;

    private InstantFilter paymentDate;

    private BigDecimalFilter paymentAmount;

    private StringFilter code;

    private LongFilter shipmentId;

    private LongFilter orderId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getDate() {
        return date;
    }

    public void setDate(InstantFilter date) {
        this.date = date;
    }

    public StringFilter getDetails() {
        return details;
    }

    public void setDetails(StringFilter details) {
        this.details = details;
    }

    public InvoiceStatusFilter getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatusFilter status) {
        this.status = status;
    }

    public PaymentMethodFilter getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethodFilter paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public InstantFilter getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(InstantFilter paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimalFilter getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimalFilter paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public LongFilter getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(LongFilter shipmentId) {
        this.shipmentId = shipmentId;
    }

    public LongFilter getOrderId() {
        return orderId;
    }

    public void setOrderId(LongFilter orderId) {
        this.orderId = orderId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final InvoiceCriteria that = (InvoiceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(date, that.date) &&
            Objects.equals(details, that.details) &&
            Objects.equals(status, that.status) &&
            Objects.equals(paymentMethod, that.paymentMethod) &&
            Objects.equals(paymentDate, that.paymentDate) &&
            Objects.equals(paymentAmount, that.paymentAmount) &&
            Objects.equals(code, that.code) &&
            Objects.equals(shipmentId, that.shipmentId) &&
            Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        date,
        details,
        status,
        paymentMethod,
        paymentDate,
        paymentAmount,
        code,
        shipmentId,
        orderId
        );
    }

    @Override
    public String toString() {
        return "InvoiceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
                (details != null ? "details=" + details + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (paymentMethod != null ? "paymentMethod=" + paymentMethod + ", " : "") +
                (paymentDate != null ? "paymentDate=" + paymentDate + ", " : "") +
                (paymentAmount != null ? "paymentAmount=" + paymentAmount + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (shipmentId != null ? "shipmentId=" + shipmentId + ", " : "") +
                (orderId != null ? "orderId=" + orderId + ", " : "") +
            "}";
    }

}
