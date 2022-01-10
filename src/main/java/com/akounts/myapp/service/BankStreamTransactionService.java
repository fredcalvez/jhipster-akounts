package com.akounts.myapp.service;

import com.akounts.myapp.domain.BankStreamTransaction;
import com.akounts.myapp.repository.BankStreamTransactionRepository;
import com.akounts.myapp.service.dto.BankStreamTransactionDTO;
import com.akounts.myapp.service.mapper.BankStreamTransactionMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BankStreamTransactionDTO> findAll() {
        log.debug("Request to get all BankStreamTransactions");
        return bankStreamTransactionRepository
            .findAll()
            .stream()
            .map(bankStreamTransactionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
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
