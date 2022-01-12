package com.akounts.myapp.web.rest;

import com.akounts.myapp.repository.BankTransaction2Repository;
import com.akounts.myapp.service.BankTransaction2Service;
import com.akounts.myapp.service.dto.BankTransaction2DTO;
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
 * REST controller for managing {@link com.akounts.myapp.domain.BankTransaction2}.
 */
@RestController
@RequestMapping("/api")
public class BankTransaction2Resource {

    private final Logger log = LoggerFactory.getLogger(BankTransaction2Resource.class);

    private static final String ENTITY_NAME = "bankTransaction2";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankTransaction2Service bankTransaction2Service;

    private final BankTransaction2Repository bankTransaction2Repository;

    public BankTransaction2Resource(
        BankTransaction2Service bankTransaction2Service,
        BankTransaction2Repository bankTransaction2Repository
    ) {
        this.bankTransaction2Service = bankTransaction2Service;
        this.bankTransaction2Repository = bankTransaction2Repository;
    }

    /**
     * {@code POST  /bank-transaction-2-s} : Create a new bankTransaction2.
     *
     * @param bankTransaction2DTO the bankTransaction2DTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankTransaction2DTO, or with status {@code 400 (Bad Request)} if the bankTransaction2 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bank-transaction-2-s")
    public ResponseEntity<BankTransaction2DTO> createBankTransaction2(@RequestBody BankTransaction2DTO bankTransaction2DTO)
        throws URISyntaxException {
        log.debug("REST request to save BankTransaction2 : {}", bankTransaction2DTO);
        if (bankTransaction2DTO.getId() != null) {
            throw new BadRequestAlertException("A new bankTransaction2 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankTransaction2DTO result = bankTransaction2Service.save(bankTransaction2DTO);
        return ResponseEntity
            .created(new URI("/api/bank-transaction-2-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bank-transaction-2-s/:id} : Updates an existing bankTransaction2.
     *
     * @param id the id of the bankTransaction2DTO to save.
     * @param bankTransaction2DTO the bankTransaction2DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankTransaction2DTO,
     * or with status {@code 400 (Bad Request)} if the bankTransaction2DTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankTransaction2DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bank-transaction-2-s/{id}")
    public ResponseEntity<BankTransaction2DTO> updateBankTransaction2(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankTransaction2DTO bankTransaction2DTO
    ) throws URISyntaxException {
        log.debug("REST request to update BankTransaction2 : {}, {}", id, bankTransaction2DTO);
        if (bankTransaction2DTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankTransaction2DTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankTransaction2Repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BankTransaction2DTO result = bankTransaction2Service.save(bankTransaction2DTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankTransaction2DTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bank-transaction-2-s/:id} : Partial updates given fields of an existing bankTransaction2, field will ignore if it is null
     *
     * @param id the id of the bankTransaction2DTO to save.
     * @param bankTransaction2DTO the bankTransaction2DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankTransaction2DTO,
     * or with status {@code 400 (Bad Request)} if the bankTransaction2DTO is not valid,
     * or with status {@code 404 (Not Found)} if the bankTransaction2DTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bankTransaction2DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bank-transaction-2-s/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BankTransaction2DTO> partialUpdateBankTransaction2(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankTransaction2DTO bankTransaction2DTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BankTransaction2 partially : {}, {}", id, bankTransaction2DTO);
        if (bankTransaction2DTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankTransaction2DTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankTransaction2Repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BankTransaction2DTO> result = bankTransaction2Service.partialUpdate(bankTransaction2DTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankTransaction2DTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bank-transaction-2-s} : get all the bankTransaction2s.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankTransaction2s in body.
     */
    @GetMapping("/bank-transaction-2-s")
    public ResponseEntity<List<BankTransaction2DTO>> getAllBankTransaction2s(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of BankTransaction2s");
        Page<BankTransaction2DTO> page = bankTransaction2Service.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bank-transaction-2-s/:id} : get the "id" bankTransaction2.
     *
     * @param id the id of the bankTransaction2DTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankTransaction2DTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bank-transaction-2-s/{id}")
    public ResponseEntity<BankTransaction2DTO> getBankTransaction2(@PathVariable Long id) {
        log.debug("REST request to get BankTransaction2 : {}", id);
        Optional<BankTransaction2DTO> bankTransaction2DTO = bankTransaction2Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(bankTransaction2DTO);
    }

    /**
     * {@code DELETE  /bank-transaction-2-s/:id} : delete the "id" bankTransaction2.
     *
     * @param id the id of the bankTransaction2DTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bank-transaction-2-s/{id}")
    public ResponseEntity<Void> deleteBankTransaction2(@PathVariable Long id) {
        log.debug("REST request to delete BankTransaction2 : {}", id);
        bankTransaction2Service.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
