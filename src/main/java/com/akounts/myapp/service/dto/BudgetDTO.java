package com.akounts.myapp.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.akounts.myapp.domain.Budget} entity.
 */
public class BudgetDTO implements Serializable {

    private Long id;

    private Instant budgetDate;

    private String categorieLabel;

    private BigDecimal amount;

    private BankCategoryDTO category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getBudgetDate() {
        return budgetDate;
    }

    public void setBudgetDate(Instant budgetDate) {
        this.budgetDate = budgetDate;
    }

    public String getCategorieLabel() {
        return categorieLabel;
    }

    public void setCategorieLabel(String categorieLabel) {
        this.categorieLabel = categorieLabel;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BankCategoryDTO getCategory() {
        return category;
    }

    public void setCategory(BankCategoryDTO category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BudgetDTO)) {
            return false;
        }

        BudgetDTO budgetDTO = (BudgetDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, budgetDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BudgetDTO{" +
            "id=" + getId() +
            ", budgetDate='" + getBudgetDate() + "'" +
            ", categorieLabel='" + getCategorieLabel() + "'" +
            ", amount=" + getAmount() +
            ", category=" + getCategory() +
            "}";
    }
}
