package com.akounts.myapp.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.akounts.myapp.domain.PlaidItem} entity.
 */
public class PlaidItemDTO implements Serializable {

    private Long id;

    private String itemId;

    private String institutionId;

    private String accessToken;

    private Instant addedDate;

    private Instant updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Instant getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Instant addedDate) {
        this.addedDate = addedDate;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlaidItemDTO)) {
            return false;
        }

        PlaidItemDTO plaidItemDTO = (PlaidItemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, plaidItemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlaidItemDTO{" +
            "id=" + getId() +
            ", itemId='" + getItemId() + "'" +
            ", institutionId='" + getInstitutionId() + "'" +
            ", accessToken='" + getAccessToken() + "'" +
            ", addedDate='" + getAddedDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
