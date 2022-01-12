package com.akounts.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.akounts.myapp.domain.AkountsSettings} entity.
 */
public class AkountsSettingsDTO implements Serializable {

    private Long id;

    private String settingKey;

    private String settingVal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSettingKey() {
        return settingKey;
    }

    public void setSettingKey(String settingKey) {
        this.settingKey = settingKey;
    }

    public String getSettingVal() {
        return settingVal;
    }

    public void setSettingVal(String settingVal) {
        this.settingVal = settingVal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AkountsSettingsDTO)) {
            return false;
        }

        AkountsSettingsDTO akountsSettingsDTO = (AkountsSettingsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, akountsSettingsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AkountsSettingsDTO{" +
            "id=" + getId() +
            ", settingKey='" + getSettingKey() + "'" +
            ", settingVal='" + getSettingVal() + "'" +
            "}";
    }
}
