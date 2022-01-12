package com.akounts.myapp.web.rest;

import com.akounts.myapp.repository.PlaidRunRepository;
import com.akounts.myapp.service.PlaidRunService;
import com.akounts.myapp.service.dto.PlaidRunDTO;
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
 * REST controller for managing {@link com.akounts.myapp.domain.PlaidRun}.
 */
@RestController
@RequestMapping("/api")
public class PlaidRunResource {

    private final Logger log = LoggerFactory.getLogger(PlaidRunResource.class);

    private static final String ENTITY_NAME = "plaidRun";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlaidRunService plaidRunService;

    private final PlaidRunRepository plaidRunRepository;

    public PlaidRunResource(PlaidRunService plaidRunService, PlaidRunRepository plaidRunRepository) {
        this.plaidRunService = plaidRunService;
        this.plaidRunRepository = plaidRunRepository;
    }

    /**
     * {@code POST  /plaid-runs} : Create a new plaidRun.
     *
     * @param plaidRunDTO the plaidRunDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new plaidRunDTO, or with status {@code 400 (Bad Request)} if the plaidRun has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plaid-runs")
    public ResponseEntity<PlaidRunDTO> createPlaidRun(@RequestBody PlaidRunDTO plaidRunDTO) throws URISyntaxException {
        log.debug("REST request to save PlaidRun : {}", plaidRunDTO);
        if (plaidRunDTO.getId() != null) {
            throw new BadRequestAlertException("A new plaidRun cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlaidRunDTO result = plaidRunService.save(plaidRunDTO);
        return ResponseEntity
            .created(new URI("/api/plaid-runs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plaid-runs/:id} : Updates an existing plaidRun.
     *
     * @param id the id of the plaidRunDTO to save.
     * @param plaidRunDTO the plaidRunDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plaidRunDTO,
     * or with status {@code 400 (Bad Request)} if the plaidRunDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the plaidRunDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plaid-runs/{id}")
    public ResponseEntity<PlaidRunDTO> updatePlaidRun(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PlaidRunDTO plaidRunDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PlaidRun : {}, {}", id, plaidRunDTO);
        if (plaidRunDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plaidRunDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plaidRunRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PlaidRunDTO result = plaidRunService.save(plaidRunDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plaidRunDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /plaid-runs/:id} : Partial updates given fields of an existing plaidRun, field will ignore if it is null
     *
     * @param id the id of the plaidRunDTO to save.
     * @param plaidRunDTO the plaidRunDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plaidRunDTO,
     * or with status {@code 400 (Bad Request)} if the plaidRunDTO is not valid,
     * or with status {@code 404 (Not Found)} if the plaidRunDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the plaidRunDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/plaid-runs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlaidRunDTO> partialUpdatePlaidRun(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PlaidRunDTO plaidRunDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PlaidRun partially : {}, {}", id, plaidRunDTO);
        if (plaidRunDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plaidRunDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plaidRunRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PlaidRunDTO> result = plaidRunService.partialUpdate(plaidRunDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plaidRunDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /plaid-runs} : get all the plaidRuns.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plaidRuns in body.
     */
    @GetMapping("/plaid-runs")
    public ResponseEntity<List<PlaidRunDTO>> getAllPlaidRuns(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of PlaidRuns");
        Page<PlaidRunDTO> page = plaidRunService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /plaid-runs/:id} : get the "id" plaidRun.
     *
     * @param id the id of the plaidRunDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the plaidRunDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plaid-runs/{id}")
    public ResponseEntity<PlaidRunDTO> getPlaidRun(@PathVariable Long id) {
        log.debug("REST request to get PlaidRun : {}", id);
        Optional<PlaidRunDTO> plaidRunDTO = plaidRunService.findOne(id);
        return ResponseUtil.wrapOrNotFound(plaidRunDTO);
    }

    /**
     * {@code DELETE  /plaid-runs/:id} : delete the "id" plaidRun.
     *
     * @param id the id of the plaidRunDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plaid-runs/{id}")
    public ResponseEntity<Void> deletePlaidRun(@PathVariable Long id) {
        log.debug("REST request to delete PlaidRun : {}", id);
        plaidRunService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
