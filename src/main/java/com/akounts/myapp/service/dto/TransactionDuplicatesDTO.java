package com.akounts.myapp.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.akounts.myapp.domain.TransactionDuplicates} entity.
 */
public class TransactionDuplicatesDTO implements Serializable {

    private Long id;

    private Boolean isduplicate;

    private Instant dateAdd;

    private String action;

    private Boolean checked;

    private BankTransactionDTO parentTransactionId;

    private BankTransactionDTO childTransactionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsduplicate() {
        return isduplicate;
    }

    public void setIsduplicate(Boolean isduplicate) {
        this.isduplicate = isduplicate;
    }

    public Instant getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(Instant dateAdd) {
        this.dateAdd = dateAdd;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public BankTransactionDTO getParentTransactionId() {
        return parentTransactionId;
    }

    public void setParentTransactionId(BankTransactionDTO parentTransactionId) {
        this.parentTransactionId = parentTransactionId;
    }

    public BankTransactionDTO getChildTransactionId() {
        return childTransactionId;
    }

    public void setChildTransactionId(BankTransactionDTO childTransactionId) {
        this.childTransactionId = childTransactionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionDuplicatesDTO)) {
            return false;
        }

        TransactionDuplicatesDTO transactionDuplicatesDTO = (TransactionDuplicatesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, transactionDuplicatesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransactionDuplicatesDTO{" +
            "id=" + getId() +
            ", isduplicate='" + getIsduplicate() + "'" +
            ", dateAdd='" + getDateAdd() + "'" +
            ", action='" + getAction() + "'" +
            ", checked='" + getChecked() + "'" +
            ", parentTransactionId=" + getParentTransactionId() +
            ", childTransactionId=" + getChildTransactionId() +
            "}";
    }
}
