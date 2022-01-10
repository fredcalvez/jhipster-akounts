package com.akounts.myapp.service.dto;

import com.akounts.myapp.domain.enumeration.AccountType;
import com.akounts.myapp.domain.enumeration.Currency;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.akounts.myapp.domain.BankAccount} entity.
 */
public class BankAccountDTO implements Serializable {

    private Long id;

    private String accountLabel;

    private String accountNumber;

    private Boolean active;

    private Currency currency;

    private BigDecimal initialAmount;

    private BigDecimal initialAmountLocal;

    private AccountType accountType;

    private BigDecimal interest;

    private String nickname;

    private BankInstitutionDTO institution;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountLabel() {
        return accountLabel;
    }

    public void setAccountLabel(String accountLabel) {
        this.accountLabel = accountLabel;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getInitialAmount() {
        return initialAmount;
    }

    public void setInitialAmount(BigDecimal initialAmount) {
        this.initialAmount = initialAmount;
    }

    public BigDecimal getInitialAmountLocal() {
        return initialAmountLocal;
    }

    public void setInitialAmountLocal(BigDecimal initialAmountLocal) {
        this.initialAmountLocal = initialAmountLocal;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public BankInstitutionDTO getInstitution() {
        return institution;
    }

    public void setInstitution(BankInstitutionDTO institution) {
        this.institution = institution;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankAccountDTO)) {
            return false;
        }

        BankAccountDTO bankAccountDTO = (BankAccountDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bankAccountDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankAccountDTO{" +
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
            ", institution=" + getInstitution() +
            "}";
    }
}
