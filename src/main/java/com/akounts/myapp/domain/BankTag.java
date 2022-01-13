package com.akounts.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BankTag.
 */
@Entity
@Table(name = "bank_tag")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BankTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tag")
    private String tag;

    @Column(name = "use_count")
    private Integer useCount;

    @ManyToOne
    @JsonIgnoreProperties(value = { "bankTags" }, allowSetters = true)
    private BankVendor vendor;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BankTag id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return this.tag;
    }

    public BankTag tag(String tag) {
        this.setTag(tag);
        return this;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getUseCount() {
        return this.useCount;
    }

    public BankTag useCount(Integer useCount) {
        this.setUseCount(useCount);
        return this;
    }

    public void setUseCount(Integer useCount) {
        this.useCount = useCount;
    }

    public BankVendor getVendor() {
        return this.vendor;
    }

    public void setVendor(BankVendor bankVendor) {
        this.vendor = bankVendor;
    }

    public BankTag vendor(BankVendor bankVendor) {
        this.setVendor(bankVendor);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankTag)) {
            return false;
        }
        return id != null && id.equals(((BankTag) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankTag{" +
            "id=" + getId() +
            ", tag='" + getTag() + "'" +
            ", useCount=" + getUseCount() +
            "}";
    }
}
