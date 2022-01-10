package com.akounts.myapp.web.rest;

import com.akounts.myapp.domain.BankInstitution;
import com.akounts.myapp.repository.BankInstitutionRepository;
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
 * REST controller for managing {@link com.akounts.myapp.domain.BankInstitution}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BankInstitutionResource {

    private final Logger log = LoggerFactory.getLogger(BankInstitutionResource.class);

    private static final String ENTITY_NAME = "bankInstitution";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankInstitutionRepository bankInstitutionRepository;

    public BankInstitutionResource(BankInstitutionRepository bankInstitutionRepository) {
        this.bankInstitutionRepository = bankInstitutionRepository;
    }

    /**
     * {@code POST  /bank-institutions} : Create a new bankInstitution.
     *
     * @param bankInstitution the bankInstitution to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankInstitution, or with status {@code 400 (Bad Request)} if the bankInstitution has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bank-institutions")
    public ResponseEntity<BankInstitution> createBankInstitution(@RequestBody BankInstitution bankInstitution) throws URISyntaxException {
        log.debug("REST request to save BankInstitution : {}", bankInstitution);
        if (bankInstitution.getId() != null) {
            throw new BadRequestAlertException("A new bankInstitution cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankInstitution result = bankInstitutionRepository.save(bankInstitution);
        return ResponseEntity
            .created(new URI("/api/bank-institutions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bank-institutions/:id} : Updates an existing bankInstitution.
     *
     * @param id the id of the bankInstitution to save.
     * @param bankInstitution the bankInstitution to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankInstitution,
     * or with status {@code 400 (Bad Request)} if the bankInstitution is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankInstitution couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bank-institutions/{id}")
    public ResponseEntity<BankInstitution> updateBankInstitution(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankInstitution bankInstitution
    ) throws URISyntaxException {
        log.debug("REST request to update BankInstitution : {}, {}", id, bankInstitution);
        if (bankInstitution.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankInstitution.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankInstitutionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BankInstitution result = bankInstitutionRepository.save(bankInstitution);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankInstitution.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bank-institutions/:id} : Partial updates given fields of an existing bankInstitution, field will ignore if it is null
     *
     * @param id the id of the bankInstitution to save.
     * @param bankInstitution the bankInstitution to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankInstitution,
     * or with status {@code 400 (Bad Request)} if the bankInstitution is not valid,
     * or with status {@code 404 (Not Found)} if the bankInstitution is not found,
     * or with status {@code 500 (Internal Server Error)} if the bankInstitution couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bank-institutions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BankInstitution> partialUpdateBankInstitution(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankInstitution bankInstitution
    ) throws URISyntaxException {
        log.debug("REST request to partial update BankInstitution partially : {}, {}", id, bankInstitution);
        if (bankInstitution.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankInstitution.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankInstitutionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BankInstitution> result = bankInstitutionRepository
            .findById(bankInstitution.getId())
            .map(existingBankInstitution -> {
                if (bankInstitution.getInstitutionLabel() != null) {
                    existingBankInstitution.setInstitutionLabel(bankInstitution.getInstitutionLabel());
                }
                if (bankInstitution.getCode() != null) {
                    existingBankInstitution.setCode(bankInstitution.getCode());
                }
                if (bankInstitution.getActive() != null) {
                    existingBankInstitution.setActive(bankInstitution.getActive());
                }
                if (bankInstitution.getCurrency() != null) {
                    existingBankInstitution.setCurrency(bankInstitution.getCurrency());
                }
                if (bankInstitution.getIsoCountryCode() != null) {
                    existingBankInstitution.setIsoCountryCode(bankInstitution.getIsoCountryCode());
                }

                return existingBankInstitution;
            })
            .map(bankInstitutionRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankInstitution.getId().toString())
        );
    }

    /**
     * {@code GET  /bank-institutions} : get all the bankInstitutions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankInstitutions in body.
     */
    @GetMapping("/bank-institutions")
    public List<BankInstitution> getAllBankInstitutions() {
        log.debug("REST request to get all BankInstitutions");
        return bankInstitutionRepository.findAll();
    }

    /**
     * {@code GET  /bank-institutions/:id} : get the "id" bankInstitution.
     *
     * @param id the id of the bankInstitution to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankInstitution, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bank-institutions/{id}")
    public ResponseEntity<BankInstitution> getBankInstitution(@PathVariable Long id) {
        log.debug("REST request to get BankInstitution : {}", id);
        Optional<BankInstitution> bankInstitution = bankInstitutionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bankInstitution);
    }

    /**
     * {@code DELETE  /bank-institutions/:id} : delete the "id" bankInstitution.
     *
     * @param id the id of the bankInstitution to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bank-institutions/{id}")
    public ResponseEntity<Void> deleteBankInstitution(@PathVariable Long id) {
        log.debug("REST request to delete BankInstitution : {}", id);
        bankInstitutionRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
