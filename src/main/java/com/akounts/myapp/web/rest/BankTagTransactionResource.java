package com.akounts.myapp.web.rest;

import com.akounts.myapp.repository.BankTagTransactionRepository;
import com.akounts.myapp.service.BankTagTransactionService;
import com.akounts.myapp.service.dto.BankTagTransactionDTO;
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
 * REST controller for managing {@link com.akounts.myapp.domain.BankTagTransaction}.
 */
@RestController
@RequestMapping("/api")
public class BankTagTransactionResource {

    private final Logger log = LoggerFactory.getLogger(BankTagTransactionResource.class);

    private static final String ENTITY_NAME = "bankTagTransaction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankTagTransactionService bankTagTransactionService;

    private final BankTagTransactionRepository bankTagTransactionRepository;

    public BankTagTransactionResource(
        BankTagTransactionService bankTagTransactionService,
        BankTagTransactionRepository bankTagTransactionRepository
    ) {
        this.bankTagTransactionService = bankTagTransactionService;
        this.bankTagTransactionRepository = bankTagTransactionRepository;
    }

    /**
     * {@code POST  /bank-tag-transactions} : Create a new bankTagTransaction.
     *
     * @param bankTagTransactionDTO the bankTagTransactionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankTagTransactionDTO, or with status {@code 400 (Bad Request)} if the bankTagTransaction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bank-tag-transactions")
    public ResponseEntity<BankTagTransactionDTO> createBankTagTransaction(@RequestBody BankTagTransactionDTO bankTagTransactionDTO)
        throws URISyntaxException {
        log.debug("REST request to save BankTagTransaction : {}", bankTagTransactionDTO);
        if (bankTagTransactionDTO.getId() != null) {
            throw new BadRequestAlertException("A new bankTagTransaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankTagTransactionDTO result = bankTagTransactionService.save(bankTagTransactionDTO);
        return ResponseEntity
            .created(new URI("/api/bank-tag-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bank-tag-transactions/:id} : Updates an existing bankTagTransaction.
     *
     * @param id the id of the bankTagTransactionDTO to save.
     * @param bankTagTransactionDTO the bankTagTransactionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankTagTransactionDTO,
     * or with status {@code 400 (Bad Request)} if the bankTagTransactionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankTagTransactionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bank-tag-transactions/{id}")
    public ResponseEntity<BankTagTransactionDTO> updateBankTagTransaction(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankTagTransactionDTO bankTagTransactionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BankTagTransaction : {}, {}", id, bankTagTransactionDTO);
        if (bankTagTransactionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankTagTransactionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankTagTransactionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BankTagTransactionDTO result = bankTagTransactionService.save(bankTagTransactionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankTagTransactionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bank-tag-transactions/:id} : Partial updates given fields of an existing bankTagTransaction, field will ignore if it is null
     *
     * @param id the id of the bankTagTransactionDTO to save.
     * @param bankTagTransactionDTO the bankTagTransactionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankTagTransactionDTO,
     * or with status {@code 400 (Bad Request)} if the bankTagTransactionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bankTagTransactionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bankTagTransactionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bank-tag-transactions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BankTagTransactionDTO> partialUpdateBankTagTransaction(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankTagTransactionDTO bankTagTransactionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BankTagTransaction partially : {}, {}", id, bankTagTransactionDTO);
        if (bankTagTransactionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankTagTransactionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankTagTransactionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BankTagTransactionDTO> result = bankTagTransactionService.partialUpdate(bankTagTransactionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankTagTransactionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bank-tag-transactions} : get all the bankTagTransactions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankTagTransactions in body.
     */
    @GetMapping("/bank-tag-transactions")
    public ResponseEntity<List<BankTagTransactionDTO>> getAllBankTagTransactions(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of BankTagTransactions");
        Page<BankTagTransactionDTO> page = bankTagTransactionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bank-tag-transactions/:id} : get the "id" bankTagTransaction.
     *
     * @param id the id of the bankTagTransactionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankTagTransactionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bank-tag-transactions/{id}")
    public ResponseEntity<BankTagTransactionDTO> getBankTagTransaction(@PathVariable Long id) {
        log.debug("REST request to get BankTagTransaction : {}", id);
        Optional<BankTagTransactionDTO> bankTagTransactionDTO = bankTagTransactionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bankTagTransactionDTO);
    }

    /**
     * {@code DELETE  /bank-tag-transactions/:id} : delete the "id" bankTagTransaction.
     *
     * @param id the id of the bankTagTransactionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bank-tag-transactions/{id}")
    public ResponseEntity<Void> deleteBankTagTransaction(@PathVariable Long id) {
        log.debug("REST request to delete BankTagTransaction : {}", id);
        bankTagTransactionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
