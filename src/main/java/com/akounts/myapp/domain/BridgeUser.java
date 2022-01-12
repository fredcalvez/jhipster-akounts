package com.akounts.myapp.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BridgeUser.
 */
@Entity
@Table(name = "bridge_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BridgeUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "email")
    private String email;

    @Column(name = "pass")
    private String pass;

    @Column(name = "last_login_date")
    private Instant lastLoginDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BridgeUser id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return this.uuid;
    }

    public BridgeUser uuid(String uuid) {
        this.setUuid(uuid);
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return this.email;
    }

    public BridgeUser email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return this.pass;
    }

    public BridgeUser pass(String pass) {
        this.setPass(pass);
        return this;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Instant getLastLoginDate() {
        return this.lastLoginDate;
    }

    public BridgeUser lastLoginDate(Instant lastLoginDate) {
        this.setLastLoginDate(lastLoginDate);
        return this;
    }

    public void setLastLoginDate(Instant lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BridgeUser)) {
            return false;
        }
        return id != null && id.equals(((BridgeUser) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BridgeUser{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", email='" + getEmail() + "'" +
            ", pass='" + getPass() + "'" +
            ", lastLoginDate='" + getLastLoginDate() + "'" +
            "}";
    }
}
