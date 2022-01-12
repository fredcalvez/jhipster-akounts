package com.akounts.myapp.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PlaidItem.
 */
@Entity
@Table(name = "plaid_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PlaidItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "item_id")
    private String itemId;

    @Column(name = "institution_id")
    private String institutionId;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "added_date")
    private Instant addedDate;

    @Column(name = "update_date")
    private Instant updateDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PlaidItem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemId() {
        return this.itemId;
    }

    public PlaidItem itemId(String itemId) {
        this.setItemId(itemId);
        return this;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getInstitutionId() {
        return this.institutionId;
    }

    public PlaidItem institutionId(String institutionId) {
        this.setInstitutionId(institutionId);
        return this;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public PlaidItem accessToken(String accessToken) {
        this.setAccessToken(accessToken);
        return this;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Instant getAddedDate() {
        return this.addedDate;
    }

    public PlaidItem addedDate(Instant addedDate) {
        this.setAddedDate(addedDate);
        return this;
    }

    public void setAddedDate(Instant addedDate) {
        this.addedDate = addedDate;
    }

    public Instant getUpdateDate() {
        return this.updateDate;
    }

    public PlaidItem updateDate(Instant updateDate) {
        this.setUpdateDate(updateDate);
        return this;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlaidItem)) {
            return false;
        }
        return id != null && id.equals(((PlaidItem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlaidItem{" +
            "id=" + getId() +
            ", itemId='" + getItemId() + "'" +
            ", institutionId='" + getInstitutionId() + "'" +
            ", accessToken='" + getAccessToken() + "'" +
            ", addedDate='" + getAddedDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
