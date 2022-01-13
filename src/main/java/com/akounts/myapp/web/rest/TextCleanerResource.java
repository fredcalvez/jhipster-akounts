package com.akounts.myapp.web.rest;

import com.akounts.myapp.repository.TextCleanerRepository;
import com.akounts.myapp.service.TextCleanerService;
import com.akounts.myapp.service.dto.TextCleanerDTO;
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
 * REST controller for managing {@link com.akounts.myapp.domain.TextCleaner}.
 */
@RestController
@RequestMapping("/api")
public class TextCleanerResource {

    private final Logger log = LoggerFactory.getLogger(TextCleanerResource.class);

    private static final String ENTITY_NAME = "textCleaner";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TextCleanerService textCleanerService;

    private final TextCleanerRepository textCleanerRepository;

    public TextCleanerResource(TextCleanerService textCleanerService, TextCleanerRepository textCleanerRepository) {
        this.textCleanerService = textCleanerService;
        this.textCleanerRepository = textCleanerRepository;
    }

    /**
     * {@code POST  /text-cleaners} : Create a new textCleaner.
     *
     * @param textCleanerDTO the textCleanerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new textCleanerDTO, or with status {@code 400 (Bad Request)} if the textCleaner has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/text-cleaners")
    public ResponseEntity<TextCleanerDTO> createTextCleaner(@RequestBody TextCleanerDTO textCleanerDTO) throws URISyntaxException {
        log.debug("REST request to save TextCleaner : {}", textCleanerDTO);
        if (textCleanerDTO.getId() != null) {
            throw new BadRequestAlertException("A new textCleaner cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TextCleanerDTO result = textCleanerService.save(textCleanerDTO);
        return ResponseEntity
            .created(new URI("/api/text-cleaners/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /text-cleaners/:id} : Updates an existing textCleaner.
     *
     * @param id the id of the textCleanerDTO to save.
     * @param textCleanerDTO the textCleanerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated textCleanerDTO,
     * or with status {@code 400 (Bad Request)} if the textCleanerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the textCleanerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/text-cleaners/{id}")
    public ResponseEntity<TextCleanerDTO> updateTextCleaner(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TextCleanerDTO textCleanerDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TextCleaner : {}, {}", id, textCleanerDTO);
        if (textCleanerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, textCleanerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!textCleanerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TextCleanerDTO result = textCleanerService.save(textCleanerDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, textCleanerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /text-cleaners/:id} : Partial updates given fields of an existing textCleaner, field will ignore if it is null
     *
     * @param id the id of the textCleanerDTO to save.
     * @param textCleanerDTO the textCleanerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated textCleanerDTO,
     * or with status {@code 400 (Bad Request)} if the textCleanerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the textCleanerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the textCleanerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/text-cleaners/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TextCleanerDTO> partialUpdateTextCleaner(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TextCleanerDTO textCleanerDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TextCleaner partially : {}, {}", id, textCleanerDTO);
        if (textCleanerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, textCleanerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!textCleanerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TextCleanerDTO> result = textCleanerService.partialUpdate(textCleanerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, textCleanerDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /text-cleaners} : get all the textCleaners.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of textCleaners in body.
     */
    @GetMapping("/text-cleaners")
    public ResponseEntity<List<TextCleanerDTO>> getAllTextCleaners(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TextCleaners");
        Page<TextCleanerDTO> page = textCleanerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /text-cleaners/:id} : get the "id" textCleaner.
     *
     * @param id the id of the textCleanerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the textCleanerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/text-cleaners/{id}")
    public ResponseEntity<TextCleanerDTO> getTextCleaner(@PathVariable Long id) {
        log.debug("REST request to get TextCleaner : {}", id);
        Optional<TextCleanerDTO> textCleanerDTO = textCleanerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(textCleanerDTO);
    }

    /**
     * {@code DELETE  /text-cleaners/:id} : delete the "id" textCleaner.
     *
     * @param id the id of the textCleanerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/text-cleaners/{id}")
    public ResponseEntity<Void> deleteTextCleaner(@PathVariable Long id) {
        log.debug("REST request to delete TextCleaner : {}", id);
        textCleanerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
