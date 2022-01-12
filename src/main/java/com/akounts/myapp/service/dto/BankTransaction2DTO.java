package com.akounts.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.akounts.myapp.domain.BankTransaction2} entity.
 */
public class BankTransaction2DTO implements Serializable {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankTransaction2DTO)) {
            return false;
        }

        BankTransaction2DTO bankTransaction2DTO = (BankTransaction2DTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bankTransaction2DTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankTransaction2DTO{" +
            "id=" + getId() +
            "}";
    }
}
