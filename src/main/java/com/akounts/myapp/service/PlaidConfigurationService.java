package com.akounts.myapp.service;

import com.akounts.myapp.domain.PlaidConfiguration;
import com.akounts.myapp.repository.PlaidConfigurationRepository;
import com.akounts.myapp.service.dto.PlaidConfigurationDTO;
import com.akounts.myapp.service.mapper.PlaidConfigurationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PlaidConfiguration}.
 */
@Service
@Transactional
public class PlaidConfigurationService {

    private final Logger log = LoggerFactory.getLogger(PlaidConfigurationService.class);

    private final PlaidConfigurationRepository plaidConfigurationRepository;

    private final PlaidConfigurationMapper plaidConfigurationMapper;

    public PlaidConfigurationService(
        PlaidConfigurationRepository plaidConfigurationRepository,
        PlaidConfigurationMapper plaidConfigurationMapper
    ) {
        this.plaidConfigurationRepository = plaidConfigurationRepository;
        this.plaidConfigurationMapper = plaidConfigurationMapper;
    }

    /**
     * Save a plaidConfiguration.
     *
     * @param plaidConfigurationDTO the entity to save.
     * @return the persisted entity.
     */
    public PlaidConfigurationDTO save(PlaidConfigurationDTO plaidConfigurationDTO) {
        log.debug("Request to save PlaidConfiguration : {}", plaidConfigurationDTO);
        PlaidConfiguration plaidConfiguration = plaidConfigurationMapper.toEntity(plaidConfigurationDTO);
        plaidConfiguration = plaidConfigurationRepository.save(plaidConfiguration);
        return plaidConfigurationMapper.toDto(plaidConfiguration);
    }

    /**
     * Partially update a plaidConfiguration.
     *
     * @param plaidConfigurationDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PlaidConfigurationDTO> partialUpdate(PlaidConfigurationDTO plaidConfigurationDTO) {
        log.debug("Request to partially update PlaidConfiguration : {}", plaidConfigurationDTO);

        return plaidConfigurationRepository
            .findById(plaidConfigurationDTO.getId())
            .map(existingPlaidConfiguration -> {
                plaidConfigurationMapper.partialUpdate(existingPlaidConfiguration, plaidConfigurationDTO);

                return existingPlaidConfiguration;
            })
            .map(plaidConfigurationRepository::save)
            .map(plaidConfigurationMapper::toDto);
    }

    /**
     * Get all the plaidConfigurations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PlaidConfigurationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PlaidConfigurations");
        return plaidConfigurationRepository.findAll(pageable).map(plaidConfigurationMapper::toDto);
    }

    /**
     * Get one plaidConfiguration by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PlaidConfigurationDTO> findOne(Long id) {
        log.debug("Request to get PlaidConfiguration : {}", id);
        return plaidConfigurationRepository.findById(id).map(plaidConfigurationMapper::toDto);
    }

    /**
     * Delete the plaidConfiguration by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PlaidConfiguration : {}", id);
        plaidConfigurationRepository.deleteById(id);
    }
}
