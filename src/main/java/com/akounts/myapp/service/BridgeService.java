package com.akounts.myapp.service;

import com.akounts.myapp.domain.Bridge;
import com.akounts.myapp.repository.BridgeRepository;
import com.akounts.myapp.service.dto.BridgeDTO;
import com.akounts.myapp.service.mapper.BridgeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Bridge}.
 */
@Service
@Transactional
public class BridgeService {

    private final Logger log = LoggerFactory.getLogger(BridgeService.class);

    private final BridgeRepository bridgeRepository;

    private final BridgeMapper bridgeMapper;

    public BridgeService(BridgeRepository bridgeRepository, BridgeMapper bridgeMapper) {
        this.bridgeRepository = bridgeRepository;
        this.bridgeMapper = bridgeMapper;
    }

    /**
     * Save a bridge.
     *
     * @param bridgeDTO the entity to save.
     * @return the persisted entity.
     */
    public BridgeDTO save(BridgeDTO bridgeDTO) {
        log.debug("Request to save Bridge : {}", bridgeDTO);
        Bridge bridge = bridgeMapper.toEntity(bridgeDTO);
        bridge = bridgeRepository.save(bridge);
        return bridgeMapper.toDto(bridge);
    }

    /**
     * Partially update a bridge.
     *
     * @param bridgeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BridgeDTO> partialUpdate(BridgeDTO bridgeDTO) {
        log.debug("Request to partially update Bridge : {}", bridgeDTO);

        return bridgeRepository
            .findById(bridgeDTO.getId())
            .map(existingBridge -> {
                bridgeMapper.partialUpdate(existingBridge, bridgeDTO);

                return existingBridge;
            })
            .map(bridgeRepository::save)
            .map(bridgeMapper::toDto);
    }

    /**
     * Get all the bridges.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BridgeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Bridges");
        return bridgeRepository.findAll(pageable).map(bridgeMapper::toDto);
    }

    /**
     * Get one bridge by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BridgeDTO> findOne(Long id) {
        log.debug("Request to get Bridge : {}", id);
        return bridgeRepository.findById(id).map(bridgeMapper::toDto);
    }

    /**
     * Delete the bridge by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Bridge : {}", id);
        bridgeRepository.deleteById(id);
    }
}
