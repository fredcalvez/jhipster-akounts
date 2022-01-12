package com.akounts.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.akounts.myapp.domain.BankTransactionAutomatch} entity.
 */
public class BankTransactionAutomatchDTO implements Serializable {

    private Long id;

    private BankTransactionDTO transactionId;

    private AutomatchDTO automatchId;

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

    public AutomatchDTO getAutomatchId() {
        return automatchId;
    }

    public void setAutomatchId(AutomatchDTO automatchId) {
        this.automatchId = automatchId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankTransactionAutomatchDTO)) {
            return false;
        }

        BankTransactionAutomatchDTO bankTransactionAutomatchDTO = (BankTransactionAutomatchDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bankTransactionAutomatchDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankTransactionAutomatchDTO{" +
            "id=" + getId() +
            ", transactionId=" + getTransactionId() +
            ", automatchId=" + getAutomatchId() +
            "}";
    }
}
