package com.akounts.myapp.web.rest;

import com.akounts.myapp.repository.FileImportRepository;
import com.akounts.myapp.service.FileImportService;
import com.akounts.myapp.service.dto.FileImportDTO;
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
 * REST controller for managing {@link com.akounts.myapp.domain.FileImport}.
 */
@RestController
@RequestMapping("/api")
public class FileImportResource {

    private final Logger log = LoggerFactory.getLogger(FileImportResource.class);

    private static final String ENTITY_NAME = "fileImport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FileImportService fileImportService;

    private final FileImportRepository fileImportRepository;

    public FileImportResource(FileImportService fileImportService, FileImportRepository fileImportRepository) {
        this.fileImportService = fileImportService;
        this.fileImportRepository = fileImportRepository;
    }

    /**
     * {@code POST  /file-imports} : Create a new fileImport.
     *
     * @param fileImportDTO the fileImportDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fileImportDTO, or with status {@code 400 (Bad Request)} if the fileImport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/file-imports")
    public ResponseEntity<FileImportDTO> createFileImport(@RequestBody FileImportDTO fileImportDTO) throws URISyntaxException {
        log.debug("REST request to save FileImport : {}", fileImportDTO);
        if (fileImportDTO.getId() != null) {
            throw new BadRequestAlertException("A new fileImport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FileImportDTO result = fileImportService.save(fileImportDTO);
        return ResponseEntity
            .created(new URI("/api/file-imports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /file-imports/:id} : Updates an existing fileImport.
     *
     * @param id the id of the fileImportDTO to save.
     * @param fileImportDTO the fileImportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fileImportDTO,
     * or with status {@code 400 (Bad Request)} if the fileImportDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fileImportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/file-imports/{id}")
    public ResponseEntity<FileImportDTO> updateFileImport(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FileImportDTO fileImportDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FileImport : {}, {}", id, fileImportDTO);
        if (fileImportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fileImportDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fileImportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FileImportDTO result = fileImportService.save(fileImportDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fileImportDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /file-imports/:id} : Partial updates given fields of an existing fileImport, field will ignore if it is null
     *
     * @param id the id of the fileImportDTO to save.
     * @param fileImportDTO the fileImportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fileImportDTO,
     * or with status {@code 400 (Bad Request)} if the fileImportDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fileImportDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fileImportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/file-imports/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FileImportDTO> partialUpdateFileImport(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FileImportDTO fileImportDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FileImport partially : {}, {}", id, fileImportDTO);
        if (fileImportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fileImportDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fileImportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FileImportDTO> result = fileImportService.partialUpdate(fileImportDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fileImportDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /file-imports} : get all the fileImports.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fileImports in body.
     */
    @GetMapping("/file-imports")
    public ResponseEntity<List<FileImportDTO>> getAllFileImports(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of FileImports");
        Page<FileImportDTO> page = fileImportService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /file-imports/:id} : get the "id" fileImport.
     *
     * @param id the id of the fileImportDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fileImportDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/file-imports/{id}")
    public ResponseEntity<FileImportDTO> getFileImport(@PathVariable Long id) {
        log.debug("REST request to get FileImport : {}", id);
        Optional<FileImportDTO> fileImportDTO = fileImportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fileImportDTO);
    }

    /**
     * {@code DELETE  /file-imports/:id} : delete the "id" fileImport.
     *
     * @param id the id of the fileImportDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/file-imports/{id}")
    public ResponseEntity<Void> deleteFileImport(@PathVariable Long id) {
        log.debug("REST request to delete FileImport : {}", id);
        fileImportService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
