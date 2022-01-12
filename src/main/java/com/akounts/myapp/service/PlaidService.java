package com.akounts.myapp.service;

import com.akounts.myapp.domain.Plaid;
import com.akounts.myapp.repository.PlaidRepository;
import com.akounts.myapp.service.dto.PlaidDTO;
import com.akounts.myapp.service.mapper.PlaidMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Plaid}.
 */
@Service
@Transactional
public class PlaidService {

    private final Logger log = LoggerFactory.getLogger(PlaidService.class);

    private final PlaidRepository plaidRepository;

    private final PlaidMapper plaidMapper;

    public PlaidService(PlaidRepository plaidRepository, PlaidMapper plaidMapper) {
        this.plaidRepository = plaidRepository;
        this.plaidMapper = plaidMapper;
    }

    /**
     * Save a plaid.
     *
     * @param plaidDTO the entity to save.
     * @return the persisted entity.
     */
    public PlaidDTO save(PlaidDTO plaidDTO) {
        log.debug("Request to save Plaid : {}", plaidDTO);
        Plaid plaid = plaidMapper.toEntity(plaidDTO);
        plaid = plaidRepository.save(plaid);
        return plaidMapper.toDto(plaid);
    }

    /**
     * Partially update a plaid.
     *
     * @param plaidDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PlaidDTO> partialUpdate(PlaidDTO plaidDTO) {
        log.debug("Request to partially update Plaid : {}", plaidDTO);

        return plaidRepository
            .findById(plaidDTO.getId())
            .map(existingPlaid -> {
                plaidMapper.partialUpdate(existingPlaid, plaidDTO);

                return existingPlaid;
            })
            .map(plaidRepository::save)
            .map(plaidMapper::toDto);
    }

    /**
     * Get all the plaids.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PlaidDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Plaids");
        return plaidRepository.findAll(pageable).map(plaidMapper::toDto);
    }

    /**
     * Get one plaid by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PlaidDTO> findOne(Long id) {
        log.debug("Request to get Plaid : {}", id);
        return plaidRepository.findById(id).map(plaidMapper::toDto);
    }

    /**
     * Delete the plaid by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Plaid : {}", id);
        plaidRepository.deleteById(id);
    }
}
