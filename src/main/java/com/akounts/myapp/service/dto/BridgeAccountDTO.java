package com.akounts.myapp.service.dto;

import com.akounts.myapp.domain.enumeration.Currency;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.akounts.myapp.domain.BridgeAccount} entity.
 */
public class BridgeAccountDTO implements Serializable {

    private Long id;

    private String bridgeAccountId;

    private String type;

    private String status;

    private BigDecimal balance;

    private Currency isoCurrencyCode;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBridgeAccountId() {
        return bridgeAccountId;
    }

    public void setBridgeAccountId(String bridgeAccountId) {
        this.bridgeAccountId = bridgeAccountId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BridgeAccountDTO)) {
            return false;
        }

        BridgeAccountDTO bridgeAccountDTO = (BridgeAccountDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bridgeAccountDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BridgeAccountDTO{" +
            "id=" + getId() +
            ", bridgeAccountId='" + getBridgeAccountId() + "'" +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            ", balance=" + getBalance() +
            ", isoCurrencyCode='" + getIsoCurrencyCode() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
