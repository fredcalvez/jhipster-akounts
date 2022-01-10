package com.akounts.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BankCategory.
 */
@Entity
@Table(name = "bank_category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BankCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "parent")
    private Integer parent;

    @Column(name = "categorie_label")
    private String categorieLabel;

    @Column(name = "categorie_desc")
    private String categorieDesc;

    @Column(name = "income")
    private Boolean income;

    @Column(name = "isexpense")
    private Boolean isexpense;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BankCategory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getParent() {
        return this.parent;
    }

    public BankCategory parent(Integer parent) {
        this.setParent(parent);
        return this;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public String getCategorieLabel() {
        return this.categorieLabel;
    }

    public BankCategory categorieLabel(String categorieLabel) {
        this.setCategorieLabel(categorieLabel);
        return this;
    }

    public void setCategorieLabel(String categorieLabel) {
        this.categorieLabel = categorieLabel;
    }

    public String getCategorieDesc() {
        return this.categorieDesc;
    }

    public BankCategory categorieDesc(String categorieDesc) {
        this.setCategorieDesc(categorieDesc);
        return this;
    }

    public void setCategorieDesc(String categorieDesc) {
        this.categorieDesc = categorieDesc;
    }

    public Boolean getIncome() {
        return this.income;
    }

    public BankCategory income(Boolean income) {
        this.setIncome(income);
        return this;
    }

    public void setIncome(Boolean income) {
        this.income = income;
    }

    public Boolean getIsexpense() {
        return this.isexpense;
    }

    public BankCategory isexpense(Boolean isexpense) {
        this.setIsexpense(isexpense);
        return this;
    }

    public void setIsexpense(Boolean isexpense) {
        this.isexpense = isexpense;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankCategory)) {
            return false;
        }
        return id != null && id.equals(((BankCategory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankCategory{" +
            "id=" + getId() +
            ", parent=" + getParent() +
            ", categorieLabel='" + getCategorieLabel() + "'" +
            ", categorieDesc='" + getCategorieDesc() + "'" +
            ", income='" + getIncome() + "'" +
            ", isexpense='" + getIsexpense() + "'" +
            "}";
    }
}
