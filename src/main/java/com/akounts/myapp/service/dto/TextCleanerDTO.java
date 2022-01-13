package com.akounts.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.akounts.myapp.domain.TextCleaner} entity.
 */
public class TextCleanerDTO implements Serializable {

    private Long id;

    private String type;

    private String rule;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TextCleanerDTO)) {
            return false;
        }

        TextCleanerDTO textCleanerDTO = (TextCleanerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, textCleanerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TextCleanerDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", rule='" + getRule() + "'" +
            "}";
    }
}
