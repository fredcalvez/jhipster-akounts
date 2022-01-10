package com.akounts.myapp.web.rest;

import com.akounts.myapp.repository.BankCategoryRepository;
import com.akounts.myapp.service.BankCategoryService;
import com.akounts.myapp.service.dto.BankCategoryDTO;
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
 * REST controller for managing {@link com.akounts.myapp.domain.BankCategory}.
 */
@RestController
@RequestMapping("/api")
public class BankCategoryResource {

    private final Logger log = LoggerFactory.getLogger(BankCategoryResource.class);

    private static final String ENTITY_NAME = "bankCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankCategoryService bankCategoryService;

    private final BankCategoryRepository bankCategoryRepository;

    public BankCategoryResource(BankCategoryService bankCategoryService, BankCategoryRepository bankCategoryRepository) {
        this.bankCategoryService = bankCategoryService;
        this.bankCategoryRepository = bankCategoryRepository;
    }

    /**
     * {@code POST  /bank-categories} : Create a new bankCategory.
     *
     * @param bankCategoryDTO the bankCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankCategoryDTO, or with status {@code 400 (Bad Request)} if the bankCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bank-categories")
    public ResponseEntity<BankCategoryDTO> createBankCategory(@RequestBody BankCategoryDTO bankCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save BankCategory : {}", bankCategoryDTO);
        if (bankCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new bankCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankCategoryDTO result = bankCategoryService.save(bankCategoryDTO);
        return ResponseEntity
            .created(new URI("/api/bank-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bank-categories/:id} : Updates an existing bankCategory.
     *
     * @param id the id of the bankCategoryDTO to save.
     * @param bankCategoryDTO the bankCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the bankCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bank-categories/{id}")
    public ResponseEntity<BankCategoryDTO> updateBankCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankCategoryDTO bankCategoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BankCategory : {}, {}", id, bankCategoryDTO);
        if (bankCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankCategoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BankCategoryDTO result = bankCategoryService.save(bankCategoryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bank-categories/:id} : Partial updates given fields of an existing bankCategory, field will ignore if it is null
     *
     * @param id the id of the bankCategoryDTO to save.
     * @param bankCategoryDTO the bankCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the bankCategoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bankCategoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bankCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bank-categories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BankCategoryDTO> partialUpdateBankCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankCategoryDTO bankCategoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BankCategory partially : {}, {}", id, bankCategoryDTO);
        if (bankCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankCategoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BankCategoryDTO> result = bankCategoryService.partialUpdate(bankCategoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankCategoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bank-categories} : get all the bankCategories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankCategories in body.
     */
    @GetMapping("/bank-categories")
    public ResponseEntity<List<BankCategoryDTO>> getAllBankCategories(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of BankCategories");
        Page<BankCategoryDTO> page = bankCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bank-categories/:id} : get the "id" bankCategory.
     *
     * @param id the id of the bankCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bank-categories/{id}")
    public ResponseEntity<BankCategoryDTO> getBankCategory(@PathVariable Long id) {
        log.debug("REST request to get BankCategory : {}", id);
        Optional<BankCategoryDTO> bankCategoryDTO = bankCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bankCategoryDTO);
    }

    /**
     * {@code DELETE  /bank-categories/:id} : delete the "id" bankCategory.
     *
     * @param id the id of the bankCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bank-categories/{id}")
    public ResponseEntity<Void> deleteBankCategory(@PathVariable Long id) {
        log.debug("REST request to delete BankCategory : {}", id);
        bankCategoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
