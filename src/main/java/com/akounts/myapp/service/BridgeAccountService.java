package com.akounts.myapp.service;

import com.akounts.myapp.domain.BridgeAccount;
import com.akounts.myapp.repository.BridgeAccountRepository;
import com.akounts.myapp.service.dto.BridgeAccountDTO;
import com.akounts.myapp.service.mapper.BridgeAccountMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BridgeAccount}.
 */
@Service
@Transactional
public class BridgeAccountService {

    private final Logger log = LoggerFactory.getLogger(BridgeAccountService.class);

    private final BridgeAccountRepository bridgeAccountRepository;

    private final BridgeAccountMapper bridgeAccountMapper;

    public BridgeAccountService(BridgeAccountRepository bridgeAccountRepository, BridgeAccountMapper bridgeAccountMapper) {
        this.bridgeAccountRepository = bridgeAccountRepository;
        this.bridgeAccountMapper = bridgeAccountMapper;
    }

    /**
     * Save a bridgeAccount.
     *
     * @param bridgeAccountDTO the entity to save.
     * @return the persisted entity.
     */
    public BridgeAccountDTO save(BridgeAccountDTO bridgeAccountDTO) {
        log.debug("Request to save BridgeAccount : {}", bridgeAccountDTO);
        BridgeAccount bridgeAccount = bridgeAccountMapper.toEntity(bridgeAccountDTO);
        bridgeAccount = bridgeAccountRepository.save(bridgeAccount);
        return bridgeAccountMapper.toDto(bridgeAccount);
    }

    /**
     * Partially update a bridgeAccount.
     *
     * @param bridgeAccountDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BridgeAccountDTO> partialUpdate(BridgeAccountDTO bridgeAccountDTO) {
        log.debug("Request to partially update BridgeAccount : {}", bridgeAccountDTO);

        return bridgeAccountRepository
            .findById(bridgeAccountDTO.getId())
            .map(existingBridgeAccount -> {
                bridgeAccountMapper.partialUpdate(existingBridgeAccount, bridgeAccountDTO);

                return existingBridgeAccount;
            })
            .map(bridgeAccountRepository::save)
            .map(bridgeAccountMapper::toDto);
    }

    /**
     * Get all the bridgeAccounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BridgeAccountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BridgeAccounts");
        return bridgeAccountRepository.findAll(pageable).map(bridgeAccountMapper::toDto);
    }

    /**
     * Get one bridgeAccount by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BridgeAccountDTO> findOne(Long id) {
        log.debug("Request to get BridgeAccount : {}", id);
        return bridgeAccountRepository.findById(id).map(bridgeAccountMapper::toDto);
    }

    /**
     * Delete the bridgeAccount by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BridgeAccount : {}", id);
        bridgeAccountRepository.deleteById(id);
    }
}
