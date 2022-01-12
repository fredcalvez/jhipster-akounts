package com.akounts.myapp.web.rest;

import com.akounts.myapp.repository.AkountsSettingsRepository;
import com.akounts.myapp.service.AkountsSettingsService;
import com.akounts.myapp.service.dto.AkountsSettingsDTO;
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
 * REST controller for managing {@link com.akounts.myapp.domain.AkountsSettings}.
 */
@RestController
@RequestMapping("/api")
public class AkountsSettingsResource {

    private final Logger log = LoggerFactory.getLogger(AkountsSettingsResource.class);

    private static final String ENTITY_NAME = "akountsSettings";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AkountsSettingsService akountsSettingsService;

    private final AkountsSettingsRepository akountsSettingsRepository;

    public AkountsSettingsResource(AkountsSettingsService akountsSettingsService, AkountsSettingsRepository akountsSettingsRepository) {
        this.akountsSettingsService = akountsSettingsService;
        this.akountsSettingsRepository = akountsSettingsRepository;
    }

    /**
     * {@code POST  /akounts-settings} : Create a new akountsSettings.
     *
     * @param akountsSettingsDTO the akountsSettingsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new akountsSettingsDTO, or with status {@code 400 (Bad Request)} if the akountsSettings has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/akounts-settings")
    public ResponseEntity<AkountsSettingsDTO> createAkountsSettings(@RequestBody AkountsSettingsDTO akountsSettingsDTO)
        throws URISyntaxException {
        log.debug("REST request to save AkountsSettings : {}", akountsSettingsDTO);
        if (akountsSettingsDTO.getId() != null) {
            throw new BadRequestAlertException("A new akountsSettings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AkountsSettingsDTO result = akountsSettingsService.save(akountsSettingsDTO);
        return ResponseEntity
            .created(new URI("/api/akounts-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /akounts-settings/:id} : Updates an existing akountsSettings.
     *
     * @param id the id of the akountsSettingsDTO to save.
     * @param akountsSettingsDTO the akountsSettingsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated akountsSettingsDTO,
     * or with status {@code 400 (Bad Request)} if the akountsSettingsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the akountsSettingsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/akounts-settings/{id}")
    public ResponseEntity<AkountsSettingsDTO> updateAkountsSettings(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AkountsSettingsDTO akountsSettingsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AkountsSettings : {}, {}", id, akountsSettingsDTO);
        if (akountsSettingsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, akountsSettingsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!akountsSettingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AkountsSettingsDTO result = akountsSettingsService.save(akountsSettingsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, akountsSettingsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /akounts-settings/:id} : Partial updates given fields of an existing akountsSettings, field will ignore if it is null
     *
     * @param id the id of the akountsSettingsDTO to save.
     * @param akountsSettingsDTO the akountsSettingsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated akountsSettingsDTO,
     * or with status {@code 400 (Bad Request)} if the akountsSettingsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the akountsSettingsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the akountsSettingsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/akounts-settings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AkountsSettingsDTO> partialUpdateAkountsSettings(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AkountsSettingsDTO akountsSettingsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AkountsSettings partially : {}, {}", id, akountsSettingsDTO);
        if (akountsSettingsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, akountsSettingsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!akountsSettingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AkountsSettingsDTO> result = akountsSettingsService.partialUpdate(akountsSettingsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, akountsSettingsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /akounts-settings} : get all the akountsSettings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of akountsSettings in body.
     */
    @GetMapping("/akounts-settings")
    public ResponseEntity<List<AkountsSettingsDTO>> getAllAkountsSettings(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of AkountsSettings");
        Page<AkountsSettingsDTO> page = akountsSettingsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /akounts-settings/:id} : get the "id" akountsSettings.
     *
     * @param id the id of the akountsSettingsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the akountsSettingsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/akounts-settings/{id}")
    public ResponseEntity<AkountsSettingsDTO> getAkountsSettings(@PathVariable Long id) {
        log.debug("REST request to get AkountsSettings : {}", id);
        Optional<AkountsSettingsDTO> akountsSettingsDTO = akountsSettingsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(akountsSettingsDTO);
    }

    /**
     * {@code DELETE  /akounts-settings/:id} : delete the "id" akountsSettings.
     *
     * @param id the id of the akountsSettingsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/akounts-settings/{id}")
    public ResponseEntity<Void> deleteAkountsSettings(@PathVariable Long id) {
        log.debug("REST request to delete AkountsSettings : {}", id);
        akountsSettingsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
