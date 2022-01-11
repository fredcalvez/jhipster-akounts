package com.akounts.myapp.web.rest;

import com.akounts.myapp.repository.RebaseHistoryRepository;
import com.akounts.myapp.service.RebaseHistoryService;
import com.akounts.myapp.service.dto.RebaseHistoryDTO;
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
 * REST controller for managing {@link com.akounts.myapp.domain.RebaseHistory}.
 */
@RestController
@RequestMapping("/api")
public class RebaseHistoryResource {

    private final Logger log = LoggerFactory.getLogger(RebaseHistoryResource.class);

    private static final String ENTITY_NAME = "rebaseHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RebaseHistoryService rebaseHistoryService;

    private final RebaseHistoryRepository rebaseHistoryRepository;

    public RebaseHistoryResource(RebaseHistoryService rebaseHistoryService, RebaseHistoryRepository rebaseHistoryRepository) {
        this.rebaseHistoryService = rebaseHistoryService;
        this.rebaseHistoryRepository = rebaseHistoryRepository;
    }

    /**
     * {@code POST  /rebase-histories} : Create a new rebaseHistory.
     *
     * @param rebaseHistoryDTO the rebaseHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rebaseHistoryDTO, or with status {@code 400 (Bad Request)} if the rebaseHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rebase-histories")
    public ResponseEntity<RebaseHistoryDTO> createRebaseHistory(@RequestBody RebaseHistoryDTO rebaseHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save RebaseHistory : {}", rebaseHistoryDTO);
        if (rebaseHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new rebaseHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RebaseHistoryDTO result = rebaseHistoryService.save(rebaseHistoryDTO);
        return ResponseEntity
            .created(new URI("/api/rebase-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rebase-histories/:id} : Updates an existing rebaseHistory.
     *
     * @param id the id of the rebaseHistoryDTO to save.
     * @param rebaseHistoryDTO the rebaseHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rebaseHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the rebaseHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rebaseHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rebase-histories/{id}")
    public ResponseEntity<RebaseHistoryDTO> updateRebaseHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RebaseHistoryDTO rebaseHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RebaseHistory : {}, {}", id, rebaseHistoryDTO);
        if (rebaseHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rebaseHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rebaseHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RebaseHistoryDTO result = rebaseHistoryService.save(rebaseHistoryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rebaseHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rebase-histories/:id} : Partial updates given fields of an existing rebaseHistory, field will ignore if it is null
     *
     * @param id the id of the rebaseHistoryDTO to save.
     * @param rebaseHistoryDTO the rebaseHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rebaseHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the rebaseHistoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the rebaseHistoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the rebaseHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rebase-histories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RebaseHistoryDTO> partialUpdateRebaseHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RebaseHistoryDTO rebaseHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RebaseHistory partially : {}, {}", id, rebaseHistoryDTO);
        if (rebaseHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rebaseHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rebaseHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RebaseHistoryDTO> result = rebaseHistoryService.partialUpdate(rebaseHistoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rebaseHistoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /rebase-histories} : get all the rebaseHistories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rebaseHistories in body.
     */
    @GetMapping("/rebase-histories")
    public ResponseEntity<List<RebaseHistoryDTO>> getAllRebaseHistories(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of RebaseHistories");
        Page<RebaseHistoryDTO> page = rebaseHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rebase-histories/:id} : get the "id" rebaseHistory.
     *
     * @param id the id of the rebaseHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rebaseHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rebase-histories/{id}")
    public ResponseEntity<RebaseHistoryDTO> getRebaseHistory(@PathVariable Long id) {
        log.debug("REST request to get RebaseHistory : {}", id);
        Optional<RebaseHistoryDTO> rebaseHistoryDTO = rebaseHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rebaseHistoryDTO);
    }

    /**
     * {@code DELETE  /rebase-histories/:id} : delete the "id" rebaseHistory.
     *
     * @param id the id of the rebaseHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rebase-histories/{id}")
    public ResponseEntity<Void> deleteRebaseHistory(@PathVariable Long id) {
        log.debug("REST request to delete RebaseHistory : {}", id);
        rebaseHistoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
