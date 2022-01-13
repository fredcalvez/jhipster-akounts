package com.akounts.myapp.service.dto;

import com.akounts.myapp.domain.enumeration.Currency;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.akounts.myapp.domain.BankInstitution} entity.
 */
public class BankInstitutionDTO implements Serializable {

    private Long id;

    private String institutionLabel;

    private String code;

    private Boolean active;

    private Currency currency;

    private String isoCountryCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstitutionLabel() {
        return institutionLabel;
    }

    public void setInstitutionLabel(String institutionLabel) {
        this.institutionLabel = institutionLabel;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getIsoCountryCode() {
        return isoCountryCode;
    }

    public void setIsoCountryCode(String isoCountryCode) {
        this.isoCountryCode = isoCountryCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankInstitutionDTO)) {
            return false;
        }

        BankInstitutionDTO bankInstitutionDTO = (BankInstitutionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bankInstitutionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankInstitutionDTO{" +
            "id=" + getId() +
            ", institutionLabel='" + getInstitutionLabel() + "'" +
            ", code='" + getCode() + "'" +
            ", active='" + getActive() + "'" +
            ", currency='" + getCurrency() + "'" +
            ", isoCountryCode='" + getIsoCountryCode() + "'" +
            "}";
    }
}
