package com.akounts.myapp.service;

import com.akounts.myapp.domain.PlaidAccount;
import com.akounts.myapp.repository.PlaidAccountRepository;
import com.akounts.myapp.service.dto.PlaidAccountDTO;
import com.akounts.myapp.service.mapper.PlaidAccountMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PlaidAccount}.
 */
@Service
@Transactional
public class PlaidAccountService {

    private final Logger log = LoggerFactory.getLogger(PlaidAccountService.class);

    private final PlaidAccountRepository plaidAccountRepository;

    private final PlaidAccountMapper plaidAccountMapper;

    public PlaidAccountService(PlaidAccountRepository plaidAccountRepository, PlaidAccountMapper plaidAccountMapper) {
        this.plaidAccountRepository = plaidAccountRepository;
        this.plaidAccountMapper = plaidAccountMapper;
    }

    /**
     * Save a plaidAccount.
     *
     * @param plaidAccountDTO the entity to save.
     * @return the persisted entity.
     */
    public PlaidAccountDTO save(PlaidAccountDTO plaidAccountDTO) {
        log.debug("Request to save PlaidAccount : {}", plaidAccountDTO);
        PlaidAccount plaidAccount = plaidAccountMapper.toEntity(plaidAccountDTO);
        plaidAccount = plaidAccountRepository.save(plaidAccount);
        return plaidAccountMapper.toDto(plaidAccount);
    }

    /**
     * Partially update a plaidAccount.
     *
     * @param plaidAccountDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PlaidAccountDTO> partialUpdate(PlaidAccountDTO plaidAccountDTO) {
        log.debug("Request to partially update PlaidAccount : {}", plaidAccountDTO);

        return plaidAccountRepository
            .findById(plaidAccountDTO.getId())
            .map(existingPlaidAccount -> {
                plaidAccountMapper.partialUpdate(existingPlaidAccount, plaidAccountDTO);

                return existingPlaidAccount;
            })
            .map(plaidAccountRepository::save)
            .map(plaidAccountMapper::toDto);
    }

    /**
     * Get all the plaidAccounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PlaidAccountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PlaidAccounts");
        return plaidAccountRepository.findAll(pageable).map(plaidAccountMapper::toDto);
    }

    /**
     * Get one plaidAccount by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PlaidAccountDTO> findOne(Long id) {
        log.debug("Request to get PlaidAccount : {}", id);
        return plaidAccountRepository.findById(id).map(plaidAccountMapper::toDto);
    }

    /**
     * Delete the plaidAccount by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PlaidAccount : {}", id);
        plaidAccountRepository.deleteById(id);
    }
}
