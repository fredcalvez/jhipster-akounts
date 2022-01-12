package com.akounts.myapp.web.rest;

import com.akounts.myapp.repository.PlaidRepository;
import com.akounts.myapp.service.PlaidService;
import com.akounts.myapp.service.dto.PlaidDTO;
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
 * REST controller for managing {@link com.akounts.myapp.domain.Plaid}.
 */
@RestController
@RequestMapping("/api")
public class PlaidResource {

    private final Logger log = LoggerFactory.getLogger(PlaidResource.class);

    private static final String ENTITY_NAME = "plaid";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlaidService plaidService;

    private final PlaidRepository plaidRepository;

    public PlaidResource(PlaidService plaidService, PlaidRepository plaidRepository) {
        this.plaidService = plaidService;
        this.plaidRepository = plaidRepository;
    }

    /**
     * {@code POST  /plaids} : Create a new plaid.
     *
     * @param plaidDTO the plaidDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new plaidDTO, or with status {@code 400 (Bad Request)} if the plaid has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plaids")
    public ResponseEntity<PlaidDTO> createPlaid(@RequestBody PlaidDTO plaidDTO) throws URISyntaxException {
        log.debug("REST request to save Plaid : {}", plaidDTO);
        if (plaidDTO.getId() != null) {
            throw new BadRequestAlertException("A new plaid cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlaidDTO result = plaidService.save(plaidDTO);
        return ResponseEntity
            .created(new URI("/api/plaids/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plaids/:id} : Updates an existing plaid.
     *
     * @param id the id of the plaidDTO to save.
     * @param plaidDTO the plaidDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plaidDTO,
     * or with status {@code 400 (Bad Request)} if the plaidDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the plaidDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plaids/{id}")
    public ResponseEntity<PlaidDTO> updatePlaid(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PlaidDTO plaidDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Plaid : {}, {}", id, plaidDTO);
        if (plaidDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plaidDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plaidRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PlaidDTO result = plaidService.save(plaidDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plaidDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /plaids/:id} : Partial updates given fields of an existing plaid, field will ignore if it is null
     *
     * @param id the id of the plaidDTO to save.
     * @param plaidDTO the plaidDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plaidDTO,
     * or with status {@code 400 (Bad Request)} if the plaidDTO is not valid,
     * or with status {@code 404 (Not Found)} if the plaidDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the plaidDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/plaids/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlaidDTO> partialUpdatePlaid(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PlaidDTO plaidDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Plaid partially : {}, {}", id, plaidDTO);
        if (plaidDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plaidDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plaidRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PlaidDTO> result = plaidService.partialUpdate(plaidDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plaidDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /plaids} : get all the plaids.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plaids in body.
     */
    @GetMapping("/plaids")
    public ResponseEntity<List<PlaidDTO>> getAllPlaids(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Plaids");
        Page<PlaidDTO> page = plaidService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /plaids/:id} : get the "id" plaid.
     *
     * @param id the id of the plaidDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the plaidDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plaids/{id}")
    public ResponseEntity<PlaidDTO> getPlaid(@PathVariable Long id) {
        log.debug("REST request to get Plaid : {}", id);
        Optional<PlaidDTO> plaidDTO = plaidService.findOne(id);
        return ResponseUtil.wrapOrNotFound(plaidDTO);
    }

    /**
     * {@code DELETE  /plaids/:id} : delete the "id" plaid.
     *
     * @param id the id of the plaidDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plaids/{id}")
    public ResponseEntity<Void> deletePlaid(@PathVariable Long id) {
        log.debug("REST request to delete Plaid : {}", id);
        plaidService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
