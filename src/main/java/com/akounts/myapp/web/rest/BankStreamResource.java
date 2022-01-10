package com.akounts.myapp.web.rest;

import com.akounts.myapp.repository.BankStreamRepository;
import com.akounts.myapp.service.BankStreamService;
import com.akounts.myapp.service.dto.BankStreamDTO;
import com.akounts.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.akounts.myapp.domain.BankStream}.
 */
@RestController
@RequestMapping("/api")
public class BankStreamResource {

    private final Logger log = LoggerFactory.getLogger(BankStreamResource.class);

    private static final String ENTITY_NAME = "bankStream";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankStreamService bankStreamService;

    private final BankStreamRepository bankStreamRepository;

    public BankStreamResource(BankStreamService bankStreamService, BankStreamRepository bankStreamRepository) {
        this.bankStreamService = bankStreamService;
        this.bankStreamRepository = bankStreamRepository;
    }

    /**
     * {@code POST  /bank-streams} : Create a new bankStream.
     *
     * @param bankStreamDTO the bankStreamDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankStreamDTO, or with status {@code 400 (Bad Request)} if the bankStream has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bank-streams")
    public ResponseEntity<BankStreamDTO> createBankStream(@RequestBody BankStreamDTO bankStreamDTO) throws URISyntaxException {
        log.debug("REST request to save BankStream : {}", bankStreamDTO);
        if (bankStreamDTO.getId() != null) {
            throw new BadRequestAlertException("A new bankStream cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankStreamDTO result = bankStreamService.save(bankStreamDTO);
        return ResponseEntity
            .created(new URI("/api/bank-streams/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bank-streams/:id} : Updates an existing bankStream.
     *
     * @param id the id of the bankStreamDTO to save.
     * @param bankStreamDTO the bankStreamDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankStreamDTO,
     * or with status {@code 400 (Bad Request)} if the bankStreamDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankStreamDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bank-streams/{id}")
    public ResponseEntity<BankStreamDTO> updateBankStream(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankStreamDTO bankStreamDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BankStream : {}, {}", id, bankStreamDTO);
        if (bankStreamDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankStreamDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankStreamRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BankStreamDTO result = bankStreamService.save(bankStreamDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankStreamDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bank-streams/:id} : Partial updates given fields of an existing bankStream, field will ignore if it is null
     *
     * @param id the id of the bankStreamDTO to save.
     * @param bankStreamDTO the bankStreamDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankStreamDTO,
     * or with status {@code 400 (Bad Request)} if the bankStreamDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bankStreamDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bankStreamDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bank-streams/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BankStreamDTO> partialUpdateBankStream(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankStreamDTO bankStreamDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BankStream partially : {}, {}", id, bankStreamDTO);
        if (bankStreamDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankStreamDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankStreamRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BankStreamDTO> result = bankStreamService.partialUpdate(bankStreamDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankStreamDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bank-streams} : get all the bankStreams.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankStreams in body.
     */
    @GetMapping("/bank-streams")
    public List<BankStreamDTO> getAllBankStreams() {
        log.debug("REST request to get all BankStreams");
        return bankStreamService.findAll();
    }

    /**
     * {@code GET  /bank-streams/:id} : get the "id" bankStream.
     *
     * @param id the id of the bankStreamDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankStreamDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bank-streams/{id}")
    public ResponseEntity<BankStreamDTO> getBankStream(@PathVariable Long id) {
        log.debug("REST request to get BankStream : {}", id);
        Optional<BankStreamDTO> bankStreamDTO = bankStreamService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bankStreamDTO);
    }

    /**
     * {@code DELETE  /bank-streams/:id} : delete the "id" bankStream.
     *
     * @param id the id of the bankStreamDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bank-streams/{id}")
    public ResponseEntity<Void> deleteBankStream(@PathVariable Long id) {
        log.debug("REST request to delete BankStream : {}", id);
        bankStreamService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
