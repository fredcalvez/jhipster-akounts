package com.akounts.myapp.service;

import com.akounts.myapp.domain.BankProject;
import com.akounts.myapp.repository.BankProjectRepository;
import com.akounts.myapp.service.dto.BankProjectDTO;
import com.akounts.myapp.service.mapper.BankProjectMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BankProject}.
 */
@Service
@Transactional
public class BankProjectService {

    private final Logger log = LoggerFactory.getLogger(BankProjectService.class);

    private final BankProjectRepository bankProjectRepository;

    private final BankProjectMapper bankProjectMapper;

    public BankProjectService(BankProjectRepository bankProjectRepository, BankProjectMapper bankProjectMapper) {
        this.bankProjectRepository = bankProjectRepository;
        this.bankProjectMapper = bankProjectMapper;
    }

    /**
     * Save a bankProject.
     *
     * @param bankProjectDTO the entity to save.
     * @return the persisted entity.
     */
    public BankProjectDTO save(BankProjectDTO bankProjectDTO) {
        log.debug("Request to save BankProject : {}", bankProjectDTO);
        BankProject bankProject = bankProjectMapper.toEntity(bankProjectDTO);
        bankProject = bankProjectRepository.save(bankProject);
        return bankProjectMapper.toDto(bankProject);
    }

    /**
     * Partially update a bankProject.
     *
     * @param bankProjectDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BankProjectDTO> partialUpdate(BankProjectDTO bankProjectDTO) {
        log.debug("Request to partially update BankProject : {}", bankProjectDTO);

        return bankProjectRepository
            .findById(bankProjectDTO.getId())
            .map(existingBankProject -> {
                bankProjectMapper.partialUpdate(existingBankProject, bankProjectDTO);

                return existingBankProject;
            })
            .map(bankProjectRepository::save)
            .map(bankProjectMapper::toDto);
    }

    /**
     * Get all the bankProjects.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BankProjectDTO> findAll() {
        log.debug("Request to get all BankProjects");
        return bankProjectRepository.findAll().stream().map(bankProjectMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one bankProject by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BankProjectDTO> findOne(Long id) {
        log.debug("Request to get BankProject : {}", id);
        return bankProjectRepository.findById(id).map(bankProjectMapper::toDto);
    }

    /**
     * Delete the bankProject by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BankProject : {}", id);
        bankProjectRepository.deleteById(id);
    }
}
