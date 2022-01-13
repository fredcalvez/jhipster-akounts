package com.akounts.myapp.domain;

import com.akounts.myapp.domain.enumeration.Currency;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BridgeTransaction.
 */
@Entity
@Table(name = "bridge_transaction")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BridgeTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Enumerated(EnumType.STRING)
    @Column(name = "iso_currency_code")
    private Currency isoCurrencyCode;

    @Column(name = "transaction_date")
    private Instant transactionDate;

    @Column(name = "description")
    private String description;

    @Column(name = "is_future")
    private Boolean isFuture;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "added_date")
    private Instant addedDate;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "checked")
    private Boolean checked;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BridgeTransaction id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public BridgeTransaction transactionId(String transactionId) {
        this.setTransactionId(transactionId);
        return this;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionType() {
        return this.transactionType;
    }

    public BridgeTransaction transactionType(String transactionType) {
        this.setTransactionType(transactionType);
        return this;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getAccountId() {
        return this.accountId;
    }

    public BridgeTransaction accountId(String accountId) {
        this.setAccountId(accountId);
        return this;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public BridgeTransaction amount(BigDecimal amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currency getIsoCurrencyCode() {
        return this.isoCurrencyCode;
    }

    public BridgeTransaction isoCurrencyCode(Currency isoCurrencyCode) {
        this.setIsoCurrencyCode(isoCurrencyCode);
        return this;
    }

    public void setIsoCurrencyCode(Currency isoCurrencyCode) {
        this.isoCurrencyCode = isoCurrencyCode;
    }

    public Instant getTransactionDate() {
        return this.transactionDate;
    }

    public BridgeTransaction transactionDate(Instant transactionDate) {
        this.setTransactionDate(transactionDate);
        return this;
    }

    public void setTransactionDate(Instant transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getDescription() {
        return this.description;
    }

    public BridgeTransaction description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsFuture() {
        return this.isFuture;
    }

    public BridgeTransaction isFuture(Boolean isFuture) {
        this.setIsFuture(isFuture);
        return this;
    }

    public void setIsFuture(Boolean isFuture) {
        this.isFuture = isFuture;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public BridgeTransaction isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Instant getAddedDate() {
        return this.addedDate;
    }

    public BridgeTransaction addedDate(Instant addedDate) {
        this.setAddedDate(addedDate);
        return this;
    }

    public void setAddedDate(Instant addedDate) {
        this.addedDate = addedDate;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public BridgeTransaction updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getChecked() {
        return this.checked;
    }

    public BridgeTransaction checked(Boolean checked) {
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
        if (!(o instanceof BridgeTransaction)) {
            return false;
        }
        return id != null && id.equals(((BridgeTransaction) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BridgeTransaction{" +
            "id=" + getId() +
            ", transactionId='" + getTransactionId() + "'" +
            ", transactionType='" + getTransactionType() + "'" +
            ", accountId='" + getAccountId() + "'" +
            ", amount=" + getAmount() +
            ", isoCurrencyCode='" + getIsoCurrencyCode() + "'" +
            ", transactionDate='" + getTransactionDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", isFuture='" + getIsFuture() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", addedDate='" + getAddedDate() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", checked='" + getChecked() + "'" +
            "}";
    }
}
