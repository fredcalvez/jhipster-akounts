package com.akounts.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TransactionDuplicates.
 */
@Entity
@Table(name = "transaction_duplicates")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TransactionDuplicates implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "isduplicate")
    private Boolean isduplicate;

    @Column(name = "date_add")
    private Instant dateAdd;

    @Column(name = "action")
    private String action;

    @Column(name = "checked")
    private Boolean checked;

    @JsonIgnoreProperties(value = { "catId", "accountId", "category", "account" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private BankTransaction parentTransactionId;

    @JsonIgnoreProperties(value = { "catId", "accountId", "category", "account" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private BankTransaction childTransactionId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TransactionDuplicates id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsduplicate() {
        return this.isduplicate;
    }

    public TransactionDuplicates isduplicate(Boolean isduplicate) {
        this.setIsduplicate(isduplicate);
        return this;
    }

    public void setIsduplicate(Boolean isduplicate) {
        this.isduplicate = isduplicate;
    }

    public Instant getDateAdd() {
        return this.dateAdd;
    }

    public TransactionDuplicates dateAdd(Instant dateAdd) {
        this.setDateAdd(dateAdd);
        return this;
    }

    public void setDateAdd(Instant dateAdd) {
        this.dateAdd = dateAdd;
    }

    public String getAction() {
        return this.action;
    }

    public TransactionDuplicates action(String action) {
        this.setAction(action);
        return this;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Boolean getChecked() {
        return this.checked;
    }

    public TransactionDuplicates checked(Boolean checked) {
        this.setChecked(checked);
        return this;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public BankTransaction getParentTransactionId() {
        return this.parentTransactionId;
    }

    public void setParentTransactionId(BankTransaction bankTransaction) {
        this.parentTransactionId = bankTransaction;
    }

    public TransactionDuplicates parentTransactionId(BankTransaction bankTransaction) {
        this.setParentTransactionId(bankTransaction);
        return this;
    }

    public BankTransaction getChildTransactionId() {
        return this.childTransactionId;
    }

    public void setChildTransactionId(BankTransaction bankTransaction) {
        this.childTransactionId = bankTransaction;
    }

    public TransactionDuplicates childTransactionId(BankTransaction bankTransaction) {
        this.setChildTransactionId(bankTransaction);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionDuplicates)) {
            return false;
        }
        return id != null && id.equals(((TransactionDuplicates) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransactionDuplicates{" +
            "id=" + getId() +
            ", isduplicate='" + getIsduplicate() + "'" +
            ", dateAdd='" + getDateAdd() + "'" +
            ", action='" + getAction() + "'" +
            ", checked='" + getChecked() + "'" +
            "}";
    }
}
