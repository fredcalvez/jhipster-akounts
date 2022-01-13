package com.akounts.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BankProject.
 */
@Entity
@Table(name = "bank_project")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BankProject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "project_type")
    private String projectType;

    @Column(name = "initial_value", precision = 21, scale = 2)
    private BigDecimal initialValue;

    @Column(name = "current_value", precision = 21, scale = 2)
    private BigDecimal currentValue;

    @JsonIgnoreProperties(value = { "institution" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private BankAccount mainAccountId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BankProject id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public BankProject name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectType() {
        return this.projectType;
    }

    public BankProject projectType(String projectType) {
        this.setProjectType(projectType);
        return this;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public BigDecimal getInitialValue() {
        return this.initialValue;
    }

    public BankProject initialValue(BigDecimal initialValue) {
        this.setInitialValue(initialValue);
        return this;
    }

    public void setInitialValue(BigDecimal initialValue) {
        this.initialValue = initialValue;
    }

    public BigDecimal getCurrentValue() {
        return this.currentValue;
    }

    public BankProject currentValue(BigDecimal currentValue) {
        this.setCurrentValue(currentValue);
        return this;
    }

    public void setCurrentValue(BigDecimal currentValue) {
        this.currentValue = currentValue;
    }

    public BankAccount getMainAccountId() {
        return this.mainAccountId;
    }

    public void setMainAccountId(BankAccount bankAccount) {
        this.mainAccountId = bankAccount;
    }

    public BankProject mainAccountId(BankAccount bankAccount) {
        this.setMainAccountId(bankAccount);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankProject)) {
            return false;
        }
        return id != null && id.equals(((BankProject) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankProject{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", projectType='" + getProjectType() + "'" +
            ", initialValue=" + getInitialValue() +
            ", currentValue=" + getCurrentValue() +
            "}";
    }
}
