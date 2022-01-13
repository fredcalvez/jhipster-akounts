package com.akounts.myapp.domain;

import com.akounts.myapp.domain.enumeration.AccountType;
import com.akounts.myapp.domain.enumeration.Currency;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BankAccount.
 */
@Entity
@Table(name = "bank_account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BankAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "account_label")
    private String accountLabel;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "active")
    private Boolean active;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private Currency currency;

    @Column(name = "initial_amount", precision = 21, scale = 2)
    private BigDecimal initialAmount;

    @Column(name = "initial_amount_local", precision = 21, scale = 2)
    private BigDecimal initialAmountLocal;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private AccountType accountType;

    @Column(name = "interest", precision = 21, scale = 2)
    private BigDecimal interest;

    @Column(name = "nickname")
    private String nickname;

    @ManyToOne
    private BankInstitution institution;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BankAccount id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountLabel() {
        return this.accountLabel;
    }

    public BankAccount accountLabel(String accountLabel) {
        this.setAccountLabel(accountLabel);
        return this;
    }

    public void setAccountLabel(String accountLabel) {
        this.accountLabel = accountLabel;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public BankAccount accountNumber(String accountNumber) {
        this.setAccountNumber(accountNumber);
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Boolean getActive() {
        return this.active;
    }

    public BankAccount active(Boolean active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public BankAccount currency(Currency currency) {
        this.setCurrency(currency);
        return this;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getInitialAmount() {
        return this.initialAmount;
    }

    public BankAccount initialAmount(BigDecimal initialAmount) {
        this.setInitialAmount(initialAmount);
        return this;
    }

    public void setInitialAmount(BigDecimal initialAmount) {
        this.initialAmount = initialAmount;
    }

    public BigDecimal getInitialAmountLocal() {
        return this.initialAmountLocal;
    }

    public BankAccount initialAmountLocal(BigDecimal initialAmountLocal) {
        this.setInitialAmountLocal(initialAmountLocal);
        return this;
    }

    public void setInitialAmountLocal(BigDecimal initialAmountLocal) {
        this.initialAmountLocal = initialAmountLocal;
    }

    public AccountType getAccountType() {
        return this.accountType;
    }

    public BankAccount accountType(AccountType accountType) {
        this.setAccountType(accountType);
        return this;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getInterest() {
        return this.interest;
    }

    public BankAccount interest(BigDecimal interest) {
        this.setInterest(interest);
        return this;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public String getNickname() {
        return this.nickname;
    }

    public BankAccount nickname(String nickname) {
        this.setNickname(nickname);
        return this;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public BankInstitution getInstitution() {
        return this.institution;
    }

    public void setInstitution(BankInstitution bankInstitution) {
        this.institution = bankInstitution;
    }

    public BankAccount institution(BankInstitution bankInstitution) {
        this.setInstitution(bankInstitution);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankAccount)) {
            return false;
        }
        return id != null && id.equals(((BankAccount) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankAccount{" +
            "id=" + getId() +
            ", accountLabel='" + getAccountLabel() + "'" +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", active='" + getActive() + "'" +
            ", currency='" + getCurrency() + "'" +
            ", initialAmount=" + getInitialAmount() +
            ", initialAmountLocal=" + getInitialAmountLocal() +
            ", accountType='" + getAccountType() + "'" +
            ", interest=" + getInterest() +
            ", nickname='" + getNickname() + "'" +
            "}";
    }
}
