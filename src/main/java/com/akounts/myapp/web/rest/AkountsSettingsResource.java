package com.akounts.myapp.web.rest;

import com.akounts.myapp.domain.AkountsSettings;
import com.akounts.myapp.repository.AkountsSettingsRepository;
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
 * REST controller for managing {@link com.akounts.myapp.domain.AkountsSettings}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AkountsSettingsResource {

    private final Logger log = LoggerFactory.getLogger(AkountsSettingsResource.class);

    private static final String ENTITY_NAME = "akountsSettings";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AkountsSettingsRepository akountsSettingsRepository;

    public AkountsSettingsResource(AkountsSettingsRepository akountsSettingsRepository) {
        this.akountsSettingsRepository = akountsSettingsRepository;
    }

    /**
     * {@code POST  /akounts-settings} : Create a new akountsSettings.
     *
     * @param akountsSettings the akountsSettings to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new akountsSettings, or with status {@code 400 (Bad Request)} if the akountsSettings has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/akounts-settings")
    public ResponseEntity<AkountsSettings> createAkountsSettings(@RequestBody AkountsSettings akountsSettings) throws URISyntaxException {
        log.debug("REST request to save AkountsSettings : {}", akountsSettings);
        if (akountsSettings.getId() != null) {
            throw new BadRequestAlertException("A new akountsSettings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AkountsSettings result = akountsSettingsRepository.save(akountsSettings);
        return ResponseEntity
            .created(new URI("/api/akounts-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /akounts-settings/:id} : Updates an existing akountsSettings.
     *
     * @param id the id of the akountsSettings to save.
     * @param akountsSettings the akountsSettings to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated akountsSettings,
     * or with status {@code 400 (Bad Request)} if the akountsSettings is not valid,
     * or with status {@code 500 (Internal Server Error)} if the akountsSettings couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/akounts-settings/{id}")
    public ResponseEntity<AkountsSettings> updateAkountsSettings(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AkountsSettings akountsSettings
    ) throws URISyntaxException {
        log.debug("REST request to update AkountsSettings : {}, {}", id, akountsSettings);
        if (akountsSettings.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, akountsSettings.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!akountsSettingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AkountsSettings result = akountsSettingsRepository.save(akountsSettings);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, akountsSettings.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /akounts-settings/:id} : Partial updates given fields of an existing akountsSettings, field will ignore if it is null
     *
     * @param id the id of the akountsSettings to save.
     * @param akountsSettings the akountsSettings to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated akountsSettings,
     * or with status {@code 400 (Bad Request)} if the akountsSettings is not valid,
     * or with status {@code 404 (Not Found)} if the akountsSettings is not found,
     * or with status {@code 500 (Internal Server Error)} if the akountsSettings couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/akounts-settings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AkountsSettings> partialUpdateAkountsSettings(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AkountsSettings akountsSettings
    ) throws URISyntaxException {
        log.debug("REST request to partial update AkountsSettings partially : {}, {}", id, akountsSettings);
        if (akountsSettings.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, akountsSettings.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!akountsSettingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AkountsSettings> result = akountsSettingsRepository
            .findById(akountsSettings.getId())
            .map(existingAkountsSettings -> {
                if (akountsSettings.getSettingKey() != null) {
                    existingAkountsSettings.setSettingKey(akountsSettings.getSettingKey());
                }
                if (akountsSettings.getSettingVal() != null) {
                    existingAkountsSettings.setSettingVal(akountsSettings.getSettingVal());
                }

                return existingAkountsSettings;
            })
            .map(akountsSettingsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, akountsSettings.getId().toString())
        );
    }

    /**
     * {@code GET  /akounts-settings} : get all the akountsSettings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of akountsSettings in body.
     */
    @GetMapping("/akounts-settings")
    public List<AkountsSettings> getAllAkountsSettings() {
        log.debug("REST request to get all AkountsSettings");
        return akountsSettingsRepository.findAll();
    }

    /**
     * {@code GET  /akounts-settings/:id} : get the "id" akountsSettings.
     *
     * @param id the id of the akountsSettings to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the akountsSettings, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/akounts-settings/{id}")
    public ResponseEntity<AkountsSettings> getAkountsSettings(@PathVariable Long id) {
        log.debug("REST request to get AkountsSettings : {}", id);
        Optional<AkountsSettings> akountsSettings = akountsSettingsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(akountsSettings);
    }

    /**
     * {@code DELETE  /akounts-settings/:id} : delete the "id" akountsSettings.
     *
     * @param id the id of the akountsSettings to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/akounts-settings/{id}")
    public ResponseEntity<Void> deleteAkountsSettings(@PathVariable Long id) {
        log.debug("REST request to delete AkountsSettings : {}", id);
        akountsSettingsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
