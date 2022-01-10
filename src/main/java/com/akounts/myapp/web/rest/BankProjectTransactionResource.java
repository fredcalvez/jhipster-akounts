package com.akounts.myapp.web.rest;

import com.akounts.myapp.repository.BankProjectTransactionRepository;
import com.akounts.myapp.service.BankProjectTransactionService;
import com.akounts.myapp.service.dto.BankProjectTransactionDTO;
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
 * REST controller for managing {@link com.akounts.myapp.domain.BankProjectTransaction}.
 */
@RestController
@RequestMapping("/api")
public class BankProjectTransactionResource {

    private final Logger log = LoggerFactory.getLogger(BankProjectTransactionResource.class);

    private static final String ENTITY_NAME = "bankProjectTransaction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankProjectTransactionService bankProjectTransactionService;

    private final BankProjectTransactionRepository bankProjectTransactionRepository;

    public BankProjectTransactionResource(
        BankProjectTransactionService bankProjectTransactionService,
        BankProjectTransactionRepository bankProjectTransactionRepository
    ) {
        this.bankProjectTransactionService = bankProjectTransactionService;
        this.bankProjectTransactionRepository = bankProjectTransactionRepository;
    }

    /**
     * {@code POST  /bank-project-transactions} : Create a new bankProjectTransaction.
     *
     * @param bankProjectTransactionDTO the bankProjectTransactionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankProjectTransactionDTO, or with status {@code 400 (Bad Request)} if the bankProjectTransaction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bank-project-transactions")
    public ResponseEntity<BankProjectTransactionDTO> createBankProjectTransaction(
        @RequestBody BankProjectTransactionDTO bankProjectTransactionDTO
    ) throws URISyntaxException {
        log.debug("REST request to save BankProjectTransaction : {}", bankProjectTransactionDTO);
        if (bankProjectTransactionDTO.getId() != null) {
            throw new BadRequestAlertException("A new bankProjectTransaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankProjectTransactionDTO result = bankProjectTransactionService.save(bankProjectTransactionDTO);
        return ResponseEntity
            .created(new URI("/api/bank-project-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bank-project-transactions/:id} : Updates an existing bankProjectTransaction.
     *
     * @param id the id of the bankProjectTransactionDTO to save.
     * @param bankProjectTransactionDTO the bankProjectTransactionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankProjectTransactionDTO,
     * or with status {@code 400 (Bad Request)} if the bankProjectTransactionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankProjectTransactionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bank-project-transactions/{id}")
    public ResponseEntity<BankProjectTransactionDTO> updateBankProjectTransaction(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankProjectTransactionDTO bankProjectTransactionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BankProjectTransaction : {}, {}", id, bankProjectTransactionDTO);
        if (bankProjectTransactionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankProjectTransactionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankProjectTransactionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BankProjectTransactionDTO result = bankProjectTransactionService.save(bankProjectTransactionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankProjectTransactionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bank-project-transactions/:id} : Partial updates given fields of an existing bankProjectTransaction, field will ignore if it is null
     *
     * @param id the id of the bankProjectTransactionDTO to save.
     * @param bankProjectTransactionDTO the bankProjectTransactionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankProjectTransactionDTO,
     * or with status {@code 400 (Bad Request)} if the bankProjectTransactionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bankProjectTransactionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bankProjectTransactionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bank-project-transactions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BankProjectTransactionDTO> partialUpdateBankProjectTransaction(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankProjectTransactionDTO bankProjectTransactionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BankProjectTransaction partially : {}, {}", id, bankProjectTransactionDTO);
        if (bankProjectTransactionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankProjectTransactionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankProjectTransactionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BankProjectTransactionDTO> result = bankProjectTransactionService.partialUpdate(bankProjectTransactionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankProjectTransactionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bank-project-transactions} : get all the bankProjectTransactions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankProjectTransactions in body.
     */
    @GetMapping("/bank-project-transactions")
    public List<BankProjectTransactionDTO> getAllBankProjectTransactions() {
        log.debug("REST request to get all BankProjectTransactions");
        return bankProjectTransactionService.findAll();
    }

    /**
     * {@code GET  /bank-project-transactions/:id} : get the "id" bankProjectTransaction.
     *
     * @param id the id of the bankProjectTransactionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankProjectTransactionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bank-project-transactions/{id}")
    public ResponseEntity<BankProjectTransactionDTO> getBankProjectTransaction(@PathVariable Long id) {
        log.debug("REST request to get BankProjectTransaction : {}", id);
        Optional<BankProjectTransactionDTO> bankProjectTransactionDTO = bankProjectTransactionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bankProjectTransactionDTO);
    }

    /**
     * {@code DELETE  /bank-project-transactions/:id} : delete the "id" bankProjectTransaction.
     *
     * @param id the id of the bankProjectTransactionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bank-project-transactions/{id}")
    public ResponseEntity<Void> deleteBankProjectTransaction(@PathVariable Long id) {
        log.debug("REST request to delete BankProjectTransaction : {}", id);
        bankProjectTransactionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
