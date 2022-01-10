package com.akounts.myapp.service;

import com.akounts.myapp.domain.BankStreamTransaction;
import com.akounts.myapp.repository.BankStreamTransactionRepository;
import com.akounts.myapp.service.dto.BankStreamTransactionDTO;
import com.akounts.myapp.service.mapper.BankStreamTransactionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BankStreamTransaction}.
 */
@Service
@Transactional
public class BankStreamTransactionService {

    private final Logger log = LoggerFactory.getLogger(BankStreamTransactionService.class);

    private final BankStreamTransactionRepository bankStreamTransactionRepository;

    private final BankStreamTransactionMapper bankStreamTransactionMapper;

    public BankStreamTransactionService(
        BankStreamTransactionRepository bankStreamTransactionRepository,
        BankStreamTransactionMapper bankStreamTransactionMapper
    ) {
        this.bankStreamTransactionRepository = bankStreamTransactionRepository;
        this.bankStreamTransactionMapper = bankStreamTransactionMapper;
    }

    /**
     * Save a bankStreamTransaction.
     *
     * @param bankStreamTransactionDTO the entity to save.
     * @return the persisted entity.
     */
    public BankStreamTransactionDTO save(BankStreamTransactionDTO bankStreamTransactionDTO) {
        log.debug("Request to save BankStreamTransaction : {}", bankStreamTransactionDTO);
        BankStreamTransaction bankStreamTransaction = bankStreamTransactionMapper.toEntity(bankStreamTransactionDTO);
        bankStreamTransaction = bankStreamTransactionRepository.save(bankStreamTransaction);
        return bankStreamTransactionMapper.toDto(bankStreamTransaction);
    }

    /**
     * Partially update a bankStreamTransaction.
     *
     * @param bankStreamTransactionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BankStreamTransactionDTO> partialUpdate(BankStreamTransactionDTO bankStreamTransactionDTO) {
        log.debug("Request to partially update BankStreamTransaction : {}", bankStreamTransactionDTO);

        return bankStreamTransactionRepository
            .findById(bankStreamTransactionDTO.getId())
            .map(existingBankStreamTransaction -> {
                bankStreamTransactionMapper.partialUpdate(existingBankStreamTransaction, bankStreamTransactionDTO);

                return existingBankStreamTransaction;
            })
            .map(bankStreamTransactionRepository::save)
            .map(bankStreamTransactionMapper::toDto);
    }

    /**
     * Get all the bankStreamTransactions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BankStreamTransactionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BankStreamTransactions");
        return bankStreamTransactionRepository.findAll(pageable).map(bankStreamTransactionMapper::toDto);
    }

    /**
     * Get one bankStreamTransaction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BankStreamTransactionDTO> findOne(Long id) {
        log.debug("Request to get BankStreamTransaction : {}", id);
        return bankStreamTransactionRepository.findById(id).map(bankStreamTransactionMapper::toDto);
    }

    /**
     * Delete the bankStreamTransaction by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BankStreamTransaction : {}", id);
        bankStreamTransactionRepository.deleteById(id);
    }
}
