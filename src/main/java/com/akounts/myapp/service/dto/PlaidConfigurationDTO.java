package com.akounts.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.akounts.myapp.domain.PlaidConfiguration} entity.
 */
public class PlaidConfigurationDTO implements Serializable {

    private Long id;

    private String environement;

    private String key;

    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnvironement() {
        return environement;
    }

    public void setEnvironement(String environement) {
        this.environement = environement;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlaidConfigurationDTO)) {
            return false;
        }

        PlaidConfigurationDTO plaidConfigurationDTO = (PlaidConfigurationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, plaidConfigurationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlaidConfigurationDTO{" +
            "id=" + getId() +
            ", environement='" + getEnvironement() + "'" +
            ", key='" + getKey() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
