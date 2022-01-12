package com.akounts.myapp.service;

import com.akounts.myapp.domain.FileConfig;
import com.akounts.myapp.repository.FileConfigRepository;
import com.akounts.myapp.service.dto.FileConfigDTO;
import com.akounts.myapp.service.mapper.FileConfigMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FileConfig}.
 */
@Service
@Transactional
public class FileConfigService {

    private final Logger log = LoggerFactory.getLogger(FileConfigService.class);

    private final FileConfigRepository fileConfigRepository;

    private final FileConfigMapper fileConfigMapper;

    public FileConfigService(FileConfigRepository fileConfigRepository, FileConfigMapper fileConfigMapper) {
        this.fileConfigRepository = fileConfigRepository;
        this.fileConfigMapper = fileConfigMapper;
    }

    /**
     * Save a fileConfig.
     *
     * @param fileConfigDTO the entity to save.
     * @return the persisted entity.
     */
    public FileConfigDTO save(FileConfigDTO fileConfigDTO) {
        log.debug("Request to save FileConfig : {}", fileConfigDTO);
        FileConfig fileConfig = fileConfigMapper.toEntity(fileConfigDTO);
        fileConfig = fileConfigRepository.save(fileConfig);
        return fileConfigMapper.toDto(fileConfig);
    }

    /**
     * Partially update a fileConfig.
     *
     * @param fileConfigDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FileConfigDTO> partialUpdate(FileConfigDTO fileConfigDTO) {
        log.debug("Request to partially update FileConfig : {}", fileConfigDTO);

        return fileConfigRepository
            .findById(fileConfigDTO.getId())
            .map(existingFileConfig -> {
                fileConfigMapper.partialUpdate(existingFileConfig, fileConfigDTO);

                return existingFileConfig;
            })
            .map(fileConfigRepository::save)
            .map(fileConfigMapper::toDto);
    }

    /**
     * Get all the fileConfigs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FileConfigDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FileConfigs");
        return fileConfigRepository.findAll(pageable).map(fileConfigMapper::toDto);
    }

    /**
     * Get one fileConfig by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FileConfigDTO> findOne(Long id) {
        log.debug("Request to get FileConfig : {}", id);
        return fileConfigRepository.findById(id).map(fileConfigMapper::toDto);
    }

    /**
     * Delete the fileConfig by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FileConfig : {}", id);
        fileConfigRepository.deleteById(id);
    }
}
