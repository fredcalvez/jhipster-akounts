package com.akounts.myapp.service;

import com.akounts.myapp.domain.FileImport;
import com.akounts.myapp.repository.FileImportRepository;
import com.akounts.myapp.service.dto.FileImportDTO;
import com.akounts.myapp.service.mapper.FileImportMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FileImport}.
 */
@Service
@Transactional
public class FileImportService {

    private final Logger log = LoggerFactory.getLogger(FileImportService.class);

    private final FileImportRepository fileImportRepository;

    private final FileImportMapper fileImportMapper;

    public FileImportService(FileImportRepository fileImportRepository, FileImportMapper fileImportMapper) {
        this.fileImportRepository = fileImportRepository;
        this.fileImportMapper = fileImportMapper;
    }

    /**
     * Save a fileImport.
     *
     * @param fileImportDTO the entity to save.
     * @return the persisted entity.
     */
    public FileImportDTO save(FileImportDTO fileImportDTO) {
        log.debug("Request to save FileImport : {}", fileImportDTO);
        FileImport fileImport = fileImportMapper.toEntity(fileImportDTO);
        fileImport = fileImportRepository.save(fileImport);
        return fileImportMapper.toDto(fileImport);
    }

    /**
     * Partially update a fileImport.
     *
     * @param fileImportDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FileImportDTO> partialUpdate(FileImportDTO fileImportDTO) {
        log.debug("Request to partially update FileImport : {}", fileImportDTO);

        return fileImportRepository
            .findById(fileImportDTO.getId())
            .map(existingFileImport -> {
                fileImportMapper.partialUpdate(existingFileImport, fileImportDTO);

                return existingFileImport;
            })
            .map(fileImportRepository::save)
            .map(fileImportMapper::toDto);
    }

    /**
     * Get all the fileImports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FileImportDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FileImports");
        return fileImportRepository.findAll(pageable).map(fileImportMapper::toDto);
    }

    /**
     * Get one fileImport by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FileImportDTO> findOne(Long id) {
        log.debug("Request to get FileImport : {}", id);
        return fileImportRepository.findById(id).map(fileImportMapper::toDto);
    }

    /**
     * Delete the fileImport by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FileImport : {}", id);
        fileImportRepository.deleteById(id);
    }
}
