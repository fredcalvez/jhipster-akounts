package com.akounts.myapp.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.akounts.myapp.domain.BankProject} entity.
 */
public class BankProjectDTO implements Serializable {

    private Long id;

    private String name;

    private String projectType;

    private BigDecimal initialValue;

    private BigDecimal currentValue;

    private BankAccountDTO mainAccountId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public BigDecimal getInitialValue() {
        return initialValue;
    }

    public void setInitialValue(BigDecimal initialValue) {
        this.initialValue = initialValue;
    }

    public BigDecimal getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(BigDecimal currentValue) {
        this.currentValue = currentValue;
    }

    public BankAccountDTO getMainAccountId() {
        return mainAccountId;
    }

    public void setMainAccountId(BankAccountDTO mainAccountId) {
        this.mainAccountId = mainAccountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankProjectDTO)) {
            return false;
        }

        BankProjectDTO bankProjectDTO = (BankProjectDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bankProjectDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankProjectDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", projectType='" + getProjectType() + "'" +
            ", initialValue=" + getInitialValue() +
            ", currentValue=" + getCurrentValue() +
            ", mainAccountId=" + getMainAccountId() +
            "}";
    }
}
