package com.akounts.myapp.web.rest;

import com.akounts.myapp.domain.BankAccountInterest;
import com.akounts.myapp.repository.BankAccountInterestRepository;
import com.akounts.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.akounts.myapp.domain.BankAccountInterest}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BankAccountInterestResource {

    private final Logger log = LoggerFactory.getLogger(BankAccountInterestResource.class);

    private static final String ENTITY_NAME = "bankAccountInterest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankAccountInterestRepository bankAccountInterestRepository;

    public BankAccountInterestResource(BankAccountInterestRepository bankAccountInterestRepository) {
        this.bankAccountInterestRepository = bankAccountInterestRepository;
    }

    /**
     * {@code POST  /bank-account-interests} : Create a new bankAccountInterest.
     *
     * @param bankAccountInterest the bankAccountInterest to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankAccountInterest, or with status {@code 400 (Bad Request)} if the bankAccountInterest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bank-account-interests")
    public ResponseEntity<BankAccountInterest> createBankAccountInterest(@RequestBody BankAccountInterest bankAccountInterest)
        throws URISyntaxException {
        log.debug("REST request to save BankAccountInterest : {}", bankAccountInterest);
        if (bankAccountInterest.getId() != null) {
            throw new BadRequestAlertException("A new bankAccountInterest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankAccountInterest result = bankAccountInterestRepository.save(bankAccountInterest);
        return ResponseEntity
            .created(new URI("/api/bank-account-interests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bank-account-interests/:id} : Updates an existing bankAccountInterest.
     *
     * @param id the id of the bankAccountInterest to save.
     * @param bankAccountInterest the bankAccountInterest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankAccountInterest,
     * or with status {@code 400 (Bad Request)} if the bankAccountInterest is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankAccountInterest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bank-account-interests/{id}")
    public ResponseEntity<BankAccountInterest> updateBankAccountInterest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankAccountInterest bankAccountInterest
    ) throws URISyntaxException {
        log.debug("REST request to update BankAccountInterest : {}, {}", id, bankAccountInterest);
        if (bankAccountInterest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankAccountInterest.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankAccountInterestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BankAccountInterest result = bankAccountInterestRepository.save(bankAccountInterest);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankAccountInterest.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bank-account-interests/:id} : Partial updates given fields of an existing bankAccountInterest, field will ignore if it is null
     *
     * @param id the id of the bankAccountInterest to save.
     * @param bankAccountInterest the bankAccountInterest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankAccountInterest,
     * or with status {@code 400 (Bad Request)} if the bankAccountInterest is not valid,
     * or with status {@code 404 (Not Found)} if the bankAccountInterest is not found,
     * or with status {@code 500 (Internal Server Error)} if the bankAccountInterest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bank-account-interests/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BankAccountInterest> partialUpdateBankAccountInterest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankAccountInterest bankAccountInterest
    ) throws URISyntaxException {
        log.debug("REST request to partial update BankAccountInterest partially : {}, {}", id, bankAccountInterest);
        if (bankAccountInterest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankAccountInterest.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankAccountInterestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BankAccountInterest> result = bankAccountInterestRepository
            .findById(bankAccountInterest.getId())
            .map(existingBankAccountInterest -> {
                if (bankAccountInterest.getInterest() != null) {
                    existingBankAccountInterest.setInterest(bankAccountInterest.getInterest());
                }
                if (bankAccountInterest.getPeriod() != null) {
                    existingBankAccountInterest.setPeriod(bankAccountInterest.getPeriod());
                }
                if (bankAccountInterest.getInterestType() != null) {
                    existingBankAccountInterest.setInterestType(bankAccountInterest.getInterestType());
                }
                if (bankAccountInterest.getUnits() != null) {
                    existingBankAccountInterest.setUnits(bankAccountInterest.getUnits());
                }
                if (bankAccountInterest.getStartDate() != null) {
                    existingBankAccountInterest.setStartDate(bankAccountInterest.getStartDate());
                }
                if (bankAccountInterest.getEndDate() != null) {
                    existingBankAccountInterest.setEndDate(bankAccountInterest.getEndDate());
                }
                if (bankAccountInterest.getScrappingURL() != null) {
                    existingBankAccountInterest.setScrappingURL(bankAccountInterest.getScrappingURL());
                }
                if (bankAccountInterest.getScrappingTag() != null) {
                    existingBankAccountInterest.setScrappingTag(bankAccountInterest.getScrappingTag());
                }
                if (bankAccountInterest.getScrappingTagBis() != null) {
                    existingBankAccountInterest.setScrappingTagBis(bankAccountInterest.getScrappingTagBis());
                }

                return existingBankAccountInterest;
            })
            .map(bankAccountInterestRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankAccountInterest.getId().toString())
        );
    }

    /**
     * {@code GET  /bank-account-interests} : get all the bankAccountInterests.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankAccountInterests in body.
     */
    @GetMapping("/bank-account-interests")
    public List<BankAccountInterest> getAllBankAccountInterests() {
        log.debug("REST request to get all BankAccountInterests");
        return bankAccountInterestRepository.findAll();
    }

    /**
     * {@code GET  /bank-account-interests/:id} : get the "id" bankAccountInterest.
     *
     * @param id the id of the bankAccountInterest to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankAccountInterest, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bank-account-interests/{id}")
    public ResponseEntity<BankAccountInterest> getBankAccountInterest(@PathVariable Long id) {
        log.debug("REST request to get BankAccountInterest : {}", id);
        Optional<BankAccountInterest> bankAccountInterest = bankAccountInterestRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bankAccountInterest);
    }

    /**
     * {@code DELETE  /bank-account-interests/:id} : delete the "id" bankAccountInterest.
     *
     * @param id the id of the bankAccountInterest to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bank-account-interests/{id}")
    public ResponseEntity<Void> deleteBankAccountInterest(@PathVariable Long id) {
        log.debug("REST request to delete BankAccountInterest : {}", id);
        bankAccountInterestRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
