package com.akounts.myapp.service.dto;

import com.akounts.myapp.domain.enumeration.Currency;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.akounts.myapp.domain.BankTransaction} entity.
 */
public class BankTransactionDTO implements Serializable {

    private Long id;

    private String transactionId;

    private Instant transactionDate;

    private String description;

    private BigDecimal localAmount;

    private Currency localCurrency;

    private BigDecimal amount;

    private Currency currency;

    private String note;

    private Integer year;

    private Integer month;

    private Integer week;

    private Instant categorizedDate;

    private Instant addDate;

    private Boolean checked;

    private Instant rebasedDate;

    private Boolean deleted;

    private BankCategoryDTO catId;

    private BankAccountDTO accountId;

    private BankCategoryDTO category;

    private BankAccountDTO account;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Instant getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Instant transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getLocalAmount() {
        return localAmount;
    }

    public void setLocalAmount(BigDecimal localAmount) {
        this.localAmount = localAmount;
    }

    public Currency getLocalCurrency() {
        return localCurrency;
    }

    public void setLocalCurrency(Currency localCurrency) {
        this.localCurrency = localCurrency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Instant getCategorizedDate() {
        return categorizedDate;
    }

    public void setCategorizedDate(Instant categorizedDate) {
        this.categorizedDate = categorizedDate;
    }

    public Instant getAddDate() {
        return addDate;
    }

    public void setAddDate(Instant addDate) {
        this.addDate = addDate;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Instant getRebasedDate() {
        return rebasedDate;
    }

    public void setRebasedDate(Instant rebasedDate) {
        this.rebasedDate = rebasedDate;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public BankCategoryDTO getCatId() {
        return catId;
    }

    public void setCatId(BankCategoryDTO catId) {
        this.catId = catId;
    }

    public BankAccountDTO getAccountId() {
        return accountId;
    }

    public void setAccountId(BankAccountDTO accountId) {
        this.accountId = accountId;
    }

    public BankCategoryDTO getCategory() {
        return category;
    }

    public void setCategory(BankCategoryDTO category) {
        this.category = category;
    }

    public BankAccountDTO getAccount() {
        return account;
    }

    public void setAccount(BankAccountDTO account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankTransactionDTO)) {
            return false;
        }

        BankTransactionDTO bankTransactionDTO = (BankTransactionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bankTransactionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankTransactionDTO{" +
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
            ", catId=" + getCatId() +
            ", accountId=" + getAccountId() +
            ", category=" + getCategory() +
            ", account=" + getAccount() +
            "}";
    }
}
