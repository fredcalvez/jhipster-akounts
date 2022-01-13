package com.akounts.myapp.service;

import com.akounts.myapp.domain.ProcessRun;
import com.akounts.myapp.repository.ProcessRunRepository;
import com.akounts.myapp.service.dto.ProcessRunDTO;
import com.akounts.myapp.service.mapper.ProcessRunMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProcessRun}.
 */
@Service
@Transactional
public class ProcessRunService {

    private final Logger log = LoggerFactory.getLogger(ProcessRunService.class);

    private final ProcessRunRepository processRunRepository;

    private final ProcessRunMapper processRunMapper;

    public ProcessRunService(ProcessRunRepository processRunRepository, ProcessRunMapper processRunMapper) {
        this.processRunRepository = processRunRepository;
        this.processRunMapper = processRunMapper;
    }

    /**
     * Save a processRun.
     *
     * @param processRunDTO the entity to save.
     * @return the persisted entity.
     */
    public ProcessRunDTO save(ProcessRunDTO processRunDTO) {
        log.debug("Request to save ProcessRun : {}", processRunDTO);
        ProcessRun processRun = processRunMapper.toEntity(processRunDTO);
        processRun = processRunRepository.save(processRun);
        return processRunMapper.toDto(processRun);
    }

    /**
     * Partially update a processRun.
     *
     * @param processRunDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProcessRunDTO> partialUpdate(ProcessRunDTO processRunDTO) {
        log.debug("Request to partially update ProcessRun : {}", processRunDTO);

        return processRunRepository
            .findById(processRunDTO.getId())
            .map(existingProcessRun -> {
                processRunMapper.partialUpdate(existingProcessRun, processRunDTO);

                return existingProcessRun;
            })
            .map(processRunRepository::save)
            .map(processRunMapper::toDto);
    }

    /**
     * Get all the processRuns.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProcessRunDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProcessRuns");
        return processRunRepository.findAll(pageable).map(processRunMapper::toDto);
    }

    /**
     * Get one processRun by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProcessRunDTO> findOne(Long id) {
        log.debug("Request to get ProcessRun : {}", id);
        return processRunRepository.findById(id).map(processRunMapper::toDto);
    }

    /**
     * Delete the processRun by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProcessRun : {}", id);
        processRunRepository.deleteById(id);
    }
}
