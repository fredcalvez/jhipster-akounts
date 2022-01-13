package com.akounts.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Budget.
 */
@Entity
@Table(name = "budget")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Budget implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "budget_date")
    private Instant budgetDate;

    @Column(name = "categorie_label")
    private String categorieLabel;

    @Column(name = "amount", precision = 21, scale = 2)
    private BigDecimal amount;

    @ManyToOne
    @JsonIgnoreProperties(value = { "automatches" }, allowSetters = true)
    private BankCategory category;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Budget id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getBudgetDate() {
        return this.budgetDate;
    }

    public Budget budgetDate(Instant budgetDate) {
        this.setBudgetDate(budgetDate);
        return this;
    }

    public void setBudgetDate(Instant budgetDate) {
        this.budgetDate = budgetDate;
    }

    public String getCategorieLabel() {
        return this.categorieLabel;
    }

    public Budget categorieLabel(String categorieLabel) {
        this.setCategorieLabel(categorieLabel);
        return this;
    }

    public void setCategorieLabel(String categorieLabel) {
        this.categorieLabel = categorieLabel;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public Budget amount(BigDecimal amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BankCategory getCategory() {
        return this.category;
    }

    public void setCategory(BankCategory bankCategory) {
        this.category = bankCategory;
    }

    public Budget category(BankCategory bankCategory) {
        this.setCategory(bankCategory);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Budget)) {
            return false;
        }
        return id != null && id.equals(((Budget) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Budget{" +
            "id=" + getId() +
            ", budgetDate='" + getBudgetDate() + "'" +
            ", categorieLabel='" + getCategorieLabel() + "'" +
            ", amount=" + getAmount() +
            "}";
    }
}
