package com.akounts.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.akounts.myapp.domain.Plaid} entity.
 */
public class PlaidDTO implements Serializable {

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
        if (!(o instanceof PlaidDTO)) {
            return false;
        }

        PlaidDTO plaidDTO = (PlaidDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, plaidDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlaidDTO{" +
            "id=" + getId() +
            "}";
    }
}
