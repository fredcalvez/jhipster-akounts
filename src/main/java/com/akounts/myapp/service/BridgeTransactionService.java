package com.akounts.myapp.service;

import com.akounts.myapp.domain.BridgeTransaction;
import com.akounts.myapp.repository.BridgeTransactionRepository;
import com.akounts.myapp.service.dto.BridgeTransactionDTO;
import com.akounts.myapp.service.mapper.BridgeTransactionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BridgeTransaction}.
 */
@Service
@Transactional
public class BridgeTransactionService {

    private final Logger log = LoggerFactory.getLogger(BridgeTransactionService.class);

    private final BridgeTransactionRepository bridgeTransactionRepository;

    private final BridgeTransactionMapper bridgeTransactionMapper;

    public BridgeTransactionService(
        BridgeTransactionRepository bridgeTransactionRepository,
        BridgeTransactionMapper bridgeTransactionMapper
    ) {
        this.bridgeTransactionRepository = bridgeTransactionRepository;
        this.bridgeTransactionMapper = bridgeTransactionMapper;
    }

    /**
     * Save a bridgeTransaction.
     *
     * @param bridgeTransactionDTO the entity to save.
     * @return the persisted entity.
     */
    public BridgeTransactionDTO save(BridgeTransactionDTO bridgeTransactionDTO) {
        log.debug("Request to save BridgeTransaction : {}", bridgeTransactionDTO);
        BridgeTransaction bridgeTransaction = bridgeTransactionMapper.toEntity(bridgeTransactionDTO);
        bridgeTransaction = bridgeTransactionRepository.save(bridgeTransaction);
        return bridgeTransactionMapper.toDto(bridgeTransaction);
    }

    /**
     * Partially update a bridgeTransaction.
     *
     * @param bridgeTransactionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BridgeTransactionDTO> partialUpdate(BridgeTransactionDTO bridgeTransactionDTO) {
        log.debug("Request to partially update BridgeTransaction : {}", bridgeTransactionDTO);

        return bridgeTransactionRepository
            .findById(bridgeTransactionDTO.getId())
            .map(existingBridgeTransaction -> {
                bridgeTransactionMapper.partialUpdate(existingBridgeTransaction, bridgeTransactionDTO);

                return existingBridgeTransaction;
            })
            .map(bridgeTransactionRepository::save)
            .map(bridgeTransactionMapper::toDto);
    }

    /**
     * Get all the bridgeTransactions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BridgeTransactionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BridgeTransactions");
        return bridgeTransactionRepository.findAll(pageable).map(bridgeTransactionMapper::toDto);
    }

    /**
     * Get one bridgeTransaction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BridgeTransactionDTO> findOne(Long id) {
        log.debug("Request to get BridgeTransaction : {}", id);
        return bridgeTransactionRepository.findById(id).map(bridgeTransactionMapper::toDto);
    }

    /**
     * Delete the bridgeTransaction by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BridgeTransaction : {}", id);
        bridgeTransactionRepository.deleteById(id);
    }
}
