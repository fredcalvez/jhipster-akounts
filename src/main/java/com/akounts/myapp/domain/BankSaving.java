package com.akounts.myapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BankSaving.
 */
@Entity
@Table(name = "bank_saving")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BankSaving implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "summary_date")
    private Instant summaryDate;

    @Column(name = "amount", precision = 21, scale = 2)
    private BigDecimal amount;

    @Column(name = "goal", precision = 21, scale = 2)
    private BigDecimal goal;

    @Column(name = "reach", precision = 21, scale = 2)
    private BigDecimal reach;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BankSaving id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getSummaryDate() {
        return this.summaryDate;
    }

    public BankSaving summaryDate(Instant summaryDate) {
        this.setSummaryDate(summaryDate);
        return this;
    }

    public void setSummaryDate(Instant summaryDate) {
        this.summaryDate = summaryDate;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public BankSaving amount(BigDecimal amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getGoal() {
        return this.goal;
    }

    public BankSaving goal(BigDecimal goal) {
        this.setGoal(goal);
        return this;
    }

    public void setGoal(BigDecimal goal) {
        this.goal = goal;
    }

    public BigDecimal getReach() {
        return this.reach;
    }

    public BankSaving reach(BigDecimal reach) {
        this.setReach(reach);
        return this;
    }

    public void setReach(BigDecimal reach) {
        this.reach = reach;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankSaving)) {
            return false;
        }
        return id != null && id.equals(((BankSaving) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankSaving{" +
            "id=" + getId() +
            ", summaryDate='" + getSummaryDate() + "'" +
            ", amount=" + getAmount() +
            ", goal=" + getGoal() +
            ", reach=" + getReach() +
            "}";
    }
}
