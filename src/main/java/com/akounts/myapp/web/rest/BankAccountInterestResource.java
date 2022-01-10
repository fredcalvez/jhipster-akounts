package com.akounts.myapp.web.rest;

import com.akounts.myapp.repository.BankAccountInterestRepository;
import com.akounts.myapp.service.BankAccountInterestService;
import com.akounts.myapp.service.dto.BankAccountInterestDTO;
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
 * REST controller for managing {@link com.akounts.myapp.domain.BankAccountInterest}.
 */
@RestController
@RequestMapping("/api")
public class BankAccountInterestResource {

    private final Logger log = LoggerFactory.getLogger(BankAccountInterestResource.class);

    private static final String ENTITY_NAME = "bankAccountInterest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankAccountInterestService bankAccountInterestService;

    private final BankAccountInterestRepository bankAccountInterestRepository;

    public BankAccountInterestResource(
        BankAccountInterestService bankAccountInterestService,
        BankAccountInterestRepository bankAccountInterestRepository
    ) {
        this.bankAccountInterestService = bankAccountInterestService;
        this.bankAccountInterestRepository = bankAccountInterestRepository;
    }

    /**
     * {@code POST  /bank-account-interests} : Create a new bankAccountInterest.
     *
     * @param bankAccountInterestDTO the bankAccountInterestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankAccountInterestDTO, or with status {@code 400 (Bad Request)} if the bankAccountInterest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bank-account-interests")
    public ResponseEntity<BankAccountInterestDTO> createBankAccountInterest(@RequestBody BankAccountInterestDTO bankAccountInterestDTO)
        throws URISyntaxException {
        log.debug("REST request to save BankAccountInterest : {}", bankAccountInterestDTO);
        if (bankAccountInterestDTO.getId() != null) {
            throw new BadRequestAlertException("A new bankAccountInterest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankAccountInterestDTO result = bankAccountInterestService.save(bankAccountInterestDTO);
        return ResponseEntity
            .created(new URI("/api/bank-account-interests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bank-account-interests/:id} : Updates an existing bankAccountInterest.
     *
     * @param id the id of the bankAccountInterestDTO to save.
     * @param bankAccountInterestDTO the bankAccountInterestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankAccountInterestDTO,
     * or with status {@code 400 (Bad Request)} if the bankAccountInterestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankAccountInterestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bank-account-interests/{id}")
    public ResponseEntity<BankAccountInterestDTO> updateBankAccountInterest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankAccountInterestDTO bankAccountInterestDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BankAccountInterest : {}, {}", id, bankAccountInterestDTO);
        if (bankAccountInterestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankAccountInterestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankAccountInterestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BankAccountInterestDTO result = bankAccountInterestService.save(bankAccountInterestDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankAccountInterestDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bank-account-interests/:id} : Partial updates given fields of an existing bankAccountInterest, field will ignore if it is null
     *
     * @param id the id of the bankAccountInterestDTO to save.
     * @param bankAccountInterestDTO the bankAccountInterestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankAccountInterestDTO,
     * or with status {@code 400 (Bad Request)} if the bankAccountInterestDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bankAccountInterestDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bankAccountInterestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bank-account-interests/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BankAccountInterestDTO> partialUpdateBankAccountInterest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankAccountInterestDTO bankAccountInterestDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BankAccountInterest partially : {}, {}", id, bankAccountInterestDTO);
        if (bankAccountInterestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankAccountInterestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankAccountInterestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BankAccountInterestDTO> result = bankAccountInterestService.partialUpdate(bankAccountInterestDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankAccountInterestDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bank-account-interests} : get all the bankAccountInterests.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankAccountInterests in body.
     */
    @GetMapping("/bank-account-interests")
    public List<BankAccountInterestDTO> getAllBankAccountInterests() {
        log.debug("REST request to get all BankAccountInterests");
        return bankAccountInterestService.findAll();
    }

    /**
     * {@code GET  /bank-account-interests/:id} : get the "id" bankAccountInterest.
     *
     * @param id the id of the bankAccountInterestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankAccountInterestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bank-account-interests/{id}")
    public ResponseEntity<BankAccountInterestDTO> getBankAccountInterest(@PathVariable Long id) {
        log.debug("REST request to get BankAccountInterest : {}", id);
        Optional<BankAccountInterestDTO> bankAccountInterestDTO = bankAccountInterestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bankAccountInterestDTO);
    }

    /**
     * {@code DELETE  /bank-account-interests/:id} : delete the "id" bankAccountInterest.
     *
     * @param id the id of the bankAccountInterestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bank-account-interests/{id}")
    public ResponseEntity<Void> deleteBankAccountInterest(@PathVariable Long id) {
        log.debug("REST request to delete BankAccountInterest : {}", id);
        bankAccountInterestService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
