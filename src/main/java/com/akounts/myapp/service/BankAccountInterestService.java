package com.akounts.myapp.service;

import com.akounts.myapp.domain.BankAccountInterest;
import com.akounts.myapp.repository.BankAccountInterestRepository;
import com.akounts.myapp.service.dto.BankAccountInterestDTO;
import com.akounts.myapp.service.mapper.BankAccountInterestMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BankAccountInterest}.
 */
@Service
@Transactional
public class BankAccountInterestService {

    private final Logger log = LoggerFactory.getLogger(BankAccountInterestService.class);

    private final BankAccountInterestRepository bankAccountInterestRepository;

    private final BankAccountInterestMapper bankAccountInterestMapper;

    public BankAccountInterestService(
        BankAccountInterestRepository bankAccountInterestRepository,
        BankAccountInterestMapper bankAccountInterestMapper
    ) {
        this.bankAccountInterestRepository = bankAccountInterestRepository;
        this.bankAccountInterestMapper = bankAccountInterestMapper;
    }

    /**
     * Save a bankAccountInterest.
     *
     * @param bankAccountInterestDTO the entity to save.
     * @return the persisted entity.
     */
    public BankAccountInterestDTO save(BankAccountInterestDTO bankAccountInterestDTO) {
        log.debug("Request to save BankAccountInterest : {}", bankAccountInterestDTO);
        BankAccountInterest bankAccountInterest = bankAccountInterestMapper.toEntity(bankAccountInterestDTO);
        bankAccountInterest = bankAccountInterestRepository.save(bankAccountInterest);
        return bankAccountInterestMapper.toDto(bankAccountInterest);
    }

    /**
     * Partially update a bankAccountInterest.
     *
     * @param bankAccountInterestDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BankAccountInterestDTO> partialUpdate(BankAccountInterestDTO bankAccountInterestDTO) {
        log.debug("Request to partially update BankAccountInterest : {}", bankAccountInterestDTO);

        return bankAccountInterestRepository
            .findById(bankAccountInterestDTO.getId())
            .map(existingBankAccountInterest -> {
                bankAccountInterestMapper.partialUpdate(existingBankAccountInterest, bankAccountInterestDTO);

                return existingBankAccountInterest;
            })
            .map(bankAccountInterestRepository::save)
            .map(bankAccountInterestMapper::toDto);
    }

    /**
     * Get all the bankAccountInterests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BankAccountInterestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BankAccountInterests");
        return bankAccountInterestRepository.findAll(pageable).map(bankAccountInterestMapper::toDto);
    }

    /**
     * Get one bankAccountInterest by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BankAccountInterestDTO> findOne(Long id) {
        log.debug("Request to get BankAccountInterest : {}", id);
        return bankAccountInterestRepository.findById(id).map(bankAccountInterestMapper::toDto);
    }

    /**
     * Delete the bankAccountInterest by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BankAccountInterest : {}", id);
        bankAccountInterestRepository.deleteById(id);
    }
}
