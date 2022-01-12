package com.akounts.myapp.service;

import com.akounts.myapp.domain.Budget;
import com.akounts.myapp.repository.BudgetRepository;
import com.akounts.myapp.service.dto.BudgetDTO;
import com.akounts.myapp.service.mapper.BudgetMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Budget}.
 */
@Service
@Transactional
public class BudgetService {

    private final Logger log = LoggerFactory.getLogger(BudgetService.class);

    private final BudgetRepository budgetRepository;

    private final BudgetMapper budgetMapper;

    public BudgetService(BudgetRepository budgetRepository, BudgetMapper budgetMapper) {
        this.budgetRepository = budgetRepository;
        this.budgetMapper = budgetMapper;
    }

    /**
     * Save a budget.
     *
     * @param budgetDTO the entity to save.
     * @return the persisted entity.
     */
    public BudgetDTO save(BudgetDTO budgetDTO) {
        log.debug("Request to save Budget : {}", budgetDTO);
        Budget budget = budgetMapper.toEntity(budgetDTO);
        budget = budgetRepository.save(budget);
        return budgetMapper.toDto(budget);
    }

    /**
     * Partially update a budget.
     *
     * @param budgetDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BudgetDTO> partialUpdate(BudgetDTO budgetDTO) {
        log.debug("Request to partially update Budget : {}", budgetDTO);

        return budgetRepository
            .findById(budgetDTO.getId())
            .map(existingBudget -> {
                budgetMapper.partialUpdate(existingBudget, budgetDTO);

                return existingBudget;
            })
            .map(budgetRepository::save)
            .map(budgetMapper::toDto);
    }

    /**
     * Get all the budgets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BudgetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Budgets");
        return budgetRepository.findAll(pageable).map(budgetMapper::toDto);
    }

    /**
     * Get one budget by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BudgetDTO> findOne(Long id) {
        log.debug("Request to get Budget : {}", id);
        return budgetRepository.findById(id).map(budgetMapper::toDto);
    }

    /**
     * Delete the budget by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Budget : {}", id);
        budgetRepository.deleteById(id);
    }
}
