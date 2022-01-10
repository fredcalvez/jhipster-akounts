package com.akounts.myapp.service;

import com.akounts.myapp.domain.BankInstitution;
import com.akounts.myapp.repository.BankInstitutionRepository;
import com.akounts.myapp.service.dto.BankInstitutionDTO;
import com.akounts.myapp.service.mapper.BankInstitutionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BankInstitution}.
 */
@Service
@Transactional
public class BankInstitutionService {

    private final Logger log = LoggerFactory.getLogger(BankInstitutionService.class);

    private final BankInstitutionRepository bankInstitutionRepository;

    private final BankInstitutionMapper bankInstitutionMapper;

    public BankInstitutionService(BankInstitutionRepository bankInstitutionRepository, BankInstitutionMapper bankInstitutionMapper) {
        this.bankInstitutionRepository = bankInstitutionRepository;
        this.bankInstitutionMapper = bankInstitutionMapper;
    }

    /**
     * Save a bankInstitution.
     *
     * @param bankInstitutionDTO the entity to save.
     * @return the persisted entity.
     */
    public BankInstitutionDTO save(BankInstitutionDTO bankInstitutionDTO) {
        log.debug("Request to save BankInstitution : {}", bankInstitutionDTO);
        BankInstitution bankInstitution = bankInstitutionMapper.toEntity(bankInstitutionDTO);
        bankInstitution = bankInstitutionRepository.save(bankInstitution);
        return bankInstitutionMapper.toDto(bankInstitution);
    }

    /**
     * Partially update a bankInstitution.
     *
     * @param bankInstitutionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BankInstitutionDTO> partialUpdate(BankInstitutionDTO bankInstitutionDTO) {
        log.debug("Request to partially update BankInstitution : {}", bankInstitutionDTO);

        return bankInstitutionRepository
            .findById(bankInstitutionDTO.getId())
            .map(existingBankInstitution -> {
                bankInstitutionMapper.partialUpdate(existingBankInstitution, bankInstitutionDTO);

                return existingBankInstitution;
            })
            .map(bankInstitutionRepository::save)
            .map(bankInstitutionMapper::toDto);
    }

    /**
     * Get all the bankInstitutions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BankInstitutionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BankInstitutions");
        return bankInstitutionRepository.findAll(pageable).map(bankInstitutionMapper::toDto);
    }

    /**
     * Get one bankInstitution by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BankInstitutionDTO> findOne(Long id) {
        log.debug("Request to get BankInstitution : {}", id);
        return bankInstitutionRepository.findById(id).map(bankInstitutionMapper::toDto);
    }

    /**
     * Delete the bankInstitution by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BankInstitution : {}", id);
        bankInstitutionRepository.deleteById(id);
    }
}
