package com.akounts.myapp.web.rest;

import com.akounts.myapp.repository.PlaidTransactionRepository;
import com.akounts.myapp.service.PlaidTransactionService;
import com.akounts.myapp.service.dto.PlaidTransactionDTO;
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
 * REST controller for managing {@link com.akounts.myapp.domain.PlaidTransaction}.
 */
@RestController
@RequestMapping("/api")
public class PlaidTransactionResource {

    private final Logger log = LoggerFactory.getLogger(PlaidTransactionResource.class);

    private static final String ENTITY_NAME = "plaidTransaction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlaidTransactionService plaidTransactionService;

    private final PlaidTransactionRepository plaidTransactionRepository;

    public PlaidTransactionResource(
        PlaidTransactionService plaidTransactionService,
        PlaidTransactionRepository plaidTransactionRepository
    ) {
        this.plaidTransactionService = plaidTransactionService;
        this.plaidTransactionRepository = plaidTransactionRepository;
    }

    /**
     * {@code POST  /plaid-transactions} : Create a new plaidTransaction.
     *
     * @param plaidTransactionDTO the plaidTransactionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new plaidTransactionDTO, or with status {@code 400 (Bad Request)} if the plaidTransaction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plaid-transactions")
    public ResponseEntity<PlaidTransactionDTO> createPlaidTransaction(@RequestBody PlaidTransactionDTO plaidTransactionDTO)
        throws URISyntaxException {
        log.debug("REST request to save PlaidTransaction : {}", plaidTransactionDTO);
        if (plaidTransactionDTO.getId() != null) {
            throw new BadRequestAlertException("A new plaidTransaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlaidTransactionDTO result = plaidTransactionService.save(plaidTransactionDTO);
        return ResponseEntity
            .created(new URI("/api/plaid-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plaid-transactions/:id} : Updates an existing plaidTransaction.
     *
     * @param id the id of the plaidTransactionDTO to save.
     * @param plaidTransactionDTO the plaidTransactionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plaidTransactionDTO,
     * or with status {@code 400 (Bad Request)} if the plaidTransactionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the plaidTransactionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plaid-transactions/{id}")
    public ResponseEntity<PlaidTransactionDTO> updatePlaidTransaction(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PlaidTransactionDTO plaidTransactionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PlaidTransaction : {}, {}", id, plaidTransactionDTO);
        if (plaidTransactionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plaidTransactionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plaidTransactionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PlaidTransactionDTO result = plaidTransactionService.save(plaidTransactionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plaidTransactionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /plaid-transactions/:id} : Partial updates given fields of an existing plaidTransaction, field will ignore if it is null
     *
     * @param id the id of the plaidTransactionDTO to save.
     * @param plaidTransactionDTO the plaidTransactionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plaidTransactionDTO,
     * or with status {@code 400 (Bad Request)} if the plaidTransactionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the plaidTransactionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the plaidTransactionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/plaid-transactions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlaidTransactionDTO> partialUpdatePlaidTransaction(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PlaidTransactionDTO plaidTransactionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PlaidTransaction partially : {}, {}", id, plaidTransactionDTO);
        if (plaidTransactionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plaidTransactionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plaidTransactionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PlaidTransactionDTO> result = plaidTransactionService.partialUpdate(plaidTransactionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plaidTransactionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /plaid-transactions} : get all the plaidTransactions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plaidTransactions in body.
     */
    @GetMapping("/plaid-transactions")
    public ResponseEntity<List<PlaidTransactionDTO>> getAllPlaidTransactions(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of PlaidTransactions");
        Page<PlaidTransactionDTO> page = plaidTransactionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /plaid-transactions/:id} : get the "id" plaidTransaction.
     *
     * @param id the id of the plaidTransactionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the plaidTransactionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plaid-transactions/{id}")
    public ResponseEntity<PlaidTransactionDTO> getPlaidTransaction(@PathVariable Long id) {
        log.debug("REST request to get PlaidTransaction : {}", id);
        Optional<PlaidTransactionDTO> plaidTransactionDTO = plaidTransactionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(plaidTransactionDTO);
    }

    /**
     * {@code DELETE  /plaid-transactions/:id} : delete the "id" plaidTransaction.
     *
     * @param id the id of the plaidTransactionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plaid-transactions/{id}")
    public ResponseEntity<Void> deletePlaidTransaction(@PathVariable Long id) {
        log.debug("REST request to delete PlaidTransaction : {}", id);
        plaidTransactionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
