package com.akounts.myapp.service;

import com.akounts.myapp.domain.BankCategory;
import com.akounts.myapp.repository.BankCategoryRepository;
import com.akounts.myapp.service.dto.BankCategoryDTO;
import com.akounts.myapp.service.mapper.BankCategoryMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BankCategory}.
 */
@Service
@Transactional
public class BankCategoryService {

    private final Logger log = LoggerFactory.getLogger(BankCategoryService.class);

    private final BankCategoryRepository bankCategoryRepository;

    private final BankCategoryMapper bankCategoryMapper;

    public BankCategoryService(BankCategoryRepository bankCategoryRepository, BankCategoryMapper bankCategoryMapper) {
        this.bankCategoryRepository = bankCategoryRepository;
        this.bankCategoryMapper = bankCategoryMapper;
    }

    /**
     * Save a bankCategory.
     *
     * @param bankCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    public BankCategoryDTO save(BankCategoryDTO bankCategoryDTO) {
        log.debug("Request to save BankCategory : {}", bankCategoryDTO);
        BankCategory bankCategory = bankCategoryMapper.toEntity(bankCategoryDTO);
        bankCategory = bankCategoryRepository.save(bankCategory);
        return bankCategoryMapper.toDto(bankCategory);
    }

    /**
     * Partially update a bankCategory.
     *
     * @param bankCategoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BankCategoryDTO> partialUpdate(BankCategoryDTO bankCategoryDTO) {
        log.debug("Request to partially update BankCategory : {}", bankCategoryDTO);

        return bankCategoryRepository
            .findById(bankCategoryDTO.getId())
            .map(existingBankCategory -> {
                bankCategoryMapper.partialUpdate(existingBankCategory, bankCategoryDTO);

                return existingBankCategory;
            })
            .map(bankCategoryRepository::save)
            .map(bankCategoryMapper::toDto);
    }

    /**
     * Get all the bankCategories.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BankCategoryDTO> findAll() {
        log.debug("Request to get all BankCategories");
        return bankCategoryRepository.findAll().stream().map(bankCategoryMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one bankCategory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BankCategoryDTO> findOne(Long id) {
        log.debug("Request to get BankCategory : {}", id);
        return bankCategoryRepository.findById(id).map(bankCategoryMapper::toDto);
    }

    /**
     * Delete the bankCategory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BankCategory : {}", id);
        bankCategoryRepository.deleteById(id);
    }
}
