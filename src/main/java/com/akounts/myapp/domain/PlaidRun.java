package com.akounts.myapp.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PlaidRun.
 */
@Entity
@Table(name = "plaid_run")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PlaidRun implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "run_type")
    private String runType;

    @Column(name = "run_status")
    private String runStatus;

    @Column(name = "run_item_id")
    private String runItemId;

    @Column(name = "run_start")
    private Instant runStart;

    @Column(name = "run_end")
    private Instant runEnd;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PlaidRun id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRunType() {
        return this.runType;
    }

    public PlaidRun runType(String runType) {
        this.setRunType(runType);
        return this;
    }

    public void setRunType(String runType) {
        this.runType = runType;
    }

    public String getRunStatus() {
        return this.runStatus;
    }

    public PlaidRun runStatus(String runStatus) {
        this.setRunStatus(runStatus);
        return this;
    }

    public void setRunStatus(String runStatus) {
        this.runStatus = runStatus;
    }

    public String getRunItemId() {
        return this.runItemId;
    }

    public PlaidRun runItemId(String runItemId) {
        this.setRunItemId(runItemId);
        return this;
    }

    public void setRunItemId(String runItemId) {
        this.runItemId = runItemId;
    }

    public Instant getRunStart() {
        return this.runStart;
    }

    public PlaidRun runStart(Instant runStart) {
        this.setRunStart(runStart);
        return this;
    }

    public void setRunStart(Instant runStart) {
        this.runStart = runStart;
    }

    public Instant getRunEnd() {
        return this.runEnd;
    }

    public PlaidRun runEnd(Instant runEnd) {
        this.setRunEnd(runEnd);
        return this;
    }

    public void setRunEnd(Instant runEnd) {
        this.runEnd = runEnd;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlaidRun)) {
            return false;
        }
        return id != null && id.equals(((PlaidRun) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlaidRun{" +
            "id=" + getId() +
            ", runType='" + getRunType() + "'" +
            ", runStatus='" + getRunStatus() + "'" +
            ", runItemId='" + getRunItemId() + "'" +
            ", runStart='" + getRunStart() + "'" +
            ", runEnd='" + getRunEnd() + "'" +
            "}";
    }
}
