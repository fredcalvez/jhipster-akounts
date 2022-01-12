package com.akounts.myapp.service.dto;

import com.akounts.myapp.domain.enumeration.Currency;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.akounts.myapp.domain.PlaidAccount} entity.
 */
public class PlaidAccountDTO implements Serializable {

    private Long id;

    private String plaidAccountId;

    private String itemId;

    private String type;

    private String subtype;

    private BigDecimal balanceAvailable;

    private BigDecimal balanceCurrent;

    private Currency isoCurrencyCode;

    private String name;

    private String officialName;

    private String iban;

    private String bic;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaidAccountId() {
        return plaidAccountId;
    }

    public void setPlaidAccountId(String plaidAccountId) {
        this.plaidAccountId = plaidAccountId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public BigDecimal getBalanceAvailable() {
        return balanceAvailable;
    }

    public void setBalanceAvailable(BigDecimal balanceAvailable) {
        this.balanceAvailable = balanceAvailable;
    }

    public BigDecimal getBalanceCurrent() {
        return balanceCurrent;
    }

    public void setBalanceCurrent(BigDecimal balanceCurrent) {
        this.balanceCurrent = balanceCurrent;
    }

    public Currency getIsoCurrencyCode() {
        return isoCurrencyCode;
    }

    public void setIsoCurrencyCode(Currency isoCurrencyCode) {
        this.isoCurrencyCode = isoCurrencyCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOfficialName() {
        return officialName;
    }

    public void setOfficialName(String officialName) {
        this.officialName = officialName;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlaidAccountDTO)) {
            return false;
        }

        PlaidAccountDTO plaidAccountDTO = (PlaidAccountDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, plaidAccountDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlaidAccountDTO{" +
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
