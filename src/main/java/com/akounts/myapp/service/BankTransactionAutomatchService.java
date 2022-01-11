package com.akounts.myapp.service;

import com.akounts.myapp.domain.BankTransactionAutomatch;
import com.akounts.myapp.repository.BankTransactionAutomatchRepository;
import com.akounts.myapp.service.dto.BankTransactionAutomatchDTO;
import com.akounts.myapp.service.mapper.BankTransactionAutomatchMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BankTransactionAutomatch}.
 */
@Service
@Transactional
public class BankTransactionAutomatchService {

    private final Logger log = LoggerFactory.getLogger(BankTransactionAutomatchService.class);

    private final BankTransactionAutomatchRepository bankTransactionAutomatchRepository;

    private final BankTransactionAutomatchMapper bankTransactionAutomatchMapper;

    public BankTransactionAutomatchService(
        BankTransactionAutomatchRepository bankTransactionAutomatchRepository,
        BankTransactionAutomatchMapper bankTransactionAutomatchMapper
    ) {
        this.bankTransactionAutomatchRepository = bankTransactionAutomatchRepository;
        this.bankTransactionAutomatchMapper = bankTransactionAutomatchMapper;
    }

    /**
     * Save a bankTransactionAutomatch.
     *
     * @param bankTransactionAutomatchDTO the entity to save.
     * @return the persisted entity.
     */
    public BankTransactionAutomatchDTO save(BankTransactionAutomatchDTO bankTransactionAutomatchDTO) {
        log.debug("Request to save BankTransactionAutomatch : {}", bankTransactionAutomatchDTO);
        BankTransactionAutomatch bankTransactionAutomatch = bankTransactionAutomatchMapper.toEntity(bankTransactionAutomatchDTO);
        bankTransactionAutomatch = bankTransactionAutomatchRepository.save(bankTransactionAutomatch);
        return bankTransactionAutomatchMapper.toDto(bankTransactionAutomatch);
    }

    /**
     * Partially update a bankTransactionAutomatch.
     *
     * @param bankTransactionAutomatchDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BankTransactionAutomatchDTO> partialUpdate(BankTransactionAutomatchDTO bankTransactionAutomatchDTO) {
        log.debug("Request to partially update BankTransactionAutomatch : {}", bankTransactionAutomatchDTO);

        return bankTransactionAutomatchRepository
            .findById(bankTransactionAutomatchDTO.getId())
            .map(existingBankTransactionAutomatch -> {
                bankTransactionAutomatchMapper.partialUpdate(existingBankTransactionAutomatch, bankTransactionAutomatchDTO);

                return existingBankTransactionAutomatch;
            })
            .map(bankTransactionAutomatchRepository::save)
            .map(bankTransactionAutomatchMapper::toDto);
    }

    /**
     * Get all the bankTransactionAutomatches.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BankTransactionAutomatchDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BankTransactionAutomatches");
        return bankTransactionAutomatchRepository.findAll(pageable).map(bankTransactionAutomatchMapper::toDto);
    }

    /**
     * Get one bankTransactionAutomatch by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BankTransactionAutomatchDTO> findOne(Long id) {
        log.debug("Request to get BankTransactionAutomatch : {}", id);
        return bankTransactionAutomatchRepository.findById(id).map(bankTransactionAutomatchMapper::toDto);
    }

    /**
     * Delete the bankTransactionAutomatch by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BankTransactionAutomatch : {}", id);
        bankTransactionAutomatchRepository.deleteById(id);
    }
}
