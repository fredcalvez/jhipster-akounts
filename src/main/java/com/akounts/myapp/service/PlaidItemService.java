package com.akounts.myapp.service;

import com.akounts.myapp.domain.PlaidItem;
import com.akounts.myapp.repository.PlaidItemRepository;
import com.akounts.myapp.service.dto.PlaidItemDTO;
import com.akounts.myapp.service.mapper.PlaidItemMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PlaidItem}.
 */
@Service
@Transactional
public class PlaidItemService {

    private final Logger log = LoggerFactory.getLogger(PlaidItemService.class);

    private final PlaidItemRepository plaidItemRepository;

    private final PlaidItemMapper plaidItemMapper;

    public PlaidItemService(PlaidItemRepository plaidItemRepository, PlaidItemMapper plaidItemMapper) {
        this.plaidItemRepository = plaidItemRepository;
        this.plaidItemMapper = plaidItemMapper;
    }

    /**
     * Save a plaidItem.
     *
     * @param plaidItemDTO the entity to save.
     * @return the persisted entity.
     */
    public PlaidItemDTO save(PlaidItemDTO plaidItemDTO) {
        log.debug("Request to save PlaidItem : {}", plaidItemDTO);
        PlaidItem plaidItem = plaidItemMapper.toEntity(plaidItemDTO);
        plaidItem = plaidItemRepository.save(plaidItem);
        return plaidItemMapper.toDto(plaidItem);
    }

    /**
     * Partially update a plaidItem.
     *
     * @param plaidItemDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PlaidItemDTO> partialUpdate(PlaidItemDTO plaidItemDTO) {
        log.debug("Request to partially update PlaidItem : {}", plaidItemDTO);

        return plaidItemRepository
            .findById(plaidItemDTO.getId())
            .map(existingPlaidItem -> {
                plaidItemMapper.partialUpdate(existingPlaidItem, plaidItemDTO);

                return existingPlaidItem;
            })
            .map(plaidItemRepository::save)
            .map(plaidItemMapper::toDto);
    }

    /**
     * Get all the plaidItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PlaidItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PlaidItems");
        return plaidItemRepository.findAll(pageable).map(plaidItemMapper::toDto);
    }

    /**
     * Get one plaidItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PlaidItemDTO> findOne(Long id) {
        log.debug("Request to get PlaidItem : {}", id);
        return plaidItemRepository.findById(id).map(plaidItemMapper::toDto);
    }

    /**
     * Delete the plaidItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PlaidItem : {}", id);
        plaidItemRepository.deleteById(id);
    }
}
