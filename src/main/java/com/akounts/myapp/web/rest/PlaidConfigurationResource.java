package com.akounts.myapp.web.rest;

import com.akounts.myapp.repository.PlaidConfigurationRepository;
import com.akounts.myapp.service.PlaidConfigurationService;
import com.akounts.myapp.service.dto.PlaidConfigurationDTO;
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
 * REST controller for managing {@link com.akounts.myapp.domain.PlaidConfiguration}.
 */
@RestController
@RequestMapping("/api")
public class PlaidConfigurationResource {

    private final Logger log = LoggerFactory.getLogger(PlaidConfigurationResource.class);

    private static final String ENTITY_NAME = "plaidConfiguration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlaidConfigurationService plaidConfigurationService;

    private final PlaidConfigurationRepository plaidConfigurationRepository;

    public PlaidConfigurationResource(
        PlaidConfigurationService plaidConfigurationService,
        PlaidConfigurationRepository plaidConfigurationRepository
    ) {
        this.plaidConfigurationService = plaidConfigurationService;
        this.plaidConfigurationRepository = plaidConfigurationRepository;
    }

    /**
     * {@code POST  /plaid-configurations} : Create a new plaidConfiguration.
     *
     * @param plaidConfigurationDTO the plaidConfigurationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new plaidConfigurationDTO, or with status {@code 400 (Bad Request)} if the plaidConfiguration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plaid-configurations")
    public ResponseEntity<PlaidConfigurationDTO> createPlaidConfiguration(@RequestBody PlaidConfigurationDTO plaidConfigurationDTO)
        throws URISyntaxException {
        log.debug("REST request to save PlaidConfiguration : {}", plaidConfigurationDTO);
        if (plaidConfigurationDTO.getId() != null) {
            throw new BadRequestAlertException("A new plaidConfiguration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlaidConfigurationDTO result = plaidConfigurationService.save(plaidConfigurationDTO);
        return ResponseEntity
            .created(new URI("/api/plaid-configurations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plaid-configurations/:id} : Updates an existing plaidConfiguration.
     *
     * @param id the id of the plaidConfigurationDTO to save.
     * @param plaidConfigurationDTO the plaidConfigurationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plaidConfigurationDTO,
     * or with status {@code 400 (Bad Request)} if the plaidConfigurationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the plaidConfigurationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plaid-configurations/{id}")
    public ResponseEntity<PlaidConfigurationDTO> updatePlaidConfiguration(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PlaidConfigurationDTO plaidConfigurationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PlaidConfiguration : {}, {}", id, plaidConfigurationDTO);
        if (plaidConfigurationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plaidConfigurationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plaidConfigurationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PlaidConfigurationDTO result = plaidConfigurationService.save(plaidConfigurationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plaidConfigurationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /plaid-configurations/:id} : Partial updates given fields of an existing plaidConfiguration, field will ignore if it is null
     *
     * @param id the id of the plaidConfigurationDTO to save.
     * @param plaidConfigurationDTO the plaidConfigurationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plaidConfigurationDTO,
     * or with status {@code 400 (Bad Request)} if the plaidConfigurationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the plaidConfigurationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the plaidConfigurationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/plaid-configurations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlaidConfigurationDTO> partialUpdatePlaidConfiguration(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PlaidConfigurationDTO plaidConfigurationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PlaidConfiguration partially : {}, {}", id, plaidConfigurationDTO);
        if (plaidConfigurationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plaidConfigurationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plaidConfigurationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PlaidConfigurationDTO> result = plaidConfigurationService.partialUpdate(plaidConfigurationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plaidConfigurationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /plaid-configurations} : get all the plaidConfigurations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plaidConfigurations in body.
     */
    @GetMapping("/plaid-configurations")
    public ResponseEntity<List<PlaidConfigurationDTO>> getAllPlaidConfigurations(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of PlaidConfigurations");
        Page<PlaidConfigurationDTO> page = plaidConfigurationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /plaid-configurations/:id} : get the "id" plaidConfiguration.
     *
     * @param id the id of the plaidConfigurationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the plaidConfigurationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plaid-configurations/{id}")
    public ResponseEntity<PlaidConfigurationDTO> getPlaidConfiguration(@PathVariable Long id) {
        log.debug("REST request to get PlaidConfiguration : {}", id);
        Optional<PlaidConfigurationDTO> plaidConfigurationDTO = plaidConfigurationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(plaidConfigurationDTO);
    }

    /**
     * {@code DELETE  /plaid-configurations/:id} : delete the "id" plaidConfiguration.
     *
     * @param id the id of the plaidConfigurationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plaid-configurations/{id}")
    public ResponseEntity<Void> deletePlaidConfiguration(@PathVariable Long id) {
        log.debug("REST request to delete PlaidConfiguration : {}", id);
        plaidConfigurationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
