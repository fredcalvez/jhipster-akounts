package com.akounts.myapp.service;

import com.akounts.myapp.domain.BankSaving;
import com.akounts.myapp.repository.BankSavingRepository;
import com.akounts.myapp.service.dto.BankSavingDTO;
import com.akounts.myapp.service.mapper.BankSavingMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BankSaving}.
 */
@Service
@Transactional
public class BankSavingService {

    private final Logger log = LoggerFactory.getLogger(BankSavingService.class);

    private final BankSavingRepository bankSavingRepository;

    private final BankSavingMapper bankSavingMapper;

    public BankSavingService(BankSavingRepository bankSavingRepository, BankSavingMapper bankSavingMapper) {
        this.bankSavingRepository = bankSavingRepository;
        this.bankSavingMapper = bankSavingMapper;
    }

    /**
     * Save a bankSaving.
     *
     * @param bankSavingDTO the entity to save.
     * @return the persisted entity.
     */
    public BankSavingDTO save(BankSavingDTO bankSavingDTO) {
        log.debug("Request to save BankSaving : {}", bankSavingDTO);
        BankSaving bankSaving = bankSavingMapper.toEntity(bankSavingDTO);
        bankSaving = bankSavingRepository.save(bankSaving);
        return bankSavingMapper.toDto(bankSaving);
    }

    /**
     * Partially update a bankSaving.
     *
     * @param bankSavingDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BankSavingDTO> partialUpdate(BankSavingDTO bankSavingDTO) {
        log.debug("Request to partially update BankSaving : {}", bankSavingDTO);

        return bankSavingRepository
            .findById(bankSavingDTO.getId())
            .map(existingBankSaving -> {
                bankSavingMapper.partialUpdate(existingBankSaving, bankSavingDTO);

                return existingBankSaving;
            })
            .map(bankSavingRepository::save)
            .map(bankSavingMapper::toDto);
    }

    /**
     * Get all the bankSavings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BankSavingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BankSavings");
        return bankSavingRepository.findAll(pageable).map(bankSavingMapper::toDto);
    }

    /**
     * Get one bankSaving by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BankSavingDTO> findOne(Long id) {
        log.debug("Request to get BankSaving : {}", id);
        return bankSavingRepository.findById(id).map(bankSavingMapper::toDto);
    }

    /**
     * Delete the bankSaving by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BankSaving : {}", id);
        bankSavingRepository.deleteById(id);
    }
}
