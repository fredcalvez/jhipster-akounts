package com.akounts.myapp.web.rest;

import com.akounts.myapp.domain.BankStream;
import com.akounts.myapp.repository.BankStreamRepository;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.akounts.myapp.domain.BankStream}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BankStreamResource {

    private final Logger log = LoggerFactory.getLogger(BankStreamResource.class);

    private static final String ENTITY_NAME = "bankStream";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankStreamRepository bankStreamRepository;

    public BankStreamResource(BankStreamRepository bankStreamRepository) {
        this.bankStreamRepository = bankStreamRepository;
    }

    /**
     * {@code POST  /bank-streams} : Create a new bankStream.
     *
     * @param bankStream the bankStream to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankStream, or with status {@code 400 (Bad Request)} if the bankStream has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bank-streams")
    public ResponseEntity<BankStream> createBankStream(@RequestBody BankStream bankStream) throws URISyntaxException {
        log.debug("REST request to save BankStream : {}", bankStream);
        if (bankStream.getId() != null) {
            throw new BadRequestAlertException("A new bankStream cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankStream result = bankStreamRepository.save(bankStream);
        return ResponseEntity
            .created(new URI("/api/bank-streams/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bank-streams/:id} : Updates an existing bankStream.
     *
     * @param id the id of the bankStream to save.
     * @param bankStream the bankStream to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankStream,
     * or with status {@code 400 (Bad Request)} if the bankStream is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankStream couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bank-streams/{id}")
    public ResponseEntity<BankStream> updateBankStream(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankStream bankStream
    ) throws URISyntaxException {
        log.debug("REST request to update BankStream : {}, {}", id, bankStream);
        if (bankStream.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankStream.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankStreamRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BankStream result = bankStreamRepository.save(bankStream);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankStream.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bank-streams/:id} : Partial updates given fields of an existing bankStream, field will ignore if it is null
     *
     * @param id the id of the bankStream to save.
     * @param bankStream the bankStream to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankStream,
     * or with status {@code 400 (Bad Request)} if the bankStream is not valid,
     * or with status {@code 404 (Not Found)} if the bankStream is not found,
     * or with status {@code 500 (Internal Server Error)} if the bankStream couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bank-streams/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BankStream> partialUpdateBankStream(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankStream bankStream
    ) throws URISyntaxException {
        log.debug("REST request to partial update BankStream partially : {}, {}", id, bankStream);
        if (bankStream.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankStream.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankStreamRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BankStream> result = bankStreamRepository
            .findById(bankStream.getId())
            .map(existingBankStream -> {
                if (bankStream.getName() != null) {
                    existingBankStream.setName(bankStream.getName());
                }
                if (bankStream.getStreamType() != null) {
                    existingBankStream.setStreamType(bankStream.getStreamType());
                }

                return existingBankStream;
            })
            .map(bankStreamRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankStream.getId().toString())
        );
    }

    /**
     * {@code GET  /bank-streams} : get all the bankStreams.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankStreams in body.
     */
    @GetMapping("/bank-streams")
    public List<BankStream> getAllBankStreams() {
        log.debug("REST request to get all BankStreams");
        return bankStreamRepository.findAll();
    }

    /**
     * {@code GET  /bank-streams/:id} : get the "id" bankStream.
     *
     * @param id the id of the bankStream to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankStream, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bank-streams/{id}")
    public ResponseEntity<BankStream> getBankStream(@PathVariable Long id) {
        log.debug("REST request to get BankStream : {}", id);
        Optional<BankStream> bankStream = bankStreamRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bankStream);
    }

    /**
     * {@code DELETE  /bank-streams/:id} : delete the "id" bankStream.
     *
     * @param id the id of the bankStream to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bank-streams/{id}")
    public ResponseEntity<Void> deleteBankStream(@PathVariable Long id) {
        log.debug("REST request to delete BankStream : {}", id);
        bankStreamRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
