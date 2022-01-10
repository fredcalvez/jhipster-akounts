package com.akounts.myapp.web.rest;

import com.akounts.myapp.domain.BankCategory;
import com.akounts.myapp.repository.BankCategoryRepository;
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
 * REST controller for managing {@link com.akounts.myapp.domain.BankCategory}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BankCategoryResource {

    private final Logger log = LoggerFactory.getLogger(BankCategoryResource.class);

    private static final String ENTITY_NAME = "bankCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankCategoryRepository bankCategoryRepository;

    public BankCategoryResource(BankCategoryRepository bankCategoryRepository) {
        this.bankCategoryRepository = bankCategoryRepository;
    }

    /**
     * {@code POST  /bank-categories} : Create a new bankCategory.
     *
     * @param bankCategory the bankCategory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankCategory, or with status {@code 400 (Bad Request)} if the bankCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bank-categories")
    public ResponseEntity<BankCategory> createBankCategory(@RequestBody BankCategory bankCategory) throws URISyntaxException {
        log.debug("REST request to save BankCategory : {}", bankCategory);
        if (bankCategory.getId() != null) {
            throw new BadRequestAlertException("A new bankCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankCategory result = bankCategoryRepository.save(bankCategory);
        return ResponseEntity
            .created(new URI("/api/bank-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bank-categories/:id} : Updates an existing bankCategory.
     *
     * @param id the id of the bankCategory to save.
     * @param bankCategory the bankCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankCategory,
     * or with status {@code 400 (Bad Request)} if the bankCategory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bank-categories/{id}")
    public ResponseEntity<BankCategory> updateBankCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankCategory bankCategory
    ) throws URISyntaxException {
        log.debug("REST request to update BankCategory : {}, {}", id, bankCategory);
        if (bankCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankCategory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BankCategory result = bankCategoryRepository.save(bankCategory);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankCategory.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bank-categories/:id} : Partial updates given fields of an existing bankCategory, field will ignore if it is null
     *
     * @param id the id of the bankCategory to save.
     * @param bankCategory the bankCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankCategory,
     * or with status {@code 400 (Bad Request)} if the bankCategory is not valid,
     * or with status {@code 404 (Not Found)} if the bankCategory is not found,
     * or with status {@code 500 (Internal Server Error)} if the bankCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bank-categories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BankCategory> partialUpdateBankCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankCategory bankCategory
    ) throws URISyntaxException {
        log.debug("REST request to partial update BankCategory partially : {}, {}", id, bankCategory);
        if (bankCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankCategory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BankCategory> result = bankCategoryRepository
            .findById(bankCategory.getId())
            .map(existingBankCategory -> {
                if (bankCategory.getParent() != null) {
                    existingBankCategory.setParent(bankCategory.getParent());
                }
                if (bankCategory.getCategorieLabel() != null) {
                    existingBankCategory.setCategorieLabel(bankCategory.getCategorieLabel());
                }
                if (bankCategory.getCategorieDesc() != null) {
                    existingBankCategory.setCategorieDesc(bankCategory.getCategorieDesc());
                }
                if (bankCategory.getIncome() != null) {
                    existingBankCategory.setIncome(bankCategory.getIncome());
                }
                if (bankCategory.getIsexpense() != null) {
                    existingBankCategory.setIsexpense(bankCategory.getIsexpense());
                }

                return existingBankCategory;
            })
            .map(bankCategoryRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankCategory.getId().toString())
        );
    }

    /**
     * {@code GET  /bank-categories} : get all the bankCategories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankCategories in body.
     */
    @GetMapping("/bank-categories")
    public List<BankCategory> getAllBankCategories() {
        log.debug("REST request to get all BankCategories");
        return bankCategoryRepository.findAll();
    }

    /**
     * {@code GET  /bank-categories/:id} : get the "id" bankCategory.
     *
     * @param id the id of the bankCategory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankCategory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bank-categories/{id}")
    public ResponseEntity<BankCategory> getBankCategory(@PathVariable Long id) {
        log.debug("REST request to get BankCategory : {}", id);
        Optional<BankCategory> bankCategory = bankCategoryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bankCategory);
    }

    /**
     * {@code DELETE  /bank-categories/:id} : delete the "id" bankCategory.
     *
     * @param id the id of the bankCategory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bank-categories/{id}")
    public ResponseEntity<Void> deleteBankCategory(@PathVariable Long id) {
        log.debug("REST request to delete BankCategory : {}", id);
        bankCategoryRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
