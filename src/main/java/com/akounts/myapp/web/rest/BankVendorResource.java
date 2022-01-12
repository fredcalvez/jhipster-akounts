package com.akounts.myapp.web.rest;

import com.akounts.myapp.repository.BankVendorRepository;
import com.akounts.myapp.service.BankVendorService;
import com.akounts.myapp.service.dto.BankVendorDTO;
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
 * REST controller for managing {@link com.akounts.myapp.domain.BankVendor}.
 */
@RestController
@RequestMapping("/api")
public class BankVendorResource {

    private final Logger log = LoggerFactory.getLogger(BankVendorResource.class);

    private static final String ENTITY_NAME = "bankVendor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankVendorService bankVendorService;

    private final BankVendorRepository bankVendorRepository;

    public BankVendorResource(BankVendorService bankVendorService, BankVendorRepository bankVendorRepository) {
        this.bankVendorService = bankVendorService;
        this.bankVendorRepository = bankVendorRepository;
    }

    /**
     * {@code POST  /bank-vendors} : Create a new bankVendor.
     *
     * @param bankVendorDTO the bankVendorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankVendorDTO, or with status {@code 400 (Bad Request)} if the bankVendor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bank-vendors")
    public ResponseEntity<BankVendorDTO> createBankVendor(@RequestBody BankVendorDTO bankVendorDTO) throws URISyntaxException {
        log.debug("REST request to save BankVendor : {}", bankVendorDTO);
        if (bankVendorDTO.getId() != null) {
            throw new BadRequestAlertException("A new bankVendor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankVendorDTO result = bankVendorService.save(bankVendorDTO);
        return ResponseEntity
            .created(new URI("/api/bank-vendors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bank-vendors/:id} : Updates an existing bankVendor.
     *
     * @param id the id of the bankVendorDTO to save.
     * @param bankVendorDTO the bankVendorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankVendorDTO,
     * or with status {@code 400 (Bad Request)} if the bankVendorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankVendorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bank-vendors/{id}")
    public ResponseEntity<BankVendorDTO> updateBankVendor(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankVendorDTO bankVendorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BankVendor : {}, {}", id, bankVendorDTO);
        if (bankVendorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankVendorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankVendorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BankVendorDTO result = bankVendorService.save(bankVendorDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankVendorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bank-vendors/:id} : Partial updates given fields of an existing bankVendor, field will ignore if it is null
     *
     * @param id the id of the bankVendorDTO to save.
     * @param bankVendorDTO the bankVendorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankVendorDTO,
     * or with status {@code 400 (Bad Request)} if the bankVendorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bankVendorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bankVendorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bank-vendors/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BankVendorDTO> partialUpdateBankVendor(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankVendorDTO bankVendorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BankVendor partially : {}, {}", id, bankVendorDTO);
        if (bankVendorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankVendorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankVendorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BankVendorDTO> result = bankVendorService.partialUpdate(bankVendorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankVendorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bank-vendors} : get all the bankVendors.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankVendors in body.
     */
    @GetMapping("/bank-vendors")
    public ResponseEntity<List<BankVendorDTO>> getAllBankVendors(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of BankVendors");
        Page<BankVendorDTO> page = bankVendorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bank-vendors/:id} : get the "id" bankVendor.
     *
     * @param id the id of the bankVendorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankVendorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bank-vendors/{id}")
    public ResponseEntity<BankVendorDTO> getBankVendor(@PathVariable Long id) {
        log.debug("REST request to get BankVendor : {}", id);
        Optional<BankVendorDTO> bankVendorDTO = bankVendorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bankVendorDTO);
    }

    /**
     * {@code DELETE  /bank-vendors/:id} : delete the "id" bankVendor.
     *
     * @param id the id of the bankVendorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bank-vendors/{id}")
    public ResponseEntity<Void> deleteBankVendor(@PathVariable Long id) {
        log.debug("REST request to delete BankVendor : {}", id);
        bankVendorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
