package com.akounts.myapp.web.rest;

import com.akounts.myapp.repository.BankProjectRepository;
import com.akounts.myapp.service.BankProjectService;
import com.akounts.myapp.service.dto.BankProjectDTO;
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
 * REST controller for managing {@link com.akounts.myapp.domain.BankProject}.
 */
@RestController
@RequestMapping("/api")
public class BankProjectResource {

    private final Logger log = LoggerFactory.getLogger(BankProjectResource.class);

    private static final String ENTITY_NAME = "bankProject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankProjectService bankProjectService;

    private final BankProjectRepository bankProjectRepository;

    public BankProjectResource(BankProjectService bankProjectService, BankProjectRepository bankProjectRepository) {
        this.bankProjectService = bankProjectService;
        this.bankProjectRepository = bankProjectRepository;
    }

    /**
     * {@code POST  /bank-projects} : Create a new bankProject.
     *
     * @param bankProjectDTO the bankProjectDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankProjectDTO, or with status {@code 400 (Bad Request)} if the bankProject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bank-projects")
    public ResponseEntity<BankProjectDTO> createBankProject(@RequestBody BankProjectDTO bankProjectDTO) throws URISyntaxException {
        log.debug("REST request to save BankProject : {}", bankProjectDTO);
        if (bankProjectDTO.getId() != null) {
            throw new BadRequestAlertException("A new bankProject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankProjectDTO result = bankProjectService.save(bankProjectDTO);
        return ResponseEntity
            .created(new URI("/api/bank-projects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bank-projects/:id} : Updates an existing bankProject.
     *
     * @param id the id of the bankProjectDTO to save.
     * @param bankProjectDTO the bankProjectDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankProjectDTO,
     * or with status {@code 400 (Bad Request)} if the bankProjectDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankProjectDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bank-projects/{id}")
    public ResponseEntity<BankProjectDTO> updateBankProject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankProjectDTO bankProjectDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BankProject : {}, {}", id, bankProjectDTO);
        if (bankProjectDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankProjectDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankProjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BankProjectDTO result = bankProjectService.save(bankProjectDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankProjectDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bank-projects/:id} : Partial updates given fields of an existing bankProject, field will ignore if it is null
     *
     * @param id the id of the bankProjectDTO to save.
     * @param bankProjectDTO the bankProjectDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankProjectDTO,
     * or with status {@code 400 (Bad Request)} if the bankProjectDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bankProjectDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bankProjectDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bank-projects/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BankProjectDTO> partialUpdateBankProject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankProjectDTO bankProjectDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BankProject partially : {}, {}", id, bankProjectDTO);
        if (bankProjectDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankProjectDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankProjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BankProjectDTO> result = bankProjectService.partialUpdate(bankProjectDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankProjectDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bank-projects} : get all the bankProjects.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankProjects in body.
     */
    @GetMapping("/bank-projects")
    public List<BankProjectDTO> getAllBankProjects() {
        log.debug("REST request to get all BankProjects");
        return bankProjectService.findAll();
    }

    /**
     * {@code GET  /bank-projects/:id} : get the "id" bankProject.
     *
     * @param id the id of the bankProjectDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankProjectDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bank-projects/{id}")
    public ResponseEntity<BankProjectDTO> getBankProject(@PathVariable Long id) {
        log.debug("REST request to get BankProject : {}", id);
        Optional<BankProjectDTO> bankProjectDTO = bankProjectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bankProjectDTO);
    }

    /**
     * {@code DELETE  /bank-projects/:id} : delete the "id" bankProject.
     *
     * @param id the id of the bankProjectDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bank-projects/{id}")
    public ResponseEntity<Void> deleteBankProject(@PathVariable Long id) {
        log.debug("REST request to delete BankProject : {}", id);
        bankProjectService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
