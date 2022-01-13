package com.akounts.myapp.domain;

import com.akounts.myapp.domain.enumeration.Currency;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BridgeAccount.
 */
@Entity
@Table(name = "bridge_account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BridgeAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "bridge_account_id")
    private String bridgeAccountId;

    @Column(name = "type")
    private String type;

    @Column(name = "status")
    private String status;

    @Column(name = "balance", precision = 21, scale = 2)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "iso_currency_code")
    private Currency isoCurrencyCode;

    @Column(name = "name")
    private String name;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BridgeAccount id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBridgeAccountId() {
        return this.bridgeAccountId;
    }

    public BridgeAccount bridgeAccountId(String bridgeAccountId) {
        this.setBridgeAccountId(bridgeAccountId);
        return this;
    }

    public void setBridgeAccountId(String bridgeAccountId) {
        this.bridgeAccountId = bridgeAccountId;
    }

    public String getType() {
        return this.type;
    }

    public BridgeAccount type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return this.status;
    }

    public BridgeAccount status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public BridgeAccount balance(BigDecimal balance) {
        this.setBalance(balance);
        return this;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Currency getIsoCurrencyCode() {
        return this.isoCurrencyCode;
    }

    public BridgeAccount isoCurrencyCode(Currency isoCurrencyCode) {
        this.setIsoCurrencyCode(isoCurrencyCode);
        return this;
    }

    public void setIsoCurrencyCode(Currency isoCurrencyCode) {
        this.isoCurrencyCode = isoCurrencyCode;
    }

    public String getName() {
        return this.name;
    }

    public BridgeAccount name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BridgeAccount)) {
            return false;
        }
        return id != null && id.equals(((BridgeAccount) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BridgeAccount{" +
            "id=" + getId() +
            ", bridgeAccountId='" + getBridgeAccountId() + "'" +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            ", balance=" + getBalance() +
            ", isoCurrencyCode='" + getIsoCurrencyCode() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
