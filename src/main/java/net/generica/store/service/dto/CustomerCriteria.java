package net.generica.store.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link net.generica.store.domain.Customer} entity. This class is used
 * in {@link net.generica.store.web.rest.CustomerResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /customers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CustomerCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter erpId;

    private StringFilter name1;

    private StringFilter name2;

    private StringFilter name3;

    private StringFilter email;

    private StringFilter phone;

    private StringFilter addressLine1;

    private StringFilter addressLine2;

    private StringFilter addressLine3;

    private StringFilter plz;

    private StringFilter city;

    private StringFilter country;

    private LongFilter userId;

    private LongFilter orderId;

    public CustomerCriteria(){
    }

    public CustomerCriteria(CustomerCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.erpId = other.erpId == null ? null : other.erpId.copy();
        this.name1 = other.name1 == null ? null : other.name1.copy();
        this.name2 = other.name2 == null ? null : other.name2.copy();
        this.name3 = other.name3 == null ? null : other.name3.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.addressLine1 = other.addressLine1 == null ? null : other.addressLine1.copy();
        this.addressLine2 = other.addressLine2 == null ? null : other.addressLine2.copy();
        this.addressLine3 = other.addressLine3 == null ? null : other.addressLine3.copy();
        this.plz = other.plz == null ? null : other.plz.copy();
        this.city = other.city == null ? null : other.city.copy();
        this.country = other.country == null ? null : other.country.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.orderId = other.orderId == null ? null : other.orderId.copy();
    }

    @Override
    public CustomerCriteria copy() {
        return new CustomerCriteria(this);
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

    public StringFilter getName1() {
        return name1;
    }

    public void setName1(StringFilter name1) {
        this.name1 = name1;
    }

    public StringFilter getName2() {
        return name2;
    }

    public void setName2(StringFilter name2) {
        this.name2 = name2;
    }

    public StringFilter getName3() {
        return name3;
    }

    public void setName3(StringFilter name3) {
        this.name3 = name3;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public StringFilter getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(StringFilter addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public StringFilter getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(StringFilter addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public StringFilter getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(StringFilter addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public StringFilter getPlz() {
        return plz;
    }

    public void setPlz(StringFilter plz) {
        this.plz = plz;
    }

    public StringFilter getCity() {
        return city;
    }

    public void setCity(StringFilter city) {
        this.city = city;
    }

    public StringFilter getCountry() {
        return country;
    }

    public void setCountry(StringFilter country) {
        this.country = country;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
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
        final CustomerCriteria that = (CustomerCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(erpId, that.erpId) &&
            Objects.equals(name1, that.name1) &&
            Objects.equals(name2, that.name2) &&
            Objects.equals(name3, that.name3) &&
            Objects.equals(email, that.email) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(addressLine1, that.addressLine1) &&
            Objects.equals(addressLine2, that.addressLine2) &&
            Objects.equals(addressLine3, that.addressLine3) &&
            Objects.equals(plz, that.plz) &&
            Objects.equals(city, that.city) &&
            Objects.equals(country, that.country) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        erpId,
        name1,
        name2,
        name3,
        email,
        phone,
        addressLine1,
        addressLine2,
        addressLine3,
        plz,
        city,
        country,
        userId,
        orderId
        );
    }

    @Override
    public String toString() {
        return "CustomerCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (erpId != null ? "erpId=" + erpId + ", " : "") +
                (name1 != null ? "name1=" + name1 + ", " : "") +
                (name2 != null ? "name2=" + name2 + ", " : "") +
                (name3 != null ? "name3=" + name3 + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
                (addressLine1 != null ? "addressLine1=" + addressLine1 + ", " : "") +
                (addressLine2 != null ? "addressLine2=" + addressLine2 + ", " : "") +
                (addressLine3 != null ? "addressLine3=" + addressLine3 + ", " : "") +
                (plz != null ? "plz=" + plz + ", " : "") +
                (city != null ? "city=" + city + ", " : "") +
                (country != null ? "country=" + country + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (orderId != null ? "orderId=" + orderId + ", " : "") +
            "}";
    }

}
