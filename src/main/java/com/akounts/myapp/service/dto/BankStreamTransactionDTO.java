package com.akounts.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.akounts.myapp.domain.BankStreamTransaction} entity.
 */
public class BankStreamTransactionDTO implements Serializable {

    private Long id;

    private BankTransactionDTO transactionId;

    private BankStreamDTO streamId;

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

    public BankStreamDTO getStreamId() {
        return streamId;
    }

    public void setStreamId(BankStreamDTO streamId) {
        this.streamId = streamId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankStreamTransactionDTO)) {
            return false;
        }

        BankStreamTransactionDTO bankStreamTransactionDTO = (BankStreamTransactionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bankStreamTransactionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankStreamTransactionDTO{" +
            "id=" + getId() +
            ", transactionId=" + getTransactionId() +
            ", streamId=" + getStreamId() +
            "}";
    }
}
