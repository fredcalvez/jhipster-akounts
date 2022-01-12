package com.akounts.myapp.service;

import com.akounts.myapp.domain.TriggerRun;
import com.akounts.myapp.repository.TriggerRunRepository;
import com.akounts.myapp.service.dto.TriggerRunDTO;
import com.akounts.myapp.service.mapper.TriggerRunMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TriggerRun}.
 */
@Service
@Transactional
public class TriggerRunService {

    private final Logger log = LoggerFactory.getLogger(TriggerRunService.class);

    private final TriggerRunRepository triggerRunRepository;

    private final TriggerRunMapper triggerRunMapper;

    public TriggerRunService(TriggerRunRepository triggerRunRepository, TriggerRunMapper triggerRunMapper) {
        this.triggerRunRepository = triggerRunRepository;
        this.triggerRunMapper = triggerRunMapper;
    }

    /**
     * Save a triggerRun.
     *
     * @param triggerRunDTO the entity to save.
     * @return the persisted entity.
     */
    public TriggerRunDTO save(TriggerRunDTO triggerRunDTO) {
        log.debug("Request to save TriggerRun : {}", triggerRunDTO);
        TriggerRun triggerRun = triggerRunMapper.toEntity(triggerRunDTO);
        triggerRun = triggerRunRepository.save(triggerRun);
        return triggerRunMapper.toDto(triggerRun);
    }

    /**
     * Partially update a triggerRun.
     *
     * @param triggerRunDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TriggerRunDTO> partialUpdate(TriggerRunDTO triggerRunDTO) {
        log.debug("Request to partially update TriggerRun : {}", triggerRunDTO);

        return triggerRunRepository
            .findById(triggerRunDTO.getId())
            .map(existingTriggerRun -> {
                triggerRunMapper.partialUpdate(existingTriggerRun, triggerRunDTO);

                return existingTriggerRun;
            })
            .map(triggerRunRepository::save)
            .map(triggerRunMapper::toDto);
    }

    /**
     * Get all the triggerRuns.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TriggerRunDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TriggerRuns");
        return triggerRunRepository.findAll(pageable).map(triggerRunMapper::toDto);
    }

    /**
     * Get one triggerRun by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TriggerRunDTO> findOne(Long id) {
        log.debug("Request to get TriggerRun : {}", id);
        return triggerRunRepository.findById(id).map(triggerRunMapper::toDto);
    }

    /**
     * Delete the triggerRun by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TriggerRun : {}", id);
        triggerRunRepository.deleteById(id);
    }
}
