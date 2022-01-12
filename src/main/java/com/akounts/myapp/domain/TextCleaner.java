package com.akounts.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TextCleaner.
 */
@Entity
@Table(name = "text_cleaner")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TextCleaner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "rule")
    private String rule;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TextCleaner id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public TextCleaner type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRule() {
        return this.rule;
    }

    public TextCleaner rule(String rule) {
        this.setRule(rule);
        return this;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TextCleaner)) {
            return false;
        }
        return id != null && id.equals(((TextCleaner) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TextCleaner{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", rule='" + getRule() + "'" +
            "}";
    }
}
