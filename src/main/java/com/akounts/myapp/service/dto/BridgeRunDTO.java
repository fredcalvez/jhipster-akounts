package com.akounts.myapp.service.dto;

import com.akounts.myapp.domain.enumeration.Status;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.akounts.myapp.domain.BridgeRun} entity.
 */
public class BridgeRunDTO implements Serializable {

    private Long id;

    private String runType;

    private Status runStatus;

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

    public Status getRunStatus() {
        return runStatus;
    }

    public void setRunStatus(Status runStatus) {
        this.runStatus = runStatus;
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
        if (!(o instanceof BridgeRunDTO)) {
            return false;
        }

        BridgeRunDTO bridgeRunDTO = (BridgeRunDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bridgeRunDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BridgeRunDTO{" +
            "id=" + getId() +
            ", runType='" + getRunType() + "'" +
            ", runStatus='" + getRunStatus() + "'" +
            ", runStart='" + getRunStart() + "'" +
            ", runEnd='" + getRunEnd() + "'" +
            "}";
    }
}
