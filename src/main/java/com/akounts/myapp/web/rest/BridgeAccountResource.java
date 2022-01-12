package com.akounts.myapp.web.rest;

import com.akounts.myapp.repository.BridgeAccountRepository;
import com.akounts.myapp.service.BridgeAccountService;
import com.akounts.myapp.service.dto.BridgeAccountDTO;
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
 * REST controller for managing {@link com.akounts.myapp.domain.BridgeAccount}.
 */
@RestController
@RequestMapping("/api")
public class BridgeAccountResource {

    private final Logger log = LoggerFactory.getLogger(BridgeAccountResource.class);

    private static final String ENTITY_NAME = "bridgeAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BridgeAccountService bridgeAccountService;

    private final BridgeAccountRepository bridgeAccountRepository;

    public BridgeAccountResource(BridgeAccountService bridgeAccountService, BridgeAccountRepository bridgeAccountRepository) {
        this.bridgeAccountService = bridgeAccountService;
        this.bridgeAccountRepository = bridgeAccountRepository;
    }

    /**
     * {@code POST  /bridge-accounts} : Create a new bridgeAccount.
     *
     * @param bridgeAccountDTO the bridgeAccountDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bridgeAccountDTO, or with status {@code 400 (Bad Request)} if the bridgeAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bridge-accounts")
    public ResponseEntity<BridgeAccountDTO> createBridgeAccount(@RequestBody BridgeAccountDTO bridgeAccountDTO) throws URISyntaxException {
        log.debug("REST request to save BridgeAccount : {}", bridgeAccountDTO);
        if (bridgeAccountDTO.getId() != null) {
            throw new BadRequestAlertException("A new bridgeAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BridgeAccountDTO result = bridgeAccountService.save(bridgeAccountDTO);
        return ResponseEntity
            .created(new URI("/api/bridge-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bridge-accounts/:id} : Updates an existing bridgeAccount.
     *
     * @param id the id of the bridgeAccountDTO to save.
     * @param bridgeAccountDTO the bridgeAccountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bridgeAccountDTO,
     * or with status {@code 400 (Bad Request)} if the bridgeAccountDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bridgeAccountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bridge-accounts/{id}")
    public ResponseEntity<BridgeAccountDTO> updateBridgeAccount(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BridgeAccountDTO bridgeAccountDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BridgeAccount : {}, {}", id, bridgeAccountDTO);
        if (bridgeAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bridgeAccountDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bridgeAccountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BridgeAccountDTO result = bridgeAccountService.save(bridgeAccountDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bridgeAccountDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bridge-accounts/:id} : Partial updates given fields of an existing bridgeAccount, field will ignore if it is null
     *
     * @param id the id of the bridgeAccountDTO to save.
     * @param bridgeAccountDTO the bridgeAccountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bridgeAccountDTO,
     * or with status {@code 400 (Bad Request)} if the bridgeAccountDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bridgeAccountDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bridgeAccountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bridge-accounts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BridgeAccountDTO> partialUpdateBridgeAccount(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BridgeAccountDTO bridgeAccountDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BridgeAccount partially : {}, {}", id, bridgeAccountDTO);
        if (bridgeAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bridgeAccountDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bridgeAccountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BridgeAccountDTO> result = bridgeAccountService.partialUpdate(bridgeAccountDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bridgeAccountDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bridge-accounts} : get all the bridgeAccounts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bridgeAccounts in body.
     */
    @GetMapping("/bridge-accounts")
    public ResponseEntity<List<BridgeAccountDTO>> getAllBridgeAccounts(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of BridgeAccounts");
        Page<BridgeAccountDTO> page = bridgeAccountService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bridge-accounts/:id} : get the "id" bridgeAccount.
     *
     * @param id the id of the bridgeAccountDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bridgeAccountDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bridge-accounts/{id}")
    public ResponseEntity<BridgeAccountDTO> getBridgeAccount(@PathVariable Long id) {
        log.debug("REST request to get BridgeAccount : {}", id);
        Optional<BridgeAccountDTO> bridgeAccountDTO = bridgeAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bridgeAccountDTO);
    }

    /**
     * {@code DELETE  /bridge-accounts/:id} : delete the "id" bridgeAccount.
     *
     * @param id the id of the bridgeAccountDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bridge-accounts/{id}")
    public ResponseEntity<Void> deleteBridgeAccount(@PathVariable Long id) {
        log.debug("REST request to delete BridgeAccount : {}", id);
        bridgeAccountService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
