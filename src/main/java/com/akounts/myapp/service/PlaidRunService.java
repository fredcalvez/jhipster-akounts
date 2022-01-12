package com.akounts.myapp.service;

import com.akounts.myapp.domain.PlaidRun;
import com.akounts.myapp.repository.PlaidRunRepository;
import com.akounts.myapp.service.dto.PlaidRunDTO;
import com.akounts.myapp.service.mapper.PlaidRunMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PlaidRun}.
 */
@Service
@Transactional
public class PlaidRunService {

    private final Logger log = LoggerFactory.getLogger(PlaidRunService.class);

    private final PlaidRunRepository plaidRunRepository;

    private final PlaidRunMapper plaidRunMapper;

    public PlaidRunService(PlaidRunRepository plaidRunRepository, PlaidRunMapper plaidRunMapper) {
        this.plaidRunRepository = plaidRunRepository;
        this.plaidRunMapper = plaidRunMapper;
    }

    /**
     * Save a plaidRun.
     *
     * @param plaidRunDTO the entity to save.
     * @return the persisted entity.
     */
    public PlaidRunDTO save(PlaidRunDTO plaidRunDTO) {
        log.debug("Request to save PlaidRun : {}", plaidRunDTO);
        PlaidRun plaidRun = plaidRunMapper.toEntity(plaidRunDTO);
        plaidRun = plaidRunRepository.save(plaidRun);
        return plaidRunMapper.toDto(plaidRun);
    }

    /**
     * Partially update a plaidRun.
     *
     * @param plaidRunDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PlaidRunDTO> partialUpdate(PlaidRunDTO plaidRunDTO) {
        log.debug("Request to partially update PlaidRun : {}", plaidRunDTO);

        return plaidRunRepository
            .findById(plaidRunDTO.getId())
            .map(existingPlaidRun -> {
                plaidRunMapper.partialUpdate(existingPlaidRun, plaidRunDTO);

                return existingPlaidRun;
            })
            .map(plaidRunRepository::save)
            .map(plaidRunMapper::toDto);
    }

    /**
     * Get all the plaidRuns.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PlaidRunDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PlaidRuns");
        return plaidRunRepository.findAll(pageable).map(plaidRunMapper::toDto);
    }

    /**
     * Get one plaidRun by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PlaidRunDTO> findOne(Long id) {
        log.debug("Request to get PlaidRun : {}", id);
        return plaidRunRepository.findById(id).map(plaidRunMapper::toDto);
    }

    /**
     * Delete the plaidRun by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PlaidRun : {}", id);
        plaidRunRepository.deleteById(id);
    }
}
