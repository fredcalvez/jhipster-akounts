package com.akounts.myapp.domain;

import com.akounts.myapp.domain.enumeration.Status;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ProcessRun.
 */
@Entity
@Table(name = "process_run")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProcessRun implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "process")
    private String process;

    @Column(name = "parent_id")
    private Integer parentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "returns")
    private String returns;

    @Column(name = "error")
    private String error;

    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "end_time")
    private Instant endTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProcessRun id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProcess() {
        return this.process;
    }

    public ProcessRun process(String process) {
        this.setProcess(process);
        return this;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public Integer getParentId() {
        return this.parentId;
    }

    public ProcessRun parentId(Integer parentId) {
        this.setParentId(parentId);
        return this;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Status getStatus() {
        return this.status;
    }

    public ProcessRun status(Status status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getReturns() {
        return this.returns;
    }

    public ProcessRun returns(String returns) {
        this.setReturns(returns);
        return this;
    }

    public void setReturns(String returns) {
        this.returns = returns;
    }

    public String getError() {
        return this.error;
    }

    public ProcessRun error(String error) {
        this.setError(error);
        return this;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Instant getStartTime() {
        return this.startTime;
    }

    public ProcessRun startTime(Instant startTime) {
        this.setStartTime(startTime);
        return this;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return this.endTime;
    }

    public ProcessRun endTime(Instant endTime) {
        this.setEndTime(endTime);
        return this;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessRun)) {
            return false;
        }
        return id != null && id.equals(((ProcessRun) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessRun{" +
            "id=" + getId() +
            ", process='" + getProcess() + "'" +
            ", parentId=" + getParentId() +
            ", status='" + getStatus() + "'" +
            ", returns='" + getReturns() + "'" +
            ", error='" + getError() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            "}";
    }
}
