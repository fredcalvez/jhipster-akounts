package com.akounts.myapp.web.rest;

import com.akounts.myapp.domain.BankProject;
import com.akounts.myapp.repository.BankProjectRepository;
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
 * REST controller for managing {@link com.akounts.myapp.domain.BankProject}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BankProjectResource {

    private final Logger log = LoggerFactory.getLogger(BankProjectResource.class);

    private static final String ENTITY_NAME = "bankProject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankProjectRepository bankProjectRepository;

    public BankProjectResource(BankProjectRepository bankProjectRepository) {
        this.bankProjectRepository = bankProjectRepository;
    }

    /**
     * {@code POST  /bank-projects} : Create a new bankProject.
     *
     * @param bankProject the bankProject to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankProject, or with status {@code 400 (Bad Request)} if the bankProject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bank-projects")
    public ResponseEntity<BankProject> createBankProject(@RequestBody BankProject bankProject) throws URISyntaxException {
        log.debug("REST request to save BankProject : {}", bankProject);
        if (bankProject.getId() != null) {
            throw new BadRequestAlertException("A new bankProject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankProject result = bankProjectRepository.save(bankProject);
        return ResponseEntity
            .created(new URI("/api/bank-projects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bank-projects/:id} : Updates an existing bankProject.
     *
     * @param id the id of the bankProject to save.
     * @param bankProject the bankProject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankProject,
     * or with status {@code 400 (Bad Request)} if the bankProject is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankProject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bank-projects/{id}")
    public ResponseEntity<BankProject> updateBankProject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankProject bankProject
    ) throws URISyntaxException {
        log.debug("REST request to update BankProject : {}, {}", id, bankProject);
        if (bankProject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankProject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankProjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BankProject result = bankProjectRepository.save(bankProject);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankProject.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bank-projects/:id} : Partial updates given fields of an existing bankProject, field will ignore if it is null
     *
     * @param id the id of the bankProject to save.
     * @param bankProject the bankProject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankProject,
     * or with status {@code 400 (Bad Request)} if the bankProject is not valid,
     * or with status {@code 404 (Not Found)} if the bankProject is not found,
     * or with status {@code 500 (Internal Server Error)} if the bankProject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bank-projects/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BankProject> partialUpdateBankProject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankProject bankProject
    ) throws URISyntaxException {
        log.debug("REST request to partial update BankProject partially : {}, {}", id, bankProject);
        if (bankProject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankProject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankProjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BankProject> result = bankProjectRepository
            .findById(bankProject.getId())
            .map(existingBankProject -> {
                if (bankProject.getName() != null) {
                    existingBankProject.setName(bankProject.getName());
                }
                if (bankProject.getProjectType() != null) {
                    existingBankProject.setProjectType(bankProject.getProjectType());
                }
                if (bankProject.getInitialValue() != null) {
                    existingBankProject.setInitialValue(bankProject.getInitialValue());
                }
                if (bankProject.getCurrentValue() != null) {
                    existingBankProject.setCurrentValue(bankProject.getCurrentValue());
                }

                return existingBankProject;
            })
            .map(bankProjectRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankProject.getId().toString())
        );
    }

    /**
     * {@code GET  /bank-projects} : get all the bankProjects.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankProjects in body.
     */
    @GetMapping("/bank-projects")
    public List<BankProject> getAllBankProjects() {
        log.debug("REST request to get all BankProjects");
        return bankProjectRepository.findAll();
    }

    /**
     * {@code GET  /bank-projects/:id} : get the "id" bankProject.
     *
     * @param id the id of the bankProject to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankProject, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bank-projects/{id}")
    public ResponseEntity<BankProject> getBankProject(@PathVariable Long id) {
        log.debug("REST request to get BankProject : {}", id);
        Optional<BankProject> bankProject = bankProjectRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bankProject);
    }

    /**
     * {@code DELETE  /bank-projects/:id} : delete the "id" bankProject.
     *
     * @param id the id of the bankProject to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bank-projects/{id}")
    public ResponseEntity<Void> deleteBankProject(@PathVariable Long id) {
        log.debug("REST request to delete BankProject : {}", id);
        bankProjectRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
