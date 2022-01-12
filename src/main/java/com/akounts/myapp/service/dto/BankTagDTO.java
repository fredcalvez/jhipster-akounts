package com.akounts.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.akounts.myapp.domain.BankTag} entity.
 */
public class BankTagDTO implements Serializable {

    private Long id;

    private String tag;

    private Integer useCount;

    private BankVendorDTO vendor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getUseCount() {
        return useCount;
    }

    public void setUseCount(Integer useCount) {
        this.useCount = useCount;
    }

    public BankVendorDTO getVendor() {
        return vendor;
    }

    public void setVendor(BankVendorDTO vendor) {
        this.vendor = vendor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankTagDTO)) {
            return false;
        }

        BankTagDTO bankTagDTO = (BankTagDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bankTagDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankTagDTO{" +
            "id=" + getId() +
            ", tag='" + getTag() + "'" +
            ", useCount=" + getUseCount() +
            ", vendor=" + getVendor() +
            "}";
    }
}
