package com.akounts.myapp.web.rest;

import com.akounts.myapp.repository.TriggerRunRepository;
import com.akounts.myapp.service.TriggerRunService;
import com.akounts.myapp.service.dto.TriggerRunDTO;
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
 * REST controller for managing {@link com.akounts.myapp.domain.TriggerRun}.
 */
@RestController
@RequestMapping("/api")
public class TriggerRunResource {

    private final Logger log = LoggerFactory.getLogger(TriggerRunResource.class);

    private static final String ENTITY_NAME = "triggerRun";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TriggerRunService triggerRunService;

    private final TriggerRunRepository triggerRunRepository;

    public TriggerRunResource(TriggerRunService triggerRunService, TriggerRunRepository triggerRunRepository) {
        this.triggerRunService = triggerRunService;
        this.triggerRunRepository = triggerRunRepository;
    }

    /**
     * {@code POST  /trigger-runs} : Create a new triggerRun.
     *
     * @param triggerRunDTO the triggerRunDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new triggerRunDTO, or with status {@code 400 (Bad Request)} if the triggerRun has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/trigger-runs")
    public ResponseEntity<TriggerRunDTO> createTriggerRun(@RequestBody TriggerRunDTO triggerRunDTO) throws URISyntaxException {
        log.debug("REST request to save TriggerRun : {}", triggerRunDTO);
        if (triggerRunDTO.getId() != null) {
            throw new BadRequestAlertException("A new triggerRun cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TriggerRunDTO result = triggerRunService.save(triggerRunDTO);
        return ResponseEntity
            .created(new URI("/api/trigger-runs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /trigger-runs/:id} : Updates an existing triggerRun.
     *
     * @param id the id of the triggerRunDTO to save.
     * @param triggerRunDTO the triggerRunDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated triggerRunDTO,
     * or with status {@code 400 (Bad Request)} if the triggerRunDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the triggerRunDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/trigger-runs/{id}")
    public ResponseEntity<TriggerRunDTO> updateTriggerRun(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TriggerRunDTO triggerRunDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TriggerRun : {}, {}", id, triggerRunDTO);
        if (triggerRunDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, triggerRunDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!triggerRunRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TriggerRunDTO result = triggerRunService.save(triggerRunDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, triggerRunDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /trigger-runs/:id} : Partial updates given fields of an existing triggerRun, field will ignore if it is null
     *
     * @param id the id of the triggerRunDTO to save.
     * @param triggerRunDTO the triggerRunDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated triggerRunDTO,
     * or with status {@code 400 (Bad Request)} if the triggerRunDTO is not valid,
     * or with status {@code 404 (Not Found)} if the triggerRunDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the triggerRunDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/trigger-runs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TriggerRunDTO> partialUpdateTriggerRun(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TriggerRunDTO triggerRunDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TriggerRun partially : {}, {}", id, triggerRunDTO);
        if (triggerRunDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, triggerRunDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!triggerRunRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TriggerRunDTO> result = triggerRunService.partialUpdate(triggerRunDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, triggerRunDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /trigger-runs} : get all the triggerRuns.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of triggerRuns in body.
     */
    @GetMapping("/trigger-runs")
    public ResponseEntity<List<TriggerRunDTO>> getAllTriggerRuns(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TriggerRuns");
        Page<TriggerRunDTO> page = triggerRunService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /trigger-runs/:id} : get the "id" triggerRun.
     *
     * @param id the id of the triggerRunDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the triggerRunDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/trigger-runs/{id}")
    public ResponseEntity<TriggerRunDTO> getTriggerRun(@PathVariable Long id) {
        log.debug("REST request to get TriggerRun : {}", id);
        Optional<TriggerRunDTO> triggerRunDTO = triggerRunService.findOne(id);
        return ResponseUtil.wrapOrNotFound(triggerRunDTO);
    }

    /**
     * {@code DELETE  /trigger-runs/:id} : delete the "id" triggerRun.
     *
     * @param id the id of the triggerRunDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/trigger-runs/{id}")
    public ResponseEntity<Void> deleteTriggerRun(@PathVariable Long id) {
        log.debug("REST request to delete TriggerRun : {}", id);
        triggerRunService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
