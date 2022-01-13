package com.akounts.myapp.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.akounts.myapp.domain.PlaidTransaction} entity.
 */
public class PlaidTransactionDTO implements Serializable {

    private Long id;

    private String transactionId;

    private String transactionType;

    private String accountId;

    private BigDecimal amount;

    private String isoCurrencyCode;

    private Instant transactionDate;

    private String name;

    private String originalDescription;

    private Boolean pending;

    private String pendingTransactionId;

    private Instant addedDate;

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

    public String getIsoCurrencyCode() {
        return isoCurrencyCode;
    }

    public void setIsoCurrencyCode(String isoCurrencyCode) {
        this.isoCurrencyCode = isoCurrencyCode;
    }

    public Instant getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Instant transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalDescription() {
        return originalDescription;
    }

    public void setOriginalDescription(String originalDescription) {
        this.originalDescription = originalDescription;
    }

    public Boolean getPending() {
        return pending;
    }

    public void setPending(Boolean pending) {
        this.pending = pending;
    }

    public String getPendingTransactionId() {
        return pendingTransactionId;
    }

    public void setPendingTransactionId(String pendingTransactionId) {
        this.pendingTransactionId = pendingTransactionId;
    }

    public Instant getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Instant addedDate) {
        this.addedDate = addedDate;
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
        if (!(o instanceof PlaidTransactionDTO)) {
            return false;
        }

        PlaidTransactionDTO plaidTransactionDTO = (PlaidTransactionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, plaidTransactionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlaidTransactionDTO{" +
            "id=" + getId() +
            ", transactionId='" + getTransactionId() + "'" +
            ", transactionType='" + getTransactionType() + "'" +
            ", accountId='" + getAccountId() + "'" +
            ", amount=" + getAmount() +
            ", isoCurrencyCode='" + getIsoCurrencyCode() + "'" +
            ", transactionDate='" + getTransactionDate() + "'" +
            ", name='" + getName() + "'" +
            ", originalDescription='" + getOriginalDescription() + "'" +
            ", pending='" + getPending() + "'" +
            ", pendingTransactionId='" + getPendingTransactionId() + "'" +
            ", addedDate='" + getAddedDate() + "'" +
            ", checked='" + getChecked() + "'" +
            "}";
    }
}
