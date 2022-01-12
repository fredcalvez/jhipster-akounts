package com.akounts.myapp.web.rest;

import com.akounts.myapp.repository.ProcessRunRepository;
import com.akounts.myapp.service.ProcessRunService;
import com.akounts.myapp.service.dto.ProcessRunDTO;
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
 * REST controller for managing {@link com.akounts.myapp.domain.ProcessRun}.
 */
@RestController
@RequestMapping("/api")
public class ProcessRunResource {

    private final Logger log = LoggerFactory.getLogger(ProcessRunResource.class);

    private static final String ENTITY_NAME = "processRun";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcessRunService processRunService;

    private final ProcessRunRepository processRunRepository;

    public ProcessRunResource(ProcessRunService processRunService, ProcessRunRepository processRunRepository) {
        this.processRunService = processRunService;
        this.processRunRepository = processRunRepository;
    }

    /**
     * {@code POST  /process-runs} : Create a new processRun.
     *
     * @param processRunDTO the processRunDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new processRunDTO, or with status {@code 400 (Bad Request)} if the processRun has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/process-runs")
    public ResponseEntity<ProcessRunDTO> createProcessRun(@RequestBody ProcessRunDTO processRunDTO) throws URISyntaxException {
        log.debug("REST request to save ProcessRun : {}", processRunDTO);
        if (processRunDTO.getId() != null) {
            throw new BadRequestAlertException("A new processRun cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProcessRunDTO result = processRunService.save(processRunDTO);
        return ResponseEntity
            .created(new URI("/api/process-runs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /process-runs/:id} : Updates an existing processRun.
     *
     * @param id the id of the processRunDTO to save.
     * @param processRunDTO the processRunDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processRunDTO,
     * or with status {@code 400 (Bad Request)} if the processRunDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the processRunDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/process-runs/{id}")
    public ResponseEntity<ProcessRunDTO> updateProcessRun(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProcessRunDTO processRunDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProcessRun : {}, {}", id, processRunDTO);
        if (processRunDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, processRunDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!processRunRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProcessRunDTO result = processRunService.save(processRunDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, processRunDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /process-runs/:id} : Partial updates given fields of an existing processRun, field will ignore if it is null
     *
     * @param id the id of the processRunDTO to save.
     * @param processRunDTO the processRunDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processRunDTO,
     * or with status {@code 400 (Bad Request)} if the processRunDTO is not valid,
     * or with status {@code 404 (Not Found)} if the processRunDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the processRunDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/process-runs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProcessRunDTO> partialUpdateProcessRun(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProcessRunDTO processRunDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProcessRun partially : {}, {}", id, processRunDTO);
        if (processRunDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, processRunDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!processRunRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProcessRunDTO> result = processRunService.partialUpdate(processRunDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, processRunDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /process-runs} : get all the processRuns.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of processRuns in body.
     */
    @GetMapping("/process-runs")
    public ResponseEntity<List<ProcessRunDTO>> getAllProcessRuns(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ProcessRuns");
        Page<ProcessRunDTO> page = processRunService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /process-runs/:id} : get the "id" processRun.
     *
     * @param id the id of the processRunDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the processRunDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/process-runs/{id}")
    public ResponseEntity<ProcessRunDTO> getProcessRun(@PathVariable Long id) {
        log.debug("REST request to get ProcessRun : {}", id);
        Optional<ProcessRunDTO> processRunDTO = processRunService.findOne(id);
        return ResponseUtil.wrapOrNotFound(processRunDTO);
    }

    /**
     * {@code DELETE  /process-runs/:id} : delete the "id" processRun.
     *
     * @param id the id of the processRunDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/process-runs/{id}")
    public ResponseEntity<Void> deleteProcessRun(@PathVariable Long id) {
        log.debug("REST request to delete ProcessRun : {}", id);
        processRunService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
