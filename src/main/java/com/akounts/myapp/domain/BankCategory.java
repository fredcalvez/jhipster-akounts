package com.akounts.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(mappedBy = "category")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "category" }, allowSetters = true)
    private Set<Automatch> automatches = new HashSet<>();

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

    public Set<Automatch> getAutomatches() {
        return this.automatches;
    }

    public void setAutomatches(Set<Automatch> automatches) {
        if (this.automatches != null) {
            this.automatches.forEach(i -> i.setCategory(null));
        }
        if (automatches != null) {
            automatches.forEach(i -> i.setCategory(this));
        }
        this.automatches = automatches;
    }

    public BankCategory automatches(Set<Automatch> automatches) {
        this.setAutomatches(automatches);
        return this;
    }

    public BankCategory addAutomatch(Automatch automatch) {
        this.automatches.add(automatch);
        automatch.setCategory(this);
        return this;
    }

    public BankCategory removeAutomatch(Automatch automatch) {
        this.automatches.remove(automatch);
        automatch.setCategory(null);
        return this;
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
