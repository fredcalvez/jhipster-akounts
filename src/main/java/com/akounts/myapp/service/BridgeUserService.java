package com.akounts.myapp.service;

import com.akounts.myapp.domain.BridgeUser;
import com.akounts.myapp.repository.BridgeUserRepository;
import com.akounts.myapp.service.dto.BridgeUserDTO;
import com.akounts.myapp.service.mapper.BridgeUserMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BridgeUser}.
 */
@Service
@Transactional
public class BridgeUserService {

    private final Logger log = LoggerFactory.getLogger(BridgeUserService.class);

    private final BridgeUserRepository bridgeUserRepository;

    private final BridgeUserMapper bridgeUserMapper;

    public BridgeUserService(BridgeUserRepository bridgeUserRepository, BridgeUserMapper bridgeUserMapper) {
        this.bridgeUserRepository = bridgeUserRepository;
        this.bridgeUserMapper = bridgeUserMapper;
    }

    /**
     * Save a bridgeUser.
     *
     * @param bridgeUserDTO the entity to save.
     * @return the persisted entity.
     */
    public BridgeUserDTO save(BridgeUserDTO bridgeUserDTO) {
        log.debug("Request to save BridgeUser : {}", bridgeUserDTO);
        BridgeUser bridgeUser = bridgeUserMapper.toEntity(bridgeUserDTO);
        bridgeUser = bridgeUserRepository.save(bridgeUser);
        return bridgeUserMapper.toDto(bridgeUser);
    }

    /**
     * Partially update a bridgeUser.
     *
     * @param bridgeUserDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BridgeUserDTO> partialUpdate(BridgeUserDTO bridgeUserDTO) {
        log.debug("Request to partially update BridgeUser : {}", bridgeUserDTO);

        return bridgeUserRepository
            .findById(bridgeUserDTO.getId())
            .map(existingBridgeUser -> {
                bridgeUserMapper.partialUpdate(existingBridgeUser, bridgeUserDTO);

                return existingBridgeUser;
            })
            .map(bridgeUserRepository::save)
            .map(bridgeUserMapper::toDto);
    }

    /**
     * Get all the bridgeUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BridgeUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BridgeUsers");
        return bridgeUserRepository.findAll(pageable).map(bridgeUserMapper::toDto);
    }

    /**
     * Get one bridgeUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BridgeUserDTO> findOne(Long id) {
        log.debug("Request to get BridgeUser : {}", id);
        return bridgeUserRepository.findById(id).map(bridgeUserMapper::toDto);
    }

    /**
     * Delete the bridgeUser by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BridgeUser : {}", id);
        bridgeUserRepository.deleteById(id);
    }
}
