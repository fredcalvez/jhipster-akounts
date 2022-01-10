package com.akounts.myapp.web.rest;

import com.akounts.myapp.domain.RebaseHistory;
import com.akounts.myapp.repository.RebaseHistoryRepository;
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
 * REST controller for managing {@link com.akounts.myapp.domain.RebaseHistory}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RebaseHistoryResource {

    private final Logger log = LoggerFactory.getLogger(RebaseHistoryResource.class);

    private static final String ENTITY_NAME = "rebaseHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RebaseHistoryRepository rebaseHistoryRepository;

    public RebaseHistoryResource(RebaseHistoryRepository rebaseHistoryRepository) {
        this.rebaseHistoryRepository = rebaseHistoryRepository;
    }

    /**
     * {@code POST  /rebase-histories} : Create a new rebaseHistory.
     *
     * @param rebaseHistory the rebaseHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rebaseHistory, or with status {@code 400 (Bad Request)} if the rebaseHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rebase-histories")
    public ResponseEntity<RebaseHistory> createRebaseHistory(@RequestBody RebaseHistory rebaseHistory) throws URISyntaxException {
        log.debug("REST request to save RebaseHistory : {}", rebaseHistory);
        if (rebaseHistory.getId() != null) {
            throw new BadRequestAlertException("A new rebaseHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RebaseHistory result = rebaseHistoryRepository.save(rebaseHistory);
        return ResponseEntity
            .created(new URI("/api/rebase-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rebase-histories/:id} : Updates an existing rebaseHistory.
     *
     * @param id the id of the rebaseHistory to save.
     * @param rebaseHistory the rebaseHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rebaseHistory,
     * or with status {@code 400 (Bad Request)} if the rebaseHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rebaseHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rebase-histories/{id}")
    public ResponseEntity<RebaseHistory> updateRebaseHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RebaseHistory rebaseHistory
    ) throws URISyntaxException {
        log.debug("REST request to update RebaseHistory : {}, {}", id, rebaseHistory);
        if (rebaseHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rebaseHistory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rebaseHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RebaseHistory result = rebaseHistoryRepository.save(rebaseHistory);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rebaseHistory.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rebase-histories/:id} : Partial updates given fields of an existing rebaseHistory, field will ignore if it is null
     *
     * @param id the id of the rebaseHistory to save.
     * @param rebaseHistory the rebaseHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rebaseHistory,
     * or with status {@code 400 (Bad Request)} if the rebaseHistory is not valid,
     * or with status {@code 404 (Not Found)} if the rebaseHistory is not found,
     * or with status {@code 500 (Internal Server Error)} if the rebaseHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rebase-histories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RebaseHistory> partialUpdateRebaseHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RebaseHistory rebaseHistory
    ) throws URISyntaxException {
        log.debug("REST request to partial update RebaseHistory partially : {}, {}", id, rebaseHistory);
        if (rebaseHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rebaseHistory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rebaseHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RebaseHistory> result = rebaseHistoryRepository
            .findById(rebaseHistory.getId())
            .map(existingRebaseHistory -> {
                if (rebaseHistory.getOldValue() != null) {
                    existingRebaseHistory.setOldValue(rebaseHistory.getOldValue());
                }
                if (rebaseHistory.getOldCurrency() != null) {
                    existingRebaseHistory.setOldCurrency(rebaseHistory.getOldCurrency());
                }
                if (rebaseHistory.getNewValue() != null) {
                    existingRebaseHistory.setNewValue(rebaseHistory.getNewValue());
                }
                if (rebaseHistory.getNewCurrency() != null) {
                    existingRebaseHistory.setNewCurrency(rebaseHistory.getNewCurrency());
                }
                if (rebaseHistory.getRunDate() != null) {
                    existingRebaseHistory.setRunDate(rebaseHistory.getRunDate());
                }

                return existingRebaseHistory;
            })
            .map(rebaseHistoryRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rebaseHistory.getId().toString())
        );
    }

    /**
     * {@code GET  /rebase-histories} : get all the rebaseHistories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rebaseHistories in body.
     */
    @GetMapping("/rebase-histories")
    public List<RebaseHistory> getAllRebaseHistories() {
        log.debug("REST request to get all RebaseHistories");
        return rebaseHistoryRepository.findAll();
    }

    /**
     * {@code GET  /rebase-histories/:id} : get the "id" rebaseHistory.
     *
     * @param id the id of the rebaseHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rebaseHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rebase-histories/{id}")
    public ResponseEntity<RebaseHistory> getRebaseHistory(@PathVariable Long id) {
        log.debug("REST request to get RebaseHistory : {}", id);
        Optional<RebaseHistory> rebaseHistory = rebaseHistoryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rebaseHistory);
    }

    /**
     * {@code DELETE  /rebase-histories/:id} : delete the "id" rebaseHistory.
     *
     * @param id the id of the rebaseHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rebase-histories/{id}")
    public ResponseEntity<Void> deleteRebaseHistory(@PathVariable Long id) {
        log.debug("REST request to delete RebaseHistory : {}", id);
        rebaseHistoryRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
