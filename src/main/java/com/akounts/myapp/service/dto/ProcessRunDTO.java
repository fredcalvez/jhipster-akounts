package com.akounts.myapp.service.dto;

import com.akounts.myapp.domain.enumeration.Status;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.akounts.myapp.domain.ProcessRun} entity.
 */
public class ProcessRunDTO implements Serializable {

    private Long id;

    private String process;

    private Integer parentId;

    private Status status;

    private String returns;

    private String error;

    private Instant startTime;

    private Instant endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getReturns() {
        return returns;
    }

    public void setReturns(String returns) {
        this.returns = returns;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessRunDTO)) {
            return false;
        }

        ProcessRunDTO processRunDTO = (ProcessRunDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, processRunDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessRunDTO{" +
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
