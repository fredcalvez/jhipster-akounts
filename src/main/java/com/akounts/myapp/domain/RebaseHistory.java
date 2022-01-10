package com.akounts.myapp.domain;

import com.akounts.myapp.domain.enumeration.Currency;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RebaseHistory.
 */
@Entity
@Table(name = "rebase_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RebaseHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "old_value", precision = 21, scale = 2)
    private BigDecimal oldValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "old_currency")
    private Currency oldCurrency;

    @Column(name = "new_value", precision = 21, scale = 2)
    private BigDecimal newValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "new_currency")
    private Currency newCurrency;

    @Column(name = "run_date")
    private Instant runDate;

    @JsonIgnoreProperties(value = { "catId", "accountId" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private BankTransaction transactionId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RebaseHistory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getOldValue() {
        return this.oldValue;
    }

    public RebaseHistory oldValue(BigDecimal oldValue) {
        this.setOldValue(oldValue);
        return this;
    }

    public void setOldValue(BigDecimal oldValue) {
        this.oldValue = oldValue;
    }

    public Currency getOldCurrency() {
        return this.oldCurrency;
    }

    public RebaseHistory oldCurrency(Currency oldCurrency) {
        this.setOldCurrency(oldCurrency);
        return this;
    }

    public void setOldCurrency(Currency oldCurrency) {
        this.oldCurrency = oldCurrency;
    }

    public BigDecimal getNewValue() {
        return this.newValue;
    }

    public RebaseHistory newValue(BigDecimal newValue) {
        this.setNewValue(newValue);
        return this;
    }

    public void setNewValue(BigDecimal newValue) {
        this.newValue = newValue;
    }

    public Currency getNewCurrency() {
        return this.newCurrency;
    }

    public RebaseHistory newCurrency(Currency newCurrency) {
        this.setNewCurrency(newCurrency);
        return this;
    }

    public void setNewCurrency(Currency newCurrency) {
        this.newCurrency = newCurrency;
    }

    public Instant getRunDate() {
        return this.runDate;
    }

    public RebaseHistory runDate(Instant runDate) {
        this.setRunDate(runDate);
        return this;
    }

    public void setRunDate(Instant runDate) {
        this.runDate = runDate;
    }

    public BankTransaction getTransactionId() {
        return this.transactionId;
    }

    public void setTransactionId(BankTransaction bankTransaction) {
        this.transactionId = bankTransaction;
    }

    public RebaseHistory transactionId(BankTransaction bankTransaction) {
        this.setTransactionId(bankTransaction);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RebaseHistory)) {
            return false;
        }
        return id != null && id.equals(((RebaseHistory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RebaseHistory{" +
            "id=" + getId() +
            ", oldValue=" + getOldValue() +
            ", oldCurrency='" + getOldCurrency() + "'" +
            ", newValue=" + getNewValue() +
            ", newCurrency='" + getNewCurrency() + "'" +
            ", runDate='" + getRunDate() + "'" +
            "}";
    }
}
