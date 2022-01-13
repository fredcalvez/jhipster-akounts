package com.akounts.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BankVendor.
 */
@Entity
@Table(name = "bank_vendor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BankVendor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "vendor_name")
    private String vendorName;

    @Column(name = "use_count")
    private Integer useCount;

    @OneToMany(mappedBy = "vendor")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "vendor" }, allowSetters = true)
    private Set<BankTag> bankTags = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BankVendor id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVendorName() {
        return this.vendorName;
    }

    public BankVendor vendorName(String vendorName) {
        this.setVendorName(vendorName);
        return this;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Integer getUseCount() {
        return this.useCount;
    }

    public BankVendor useCount(Integer useCount) {
        this.setUseCount(useCount);
        return this;
    }

    public void setUseCount(Integer useCount) {
        this.useCount = useCount;
    }

    public Set<BankTag> getBankTags() {
        return this.bankTags;
    }

    public void setBankTags(Set<BankTag> bankTags) {
        if (this.bankTags != null) {
            this.bankTags.forEach(i -> i.setVendor(null));
        }
        if (bankTags != null) {
            bankTags.forEach(i -> i.setVendor(this));
        }
        this.bankTags = bankTags;
    }

    public BankVendor bankTags(Set<BankTag> bankTags) {
        this.setBankTags(bankTags);
        return this;
    }

    public BankVendor addBankTag(BankTag bankTag) {
        this.bankTags.add(bankTag);
        bankTag.setVendor(this);
        return this;
    }

    public BankVendor removeBankTag(BankTag bankTag) {
        this.bankTags.remove(bankTag);
        bankTag.setVendor(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankVendor)) {
            return false;
        }
        return id != null && id.equals(((BankVendor) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankVendor{" +
            "id=" + getId() +
            ", vendorName='" + getVendorName() + "'" +
            ", useCount=" + getUseCount() +
            "}";
    }
}
