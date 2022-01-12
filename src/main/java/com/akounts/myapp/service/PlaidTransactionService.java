package com.akounts.myapp.service;

import com.akounts.myapp.domain.PlaidTransaction;
import com.akounts.myapp.repository.PlaidTransactionRepository;
import com.akounts.myapp.service.dto.PlaidTransactionDTO;
import com.akounts.myapp.service.mapper.PlaidTransactionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PlaidTransaction}.
 */
@Service
@Transactional
public class PlaidTransactionService {

    private final Logger log = LoggerFactory.getLogger(PlaidTransactionService.class);

    private final PlaidTransactionRepository plaidTransactionRepository;

    private final PlaidTransactionMapper plaidTransactionMapper;

    public PlaidTransactionService(PlaidTransactionRepository plaidTransactionRepository, PlaidTransactionMapper plaidTransactionMapper) {
        this.plaidTransactionRepository = plaidTransactionRepository;
        this.plaidTransactionMapper = plaidTransactionMapper;
    }

    /**
     * Save a plaidTransaction.
     *
     * @param plaidTransactionDTO the entity to save.
     * @return the persisted entity.
     */
    public PlaidTransactionDTO save(PlaidTransactionDTO plaidTransactionDTO) {
        log.debug("Request to save PlaidTransaction : {}", plaidTransactionDTO);
        PlaidTransaction plaidTransaction = plaidTransactionMapper.toEntity(plaidTransactionDTO);
        plaidTransaction = plaidTransactionRepository.save(plaidTransaction);
        return plaidTransactionMapper.toDto(plaidTransaction);
    }

    /**
     * Partially update a plaidTransaction.
     *
     * @param plaidTransactionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PlaidTransactionDTO> partialUpdate(PlaidTransactionDTO plaidTransactionDTO) {
        log.debug("Request to partially update PlaidTransaction : {}", plaidTransactionDTO);

        return plaidTransactionRepository
            .findById(plaidTransactionDTO.getId())
            .map(existingPlaidTransaction -> {
                plaidTransactionMapper.partialUpdate(existingPlaidTransaction, plaidTransactionDTO);

                return existingPlaidTransaction;
            })
            .map(plaidTransactionRepository::save)
            .map(plaidTransactionMapper::toDto);
    }

    /**
     * Get all the plaidTransactions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PlaidTransactionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PlaidTransactions");
        return plaidTransactionRepository.findAll(pageable).map(plaidTransactionMapper::toDto);
    }

    /**
     * Get one plaidTransaction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PlaidTransactionDTO> findOne(Long id) {
        log.debug("Request to get PlaidTransaction : {}", id);
        return plaidTransactionRepository.findById(id).map(plaidTransactionMapper::toDto);
    }

    /**
     * Delete the plaidTransaction by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PlaidTransaction : {}", id);
        plaidTransactionRepository.deleteById(id);
    }
}
