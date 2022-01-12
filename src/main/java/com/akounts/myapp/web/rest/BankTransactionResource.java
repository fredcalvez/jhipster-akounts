package com.akounts.myapp.web.rest;

import com.akounts.myapp.repository.BankTransactionRepository;
import com.akounts.myapp.service.BankTransactionService;
import com.akounts.myapp.service.dto.BankTransactionDTO;
import com.akounts.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.akounts.myapp.domain.BankTransaction}.
 */
@RestController
@RequestMapping("/api")
public class BankTransactionResource {

    private final Logger log = LoggerFactory.getLogger(BankTransactionResource.class);

    private static final String ENTITY_NAME = "bankTransaction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankTransactionService bankTransactionService;

    private final BankTransactionRepository bankTransactionRepository;

    public BankTransactionResource(BankTransactionService bankTransactionService, BankTransactionRepository bankTransactionRepository) {
        this.bankTransactionService = bankTransactionService;
        this.bankTransactionRepository = bankTransactionRepository;
    }

    /**
     * {@code POST  /bank-transactions} : Create a new bankTransaction.
     *
     * @param bankTransactionDTO the bankTransactionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankTransactionDTO, or with status {@code 400 (Bad Request)} if the bankTransaction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bank-transactions")
    public ResponseEntity<BankTransactionDTO> createBankTransaction(@RequestBody BankTransactionDTO bankTransactionDTO)
        throws URISyntaxException {
        log.debug("REST request to save BankTransaction : {}", bankTransactionDTO);
        if (bankTransactionDTO.getId() != null) {
            throw new BadRequestAlertException("A new bankTransaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankTransactionDTO result = bankTransactionService.save(bankTransactionDTO);
        return ResponseEntity
            .created(new URI("/api/bank-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bank-transactions/:id} : Updates an existing bankTransaction.
     *
     * @param id the id of the bankTransactionDTO to save.
     * @param bankTransactionDTO the bankTransactionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankTransactionDTO,
     * or with status {@code 400 (Bad Request)} if the bankTransactionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankTransactionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bank-transactions/{id}")
    public ResponseEntity<BankTransactionDTO> updateBankTransaction(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankTransactionDTO bankTransactionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BankTransaction : {}, {}", id, bankTransactionDTO);
        if (bankTransactionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankTransactionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankTransactionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BankTransactionDTO result = bankTransactionService.save(bankTransactionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankTransactionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bank-transactions/:id} : Partial updates given fields of an existing bankTransaction, field will ignore if it is null
     *
     * @param id the id of the bankTransactionDTO to save.
     * @param bankTransactionDTO the bankTransactionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankTransactionDTO,
     * or with status {@code 400 (Bad Request)} if the bankTransactionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bankTransactionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bankTransactionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bank-transactions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BankTransactionDTO> partialUpdateBankTransaction(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankTransactionDTO bankTransactionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BankTransaction partially : {}, {}", id, bankTransactionDTO);
        if (bankTransactionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankTransactionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankTransactionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BankTransactionDTO> result = bankTransactionService.partialUpdate(bankTransactionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankTransactionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bank-transactions} : get all the bankTransactions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankTransactions in body.
     */
    @GetMapping("/bank-transactions")
    public ResponseEntity<List<BankTransactionDTO>> getAllBankTransactions(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of BankTransactions");
        Page<BankTransactionDTO> page = bankTransactionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bank-transactions/:id} : get the "id" bankTransaction.
     *
     * @param id the id of the bankTransactionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankTransactionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bank-transactions/{id}")
    public ResponseEntity<BankTransactionDTO> getBankTransaction(@PathVariable Long id) {
        log.debug("REST request to get BankTransaction : {}", id);
        Optional<BankTransactionDTO> bankTransactionDTO = bankTransactionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bankTransactionDTO);
    }

    /**
     * {@code DELETE  /bank-transactions/:id} : delete the "id" bankTransaction.
     *
     * @param id the id of the bankTransactionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bank-transactions/{id}")
    public ResponseEntity<Void> deleteBankTransaction(@PathVariable Long id) {
        log.debug("REST request to delete BankTransaction : {}", id);
        bankTransactionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
