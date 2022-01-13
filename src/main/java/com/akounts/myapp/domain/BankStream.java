package com.akounts.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BankStream.
 */
@Entity
@Table(name = "bank_stream")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BankStream implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "stream_type")
    private String streamType;

    @JsonIgnoreProperties(value = { "institution" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private BankAccount mainAccountId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BankStream id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public BankStream name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreamType() {
        return this.streamType;
    }

    public BankStream streamType(String streamType) {
        this.setStreamType(streamType);
        return this;
    }

    public void setStreamType(String streamType) {
        this.streamType = streamType;
    }

    public BankAccount getMainAccountId() {
        return this.mainAccountId;
    }

    public void setMainAccountId(BankAccount bankAccount) {
        this.mainAccountId = bankAccount;
    }

    public BankStream mainAccountId(BankAccount bankAccount) {
        this.setMainAccountId(bankAccount);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankStream)) {
            return false;
        }
        return id != null && id.equals(((BankStream) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankStream{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", streamType='" + getStreamType() + "'" +
            "}";
    }
}
