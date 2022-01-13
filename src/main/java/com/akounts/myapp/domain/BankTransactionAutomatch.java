package com.akounts.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BankTransactionAutomatch.
 */
@Entity
@Table(name = "bank_transaction_automatch")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BankTransactionAutomatch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonIgnoreProperties(value = { "catId", "accountId", "category", "account" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private BankTransaction transactionId;

    @JsonIgnoreProperties(value = { "category" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Automatch automatchId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BankTransactionAutomatch id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BankTransaction getTransactionId() {
        return this.transactionId;
    }

    public void setTransactionId(BankTransaction bankTransaction) {
        this.transactionId = bankTransaction;
    }

    public BankTransactionAutomatch transactionId(BankTransaction bankTransaction) {
        this.setTransactionId(bankTransaction);
        return this;
    }

    public Automatch getAutomatchId() {
        return this.automatchId;
    }

    public void setAutomatchId(Automatch automatch) {
        this.automatchId = automatch;
    }

    public BankTransactionAutomatch automatchId(Automatch automatch) {
        this.setAutomatchId(automatch);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankTransactionAutomatch)) {
            return false;
        }
        return id != null && id.equals(((BankTransactionAutomatch) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankTransactionAutomatch{" +
            "id=" + getId() +
            "}";
    }
}
