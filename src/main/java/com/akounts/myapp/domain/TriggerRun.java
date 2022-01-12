package com.akounts.myapp.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TriggerRun.
 */
@Entity
@Table(name = "trigger_run")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TriggerRun implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "run_type")
    private Integer runType;

    @Column(name = "status")
    private Integer status;

    @Column(name = "add_date")
    private Instant addDate;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TriggerRun id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRunType() {
        return this.runType;
    }

    public TriggerRun runType(Integer runType) {
        this.setRunType(runType);
        return this;
    }

    public void setRunType(Integer runType) {
        this.runType = runType;
    }

    public Integer getStatus() {
        return this.status;
    }

    public TriggerRun status(Integer status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Instant getAddDate() {
        return this.addDate;
    }

    public TriggerRun addDate(Instant addDate) {
        this.setAddDate(addDate);
        return this;
    }

    public void setAddDate(Instant addDate) {
        this.addDate = addDate;
    }

    public Instant getStartDate() {
        return this.startDate;
    }

    public TriggerRun startDate(Instant startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public TriggerRun endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TriggerRun)) {
            return false;
        }
        return id != null && id.equals(((TriggerRun) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TriggerRun{" +
            "id=" + getId() +
            ", runType=" + getRunType() +
            ", status=" + getStatus() +
            ", addDate='" + getAddDate() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }
}
