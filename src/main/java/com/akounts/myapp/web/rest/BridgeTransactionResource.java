package com.akounts.myapp.web.rest;

import com.akounts.myapp.repository.BridgeTransactionRepository;
import com.akounts.myapp.service.BridgeTransactionService;
import com.akounts.myapp.service.dto.BridgeTransactionDTO;
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
 * REST controller for managing {@link com.akounts.myapp.domain.BridgeTransaction}.
 */
@RestController
@RequestMapping("/api")
public class BridgeTransactionResource {

    private final Logger log = LoggerFactory.getLogger(BridgeTransactionResource.class);

    private static final String ENTITY_NAME = "bridgeTransaction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BridgeTransactionService bridgeTransactionService;

    private final BridgeTransactionRepository bridgeTransactionRepository;

    public BridgeTransactionResource(
        BridgeTransactionService bridgeTransactionService,
        BridgeTransactionRepository bridgeTransactionRepository
    ) {
        this.bridgeTransactionService = bridgeTransactionService;
        this.bridgeTransactionRepository = bridgeTransactionRepository;
    }

    /**
     * {@code POST  /bridge-transactions} : Create a new bridgeTransaction.
     *
     * @param bridgeTransactionDTO the bridgeTransactionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bridgeTransactionDTO, or with status {@code 400 (Bad Request)} if the bridgeTransaction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bridge-transactions")
    public ResponseEntity<BridgeTransactionDTO> createBridgeTransaction(@RequestBody BridgeTransactionDTO bridgeTransactionDTO)
        throws URISyntaxException {
        log.debug("REST request to save BridgeTransaction : {}", bridgeTransactionDTO);
        if (bridgeTransactionDTO.getId() != null) {
            throw new BadRequestAlertException("A new bridgeTransaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BridgeTransactionDTO result = bridgeTransactionService.save(bridgeTransactionDTO);
        return ResponseEntity
            .created(new URI("/api/bridge-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bridge-transactions/:id} : Updates an existing bridgeTransaction.
     *
     * @param id the id of the bridgeTransactionDTO to save.
     * @param bridgeTransactionDTO the bridgeTransactionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bridgeTransactionDTO,
     * or with status {@code 400 (Bad Request)} if the bridgeTransactionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bridgeTransactionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bridge-transactions/{id}")
    public ResponseEntity<BridgeTransactionDTO> updateBridgeTransaction(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BridgeTransactionDTO bridgeTransactionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BridgeTransaction : {}, {}", id, bridgeTransactionDTO);
        if (bridgeTransactionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bridgeTransactionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bridgeTransactionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BridgeTransactionDTO result = bridgeTransactionService.save(bridgeTransactionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bridgeTransactionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bridge-transactions/:id} : Partial updates given fields of an existing bridgeTransaction, field will ignore if it is null
     *
     * @param id the id of the bridgeTransactionDTO to save.
     * @param bridgeTransactionDTO the bridgeTransactionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bridgeTransactionDTO,
     * or with status {@code 400 (Bad Request)} if the bridgeTransactionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bridgeTransactionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bridgeTransactionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bridge-transactions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BridgeTransactionDTO> partialUpdateBridgeTransaction(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BridgeTransactionDTO bridgeTransactionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BridgeTransaction partially : {}, {}", id, bridgeTransactionDTO);
        if (bridgeTransactionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bridgeTransactionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bridgeTransactionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BridgeTransactionDTO> result = bridgeTransactionService.partialUpdate(bridgeTransactionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bridgeTransactionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bridge-transactions} : get all the bridgeTransactions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bridgeTransactions in body.
     */
    @GetMapping("/bridge-transactions")
    public ResponseEntity<List<BridgeTransactionDTO>> getAllBridgeTransactions(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of BridgeTransactions");
        Page<BridgeTransactionDTO> page = bridgeTransactionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bridge-transactions/:id} : get the "id" bridgeTransaction.
     *
     * @param id the id of the bridgeTransactionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bridgeTransactionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bridge-transactions/{id}")
    public ResponseEntity<BridgeTransactionDTO> getBridgeTransaction(@PathVariable Long id) {
        log.debug("REST request to get BridgeTransaction : {}", id);
        Optional<BridgeTransactionDTO> bridgeTransactionDTO = bridgeTransactionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bridgeTransactionDTO);
    }

    /**
     * {@code DELETE  /bridge-transactions/:id} : delete the "id" bridgeTransaction.
     *
     * @param id the id of the bridgeTransactionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bridge-transactions/{id}")
    public ResponseEntity<Void> deleteBridgeTransaction(@PathVariable Long id) {
        log.debug("REST request to delete BridgeTransaction : {}", id);
        bridgeTransactionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
