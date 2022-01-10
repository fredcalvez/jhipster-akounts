package com.akounts.myapp.web.rest;

import com.akounts.myapp.domain.Automatch;
import com.akounts.myapp.repository.AutomatchRepository;
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
 * REST controller for managing {@link com.akounts.myapp.domain.Automatch}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AutomatchResource {

    private final Logger log = LoggerFactory.getLogger(AutomatchResource.class);

    private static final String ENTITY_NAME = "automatch";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AutomatchRepository automatchRepository;

    public AutomatchResource(AutomatchRepository automatchRepository) {
        this.automatchRepository = automatchRepository;
    }

    /**
     * {@code POST  /automatches} : Create a new automatch.
     *
     * @param automatch the automatch to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new automatch, or with status {@code 400 (Bad Request)} if the automatch has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/automatches")
    public ResponseEntity<Automatch> createAutomatch(@RequestBody Automatch automatch) throws URISyntaxException {
        log.debug("REST request to save Automatch : {}", automatch);
        if (automatch.getId() != null) {
            throw new BadRequestAlertException("A new automatch cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Automatch result = automatchRepository.save(automatch);
        return ResponseEntity
            .created(new URI("/api/automatches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /automatches/:id} : Updates an existing automatch.
     *
     * @param id the id of the automatch to save.
     * @param automatch the automatch to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated automatch,
     * or with status {@code 400 (Bad Request)} if the automatch is not valid,
     * or with status {@code 500 (Internal Server Error)} if the automatch couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/automatches/{id}")
    public ResponseEntity<Automatch> updateAutomatch(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Automatch automatch
    ) throws URISyntaxException {
        log.debug("REST request to update Automatch : {}, {}", id, automatch);
        if (automatch.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, automatch.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!automatchRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Automatch result = automatchRepository.save(automatch);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, automatch.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /automatches/:id} : Partial updates given fields of an existing automatch, field will ignore if it is null
     *
     * @param id the id of the automatch to save.
     * @param automatch the automatch to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated automatch,
     * or with status {@code 400 (Bad Request)} if the automatch is not valid,
     * or with status {@code 404 (Not Found)} if the automatch is not found,
     * or with status {@code 500 (Internal Server Error)} if the automatch couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/automatches/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Automatch> partialUpdateAutomatch(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Automatch automatch
    ) throws URISyntaxException {
        log.debug("REST request to partial update Automatch partially : {}, {}", id, automatch);
        if (automatch.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, automatch.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!automatchRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Automatch> result = automatchRepository
            .findById(automatch.getId())
            .map(existingAutomatch -> {
                if (automatch.getMatchstring() != null) {
                    existingAutomatch.setMatchstring(automatch.getMatchstring());
                }
                if (automatch.getUpdateTime() != null) {
                    existingAutomatch.setUpdateTime(automatch.getUpdateTime());
                }
                if (automatch.getLastUsedTime() != null) {
                    existingAutomatch.setLastUsedTime(automatch.getLastUsedTime());
                }
                if (automatch.getUseCount() != null) {
                    existingAutomatch.setUseCount(automatch.getUseCount());
                }
                if (automatch.getDefaultTag() != null) {
                    existingAutomatch.setDefaultTag(automatch.getDefaultTag());
                }

                return existingAutomatch;
            })
            .map(automatchRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, automatch.getId().toString())
        );
    }

    /**
     * {@code GET  /automatches} : get all the automatches.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of automatches in body.
     */
    @GetMapping("/automatches")
    public List<Automatch> getAllAutomatches() {
        log.debug("REST request to get all Automatches");
        return automatchRepository.findAll();
    }

    /**
     * {@code GET  /automatches/:id} : get the "id" automatch.
     *
     * @param id the id of the automatch to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the automatch, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/automatches/{id}")
    public ResponseEntity<Automatch> getAutomatch(@PathVariable Long id) {
        log.debug("REST request to get Automatch : {}", id);
        Optional<Automatch> automatch = automatchRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(automatch);
    }

    /**
     * {@code DELETE  /automatches/:id} : delete the "id" automatch.
     *
     * @param id the id of the automatch to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/automatches/{id}")
    public ResponseEntity<Void> deleteAutomatch(@PathVariable Long id) {
        log.debug("REST request to delete Automatch : {}", id);
        automatchRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
