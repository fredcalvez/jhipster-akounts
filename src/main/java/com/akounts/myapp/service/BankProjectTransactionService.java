package com.akounts.myapp.service;

import com.akounts.myapp.domain.BankProjectTransaction;
import com.akounts.myapp.repository.BankProjectTransactionRepository;
import com.akounts.myapp.service.dto.BankProjectTransactionDTO;
import com.akounts.myapp.service.mapper.BankProjectTransactionMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BankProjectTransaction}.
 */
@Service
@Transactional
public class BankProjectTransactionService {

    private final Logger log = LoggerFactory.getLogger(BankProjectTransactionService.class);

    private final BankProjectTransactionRepository bankProjectTransactionRepository;

    private final BankProjectTransactionMapper bankProjectTransactionMapper;

    public BankProjectTransactionService(
        BankProjectTransactionRepository bankProjectTransactionRepository,
        BankProjectTransactionMapper bankProjectTransactionMapper
    ) {
        this.bankProjectTransactionRepository = bankProjectTransactionRepository;
        this.bankProjectTransactionMapper = bankProjectTransactionMapper;
    }

    /**
     * Save a bankProjectTransaction.
     *
     * @param bankProjectTransactionDTO the entity to save.
     * @return the persisted entity.
     */
    public BankProjectTransactionDTO save(BankProjectTransactionDTO bankProjectTransactionDTO) {
        log.debug("Request to save BankProjectTransaction : {}", bankProjectTransactionDTO);
        BankProjectTransaction bankProjectTransaction = bankProjectTransactionMapper.toEntity(bankProjectTransactionDTO);
        bankProjectTransaction = bankProjectTransactionRepository.save(bankProjectTransaction);
        return bankProjectTransactionMapper.toDto(bankProjectTransaction);
    }

    /**
     * Partially update a bankProjectTransaction.
     *
     * @param bankProjectTransactionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BankProjectTransactionDTO> partialUpdate(BankProjectTransactionDTO bankProjectTransactionDTO) {
        log.debug("Request to partially update BankProjectTransaction : {}", bankProjectTransactionDTO);

        return bankProjectTransactionRepository
            .findById(bankProjectTransactionDTO.getId())
            .map(existingBankProjectTransaction -> {
                bankProjectTransactionMapper.partialUpdate(existingBankProjectTransaction, bankProjectTransactionDTO);

                return existingBankProjectTransaction;
            })
            .map(bankProjectTransactionRepository::save)
            .map(bankProjectTransactionMapper::toDto);
    }

    /**
     * Get all the bankProjectTransactions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BankProjectTransactionDTO> findAll() {
        log.debug("Request to get all BankProjectTransactions");
        return bankProjectTransactionRepository
            .findAll()
            .stream()
            .map(bankProjectTransactionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one bankProjectTransaction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BankProjectTransactionDTO> findOne(Long id) {
        log.debug("Request to get BankProjectTransaction : {}", id);
        return bankProjectTransactionRepository.findById(id).map(bankProjectTransactionMapper::toDto);
    }

    /**
     * Delete the bankProjectTransaction by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BankProjectTransaction : {}", id);
        bankProjectTransactionRepository.deleteById(id);
    }
}
