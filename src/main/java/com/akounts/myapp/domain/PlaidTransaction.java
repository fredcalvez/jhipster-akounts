package com.akounts.myapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PlaidTransaction.
 */
@Entity
@Table(name = "plaid_transaction")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PlaidTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "amount", precision = 21, scale = 2)
    private BigDecimal amount;

    @Column(name = "iso_currency_code")
    private String isoCurrencyCode;

    @Column(name = "transaction_date")
    private Instant transactionDate;

    @Column(name = "name")
    private String name;

    @Column(name = "original_description")
    private String originalDescription;

    @Column(name = "pending")
    private Boolean pending;

    @Column(name = "pending_transaction_id")
    private String pendingTransactionId;

    @Column(name = "added_date")
    private Instant addedDate;

    @Column(name = "checked")
    private Boolean checked;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PlaidTransaction id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public PlaidTransaction transactionId(String transactionId) {
        this.setTransactionId(transactionId);
        return this;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionType() {
        return this.transactionType;
    }

    public PlaidTransaction transactionType(String transactionType) {
        this.setTransactionType(transactionType);
        return this;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getAccountId() {
        return this.accountId;
    }

    public PlaidTransaction accountId(String accountId) {
        this.setAccountId(accountId);
        return this;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public PlaidTransaction amount(BigDecimal amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getIsoCurrencyCode() {
        return this.isoCurrencyCode;
    }

    public PlaidTransaction isoCurrencyCode(String isoCurrencyCode) {
        this.setIsoCurrencyCode(isoCurrencyCode);
        return this;
    }

    public void setIsoCurrencyCode(String isoCurrencyCode) {
        this.isoCurrencyCode = isoCurrencyCode;
    }

    public Instant getTransactionDate() {
        return this.transactionDate;
    }

    public PlaidTransaction transactionDate(Instant transactionDate) {
        this.setTransactionDate(transactionDate);
        return this;
    }

    public void setTransactionDate(Instant transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getName() {
        return this.name;
    }

    public PlaidTransaction name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalDescription() {
        return this.originalDescription;
    }

    public PlaidTransaction originalDescription(String originalDescription) {
        this.setOriginalDescription(originalDescription);
        return this;
    }

    public void setOriginalDescription(String originalDescription) {
        this.originalDescription = originalDescription;
    }

    public Boolean getPending() {
        return this.pending;
    }

    public PlaidTransaction pending(Boolean pending) {
        this.setPending(pending);
        return this;
    }

    public void setPending(Boolean pending) {
        this.pending = pending;
    }

    public String getPendingTransactionId() {
        return this.pendingTransactionId;
    }

    public PlaidTransaction pendingTransactionId(String pendingTransactionId) {
        this.setPendingTransactionId(pendingTransactionId);
        return this;
    }

    public void setPendingTransactionId(String pendingTransactionId) {
        this.pendingTransactionId = pendingTransactionId;
    }

    public Instant getAddedDate() {
        return this.addedDate;
    }

    public PlaidTransaction addedDate(Instant addedDate) {
        this.setAddedDate(addedDate);
        return this;
    }

    public void setAddedDate(Instant addedDate) {
        this.addedDate = addedDate;
    }

    public Boolean getChecked() {
        return this.checked;
    }

    public PlaidTransaction checked(Boolean checked) {
        this.setChecked(checked);
        return this;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlaidTransaction)) {
            return false;
        }
        return id != null && id.equals(((PlaidTransaction) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlaidTransaction{" +
            "id=" + getId() +
            ", transactionId='" + getTransactionId() + "'" +
            ", transactionType='" + getTransactionType() + "'" +
            ", accountId='" + getAccountId() + "'" +
            ", amount=" + getAmount() +
            ", isoCurrencyCode='" + getIsoCurrencyCode() + "'" +
            ", transactionDate='" + getTransactionDate() + "'" +
            ", name='" + getName() + "'" +
            ", originalDescription='" + getOriginalDescription() + "'" +
            ", pending='" + getPending() + "'" +
            ", pendingTransactionId='" + getPendingTransactionId() + "'" +
            ", addedDate='" + getAddedDate() + "'" +
            ", checked='" + getChecked() + "'" +
            "}";
    }
}
