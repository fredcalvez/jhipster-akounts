package com.akounts.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.akounts.myapp.domain.BankProjectTransaction} entity.
 */
public class BankProjectTransactionDTO implements Serializable {

    private Long id;

    private BankTransactionDTO transactionId;

    private BankProjectDTO projectId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BankTransactionDTO getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(BankTransactionDTO transactionId) {
        this.transactionId = transactionId;
    }

    public BankProjectDTO getProjectId() {
        return projectId;
    }

    public void setProjectId(BankProjectDTO projectId) {
        this.projectId = projectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankProjectTransactionDTO)) {
            return false;
        }

        BankProjectTransactionDTO bankProjectTransactionDTO = (BankProjectTransactionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bankProjectTransactionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankProjectTransactionDTO{" +
            "id=" + getId() +
            ", transactionId=" + getTransactionId() +
            ", projectId=" + getProjectId() +
            "}";
    }
}
