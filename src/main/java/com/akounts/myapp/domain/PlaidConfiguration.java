package com.akounts.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PlaidConfiguration.
 */
@Entity
@Table(name = "plaid_configuration")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PlaidConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "environement")
    private String environement;

    @Column(name = "jhi_key")
    private String key;

    @Column(name = "value")
    private String value;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PlaidConfiguration id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnvironement() {
        return this.environement;
    }

    public PlaidConfiguration environement(String environement) {
        this.setEnvironement(environement);
        return this;
    }

    public void setEnvironement(String environement) {
        this.environement = environement;
    }

    public String getKey() {
        return this.key;
    }

    public PlaidConfiguration key(String key) {
        this.setKey(key);
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return this.value;
    }

    public PlaidConfiguration value(String value) {
        this.setValue(value);
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlaidConfiguration)) {
            return false;
        }
        return id != null && id.equals(((PlaidConfiguration) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlaidConfiguration{" +
            "id=" + getId() +
            ", environement='" + getEnvironement() + "'" +
            ", key='" + getKey() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
