package com.akounts.myapp.web.rest;

import com.akounts.myapp.repository.BankInstitutionRepository;
import com.akounts.myapp.service.BankInstitutionService;
import com.akounts.myapp.service.dto.BankInstitutionDTO;
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
 * REST controller for managing {@link com.akounts.myapp.domain.BankInstitution}.
 */
@RestController
@RequestMapping("/api")
public class BankInstitutionResource {

    private final Logger log = LoggerFactory.getLogger(BankInstitutionResource.class);

    private static final String ENTITY_NAME = "bankInstitution";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankInstitutionService bankInstitutionService;

    private final BankInstitutionRepository bankInstitutionRepository;

    public BankInstitutionResource(BankInstitutionService bankInstitutionService, BankInstitutionRepository bankInstitutionRepository) {
        this.bankInstitutionService = bankInstitutionService;
        this.bankInstitutionRepository = bankInstitutionRepository;
    }

    /**
     * {@code POST  /bank-institutions} : Create a new bankInstitution.
     *
     * @param bankInstitutionDTO the bankInstitutionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankInstitutionDTO, or with status {@code 400 (Bad Request)} if the bankInstitution has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bank-institutions")
    public ResponseEntity<BankInstitutionDTO> createBankInstitution(@RequestBody BankInstitutionDTO bankInstitutionDTO)
        throws URISyntaxException {
        log.debug("REST request to save BankInstitution : {}", bankInstitutionDTO);
        if (bankInstitutionDTO.getId() != null) {
            throw new BadRequestAlertException("A new bankInstitution cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankInstitutionDTO result = bankInstitutionService.save(bankInstitutionDTO);
        return ResponseEntity
            .created(new URI("/api/bank-institutions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bank-institutions/:id} : Updates an existing bankInstitution.
     *
     * @param id the id of the bankInstitutionDTO to save.
     * @param bankInstitutionDTO the bankInstitutionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankInstitutionDTO,
     * or with status {@code 400 (Bad Request)} if the bankInstitutionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankInstitutionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bank-institutions/{id}")
    public ResponseEntity<BankInstitutionDTO> updateBankInstitution(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankInstitutionDTO bankInstitutionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BankInstitution : {}, {}", id, bankInstitutionDTO);
        if (bankInstitutionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankInstitutionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankInstitutionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BankInstitutionDTO result = bankInstitutionService.save(bankInstitutionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankInstitutionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bank-institutions/:id} : Partial updates given fields of an existing bankInstitution, field will ignore if it is null
     *
     * @param id the id of the bankInstitutionDTO to save.
     * @param bankInstitutionDTO the bankInstitutionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankInstitutionDTO,
     * or with status {@code 400 (Bad Request)} if the bankInstitutionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bankInstitutionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bankInstitutionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bank-institutions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BankInstitutionDTO> partialUpdateBankInstitution(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankInstitutionDTO bankInstitutionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BankInstitution partially : {}, {}", id, bankInstitutionDTO);
        if (bankInstitutionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankInstitutionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankInstitutionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BankInstitutionDTO> result = bankInstitutionService.partialUpdate(bankInstitutionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankInstitutionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bank-institutions} : get all the bankInstitutions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankInstitutions in body.
     */
    @GetMapping("/bank-institutions")
    public ResponseEntity<List<BankInstitutionDTO>> getAllBankInstitutions(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of BankInstitutions");
        Page<BankInstitutionDTO> page = bankInstitutionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bank-institutions/:id} : get the "id" bankInstitution.
     *
     * @param id the id of the bankInstitutionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankInstitutionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bank-institutions/{id}")
    public ResponseEntity<BankInstitutionDTO> getBankInstitution(@PathVariable Long id) {
        log.debug("REST request to get BankInstitution : {}", id);
        Optional<BankInstitutionDTO> bankInstitutionDTO = bankInstitutionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bankInstitutionDTO);
    }

    /**
     * {@code DELETE  /bank-institutions/:id} : delete the "id" bankInstitution.
     *
     * @param id the id of the bankInstitutionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bank-institutions/{id}")
    public ResponseEntity<Void> deleteBankInstitution(@PathVariable Long id) {
        log.debug("REST request to delete BankInstitution : {}", id);
        bankInstitutionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
