package com.akounts.myapp.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.akounts.myapp.domain.BankAccountInterest} entity.
 */
public class BankAccountInterestDTO implements Serializable {

    private Long id;

    private BigDecimal interest;

    private String period;

    private String interestType;

    private BigDecimal units;

    private LocalDate startDate;

    private LocalDate endDate;

    private String scrappingURL;

    private String scrappingTag;

    private String scrappingTagBis;

    private BankAccountDTO creditedAccountId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getInterestType() {
        return interestType;
    }

    public void setInterestType(String interestType) {
        this.interestType = interestType;
    }

    public BigDecimal getUnits() {
        return units;
    }

    public void setUnits(BigDecimal units) {
        this.units = units;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getScrappingURL() {
        return scrappingURL;
    }

    public void setScrappingURL(String scrappingURL) {
        this.scrappingURL = scrappingURL;
    }

    public String getScrappingTag() {
        return scrappingTag;
    }

    public void setScrappingTag(String scrappingTag) {
        this.scrappingTag = scrappingTag;
    }

    public String getScrappingTagBis() {
        return scrappingTagBis;
    }

    public void setScrappingTagBis(String scrappingTagBis) {
        this.scrappingTagBis = scrappingTagBis;
    }

    public BankAccountDTO getCreditedAccountId() {
        return creditedAccountId;
    }

    public void setCreditedAccountId(BankAccountDTO creditedAccountId) {
        this.creditedAccountId = creditedAccountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankAccountInterestDTO)) {
            return false;
        }

        BankAccountInterestDTO bankAccountInterestDTO = (BankAccountInterestDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bankAccountInterestDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankAccountInterestDTO{" +
            "id=" + getId() +
            ", interest=" + getInterest() +
            ", period='" + getPeriod() + "'" +
            ", interestType='" + getInterestType() + "'" +
            ", units=" + getUnits() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", scrappingURL='" + getScrappingURL() + "'" +
            ", scrappingTag='" + getScrappingTag() + "'" +
            ", scrappingTagBis='" + getScrappingTagBis() + "'" +
            ", creditedAccountId=" + getCreditedAccountId() +
            "}";
    }
}
