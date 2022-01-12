package com.akounts.myapp.service;

import com.akounts.myapp.domain.BridgeRun;
import com.akounts.myapp.repository.BridgeRunRepository;
import com.akounts.myapp.service.dto.BridgeRunDTO;
import com.akounts.myapp.service.mapper.BridgeRunMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BridgeRun}.
 */
@Service
@Transactional
public class BridgeRunService {

    private final Logger log = LoggerFactory.getLogger(BridgeRunService.class);

    private final BridgeRunRepository bridgeRunRepository;

    private final BridgeRunMapper bridgeRunMapper;

    public BridgeRunService(BridgeRunRepository bridgeRunRepository, BridgeRunMapper bridgeRunMapper) {
        this.bridgeRunRepository = bridgeRunRepository;
        this.bridgeRunMapper = bridgeRunMapper;
    }

    /**
     * Save a bridgeRun.
     *
     * @param bridgeRunDTO the entity to save.
     * @return the persisted entity.
     */
    public BridgeRunDTO save(BridgeRunDTO bridgeRunDTO) {
        log.debug("Request to save BridgeRun : {}", bridgeRunDTO);
        BridgeRun bridgeRun = bridgeRunMapper.toEntity(bridgeRunDTO);
        bridgeRun = bridgeRunRepository.save(bridgeRun);
        return bridgeRunMapper.toDto(bridgeRun);
    }

    /**
     * Partially update a bridgeRun.
     *
     * @param bridgeRunDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BridgeRunDTO> partialUpdate(BridgeRunDTO bridgeRunDTO) {
        log.debug("Request to partially update BridgeRun : {}", bridgeRunDTO);

        return bridgeRunRepository
            .findById(bridgeRunDTO.getId())
            .map(existingBridgeRun -> {
                bridgeRunMapper.partialUpdate(existingBridgeRun, bridgeRunDTO);

                return existingBridgeRun;
            })
            .map(bridgeRunRepository::save)
            .map(bridgeRunMapper::toDto);
    }

    /**
     * Get all the bridgeRuns.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BridgeRunDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BridgeRuns");
        return bridgeRunRepository.findAll(pageable).map(bridgeRunMapper::toDto);
    }

    /**
     * Get one bridgeRun by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BridgeRunDTO> findOne(Long id) {
        log.debug("Request to get BridgeRun : {}", id);
        return bridgeRunRepository.findById(id).map(bridgeRunMapper::toDto);
    }

    /**
     * Delete the bridgeRun by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BridgeRun : {}", id);
        bridgeRunRepository.deleteById(id);
    }
}
