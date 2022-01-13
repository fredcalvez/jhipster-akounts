package com.akounts.myapp.service;

import com.akounts.myapp.domain.TextCleaner;
import com.akounts.myapp.repository.TextCleanerRepository;
import com.akounts.myapp.service.dto.TextCleanerDTO;
import com.akounts.myapp.service.mapper.TextCleanerMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TextCleaner}.
 */
@Service
@Transactional
public class TextCleanerService {

    private final Logger log = LoggerFactory.getLogger(TextCleanerService.class);

    private final TextCleanerRepository textCleanerRepository;

    private final TextCleanerMapper textCleanerMapper;

    public TextCleanerService(TextCleanerRepository textCleanerRepository, TextCleanerMapper textCleanerMapper) {
        this.textCleanerRepository = textCleanerRepository;
        this.textCleanerMapper = textCleanerMapper;
    }

    /**
     * Save a textCleaner.
     *
     * @param textCleanerDTO the entity to save.
     * @return the persisted entity.
     */
    public TextCleanerDTO save(TextCleanerDTO textCleanerDTO) {
        log.debug("Request to save TextCleaner : {}", textCleanerDTO);
        TextCleaner textCleaner = textCleanerMapper.toEntity(textCleanerDTO);
        textCleaner = textCleanerRepository.save(textCleaner);
        return textCleanerMapper.toDto(textCleaner);
    }

    /**
     * Partially update a textCleaner.
     *
     * @param textCleanerDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TextCleanerDTO> partialUpdate(TextCleanerDTO textCleanerDTO) {
        log.debug("Request to partially update TextCleaner : {}", textCleanerDTO);

        return textCleanerRepository
            .findById(textCleanerDTO.getId())
            .map(existingTextCleaner -> {
                textCleanerMapper.partialUpdate(existingTextCleaner, textCleanerDTO);

                return existingTextCleaner;
            })
            .map(textCleanerRepository::save)
            .map(textCleanerMapper::toDto);
    }

    /**
     * Get all the textCleaners.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TextCleanerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TextCleaners");
        return textCleanerRepository.findAll(pageable).map(textCleanerMapper::toDto);
    }

    /**
     * Get one textCleaner by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TextCleanerDTO> findOne(Long id) {
        log.debug("Request to get TextCleaner : {}", id);
        return textCleanerRepository.findById(id).map(textCleanerMapper::toDto);
    }

    /**
     * Delete the textCleaner by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TextCleaner : {}", id);
        textCleanerRepository.deleteById(id);
    }
}
