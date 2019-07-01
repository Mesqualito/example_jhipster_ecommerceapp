package net.generica.store.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ProductSubstitution.
 */
@Entity
@Table(name = "prod_substitution")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductSubstitution implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @NotNull
    @Column(name = "exchangeable", nullable = false)
    private Boolean exchangeable;

    @Column(name = "checked")
    private Boolean checked;

    @ManyToMany(mappedBy = "productSubstitutions")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Product> products = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public ProductSubstitution productName(String productName) {
        this.productName = productName;
        return this;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Boolean isExchangeable() {
        return exchangeable;
    }

    public ProductSubstitution exchangeable(Boolean exchangeable) {
        this.exchangeable = exchangeable;
        return this;
    }

    public void setExchangeable(Boolean exchangeable) {
        this.exchangeable = exchangeable;
    }

    public Boolean isChecked() {
        return checked;
    }

    public ProductSubstitution checked(Boolean checked) {
        this.checked = checked;
        return this;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public ProductSubstitution products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public ProductSubstitution addProduct(Product product) {
        this.products.add(product);
        product.getProductSubstitutions().add(this);
        return this;
    }

    public ProductSubstitution removeProduct(Product product) {
        this.products.remove(product);
        product.getProductSubstitutions().remove(this);
        return this;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductSubstitution)) {
            return false;
        }
        return id != null && id.equals(((ProductSubstitution) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProductSubstitution{" +
            "id=" + getId() +
            ", productName='" + getProductName() + "'" +
            ", exchangeable='" + isExchangeable() + "'" +
            ", checked='" + isChecked() + "'" +
            "}";
    }
}
