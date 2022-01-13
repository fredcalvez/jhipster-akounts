package com.akounts.myapp.web.rest;

import com.akounts.myapp.repository.TransactionDuplicatesRepository;
import com.akounts.myapp.service.TransactionDuplicatesService;
import com.akounts.myapp.service.dto.TransactionDuplicatesDTO;
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
 * REST controller for managing {@link com.akounts.myapp.domain.TransactionDuplicates}.
 */
@RestController
@RequestMapping("/api")
public class TransactionDuplicatesResource {

    private final Logger log = LoggerFactory.getLogger(TransactionDuplicatesResource.class);

    private static final String ENTITY_NAME = "transactionDuplicates";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransactionDuplicatesService transactionDuplicatesService;

    private final TransactionDuplicatesRepository transactionDuplicatesRepository;

    public TransactionDuplicatesResource(
        TransactionDuplicatesService transactionDuplicatesService,
        TransactionDuplicatesRepository transactionDuplicatesRepository
    ) {
        this.transactionDuplicatesService = transactionDuplicatesService;
        this.transactionDuplicatesRepository = transactionDuplicatesRepository;
    }

    /**
     * {@code POST  /transaction-duplicates} : Create a new transactionDuplicates.
     *
     * @param transactionDuplicatesDTO the transactionDuplicatesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transactionDuplicatesDTO, or with status {@code 400 (Bad Request)} if the transactionDuplicates has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transaction-duplicates")
    public ResponseEntity<TransactionDuplicatesDTO> createTransactionDuplicates(
        @RequestBody TransactionDuplicatesDTO transactionDuplicatesDTO
    ) throws URISyntaxException {
        log.debug("REST request to save TransactionDuplicates : {}", transactionDuplicatesDTO);
        if (transactionDuplicatesDTO.getId() != null) {
            throw new BadRequestAlertException("A new transactionDuplicates cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransactionDuplicatesDTO result = transactionDuplicatesService.save(transactionDuplicatesDTO);
        return ResponseEntity
            .created(new URI("/api/transaction-duplicates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transaction-duplicates/:id} : Updates an existing transactionDuplicates.
     *
     * @param id the id of the transactionDuplicatesDTO to save.
     * @param transactionDuplicatesDTO the transactionDuplicatesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactionDuplicatesDTO,
     * or with status {@code 400 (Bad Request)} if the transactionDuplicatesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transactionDuplicatesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transaction-duplicates/{id}")
    public ResponseEntity<TransactionDuplicatesDTO> updateTransactionDuplicates(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TransactionDuplicatesDTO transactionDuplicatesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TransactionDuplicates : {}, {}", id, transactionDuplicatesDTO);
        if (transactionDuplicatesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transactionDuplicatesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transactionDuplicatesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TransactionDuplicatesDTO result = transactionDuplicatesService.save(transactionDuplicatesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transactionDuplicatesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /transaction-duplicates/:id} : Partial updates given fields of an existing transactionDuplicates, field will ignore if it is null
     *
     * @param id the id of the transactionDuplicatesDTO to save.
     * @param transactionDuplicatesDTO the transactionDuplicatesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactionDuplicatesDTO,
     * or with status {@code 400 (Bad Request)} if the transactionDuplicatesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the transactionDuplicatesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the transactionDuplicatesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/transaction-duplicates/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TransactionDuplicatesDTO> partialUpdateTransactionDuplicates(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TransactionDuplicatesDTO transactionDuplicatesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TransactionDuplicates partially : {}, {}", id, transactionDuplicatesDTO);
        if (transactionDuplicatesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transactionDuplicatesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transactionDuplicatesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TransactionDuplicatesDTO> result = transactionDuplicatesService.partialUpdate(transactionDuplicatesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transactionDuplicatesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /transaction-duplicates} : get all the transactionDuplicates.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactionDuplicates in body.
     */
    @GetMapping("/transaction-duplicates")
    public ResponseEntity<List<TransactionDuplicatesDTO>> getAllTransactionDuplicates(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of TransactionDuplicates");
        Page<TransactionDuplicatesDTO> page = transactionDuplicatesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /transaction-duplicates/:id} : get the "id" transactionDuplicates.
     *
     * @param id the id of the transactionDuplicatesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transactionDuplicatesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transaction-duplicates/{id}")
    public ResponseEntity<TransactionDuplicatesDTO> getTransactionDuplicates(@PathVariable Long id) {
        log.debug("REST request to get TransactionDuplicates : {}", id);
        Optional<TransactionDuplicatesDTO> transactionDuplicatesDTO = transactionDuplicatesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transactionDuplicatesDTO);
    }

    /**
     * {@code DELETE  /transaction-duplicates/:id} : delete the "id" transactionDuplicates.
     *
     * @param id the id of the transactionDuplicatesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transaction-duplicates/{id}")
    public ResponseEntity<Void> deleteTransactionDuplicates(@PathVariable Long id) {
        log.debug("REST request to delete TransactionDuplicates : {}", id);
        transactionDuplicatesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
