package com.akounts.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.akounts.myapp.domain.BankVendor} entity.
 */
public class BankVendorDTO implements Serializable {

    private Long id;

    private String vendorName;

    private Integer useCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Integer getUseCount() {
        return useCount;
    }

    public void setUseCount(Integer useCount) {
        this.useCount = useCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankVendorDTO)) {
            return false;
        }

        BankVendorDTO bankVendorDTO = (BankVendorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bankVendorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankVendorDTO{" +
            "id=" + getId() +
            ", vendorName='" + getVendorName() + "'" +
            ", useCount=" + getUseCount() +
            "}";
    }
}
