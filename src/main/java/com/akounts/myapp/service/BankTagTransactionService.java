package com.akounts.myapp.service;

import com.akounts.myapp.domain.BankTagTransaction;
import com.akounts.myapp.repository.BankTagTransactionRepository;
import com.akounts.myapp.service.dto.BankTagTransactionDTO;
import com.akounts.myapp.service.mapper.BankTagTransactionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BankTagTransaction}.
 */
@Service
@Transactional
public class BankTagTransactionService {

    private final Logger log = LoggerFactory.getLogger(BankTagTransactionService.class);

    private final BankTagTransactionRepository bankTagTransactionRepository;

    private final BankTagTransactionMapper bankTagTransactionMapper;

    public BankTagTransactionService(
        BankTagTransactionRepository bankTagTransactionRepository,
        BankTagTransactionMapper bankTagTransactionMapper
    ) {
        this.bankTagTransactionRepository = bankTagTransactionRepository;
        this.bankTagTransactionMapper = bankTagTransactionMapper;
    }

    /**
     * Save a bankTagTransaction.
     *
     * @param bankTagTransactionDTO the entity to save.
     * @return the persisted entity.
     */
    public BankTagTransactionDTO save(BankTagTransactionDTO bankTagTransactionDTO) {
        log.debug("Request to save BankTagTransaction : {}", bankTagTransactionDTO);
        BankTagTransaction bankTagTransaction = bankTagTransactionMapper.toEntity(bankTagTransactionDTO);
        bankTagTransaction = bankTagTransactionRepository.save(bankTagTransaction);
        return bankTagTransactionMapper.toDto(bankTagTransaction);
    }

    /**
     * Partially update a bankTagTransaction.
     *
     * @param bankTagTransactionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BankTagTransactionDTO> partialUpdate(BankTagTransactionDTO bankTagTransactionDTO) {
        log.debug("Request to partially update BankTagTransaction : {}", bankTagTransactionDTO);

        return bankTagTransactionRepository
            .findById(bankTagTransactionDTO.getId())
            .map(existingBankTagTransaction -> {
                bankTagTransactionMapper.partialUpdate(existingBankTagTransaction, bankTagTransactionDTO);

                return existingBankTagTransaction;
            })
            .map(bankTagTransactionRepository::save)
            .map(bankTagTransactionMapper::toDto);
    }

    /**
     * Get all the bankTagTransactions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BankTagTransactionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BankTagTransactions");
        return bankTagTransactionRepository.findAll(pageable).map(bankTagTransactionMapper::toDto);
    }

    /**
     * Get one bankTagTransaction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BankTagTransactionDTO> findOne(Long id) {
        log.debug("Request to get BankTagTransaction : {}", id);
        return bankTagTransactionRepository.findById(id).map(bankTagTransactionMapper::toDto);
    }

    /**
     * Delete the bankTagTransaction by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BankTagTransaction : {}", id);
        bankTagTransactionRepository.deleteById(id);
    }
}
