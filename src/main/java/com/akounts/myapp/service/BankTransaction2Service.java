package com.akounts.myapp.service;

import com.akounts.myapp.domain.BankTransaction2;
import com.akounts.myapp.repository.BankTransaction2Repository;
import com.akounts.myapp.service.dto.BankTransaction2DTO;
import com.akounts.myapp.service.mapper.BankTransaction2Mapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BankTransaction2}.
 */
@Service
@Transactional
public class BankTransaction2Service {

    private final Logger log = LoggerFactory.getLogger(BankTransaction2Service.class);

    private final BankTransaction2Repository bankTransaction2Repository;

    private final BankTransaction2Mapper bankTransaction2Mapper;

    public BankTransaction2Service(BankTransaction2Repository bankTransaction2Repository, BankTransaction2Mapper bankTransaction2Mapper) {
        this.bankTransaction2Repository = bankTransaction2Repository;
        this.bankTransaction2Mapper = bankTransaction2Mapper;
    }

    /**
     * Save a bankTransaction2.
     *
     * @param bankTransaction2DTO the entity to save.
     * @return the persisted entity.
     */
    public BankTransaction2DTO save(BankTransaction2DTO bankTransaction2DTO) {
        log.debug("Request to save BankTransaction2 : {}", bankTransaction2DTO);
        BankTransaction2 bankTransaction2 = bankTransaction2Mapper.toEntity(bankTransaction2DTO);
        bankTransaction2 = bankTransaction2Repository.save(bankTransaction2);
        return bankTransaction2Mapper.toDto(bankTransaction2);
    }

    /**
     * Partially update a bankTransaction2.
     *
     * @param bankTransaction2DTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BankTransaction2DTO> partialUpdate(BankTransaction2DTO bankTransaction2DTO) {
        log.debug("Request to partially update BankTransaction2 : {}", bankTransaction2DTO);

        return bankTransaction2Repository
            .findById(bankTransaction2DTO.getId())
            .map(existingBankTransaction2 -> {
                bankTransaction2Mapper.partialUpdate(existingBankTransaction2, bankTransaction2DTO);

                return existingBankTransaction2;
            })
            .map(bankTransaction2Repository::save)
            .map(bankTransaction2Mapper::toDto);
    }

    /**
     * Get all the bankTransaction2s.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BankTransaction2DTO> findAll(Pageable pageable) {
        log.debug("Request to get all BankTransaction2s");
        return bankTransaction2Repository.findAll(pageable).map(bankTransaction2Mapper::toDto);
    }

    /**
     * Get one bankTransaction2 by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BankTransaction2DTO> findOne(Long id) {
        log.debug("Request to get BankTransaction2 : {}", id);
        return bankTransaction2Repository.findById(id).map(bankTransaction2Mapper::toDto);
    }

    /**
     * Delete the bankTransaction2 by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BankTransaction2 : {}", id);
        bankTransaction2Repository.deleteById(id);
    }
}
