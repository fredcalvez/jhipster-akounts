package com.akounts.myapp.domain;

import com.akounts.myapp.domain.enumeration.Currency;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BankInstitution.
 */
@Entity
@Table(name = "bank_institution")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BankInstitution implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "institution_label")
    private String institutionLabel;

    @Column(name = "code")
    private String code;

    @Column(name = "active")
    private Boolean active;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private Currency currency;

    @Column(name = "iso_country_code")
    private String isoCountryCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BankInstitution id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstitutionLabel() {
        return this.institutionLabel;
    }

    public BankInstitution institutionLabel(String institutionLabel) {
        this.setInstitutionLabel(institutionLabel);
        return this;
    }

    public void setInstitutionLabel(String institutionLabel) {
        this.institutionLabel = institutionLabel;
    }

    public String getCode() {
        return this.code;
    }

    public BankInstitution code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getActive() {
        return this.active;
    }

    public BankInstitution active(Boolean active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public BankInstitution currency(Currency currency) {
        this.setCurrency(currency);
        return this;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getIsoCountryCode() {
        return this.isoCountryCode;
    }

    public BankInstitution isoCountryCode(String isoCountryCode) {
        this.setIsoCountryCode(isoCountryCode);
        return this;
    }

    public void setIsoCountryCode(String isoCountryCode) {
        this.isoCountryCode = isoCountryCode;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankInstitution)) {
            return false;
        }
        return id != null && id.equals(((BankInstitution) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankInstitution{" +
            "id=" + getId() +
            ", institutionLabel='" + getInstitutionLabel() + "'" +
            ", code='" + getCode() + "'" +
            ", active='" + getActive() + "'" +
            ", currency='" + getCurrency() + "'" +
            ", isoCountryCode='" + getIsoCountryCode() + "'" +
            "}";
    }
}
