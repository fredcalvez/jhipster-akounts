package com.akounts.myapp.web.rest;

import com.akounts.myapp.repository.BridgeUserRepository;
import com.akounts.myapp.service.BridgeUserService;
import com.akounts.myapp.service.dto.BridgeUserDTO;
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
 * REST controller for managing {@link com.akounts.myapp.domain.BridgeUser}.
 */
@RestController
@RequestMapping("/api")
public class BridgeUserResource {

    private final Logger log = LoggerFactory.getLogger(BridgeUserResource.class);

    private static final String ENTITY_NAME = "bridgeUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BridgeUserService bridgeUserService;

    private final BridgeUserRepository bridgeUserRepository;

    public BridgeUserResource(BridgeUserService bridgeUserService, BridgeUserRepository bridgeUserRepository) {
        this.bridgeUserService = bridgeUserService;
        this.bridgeUserRepository = bridgeUserRepository;
    }

    /**
     * {@code POST  /bridge-users} : Create a new bridgeUser.
     *
     * @param bridgeUserDTO the bridgeUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bridgeUserDTO, or with status {@code 400 (Bad Request)} if the bridgeUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bridge-users")
    public ResponseEntity<BridgeUserDTO> createBridgeUser(@RequestBody BridgeUserDTO bridgeUserDTO) throws URISyntaxException {
        log.debug("REST request to save BridgeUser : {}", bridgeUserDTO);
        if (bridgeUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new bridgeUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BridgeUserDTO result = bridgeUserService.save(bridgeUserDTO);
        return ResponseEntity
            .created(new URI("/api/bridge-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bridge-users/:id} : Updates an existing bridgeUser.
     *
     * @param id the id of the bridgeUserDTO to save.
     * @param bridgeUserDTO the bridgeUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bridgeUserDTO,
     * or with status {@code 400 (Bad Request)} if the bridgeUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bridgeUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bridge-users/{id}")
    public ResponseEntity<BridgeUserDTO> updateBridgeUser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BridgeUserDTO bridgeUserDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BridgeUser : {}, {}", id, bridgeUserDTO);
        if (bridgeUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bridgeUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bridgeUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BridgeUserDTO result = bridgeUserService.save(bridgeUserDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bridgeUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bridge-users/:id} : Partial updates given fields of an existing bridgeUser, field will ignore if it is null
     *
     * @param id the id of the bridgeUserDTO to save.
     * @param bridgeUserDTO the bridgeUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bridgeUserDTO,
     * or with status {@code 400 (Bad Request)} if the bridgeUserDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bridgeUserDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bridgeUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bridge-users/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BridgeUserDTO> partialUpdateBridgeUser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BridgeUserDTO bridgeUserDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BridgeUser partially : {}, {}", id, bridgeUserDTO);
        if (bridgeUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bridgeUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bridgeUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BridgeUserDTO> result = bridgeUserService.partialUpdate(bridgeUserDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bridgeUserDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bridge-users} : get all the bridgeUsers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bridgeUsers in body.
     */
    @GetMapping("/bridge-users")
    public ResponseEntity<List<BridgeUserDTO>> getAllBridgeUsers(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of BridgeUsers");
        Page<BridgeUserDTO> page = bridgeUserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bridge-users/:id} : get the "id" bridgeUser.
     *
     * @param id the id of the bridgeUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bridgeUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bridge-users/{id}")
    public ResponseEntity<BridgeUserDTO> getBridgeUser(@PathVariable Long id) {
        log.debug("REST request to get BridgeUser : {}", id);
        Optional<BridgeUserDTO> bridgeUserDTO = bridgeUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bridgeUserDTO);
    }

    /**
     * {@code DELETE  /bridge-users/:id} : delete the "id" bridgeUser.
     *
     * @param id the id of the bridgeUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bridge-users/{id}")
    public ResponseEntity<Void> deleteBridgeUser(@PathVariable Long id) {
        log.debug("REST request to delete BridgeUser : {}", id);
        bridgeUserService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
