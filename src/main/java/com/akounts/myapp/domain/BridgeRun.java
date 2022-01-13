package com.akounts.myapp.domain;

import com.akounts.myapp.domain.enumeration.Status;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BridgeRun.
 */
@Entity
@Table(name = "bridge_run")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BridgeRun implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "run_type")
    private String runType;

    @Enumerated(EnumType.STRING)
    @Column(name = "run_status")
    private Status runStatus;

    @Column(name = "run_start")
    private Instant runStart;

    @Column(name = "run_end")
    private Instant runEnd;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BridgeRun id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRunType() {
        return this.runType;
    }

    public BridgeRun runType(String runType) {
        this.setRunType(runType);
        return this;
    }

    public void setRunType(String runType) {
        this.runType = runType;
    }

    public Status getRunStatus() {
        return this.runStatus;
    }

    public BridgeRun runStatus(Status runStatus) {
        this.setRunStatus(runStatus);
        return this;
    }

    public void setRunStatus(Status runStatus) {
        this.runStatus = runStatus;
    }

    public Instant getRunStart() {
        return this.runStart;
    }

    public BridgeRun runStart(Instant runStart) {
        this.setRunStart(runStart);
        return this;
    }

    public void setRunStart(Instant runStart) {
        this.runStart = runStart;
    }

    public Instant getRunEnd() {
        return this.runEnd;
    }

    public BridgeRun runEnd(Instant runEnd) {
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
        if (!(o instanceof BridgeRun)) {
            return false;
        }
        return id != null && id.equals(((BridgeRun) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BridgeRun{" +
            "id=" + getId() +
            ", runType='" + getRunType() + "'" +
            ", runStatus='" + getRunStatus() + "'" +
            ", runStart='" + getRunStart() + "'" +
            ", runEnd='" + getRunEnd() + "'" +
            "}";
    }
}
