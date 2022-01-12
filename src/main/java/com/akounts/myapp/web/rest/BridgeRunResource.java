package com.akounts.myapp.web.rest;

import com.akounts.myapp.repository.BridgeRunRepository;
import com.akounts.myapp.service.BridgeRunService;
import com.akounts.myapp.service.dto.BridgeRunDTO;
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
 * REST controller for managing {@link com.akounts.myapp.domain.BridgeRun}.
 */
@RestController
@RequestMapping("/api")
public class BridgeRunResource {

    private final Logger log = LoggerFactory.getLogger(BridgeRunResource.class);

    private static final String ENTITY_NAME = "bridgeRun";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BridgeRunService bridgeRunService;

    private final BridgeRunRepository bridgeRunRepository;

    public BridgeRunResource(BridgeRunService bridgeRunService, BridgeRunRepository bridgeRunRepository) {
        this.bridgeRunService = bridgeRunService;
        this.bridgeRunRepository = bridgeRunRepository;
    }

    /**
     * {@code POST  /bridge-runs} : Create a new bridgeRun.
     *
     * @param bridgeRunDTO the bridgeRunDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bridgeRunDTO, or with status {@code 400 (Bad Request)} if the bridgeRun has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bridge-runs")
    public ResponseEntity<BridgeRunDTO> createBridgeRun(@RequestBody BridgeRunDTO bridgeRunDTO) throws URISyntaxException {
        log.debug("REST request to save BridgeRun : {}", bridgeRunDTO);
        if (bridgeRunDTO.getId() != null) {
            throw new BadRequestAlertException("A new bridgeRun cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BridgeRunDTO result = bridgeRunService.save(bridgeRunDTO);
        return ResponseEntity
            .created(new URI("/api/bridge-runs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bridge-runs/:id} : Updates an existing bridgeRun.
     *
     * @param id the id of the bridgeRunDTO to save.
     * @param bridgeRunDTO the bridgeRunDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bridgeRunDTO,
     * or with status {@code 400 (Bad Request)} if the bridgeRunDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bridgeRunDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bridge-runs/{id}")
    public ResponseEntity<BridgeRunDTO> updateBridgeRun(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BridgeRunDTO bridgeRunDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BridgeRun : {}, {}", id, bridgeRunDTO);
        if (bridgeRunDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bridgeRunDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bridgeRunRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BridgeRunDTO result = bridgeRunService.save(bridgeRunDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bridgeRunDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bridge-runs/:id} : Partial updates given fields of an existing bridgeRun, field will ignore if it is null
     *
     * @param id the id of the bridgeRunDTO to save.
     * @param bridgeRunDTO the bridgeRunDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bridgeRunDTO,
     * or with status {@code 400 (Bad Request)} if the bridgeRunDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bridgeRunDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bridgeRunDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bridge-runs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BridgeRunDTO> partialUpdateBridgeRun(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BridgeRunDTO bridgeRunDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BridgeRun partially : {}, {}", id, bridgeRunDTO);
        if (bridgeRunDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bridgeRunDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bridgeRunRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BridgeRunDTO> result = bridgeRunService.partialUpdate(bridgeRunDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bridgeRunDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bridge-runs} : get all the bridgeRuns.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bridgeRuns in body.
     */
    @GetMapping("/bridge-runs")
    public ResponseEntity<List<BridgeRunDTO>> getAllBridgeRuns(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of BridgeRuns");
        Page<BridgeRunDTO> page = bridgeRunService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bridge-runs/:id} : get the "id" bridgeRun.
     *
     * @param id the id of the bridgeRunDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bridgeRunDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bridge-runs/{id}")
    public ResponseEntity<BridgeRunDTO> getBridgeRun(@PathVariable Long id) {
        log.debug("REST request to get BridgeRun : {}", id);
        Optional<BridgeRunDTO> bridgeRunDTO = bridgeRunService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bridgeRunDTO);
    }

    /**
     * {@code DELETE  /bridge-runs/:id} : delete the "id" bridgeRun.
     *
     * @param id the id of the bridgeRunDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bridge-runs/{id}")
    public ResponseEntity<Void> deleteBridgeRun(@PathVariable Long id) {
        log.debug("REST request to delete BridgeRun : {}", id);
        bridgeRunService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
