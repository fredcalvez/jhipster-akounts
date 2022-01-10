package com.akounts.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.akounts.myapp.domain.BankCategory} entity.
 */
public class BankCategoryDTO implements Serializable {

    private Long id;

    private Integer parent;

    private String categorieLabel;

    private String categorieDesc;

    private Boolean income;

    private Boolean isexpense;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public String getCategorieLabel() {
        return categorieLabel;
    }

    public void setCategorieLabel(String categorieLabel) {
        this.categorieLabel = categorieLabel;
    }

    public String getCategorieDesc() {
        return categorieDesc;
    }

    public void setCategorieDesc(String categorieDesc) {
        this.categorieDesc = categorieDesc;
    }

    public Boolean getIncome() {
        return income;
    }

    public void setIncome(Boolean income) {
        this.income = income;
    }

    public Boolean getIsexpense() {
        return isexpense;
    }

    public void setIsexpense(Boolean isexpense) {
        this.isexpense = isexpense;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankCategoryDTO)) {
            return false;
        }

        BankCategoryDTO bankCategoryDTO = (BankCategoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bankCategoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankCategoryDTO{" +
            "id=" + getId() +
            ", parent=" + getParent() +
            ", categorieLabel='" + getCategorieLabel() + "'" +
            ", categorieDesc='" + getCategorieDesc() + "'" +
            ", income='" + getIncome() + "'" +
            ", isexpense='" + getIsexpense() + "'" +
            "}";
    }
}
