package com.akounts.myapp.domain;

import com.akounts.myapp.domain.enumeration.Currency;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PlaidAccount.
 */
@Entity
@Table(name = "plaid_account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PlaidAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "plaid_account_id")
    private String plaidAccountId;

    @Column(name = "item_id")
    private String itemId;

    @Column(name = "type")
    private String type;

    @Column(name = "subtype")
    private String subtype;

    @Column(name = "balance_available", precision = 21, scale = 2)
    private BigDecimal balanceAvailable;

    @Column(name = "balance_current", precision = 21, scale = 2)
    private BigDecimal balanceCurrent;

    @Enumerated(EnumType.STRING)
    @Column(name = "iso_currency_code")
    private Currency isoCurrencyCode;

    @Column(name = "name")
    private String name;

    @Column(name = "official_name")
    private String officialName;

    @Column(name = "iban")
    private String iban;

    @Column(name = "bic")
    private String bic;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PlaidAccount id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaidAccountId() {
        return this.plaidAccountId;
    }

    public PlaidAccount plaidAccountId(String plaidAccountId) {
        this.setPlaidAccountId(plaidAccountId);
        return this;
    }

    public void setPlaidAccountId(String plaidAccountId) {
        this.plaidAccountId = plaidAccountId;
    }

    public String getItemId() {
        return this.itemId;
    }

    public PlaidAccount itemId(String itemId) {
        this.setItemId(itemId);
        return this;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getType() {
        return this.type;
    }

    public PlaidAccount type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return this.subtype;
    }

    public PlaidAccount subtype(String subtype) {
        this.setSubtype(subtype);
        return this;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public BigDecimal getBalanceAvailable() {
        return this.balanceAvailable;
    }

    public PlaidAccount balanceAvailable(BigDecimal balanceAvailable) {
        this.setBalanceAvailable(balanceAvailable);
        return this;
    }

    public void setBalanceAvailable(BigDecimal balanceAvailable) {
        this.balanceAvailable = balanceAvailable;
    }

    public BigDecimal getBalanceCurrent() {
        return this.balanceCurrent;
    }

    public PlaidAccount balanceCurrent(BigDecimal balanceCurrent) {
        this.setBalanceCurrent(balanceCurrent);
        return this;
    }

    public void setBalanceCurrent(BigDecimal balanceCurrent) {
        this.balanceCurrent = balanceCurrent;
    }

    public Currency getIsoCurrencyCode() {
        return this.isoCurrencyCode;
    }

    public PlaidAccount isoCurrencyCode(Currency isoCurrencyCode) {
        this.setIsoCurrencyCode(isoCurrencyCode);
        return this;
    }

    public void setIsoCurrencyCode(Currency isoCurrencyCode) {
        this.isoCurrencyCode = isoCurrencyCode;
    }

    public String getName() {
        return this.name;
    }

    public PlaidAccount name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOfficialName() {
        return this.officialName;
    }

    public PlaidAccount officialName(String officialName) {
        this.setOfficialName(officialName);
        return this;
    }

    public void setOfficialName(String officialName) {
        this.officialName = officialName;
    }

    public String getIban() {
        return this.iban;
    }

    public PlaidAccount iban(String iban) {
        this.setIban(iban);
        return this;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBic() {
        return this.bic;
    }

    public PlaidAccount bic(String bic) {
        this.setBic(bic);
        return this;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlaidAccount)) {
            return false;
        }
        return id != null && id.equals(((PlaidAccount) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlaidAccount{" +
            "id=" + getId() +
            ", plaidAccountId='" + getPlaidAccountId() + "'" +
            ", itemId='" + getItemId() + "'" +
            ", type='" + getType() + "'" +
            ", subtype='" + getSubtype() + "'" +
            ", balanceAvailable=" + getBalanceAvailable() +
            ", balanceCurrent=" + getBalanceCurrent() +
            ", isoCurrencyCode='" + getIsoCurrencyCode() + "'" +
            ", name='" + getName() + "'" +
            ", officialName='" + getOfficialName() + "'" +
            ", iban='" + getIban() + "'" +
            ", bic='" + getBic() + "'" +
            "}";
    }
}
