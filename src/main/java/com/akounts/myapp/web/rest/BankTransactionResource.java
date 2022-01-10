package com.akounts.myapp.web.rest;

import com.akounts.myapp.domain.BankTransaction;
import com.akounts.myapp.repository.BankTransactionRepository;
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
 * REST controller for managing {@link com.akounts.myapp.domain.BankTransaction}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BankTransactionResource {

    private final Logger log = LoggerFactory.getLogger(BankTransactionResource.class);

    private static final String ENTITY_NAME = "bankTransaction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankTransactionRepository bankTransactionRepository;

    public BankTransactionResource(BankTransactionRepository bankTransactionRepository) {
        this.bankTransactionRepository = bankTransactionRepository;
    }

    /**
     * {@code POST  /bank-transactions} : Create a new bankTransaction.
     *
     * @param bankTransaction the bankTransaction to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankTransaction, or with status {@code 400 (Bad Request)} if the bankTransaction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bank-transactions")
    public ResponseEntity<BankTransaction> createBankTransaction(@RequestBody BankTransaction bankTransaction) throws URISyntaxException {
        log.debug("REST request to save BankTransaction : {}", bankTransaction);
        if (bankTransaction.getId() != null) {
            throw new BadRequestAlertException("A new bankTransaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankTransaction result = bankTransactionRepository.save(bankTransaction);
        return ResponseEntity
            .created(new URI("/api/bank-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bank-transactions/:id} : Updates an existing bankTransaction.
     *
     * @param id the id of the bankTransaction to save.
     * @param bankTransaction the bankTransaction to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankTransaction,
     * or with status {@code 400 (Bad Request)} if the bankTransaction is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankTransaction couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bank-transactions/{id}")
    public ResponseEntity<BankTransaction> updateBankTransaction(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankTransaction bankTransaction
    ) throws URISyntaxException {
        log.debug("REST request to update BankTransaction : {}, {}", id, bankTransaction);
        if (bankTransaction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankTransaction.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankTransactionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BankTransaction result = bankTransactionRepository.save(bankTransaction);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankTransaction.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bank-transactions/:id} : Partial updates given fields of an existing bankTransaction, field will ignore if it is null
     *
     * @param id the id of the bankTransaction to save.
     * @param bankTransaction the bankTransaction to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankTransaction,
     * or with status {@code 400 (Bad Request)} if the bankTransaction is not valid,
     * or with status {@code 404 (Not Found)} if the bankTransaction is not found,
     * or with status {@code 500 (Internal Server Error)} if the bankTransaction couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bank-transactions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BankTransaction> partialUpdateBankTransaction(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankTransaction bankTransaction
    ) throws URISyntaxException {
        log.debug("REST request to partial update BankTransaction partially : {}, {}", id, bankTransaction);
        if (bankTransaction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankTransaction.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankTransactionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BankTransaction> result = bankTransactionRepository
            .findById(bankTransaction.getId())
            .map(existingBankTransaction -> {
                if (bankTransaction.getTransactionId() != null) {
                    existingBankTransaction.setTransactionId(bankTransaction.getTransactionId());
                }
                if (bankTransaction.getTransactionDate() != null) {
                    existingBankTransaction.setTransactionDate(bankTransaction.getTransactionDate());
                }
                if (bankTransaction.getDescription() != null) {
                    existingBankTransaction.setDescription(bankTransaction.getDescription());
                }
                if (bankTransaction.getLocalAmount() != null) {
                    existingBankTransaction.setLocalAmount(bankTransaction.getLocalAmount());
                }
                if (bankTransaction.getLocalCurrency() != null) {
                    existingBankTransaction.setLocalCurrency(bankTransaction.getLocalCurrency());
                }
                if (bankTransaction.getAmount() != null) {
                    existingBankTransaction.setAmount(bankTransaction.getAmount());
                }
                if (bankTransaction.getCurrency() != null) {
                    existingBankTransaction.setCurrency(bankTransaction.getCurrency());
                }
                if (bankTransaction.getNote() != null) {
                    existingBankTransaction.setNote(bankTransaction.getNote());
                }
                if (bankTransaction.getYear() != null) {
                    existingBankTransaction.setYear(bankTransaction.getYear());
                }
                if (bankTransaction.getMonth() != null) {
                    existingBankTransaction.setMonth(bankTransaction.getMonth());
                }
                if (bankTransaction.getWeek() != null) {
                    existingBankTransaction.setWeek(bankTransaction.getWeek());
                }
                if (bankTransaction.getCategorizedDate() != null) {
                    existingBankTransaction.setCategorizedDate(bankTransaction.getCategorizedDate());
                }
                if (bankTransaction.getAddDate() != null) {
                    existingBankTransaction.setAddDate(bankTransaction.getAddDate());
                }
                if (bankTransaction.getChecked() != null) {
                    existingBankTransaction.setChecked(bankTransaction.getChecked());
                }
                if (bankTransaction.getRebasedDate() != null) {
                    existingBankTransaction.setRebasedDate(bankTransaction.getRebasedDate());
                }
                if (bankTransaction.getDeleted() != null) {
                    existingBankTransaction.setDeleted(bankTransaction.getDeleted());
                }
                if (bankTransaction.getTag() != null) {
                    existingBankTransaction.setTag(bankTransaction.getTag());
                }
                if (bankTransaction.getCreatedOn() != null) {
                    existingBankTransaction.setCreatedOn(bankTransaction.getCreatedOn());
                }
                if (bankTransaction.getUpdatedOn() != null) {
                    existingBankTransaction.setUpdatedOn(bankTransaction.getUpdatedOn());
                }
                if (bankTransaction.getVersion() != null) {
                    existingBankTransaction.setVersion(bankTransaction.getVersion());
                }

                return existingBankTransaction;
            })
            .map(bankTransactionRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankTransaction.getId().toString())
        );
    }

    /**
     * {@code GET  /bank-transactions} : get all the bankTransactions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankTransactions in body.
     */
    @GetMapping("/bank-transactions")
    public List<BankTransaction> getAllBankTransactions() {
        log.debug("REST request to get all BankTransactions");
        return bankTransactionRepository.findAll();
    }

    /**
     * {@code GET  /bank-transactions/:id} : get the "id" bankTransaction.
     *
     * @param id the id of the bankTransaction to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankTransaction, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bank-transactions/{id}")
    public ResponseEntity<BankTransaction> getBankTransaction(@PathVariable Long id) {
        log.debug("REST request to get BankTransaction : {}", id);
        Optional<BankTransaction> bankTransaction = bankTransactionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bankTransaction);
    }

    /**
     * {@code DELETE  /bank-transactions/:id} : delete the "id" bankTransaction.
     *
     * @param id the id of the bankTransaction to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bank-transactions/{id}")
    public ResponseEntity<Void> deleteBankTransaction(@PathVariable Long id) {
        log.debug("REST request to delete BankTransaction : {}", id);
        bankTransactionRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
