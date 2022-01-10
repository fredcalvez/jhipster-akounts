package com.akounts.myapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BankAccountInterest.
 */
@Entity
@Table(name = "bank_account_interest")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BankAccountInterest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "interest", precision = 21, scale = 2)
    private BigDecimal interest;

    @Column(name = "period")
    private String period;

    @Column(name = "interest_type")
    private String interestType;

    @Column(name = "units", precision = 21, scale = 2)
    private BigDecimal units;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "scrapping_url")
    private String scrappingURL;

    @Column(name = "scrapping_tag")
    private String scrappingTag;

    @Column(name = "scrapping_tag_bis")
    private String scrappingTagBis;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BankAccountInterest id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getInterest() {
        return this.interest;
    }

    public BankAccountInterest interest(BigDecimal interest) {
        this.setInterest(interest);
        return this;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public String getPeriod() {
        return this.period;
    }

    public BankAccountInterest period(String period) {
        this.setPeriod(period);
        return this;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getInterestType() {
        return this.interestType;
    }

    public BankAccountInterest interestType(String interestType) {
        this.setInterestType(interestType);
        return this;
    }

    public void setInterestType(String interestType) {
        this.interestType = interestType;
    }

    public BigDecimal getUnits() {
        return this.units;
    }

    public BankAccountInterest units(BigDecimal units) {
        this.setUnits(units);
        return this;
    }

    public void setUnits(BigDecimal units) {
        this.units = units;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public BankAccountInterest startDate(LocalDate startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public BankAccountInterest endDate(LocalDate endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getScrappingURL() {
        return this.scrappingURL;
    }

    public BankAccountInterest scrappingURL(String scrappingURL) {
        this.setScrappingURL(scrappingURL);
        return this;
    }

    public void setScrappingURL(String scrappingURL) {
        this.scrappingURL = scrappingURL;
    }

    public String getScrappingTag() {
        return this.scrappingTag;
    }

    public BankAccountInterest scrappingTag(String scrappingTag) {
        this.setScrappingTag(scrappingTag);
        return this;
    }

    public void setScrappingTag(String scrappingTag) {
        this.scrappingTag = scrappingTag;
    }

    public String getScrappingTagBis() {
        return this.scrappingTagBis;
    }

    public BankAccountInterest scrappingTagBis(String scrappingTagBis) {
        this.setScrappingTagBis(scrappingTagBis);
        return this;
    }

    public void setScrappingTagBis(String scrappingTagBis) {
        this.scrappingTagBis = scrappingTagBis;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankAccountInterest)) {
            return false;
        }
        return id != null && id.equals(((BankAccountInterest) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankAccountInterest{" +
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
            "}";
    }
}
