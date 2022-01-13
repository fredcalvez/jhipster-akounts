package com.akounts.myapp.service;

import com.akounts.myapp.domain.BankTransaction;
import com.akounts.myapp.repository.BankTransactionRepository;
import com.akounts.myapp.service.dto.BankTransactionDTO;
import com.akounts.myapp.service.mapper.BankTransactionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BankTransaction}.
 */
@Service
@Transactional
public class BankTransactionService {

    private final Logger log = LoggerFactory.getLogger(BankTransactionService.class);

    private final BankTransactionRepository bankTransactionRepository;

    private final BankTransactionMapper bankTransactionMapper;

    public BankTransactionService(BankTransactionRepository bankTransactionRepository, BankTransactionMapper bankTransactionMapper) {
        this.bankTransactionRepository = bankTransactionRepository;
        this.bankTransactionMapper = bankTransactionMapper;
    }

    /**
     * Save a bankTransaction.
     *
     * @param bankTransactionDTO the entity to save.
     * @return the persisted entity.
     */
    public BankTransactionDTO save(BankTransactionDTO bankTransactionDTO) {
        log.debug("Request to save BankTransaction : {}", bankTransactionDTO);
        BankTransaction bankTransaction = bankTransactionMapper.toEntity(bankTransactionDTO);
        bankTransaction = bankTransactionRepository.save(bankTransaction);
        return bankTransactionMapper.toDto(bankTransaction);
    }

    /**
     * Partially update a bankTransaction.
     *
     * @param bankTransactionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BankTransactionDTO> partialUpdate(BankTransactionDTO bankTransactionDTO) {
        log.debug("Request to partially update BankTransaction : {}", bankTransactionDTO);

        return bankTransactionRepository
            .findById(bankTransactionDTO.getId())
            .map(existingBankTransaction -> {
                bankTransactionMapper.partialUpdate(existingBankTransaction, bankTransactionDTO);

                return existingBankTransaction;
            })
            .map(bankTransactionRepository::save)
            .map(bankTransactionMapper::toDto);
    }

    /**
     * Get all the bankTransactions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BankTransactionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BankTransactions");
        return bankTransactionRepository.findAll(pageable).map(bankTransactionMapper::toDto);
    }

    /**
     * Get one bankTransaction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BankTransactionDTO> findOne(Long id) {
        log.debug("Request to get BankTransaction : {}", id);
        return bankTransactionRepository.findById(id).map(bankTransactionMapper::toDto);
    }

    /**
     * Delete the bankTransaction by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BankTransaction : {}", id);
        bankTransactionRepository.deleteById(id);
    }
}
