package com.akounts.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.akounts.myapp.domain.BankStream} entity.
 */
public class BankStreamDTO implements Serializable {

    private Long id;

    private String name;

    private String streamType;

    private BankAccountDTO mainAccountId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreamType() {
        return streamType;
    }

    public void setStreamType(String streamType) {
        this.streamType = streamType;
    }

    public BankAccountDTO getMainAccountId() {
        return mainAccountId;
    }

    public void setMainAccountId(BankAccountDTO mainAccountId) {
        this.mainAccountId = mainAccountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankStreamDTO)) {
            return false;
        }

        BankStreamDTO bankStreamDTO = (BankStreamDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bankStreamDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankStreamDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", streamType='" + getStreamType() + "'" +
            ", mainAccountId=" + getMainAccountId() +
            "}";
    }
}
