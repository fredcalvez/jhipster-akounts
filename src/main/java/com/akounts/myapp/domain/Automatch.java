package com.akounts.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Automatch.
 */
@Entity
@Table(name = "automatch")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Automatch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "matchstring")
    private String matchstring;

    @Column(name = "update_time")
    private Instant updateTime;

    @Column(name = "last_used_time")
    private Instant lastUsedTime;

    @Column(name = "use_count")
    private Integer useCount;

    @Column(name = "default_tag")
    private String defaultTag;

    @ManyToOne
    @JsonIgnoreProperties(value = { "automatches" }, allowSetters = true)
    private BankCategory category;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Automatch id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatchstring() {
        return this.matchstring;
    }

    public Automatch matchstring(String matchstring) {
        this.setMatchstring(matchstring);
        return this;
    }

    public void setMatchstring(String matchstring) {
        this.matchstring = matchstring;
    }

    public Instant getUpdateTime() {
        return this.updateTime;
    }

    public Automatch updateTime(Instant updateTime) {
        this.setUpdateTime(updateTime);
        return this;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public Instant getLastUsedTime() {
        return this.lastUsedTime;
    }

    public Automatch lastUsedTime(Instant lastUsedTime) {
        this.setLastUsedTime(lastUsedTime);
        return this;
    }

    public void setLastUsedTime(Instant lastUsedTime) {
        this.lastUsedTime = lastUsedTime;
    }

    public Integer getUseCount() {
        return this.useCount;
    }

    public Automatch useCount(Integer useCount) {
        this.setUseCount(useCount);
        return this;
    }

    public void setUseCount(Integer useCount) {
        this.useCount = useCount;
    }

    public String getDefaultTag() {
        return this.defaultTag;
    }

    public Automatch defaultTag(String defaultTag) {
        this.setDefaultTag(defaultTag);
        return this;
    }

    public void setDefaultTag(String defaultTag) {
        this.defaultTag = defaultTag;
    }

    public BankCategory getCategory() {
        return this.category;
    }

    public void setCategory(BankCategory bankCategory) {
        this.category = bankCategory;
    }

    public Automatch category(BankCategory bankCategory) {
        this.setCategory(bankCategory);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Automatch)) {
            return false;
        }
        return id != null && id.equals(((Automatch) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Automatch{" +
            "id=" + getId() +
            ", matchstring='" + getMatchstring() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", lastUsedTime='" + getLastUsedTime() + "'" +
            ", useCount=" + getUseCount() +
            ", defaultTag='" + getDefaultTag() + "'" +
            "}";
    }
}
