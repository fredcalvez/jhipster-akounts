package com.akounts.myapp.web.rest;

import com.akounts.myapp.repository.BankTransactionAutomatchRepository;
import com.akounts.myapp.service.BankTransactionAutomatchService;
import com.akounts.myapp.service.dto.BankTransactionAutomatchDTO;
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
 * REST controller for managing {@link com.akounts.myapp.domain.BankTransactionAutomatch}.
 */
@RestController
@RequestMapping("/api")
public class BankTransactionAutomatchResource {

    private final Logger log = LoggerFactory.getLogger(BankTransactionAutomatchResource.class);

    private static final String ENTITY_NAME = "bankTransactionAutomatch";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankTransactionAutomatchService bankTransactionAutomatchService;

    private final BankTransactionAutomatchRepository bankTransactionAutomatchRepository;

    public BankTransactionAutomatchResource(
        BankTransactionAutomatchService bankTransactionAutomatchService,
        BankTransactionAutomatchRepository bankTransactionAutomatchRepository
    ) {
        this.bankTransactionAutomatchService = bankTransactionAutomatchService;
        this.bankTransactionAutomatchRepository = bankTransactionAutomatchRepository;
    }

    /**
     * {@code POST  /bank-transaction-automatches} : Create a new bankTransactionAutomatch.
     *
     * @param bankTransactionAutomatchDTO the bankTransactionAutomatchDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankTransactionAutomatchDTO, or with status {@code 400 (Bad Request)} if the bankTransactionAutomatch has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bank-transaction-automatches")
    public ResponseEntity<BankTransactionAutomatchDTO> createBankTransactionAutomatch(
        @RequestBody BankTransactionAutomatchDTO bankTransactionAutomatchDTO
    ) throws URISyntaxException {
        log.debug("REST request to save BankTransactionAutomatch : {}", bankTransactionAutomatchDTO);
        if (bankTransactionAutomatchDTO.getId() != null) {
            throw new BadRequestAlertException("A new bankTransactionAutomatch cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankTransactionAutomatchDTO result = bankTransactionAutomatchService.save(bankTransactionAutomatchDTO);
        return ResponseEntity
            .created(new URI("/api/bank-transaction-automatches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bank-transaction-automatches/:id} : Updates an existing bankTransactionAutomatch.
     *
     * @param id the id of the bankTransactionAutomatchDTO to save.
     * @param bankTransactionAutomatchDTO the bankTransactionAutomatchDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankTransactionAutomatchDTO,
     * or with status {@code 400 (Bad Request)} if the bankTransactionAutomatchDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankTransactionAutomatchDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bank-transaction-automatches/{id}")
    public ResponseEntity<BankTransactionAutomatchDTO> updateBankTransactionAutomatch(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankTransactionAutomatchDTO bankTransactionAutomatchDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BankTransactionAutomatch : {}, {}", id, bankTransactionAutomatchDTO);
        if (bankTransactionAutomatchDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankTransactionAutomatchDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankTransactionAutomatchRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BankTransactionAutomatchDTO result = bankTransactionAutomatchService.save(bankTransactionAutomatchDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankTransactionAutomatchDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bank-transaction-automatches/:id} : Partial updates given fields of an existing bankTransactionAutomatch, field will ignore if it is null
     *
     * @param id the id of the bankTransactionAutomatchDTO to save.
     * @param bankTransactionAutomatchDTO the bankTransactionAutomatchDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankTransactionAutomatchDTO,
     * or with status {@code 400 (Bad Request)} if the bankTransactionAutomatchDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bankTransactionAutomatchDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bankTransactionAutomatchDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bank-transaction-automatches/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BankTransactionAutomatchDTO> partialUpdateBankTransactionAutomatch(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankTransactionAutomatchDTO bankTransactionAutomatchDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BankTransactionAutomatch partially : {}, {}", id, bankTransactionAutomatchDTO);
        if (bankTransactionAutomatchDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankTransactionAutomatchDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankTransactionAutomatchRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BankTransactionAutomatchDTO> result = bankTransactionAutomatchService.partialUpdate(bankTransactionAutomatchDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankTransactionAutomatchDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bank-transaction-automatches} : get all the bankTransactionAutomatches.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankTransactionAutomatches in body.
     */
    @GetMapping("/bank-transaction-automatches")
    public ResponseEntity<List<BankTransactionAutomatchDTO>> getAllBankTransactionAutomatches(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of BankTransactionAutomatches");
        Page<BankTransactionAutomatchDTO> page = bankTransactionAutomatchService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bank-transaction-automatches/:id} : get the "id" bankTransactionAutomatch.
     *
     * @param id the id of the bankTransactionAutomatchDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankTransactionAutomatchDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bank-transaction-automatches/{id}")
    public ResponseEntity<BankTransactionAutomatchDTO> getBankTransactionAutomatch(@PathVariable Long id) {
        log.debug("REST request to get BankTransactionAutomatch : {}", id);
        Optional<BankTransactionAutomatchDTO> bankTransactionAutomatchDTO = bankTransactionAutomatchService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bankTransactionAutomatchDTO);
    }

    /**
     * {@code DELETE  /bank-transaction-automatches/:id} : delete the "id" bankTransactionAutomatch.
     *
     * @param id the id of the bankTransactionAutomatchDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bank-transaction-automatches/{id}")
    public ResponseEntity<Void> deleteBankTransactionAutomatch(@PathVariable Long id) {
        log.debug("REST request to delete BankTransactionAutomatch : {}", id);
        bankTransactionAutomatchService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
