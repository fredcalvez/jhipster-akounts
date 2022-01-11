package com.akounts.myapp.service;

import com.akounts.myapp.domain.Automatch;
import com.akounts.myapp.repository.AutomatchRepository;
import com.akounts.myapp.service.dto.AutomatchDTO;
import com.akounts.myapp.service.mapper.AutomatchMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Automatch}.
 */
@Service
@Transactional
public class AutomatchService {

    private final Logger log = LoggerFactory.getLogger(AutomatchService.class);

    private final AutomatchRepository automatchRepository;

    private final AutomatchMapper automatchMapper;

    public AutomatchService(AutomatchRepository automatchRepository, AutomatchMapper automatchMapper) {
        this.automatchRepository = automatchRepository;
        this.automatchMapper = automatchMapper;
    }

    /**
     * Save a automatch.
     *
     * @param automatchDTO the entity to save.
     * @return the persisted entity.
     */
    public AutomatchDTO save(AutomatchDTO automatchDTO) {
        log.debug("Request to save Automatch : {}", automatchDTO);
        Automatch automatch = automatchMapper.toEntity(automatchDTO);
        automatch = automatchRepository.save(automatch);
        return automatchMapper.toDto(automatch);
    }

    /**
     * Partially update a automatch.
     *
     * @param automatchDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AutomatchDTO> partialUpdate(AutomatchDTO automatchDTO) {
        log.debug("Request to partially update Automatch : {}", automatchDTO);

        return automatchRepository
            .findById(automatchDTO.getId())
            .map(existingAutomatch -> {
                automatchMapper.partialUpdate(existingAutomatch, automatchDTO);

                return existingAutomatch;
            })
            .map(automatchRepository::save)
            .map(automatchMapper::toDto);
    }

    /**
     * Get all the automatches.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AutomatchDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Automatches");
        return automatchRepository.findAll(pageable).map(automatchMapper::toDto);
    }

    /**
     * Get one automatch by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AutomatchDTO> findOne(Long id) {
        log.debug("Request to get Automatch : {}", id);
        return automatchRepository.findById(id).map(automatchMapper::toDto);
    }

    /**
     * Delete the automatch by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Automatch : {}", id);
        automatchRepository.deleteById(id);
    }
}
