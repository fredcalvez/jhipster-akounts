package com.akounts.myapp.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.akounts.myapp.domain.Automatch} entity.
 */
public class AutomatchDTO implements Serializable {

    private Long id;

    private String matchstring;

    private Instant updateTime;

    private Instant lastUsedTime;

    private Integer useCount;

    private String defaultTag;

    private BankCategoryDTO category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatchstring() {
        return matchstring;
    }

    public void setMatchstring(String matchstring) {
        this.matchstring = matchstring;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public Instant getLastUsedTime() {
        return lastUsedTime;
    }

    public void setLastUsedTime(Instant lastUsedTime) {
        this.lastUsedTime = lastUsedTime;
    }

    public Integer getUseCount() {
        return useCount;
    }

    public void setUseCount(Integer useCount) {
        this.useCount = useCount;
    }

    public String getDefaultTag() {
        return defaultTag;
    }

    public void setDefaultTag(String defaultTag) {
        this.defaultTag = defaultTag;
    }

    public BankCategoryDTO getCategory() {
        return category;
    }

    public void setCategory(BankCategoryDTO category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AutomatchDTO)) {
            return false;
        }

        AutomatchDTO automatchDTO = (AutomatchDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, automatchDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AutomatchDTO{" +
            "id=" + getId() +
            ", matchstring='" + getMatchstring() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", lastUsedTime='" + getLastUsedTime() + "'" +
            ", useCount=" + getUseCount() +
            ", defaultTag='" + getDefaultTag() + "'" +
            ", category=" + getCategory() +
            "}";
    }
}
