package com.akounts.myapp.service.dto;

import com.akounts.myapp.domain.enumeration.Currency;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.akounts.myapp.domain.RebaseHistory} entity.
 */
public class RebaseHistoryDTO implements Serializable {

    private Long id;

    private BigDecimal oldValue;

    private Currency oldCurrency;

    private BigDecimal newValue;

    private Currency newCurrency;

    private Instant runDate;

    private BankTransactionDTO transactionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getOldValue() {
        return oldValue;
    }

    public void setOldValue(BigDecimal oldValue) {
        this.oldValue = oldValue;
    }

    public Currency getOldCurrency() {
        return oldCurrency;
    }

    public void setOldCurrency(Currency oldCurrency) {
        this.oldCurrency = oldCurrency;
    }

    public BigDecimal getNewValue() {
        return newValue;
    }

    public void setNewValue(BigDecimal newValue) {
        this.newValue = newValue;
    }

    public Currency getNewCurrency() {
        return newCurrency;
    }

    public void setNewCurrency(Currency newCurrency) {
        this.newCurrency = newCurrency;
    }

    public Instant getRunDate() {
        return runDate;
    }

    public void setRunDate(Instant runDate) {
        this.runDate = runDate;
    }

    public BankTransactionDTO getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(BankTransactionDTO transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RebaseHistoryDTO)) {
            return false;
        }

        RebaseHistoryDTO rebaseHistoryDTO = (RebaseHistoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, rebaseHistoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RebaseHistoryDTO{" +
            "id=" + getId() +
            ", oldValue=" + getOldValue() +
            ", oldCurrency='" + getOldCurrency() + "'" +
            ", newValue=" + getNewValue() +
            ", newCurrency='" + getNewCurrency() + "'" +
            ", runDate='" + getRunDate() + "'" +
            ", transactionId=" + getTransactionId() +
            "}";
    }
}
