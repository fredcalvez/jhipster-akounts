package com.akounts.myapp.web.rest;

import com.akounts.myapp.domain.BankSaving;
import com.akounts.myapp.repository.BankSavingRepository;
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
 * REST controller for managing {@link com.akounts.myapp.domain.BankSaving}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BankSavingResource {

    private final Logger log = LoggerFactory.getLogger(BankSavingResource.class);

    private static final String ENTITY_NAME = "bankSaving";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankSavingRepository bankSavingRepository;

    public BankSavingResource(BankSavingRepository bankSavingRepository) {
        this.bankSavingRepository = bankSavingRepository;
    }

    /**
     * {@code POST  /bank-savings} : Create a new bankSaving.
     *
     * @param bankSaving the bankSaving to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankSaving, or with status {@code 400 (Bad Request)} if the bankSaving has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bank-savings")
    public ResponseEntity<BankSaving> createBankSaving(@RequestBody BankSaving bankSaving) throws URISyntaxException {
        log.debug("REST request to save BankSaving : {}", bankSaving);
        if (bankSaving.getId() != null) {
            throw new BadRequestAlertException("A new bankSaving cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankSaving result = bankSavingRepository.save(bankSaving);
        return ResponseEntity
            .created(new URI("/api/bank-savings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bank-savings/:id} : Updates an existing bankSaving.
     *
     * @param id the id of the bankSaving to save.
     * @param bankSaving the bankSaving to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankSaving,
     * or with status {@code 400 (Bad Request)} if the bankSaving is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankSaving couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bank-savings/{id}")
    public ResponseEntity<BankSaving> updateBankSaving(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankSaving bankSaving
    ) throws URISyntaxException {
        log.debug("REST request to update BankSaving : {}, {}", id, bankSaving);
        if (bankSaving.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankSaving.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankSavingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BankSaving result = bankSavingRepository.save(bankSaving);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankSaving.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bank-savings/:id} : Partial updates given fields of an existing bankSaving, field will ignore if it is null
     *
     * @param id the id of the bankSaving to save.
     * @param bankSaving the bankSaving to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankSaving,
     * or with status {@code 400 (Bad Request)} if the bankSaving is not valid,
     * or with status {@code 404 (Not Found)} if the bankSaving is not found,
     * or with status {@code 500 (Internal Server Error)} if the bankSaving couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bank-savings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BankSaving> partialUpdateBankSaving(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankSaving bankSaving
    ) throws URISyntaxException {
        log.debug("REST request to partial update BankSaving partially : {}, {}", id, bankSaving);
        if (bankSaving.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankSaving.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankSavingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BankSaving> result = bankSavingRepository
            .findById(bankSaving.getId())
            .map(existingBankSaving -> {
                if (bankSaving.getSummaryDate() != null) {
                    existingBankSaving.setSummaryDate(bankSaving.getSummaryDate());
                }
                if (bankSaving.getAmount() != null) {
                    existingBankSaving.setAmount(bankSaving.getAmount());
                }
                if (bankSaving.getGoal() != null) {
                    existingBankSaving.setGoal(bankSaving.getGoal());
                }
                if (bankSaving.getReach() != null) {
                    existingBankSaving.setReach(bankSaving.getReach());
                }

                return existingBankSaving;
            })
            .map(bankSavingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankSaving.getId().toString())
        );
    }

    /**
     * {@code GET  /bank-savings} : get all the bankSavings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankSavings in body.
     */
    @GetMapping("/bank-savings")
    public List<BankSaving> getAllBankSavings() {
        log.debug("REST request to get all BankSavings");
        return bankSavingRepository.findAll();
    }

    /**
     * {@code GET  /bank-savings/:id} : get the "id" bankSaving.
     *
     * @param id the id of the bankSaving to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankSaving, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bank-savings/{id}")
    public ResponseEntity<BankSaving> getBankSaving(@PathVariable Long id) {
        log.debug("REST request to get BankSaving : {}", id);
        Optional<BankSaving> bankSaving = bankSavingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bankSaving);
    }

    /**
     * {@code DELETE  /bank-savings/:id} : delete the "id" bankSaving.
     *
     * @param id the id of the bankSaving to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bank-savings/{id}")
    public ResponseEntity<Void> deleteBankSaving(@PathVariable Long id) {
        log.debug("REST request to delete BankSaving : {}", id);
        bankSavingRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
