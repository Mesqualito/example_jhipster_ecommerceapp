package net.generica.store.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A ProductSubstitution.
 */
@Entity
@Table(name = "product_substitution")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductSubstitution implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "exchangeable", nullable = false)
    private Boolean exchangeable;

    @Column(name = "checked")
    private Boolean checked;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ProductSubstitution name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
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
            ", name='" + getName() + "'" +
            ", exchangeable='" + isExchangeable() + "'" +
            ", checked='" + isChecked() + "'" +
            "}";
    }
}
