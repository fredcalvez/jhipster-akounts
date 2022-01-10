package com.akounts.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AkountsSettings.
 */
@Entity
@Table(name = "akounts_settings")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AkountsSettings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "setting_key")
    private String settingKey;

    @Column(name = "setting_val")
    private String settingVal;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AkountsSettings id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSettingKey() {
        return this.settingKey;
    }

    public AkountsSettings settingKey(String settingKey) {
        this.setSettingKey(settingKey);
        return this;
    }

    public void setSettingKey(String settingKey) {
        this.settingKey = settingKey;
    }

    public String getSettingVal() {
        return this.settingVal;
    }

    public AkountsSettings settingVal(String settingVal) {
        this.setSettingVal(settingVal);
        return this;
    }

    public void setSettingVal(String settingVal) {
        this.settingVal = settingVal;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AkountsSettings)) {
            return false;
        }
        return id != null && id.equals(((AkountsSettings) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AkountsSettings{" +
            "id=" + getId() +
            ", settingKey='" + getSettingKey() + "'" +
            ", settingVal='" + getSettingVal() + "'" +
            "}";
    }
}
