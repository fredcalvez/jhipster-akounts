package com.akounts.myapp.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.akounts.myapp.domain.BankSaving} entity.
 */
public class BankSavingDTO implements Serializable {

    private Long id;

    private Instant summaryDate;

    private BigDecimal amount;

    private BigDecimal goal;

    private BigDecimal reach;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getSummaryDate() {
        return summaryDate;
    }

    public void setSummaryDate(Instant summaryDate) {
        this.summaryDate = summaryDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getGoal() {
        return goal;
    }

    public void setGoal(BigDecimal goal) {
        this.goal = goal;
    }

    public BigDecimal getReach() {
        return reach;
    }

    public void setReach(BigDecimal reach) {
        this.reach = reach;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankSavingDTO)) {
            return false;
        }

        BankSavingDTO bankSavingDTO = (BankSavingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bankSavingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankSavingDTO{" +
            "id=" + getId() +
            ", summaryDate='" + getSummaryDate() + "'" +
            ", amount=" + getAmount() +
            ", goal=" + getGoal() +
            ", reach=" + getReach() +
            "}";
    }
}
