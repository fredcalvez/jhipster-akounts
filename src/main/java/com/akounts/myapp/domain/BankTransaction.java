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
 * A BankTransaction.
 */
@Entity
@Table(name = "bank_transaction")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BankTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "transaction_date")
    private Instant transactionDate;

    @Column(name = "description")
    private String description;

    @Column(name = "local_amount", precision = 21, scale = 2)
    private BigDecimal localAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "local_currency")
    private Currency localCurrency;

    @Column(name = "amount", precision = 21, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private Currency currency;

    @Column(name = "note")
    private String note;

    @Column(name = "year")
    private Integer year;

    @Column(name = "month")
    private Integer month;

    @Column(name = "week")
    private Integer week;

    @Column(name = "categorized_date")
    private Instant categorizedDate;

    @Column(name = "add_date")
    private Instant addDate;

    @Column(name = "checked")
    private Boolean checked;

    @Column(name = "rebased_date")
    private Instant rebasedDate;

    @Column(name = "deleted")
    private Boolean deleted;

    @JsonIgnoreProperties(value = { "automatches" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private BankCategory catId;

    @JsonIgnoreProperties(value = { "institution" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private BankAccount accountId;

    @ManyToOne
    @JsonIgnoreProperties(value = { "automatches" }, allowSetters = true)
    private BankCategory category;

    @ManyToOne
    @JsonIgnoreProperties(value = { "institution" }, allowSetters = true)
    private BankAccount account;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BankTransaction id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public BankTransaction transactionId(String transactionId) {
        this.setTransactionId(transactionId);
        return this;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Instant getTransactionDate() {
        return this.transactionDate;
    }

    public BankTransaction transactionDate(Instant transactionDate) {
        this.setTransactionDate(transactionDate);
        return this;
    }

    public void setTransactionDate(Instant transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getDescription() {
        return this.description;
    }

    public BankTransaction description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getLocalAmount() {
        return this.localAmount;
    }

    public BankTransaction localAmount(BigDecimal localAmount) {
        this.setLocalAmount(localAmount);
        return this;
    }

    public void setLocalAmount(BigDecimal localAmount) {
        this.localAmount = localAmount;
    }

    public Currency getLocalCurrency() {
        return this.localCurrency;
    }

    public BankTransaction localCurrency(Currency localCurrency) {
        this.setLocalCurrency(localCurrency);
        return this;
    }

    public void setLocalCurrency(Currency localCurrency) {
        this.localCurrency = localCurrency;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public BankTransaction amount(BigDecimal amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public BankTransaction currency(Currency currency) {
        this.setCurrency(currency);
        return this;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getNote() {
        return this.note;
    }

    public BankTransaction note(String note) {
        this.setNote(note);
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getYear() {
        return this.year;
    }

    public BankTransaction year(Integer year) {
        this.setYear(year);
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return this.month;
    }

    public BankTransaction month(Integer month) {
        this.setMonth(month);
        return this;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getWeek() {
        return this.week;
    }

    public BankTransaction week(Integer week) {
        this.setWeek(week);
        return this;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Instant getCategorizedDate() {
        return this.categorizedDate;
    }

    public BankTransaction categorizedDate(Instant categorizedDate) {
        this.setCategorizedDate(categorizedDate);
        return this;
    }

    public void setCategorizedDate(Instant categorizedDate) {
        this.categorizedDate = categorizedDate;
    }

    public Instant getAddDate() {
        return this.addDate;
    }

    public BankTransaction addDate(Instant addDate) {
        this.setAddDate(addDate);
        return this;
    }

    public void setAddDate(Instant addDate) {
        this.addDate = addDate;
    }

    public Boolean getChecked() {
        return this.checked;
    }

    public BankTransaction checked(Boolean checked) {
        this.setChecked(checked);
        return this;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Instant getRebasedDate() {
        return this.rebasedDate;
    }

    public BankTransaction rebasedDate(Instant rebasedDate) {
        this.setRebasedDate(rebasedDate);
        return this;
    }

    public void setRebasedDate(Instant rebasedDate) {
        this.rebasedDate = rebasedDate;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public BankTransaction deleted(Boolean deleted) {
        this.setDeleted(deleted);
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public BankCategory getCatId() {
        return this.catId;
    }

    public void setCatId(BankCategory bankCategory) {
        this.catId = bankCategory;
    }

    public BankTransaction catId(BankCategory bankCategory) {
        this.setCatId(bankCategory);
        return this;
    }

    public BankAccount getAccountId() {
        return this.accountId;
    }

    public void setAccountId(BankAccount bankAccount) {
        this.accountId = bankAccount;
    }

    public BankTransaction accountId(BankAccount bankAccount) {
        this.setAccountId(bankAccount);
        return this;
    }

    public BankCategory getCategory() {
        return this.category;
    }

    public void setCategory(BankCategory bankCategory) {
        this.category = bankCategory;
    }

    public BankTransaction category(BankCategory bankCategory) {
        this.setCategory(bankCategory);
        return this;
    }

    public BankAccount getAccount() {
        return this.account;
    }

    public void setAccount(BankAccount bankAccount) {
        this.account = bankAccount;
    }

    public BankTransaction account(BankAccount bankAccount) {
        this.setAccount(bankAccount);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankTransaction)) {
            return false;
        }
        return id != null && id.equals(((BankTransaction) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankTransaction{" +
            "id=" + getId() +
            ", transactionId='" + getTransactionId() + "'" +
            ", transactionDate='" + getTransactionDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", localAmount=" + getLocalAmount() +
            ", localCurrency='" + getLocalCurrency() + "'" +
            ", amount=" + getAmount() +
            ", currency='" + getCurrency() + "'" +
            ", note='" + getNote() + "'" +
            ", year=" + getYear() +
            ", month=" + getMonth() +
            ", week=" + getWeek() +
            ", categorizedDate='" + getCategorizedDate() + "'" +
            ", addDate='" + getAddDate() + "'" +
            ", checked='" + getChecked() + "'" +
            ", rebasedDate='" + getRebasedDate() + "'" +
            ", deleted='" + getDeleted() + "'" +
            "}";
    }
}
