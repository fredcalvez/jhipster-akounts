package com.akounts.myapp.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.akounts.myapp.domain.PlaidRun} entity.
 */
public class PlaidRunDTO implements Serializable {

    private Long id;

    private String runType;

    private String runStatus;

    private String runItemId;

    private Instant runStart;

    private Instant runEnd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRunType() {
        return runType;
    }

    public void setRunType(String runType) {
        this.runType = runType;
    }

    public String getRunStatus() {
        return runStatus;
    }

    public void setRunStatus(String runStatus) {
        this.runStatus = runStatus;
    }

    public String getRunItemId() {
        return runItemId;
    }

    public void setRunItemId(String runItemId) {
        this.runItemId = runItemId;
    }

    public Instant getRunStart() {
        return runStart;
    }

    public void setRunStart(Instant runStart) {
        this.runStart = runStart;
    }

    public Instant getRunEnd() {
        return runEnd;
    }

    public void setRunEnd(Instant runEnd) {
        this.runEnd = runEnd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlaidRunDTO)) {
            return false;
        }

        PlaidRunDTO plaidRunDTO = (PlaidRunDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, plaidRunDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlaidRunDTO{" +
            "id=" + getId() +
            ", runType='" + getRunType() + "'" +
            ", runStatus='" + getRunStatus() + "'" +
            ", runItemId='" + getRunItemId() + "'" +
            ", runStart='" + getRunStart() + "'" +
            ", runEnd='" + getRunEnd() + "'" +
            "}";
    }
}
