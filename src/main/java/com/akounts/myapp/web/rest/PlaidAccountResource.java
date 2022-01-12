package com.akounts.myapp.web.rest;

import com.akounts.myapp.repository.PlaidAccountRepository;
import com.akounts.myapp.service.PlaidAccountService;
import com.akounts.myapp.service.dto.PlaidAccountDTO;
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
 * REST controller for managing {@link com.akounts.myapp.domain.PlaidAccount}.
 */
@RestController
@RequestMapping("/api")
public class PlaidAccountResource {

    private final Logger log = LoggerFactory.getLogger(PlaidAccountResource.class);

    private static final String ENTITY_NAME = "plaidAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlaidAccountService plaidAccountService;

    private final PlaidAccountRepository plaidAccountRepository;

    public PlaidAccountResource(PlaidAccountService plaidAccountService, PlaidAccountRepository plaidAccountRepository) {
        this.plaidAccountService = plaidAccountService;
        this.plaidAccountRepository = plaidAccountRepository;
    }

    /**
     * {@code POST  /plaid-accounts} : Create a new plaidAccount.
     *
     * @param plaidAccountDTO the plaidAccountDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new plaidAccountDTO, or with status {@code 400 (Bad Request)} if the plaidAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plaid-accounts")
    public ResponseEntity<PlaidAccountDTO> createPlaidAccount(@RequestBody PlaidAccountDTO plaidAccountDTO) throws URISyntaxException {
        log.debug("REST request to save PlaidAccount : {}", plaidAccountDTO);
        if (plaidAccountDTO.getId() != null) {
            throw new BadRequestAlertException("A new plaidAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlaidAccountDTO result = plaidAccountService.save(plaidAccountDTO);
        return ResponseEntity
            .created(new URI("/api/plaid-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plaid-accounts/:id} : Updates an existing plaidAccount.
     *
     * @param id the id of the plaidAccountDTO to save.
     * @param plaidAccountDTO the plaidAccountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plaidAccountDTO,
     * or with status {@code 400 (Bad Request)} if the plaidAccountDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the plaidAccountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plaid-accounts/{id}")
    public ResponseEntity<PlaidAccountDTO> updatePlaidAccount(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PlaidAccountDTO plaidAccountDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PlaidAccount : {}, {}", id, plaidAccountDTO);
        if (plaidAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plaidAccountDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plaidAccountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PlaidAccountDTO result = plaidAccountService.save(plaidAccountDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plaidAccountDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /plaid-accounts/:id} : Partial updates given fields of an existing plaidAccount, field will ignore if it is null
     *
     * @param id the id of the plaidAccountDTO to save.
     * @param plaidAccountDTO the plaidAccountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plaidAccountDTO,
     * or with status {@code 400 (Bad Request)} if the plaidAccountDTO is not valid,
     * or with status {@code 404 (Not Found)} if the plaidAccountDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the plaidAccountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/plaid-accounts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlaidAccountDTO> partialUpdatePlaidAccount(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PlaidAccountDTO plaidAccountDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PlaidAccount partially : {}, {}", id, plaidAccountDTO);
        if (plaidAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plaidAccountDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plaidAccountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PlaidAccountDTO> result = plaidAccountService.partialUpdate(plaidAccountDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plaidAccountDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /plaid-accounts} : get all the plaidAccounts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plaidAccounts in body.
     */
    @GetMapping("/plaid-accounts")
    public ResponseEntity<List<PlaidAccountDTO>> getAllPlaidAccounts(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of PlaidAccounts");
        Page<PlaidAccountDTO> page = plaidAccountService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /plaid-accounts/:id} : get the "id" plaidAccount.
     *
     * @param id the id of the plaidAccountDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the plaidAccountDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plaid-accounts/{id}")
    public ResponseEntity<PlaidAccountDTO> getPlaidAccount(@PathVariable Long id) {
        log.debug("REST request to get PlaidAccount : {}", id);
        Optional<PlaidAccountDTO> plaidAccountDTO = plaidAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(plaidAccountDTO);
    }

    /**
     * {@code DELETE  /plaid-accounts/:id} : delete the "id" plaidAccount.
     *
     * @param id the id of the plaidAccountDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plaid-accounts/{id}")
    public ResponseEntity<Void> deletePlaidAccount(@PathVariable Long id) {
        log.debug("REST request to delete PlaidAccount : {}", id);
        plaidAccountService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
