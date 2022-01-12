package com.akounts.myapp.service;

import com.akounts.myapp.domain.AkountsSettings;
import com.akounts.myapp.repository.AkountsSettingsRepository;
import com.akounts.myapp.service.dto.AkountsSettingsDTO;
import com.akounts.myapp.service.mapper.AkountsSettingsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AkountsSettings}.
 */
@Service
@Transactional
public class AkountsSettingsService {

    private final Logger log = LoggerFactory.getLogger(AkountsSettingsService.class);

    private final AkountsSettingsRepository akountsSettingsRepository;

    private final AkountsSettingsMapper akountsSettingsMapper;

    public AkountsSettingsService(AkountsSettingsRepository akountsSettingsRepository, AkountsSettingsMapper akountsSettingsMapper) {
        this.akountsSettingsRepository = akountsSettingsRepository;
        this.akountsSettingsMapper = akountsSettingsMapper;
    }

    /**
     * Save a akountsSettings.
     *
     * @param akountsSettingsDTO the entity to save.
     * @return the persisted entity.
     */
    public AkountsSettingsDTO save(AkountsSettingsDTO akountsSettingsDTO) {
        log.debug("Request to save AkountsSettings : {}", akountsSettingsDTO);
        AkountsSettings akountsSettings = akountsSettingsMapper.toEntity(akountsSettingsDTO);
        akountsSettings = akountsSettingsRepository.save(akountsSettings);
        return akountsSettingsMapper.toDto(akountsSettings);
    }

    /**
     * Partially update a akountsSettings.
     *
     * @param akountsSettingsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AkountsSettingsDTO> partialUpdate(AkountsSettingsDTO akountsSettingsDTO) {
        log.debug("Request to partially update AkountsSettings : {}", akountsSettingsDTO);

        return akountsSettingsRepository
            .findById(akountsSettingsDTO.getId())
            .map(existingAkountsSettings -> {
                akountsSettingsMapper.partialUpdate(existingAkountsSettings, akountsSettingsDTO);

                return existingAkountsSettings;
            })
            .map(akountsSettingsRepository::save)
            .map(akountsSettingsMapper::toDto);
    }

    /**
     * Get all the akountsSettings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AkountsSettingsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AkountsSettings");
        return akountsSettingsRepository.findAll(pageable).map(akountsSettingsMapper::toDto);
    }

    /**
     * Get one akountsSettings by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AkountsSettingsDTO> findOne(Long id) {
        log.debug("Request to get AkountsSettings : {}", id);
        return akountsSettingsRepository.findById(id).map(akountsSettingsMapper::toDto);
    }

    /**
     * Delete the akountsSettings by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AkountsSettings : {}", id);
        akountsSettingsRepository.deleteById(id);
    }
}
