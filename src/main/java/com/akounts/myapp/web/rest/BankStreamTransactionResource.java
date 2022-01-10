package com.akounts.myapp.web.rest;

import com.akounts.myapp.repository.BankStreamTransactionRepository;
import com.akounts.myapp.service.BankStreamTransactionService;
import com.akounts.myapp.service.dto.BankStreamTransactionDTO;
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
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.akounts.myapp.domain.BankStreamTransaction}.
 */
@RestController
@RequestMapping("/api")
public class BankStreamTransactionResource {

    private final Logger log = LoggerFactory.getLogger(BankStreamTransactionResource.class);

    private static final String ENTITY_NAME = "bankStreamTransaction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankStreamTransactionService bankStreamTransactionService;

    private final BankStreamTransactionRepository bankStreamTransactionRepository;

    public BankStreamTransactionResource(
        BankStreamTransactionService bankStreamTransactionService,
        BankStreamTransactionRepository bankStreamTransactionRepository
    ) {
        this.bankStreamTransactionService = bankStreamTransactionService;
        this.bankStreamTransactionRepository = bankStreamTransactionRepository;
    }

    /**
     * {@code POST  /bank-stream-transactions} : Create a new bankStreamTransaction.
     *
     * @param bankStreamTransactionDTO the bankStreamTransactionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankStreamTransactionDTO, or with status {@code 400 (Bad Request)} if the bankStreamTransaction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bank-stream-transactions")
    public ResponseEntity<BankStreamTransactionDTO> createBankStreamTransaction(
        @RequestBody BankStreamTransactionDTO bankStreamTransactionDTO
    ) throws URISyntaxException {
        log.debug("REST request to save BankStreamTransaction : {}", bankStreamTransactionDTO);
        if (bankStreamTransactionDTO.getId() != null) {
            throw new BadRequestAlertException("A new bankStreamTransaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankStreamTransactionDTO result = bankStreamTransactionService.save(bankStreamTransactionDTO);
        return ResponseEntity
            .created(new URI("/api/bank-stream-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bank-stream-transactions/:id} : Updates an existing bankStreamTransaction.
     *
     * @param id the id of the bankStreamTransactionDTO to save.
     * @param bankStreamTransactionDTO the bankStreamTransactionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankStreamTransactionDTO,
     * or with status {@code 400 (Bad Request)} if the bankStreamTransactionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankStreamTransactionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bank-stream-transactions/{id}")
    public ResponseEntity<BankStreamTransactionDTO> updateBankStreamTransaction(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankStreamTransactionDTO bankStreamTransactionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BankStreamTransaction : {}, {}", id, bankStreamTransactionDTO);
        if (bankStreamTransactionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankStreamTransactionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankStreamTransactionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BankStreamTransactionDTO result = bankStreamTransactionService.save(bankStreamTransactionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankStreamTransactionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bank-stream-transactions/:id} : Partial updates given fields of an existing bankStreamTransaction, field will ignore if it is null
     *
     * @param id the id of the bankStreamTransactionDTO to save.
     * @param bankStreamTransactionDTO the bankStreamTransactionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankStreamTransactionDTO,
     * or with status {@code 400 (Bad Request)} if the bankStreamTransactionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bankStreamTransactionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bankStreamTransactionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bank-stream-transactions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BankStreamTransactionDTO> partialUpdateBankStreamTransaction(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankStreamTransactionDTO bankStreamTransactionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BankStreamTransaction partially : {}, {}", id, bankStreamTransactionDTO);
        if (bankStreamTransactionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankStreamTransactionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankStreamTransactionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BankStreamTransactionDTO> result = bankStreamTransactionService.partialUpdate(bankStreamTransactionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankStreamTransactionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bank-stream-transactions} : get all the bankStreamTransactions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankStreamTransactions in body.
     */
    @GetMapping("/bank-stream-transactions")
    public List<BankStreamTransactionDTO> getAllBankStreamTransactions() {
        log.debug("REST request to get all BankStreamTransactions");
        return bankStreamTransactionService.findAll();
    }

    /**
     * {@code GET  /bank-stream-transactions/:id} : get the "id" bankStreamTransaction.
     *
     * @param id the id of the bankStreamTransactionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankStreamTransactionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bank-stream-transactions/{id}")
    public ResponseEntity<BankStreamTransactionDTO> getBankStreamTransaction(@PathVariable Long id) {
        log.debug("REST request to get BankStreamTransaction : {}", id);
        Optional<BankStreamTransactionDTO> bankStreamTransactionDTO = bankStreamTransactionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bankStreamTransactionDTO);
    }

    /**
     * {@code DELETE  /bank-stream-transactions/:id} : delete the "id" bankStreamTransaction.
     *
     * @param id the id of the bankStreamTransactionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bank-stream-transactions/{id}")
    public ResponseEntity<Void> deleteBankStreamTransaction(@PathVariable Long id) {
        log.debug("REST request to delete BankStreamTransaction : {}", id);
        bankStreamTransactionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
