package com.akounts.myapp.service.dto;

import com.akounts.myapp.domain.enumeration.Currency;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.akounts.myapp.domain.BridgeTransaction} entity.
 */
public class BridgeTransactionDTO implements Serializable {

    private Long id;

    private String transactionId;

    private String transactionType;

    private String accountId;

    private BigDecimal amount;

    private Currency isoCurrencyCode;

    private Instant transactionDate;

    private String description;

    private Boolean isFuture;

    private Boolean isDeleted;

    private Instant addedDate;

    private Instant updatedAt;

    private Boolean checked;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currency getIsoCurrencyCode() {
        return isoCurrencyCode;
    }

    public void setIsoCurrencyCode(Currency isoCurrencyCode) {
        this.isoCurrencyCode = isoCurrencyCode;
    }

    public Instant getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Instant transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsFuture() {
        return isFuture;
    }

    public void setIsFuture(Boolean isFuture) {
        this.isFuture = isFuture;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Instant getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Instant addedDate) {
        this.addedDate = addedDate;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BridgeTransactionDTO)) {
            return false;
        }

        BridgeTransactionDTO bridgeTransactionDTO = (BridgeTransactionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bridgeTransactionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BridgeTransactionDTO{" +
            "id=" + getId() +
            ", transactionId='" + getTransactionId() + "'" +
            ", transactionType='" + getTransactionType() + "'" +
            ", accountId='" + getAccountId() + "'" +
            ", amount=" + getAmount() +
            ", isoCurrencyCode='" + getIsoCurrencyCode() + "'" +
            ", transactionDate='" + getTransactionDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", isFuture='" + getIsFuture() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", addedDate='" + getAddedDate() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", checked='" + getChecked() + "'" +
            "}";
    }
}
