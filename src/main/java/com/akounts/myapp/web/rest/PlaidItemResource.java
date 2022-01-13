package com.akounts.myapp.web.rest;

import com.akounts.myapp.repository.PlaidItemRepository;
import com.akounts.myapp.service.PlaidItemService;
import com.akounts.myapp.service.dto.PlaidItemDTO;
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
 * REST controller for managing {@link com.akounts.myapp.domain.PlaidItem}.
 */
@RestController
@RequestMapping("/api")
public class PlaidItemResource {

    private final Logger log = LoggerFactory.getLogger(PlaidItemResource.class);

    private static final String ENTITY_NAME = "plaidItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlaidItemService plaidItemService;

    private final PlaidItemRepository plaidItemRepository;

    public PlaidItemResource(PlaidItemService plaidItemService, PlaidItemRepository plaidItemRepository) {
        this.plaidItemService = plaidItemService;
        this.plaidItemRepository = plaidItemRepository;
    }

    /**
     * {@code POST  /plaid-items} : Create a new plaidItem.
     *
     * @param plaidItemDTO the plaidItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new plaidItemDTO, or with status {@code 400 (Bad Request)} if the plaidItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plaid-items")
    public ResponseEntity<PlaidItemDTO> createPlaidItem(@RequestBody PlaidItemDTO plaidItemDTO) throws URISyntaxException {
        log.debug("REST request to save PlaidItem : {}", plaidItemDTO);
        if (plaidItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new plaidItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlaidItemDTO result = plaidItemService.save(plaidItemDTO);
        return ResponseEntity
            .created(new URI("/api/plaid-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plaid-items/:id} : Updates an existing plaidItem.
     *
     * @param id the id of the plaidItemDTO to save.
     * @param plaidItemDTO the plaidItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plaidItemDTO,
     * or with status {@code 400 (Bad Request)} if the plaidItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the plaidItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plaid-items/{id}")
    public ResponseEntity<PlaidItemDTO> updatePlaidItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PlaidItemDTO plaidItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PlaidItem : {}, {}", id, plaidItemDTO);
        if (plaidItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plaidItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plaidItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PlaidItemDTO result = plaidItemService.save(plaidItemDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plaidItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /plaid-items/:id} : Partial updates given fields of an existing plaidItem, field will ignore if it is null
     *
     * @param id the id of the plaidItemDTO to save.
     * @param plaidItemDTO the plaidItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plaidItemDTO,
     * or with status {@code 400 (Bad Request)} if the plaidItemDTO is not valid,
     * or with status {@code 404 (Not Found)} if the plaidItemDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the plaidItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/plaid-items/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlaidItemDTO> partialUpdatePlaidItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PlaidItemDTO plaidItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PlaidItem partially : {}, {}", id, plaidItemDTO);
        if (plaidItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plaidItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plaidItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PlaidItemDTO> result = plaidItemService.partialUpdate(plaidItemDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plaidItemDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /plaid-items} : get all the plaidItems.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plaidItems in body.
     */
    @GetMapping("/plaid-items")
    public ResponseEntity<List<PlaidItemDTO>> getAllPlaidItems(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of PlaidItems");
        Page<PlaidItemDTO> page = plaidItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /plaid-items/:id} : get the "id" plaidItem.
     *
     * @param id the id of the plaidItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the plaidItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plaid-items/{id}")
    public ResponseEntity<PlaidItemDTO> getPlaidItem(@PathVariable Long id) {
        log.debug("REST request to get PlaidItem : {}", id);
        Optional<PlaidItemDTO> plaidItemDTO = plaidItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(plaidItemDTO);
    }

    /**
     * {@code DELETE  /plaid-items/:id} : delete the "id" plaidItem.
     *
     * @param id the id of the plaidItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plaid-items/{id}")
    public ResponseEntity<Void> deletePlaidItem(@PathVariable Long id) {
        log.debug("REST request to delete PlaidItem : {}", id);
        plaidItemService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
