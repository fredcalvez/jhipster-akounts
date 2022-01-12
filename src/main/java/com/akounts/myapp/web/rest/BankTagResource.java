package com.akounts.myapp.web.rest;

import com.akounts.myapp.repository.BankTagRepository;
import com.akounts.myapp.service.BankTagService;
import com.akounts.myapp.service.dto.BankTagDTO;
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
 * REST controller for managing {@link com.akounts.myapp.domain.BankTag}.
 */
@RestController
@RequestMapping("/api")
public class BankTagResource {

    private final Logger log = LoggerFactory.getLogger(BankTagResource.class);

    private static final String ENTITY_NAME = "bankTag";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankTagService bankTagService;

    private final BankTagRepository bankTagRepository;

    public BankTagResource(BankTagService bankTagService, BankTagRepository bankTagRepository) {
        this.bankTagService = bankTagService;
        this.bankTagRepository = bankTagRepository;
    }

    /**
     * {@code POST  /bank-tags} : Create a new bankTag.
     *
     * @param bankTagDTO the bankTagDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankTagDTO, or with status {@code 400 (Bad Request)} if the bankTag has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bank-tags")
    public ResponseEntity<BankTagDTO> createBankTag(@RequestBody BankTagDTO bankTagDTO) throws URISyntaxException {
        log.debug("REST request to save BankTag : {}", bankTagDTO);
        if (bankTagDTO.getId() != null) {
            throw new BadRequestAlertException("A new bankTag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankTagDTO result = bankTagService.save(bankTagDTO);
        return ResponseEntity
            .created(new URI("/api/bank-tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bank-tags/:id} : Updates an existing bankTag.
     *
     * @param id the id of the bankTagDTO to save.
     * @param bankTagDTO the bankTagDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankTagDTO,
     * or with status {@code 400 (Bad Request)} if the bankTagDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankTagDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bank-tags/{id}")
    public ResponseEntity<BankTagDTO> updateBankTag(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankTagDTO bankTagDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BankTag : {}, {}", id, bankTagDTO);
        if (bankTagDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankTagDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankTagRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BankTagDTO result = bankTagService.save(bankTagDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankTagDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bank-tags/:id} : Partial updates given fields of an existing bankTag, field will ignore if it is null
     *
     * @param id the id of the bankTagDTO to save.
     * @param bankTagDTO the bankTagDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankTagDTO,
     * or with status {@code 400 (Bad Request)} if the bankTagDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bankTagDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bankTagDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bank-tags/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BankTagDTO> partialUpdateBankTag(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankTagDTO bankTagDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BankTag partially : {}, {}", id, bankTagDTO);
        if (bankTagDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankTagDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankTagRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BankTagDTO> result = bankTagService.partialUpdate(bankTagDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankTagDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bank-tags} : get all the bankTags.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankTags in body.
     */
    @GetMapping("/bank-tags")
    public ResponseEntity<List<BankTagDTO>> getAllBankTags(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of BankTags");
        Page<BankTagDTO> page = bankTagService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bank-tags/:id} : get the "id" bankTag.
     *
     * @param id the id of the bankTagDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankTagDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bank-tags/{id}")
    public ResponseEntity<BankTagDTO> getBankTag(@PathVariable Long id) {
        log.debug("REST request to get BankTag : {}", id);
        Optional<BankTagDTO> bankTagDTO = bankTagService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bankTagDTO);
    }

    /**
     * {@code DELETE  /bank-tags/:id} : delete the "id" bankTag.
     *
     * @param id the id of the bankTagDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bank-tags/{id}")
    public ResponseEntity<Void> deleteBankTag(@PathVariable Long id) {
        log.debug("REST request to delete BankTag : {}", id);
        bankTagService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
