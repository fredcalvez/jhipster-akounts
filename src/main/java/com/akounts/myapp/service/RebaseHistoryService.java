package com.akounts.myapp.service;

import com.akounts.myapp.domain.RebaseHistory;
import com.akounts.myapp.repository.RebaseHistoryRepository;
import com.akounts.myapp.service.dto.RebaseHistoryDTO;
import com.akounts.myapp.service.mapper.RebaseHistoryMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RebaseHistory}.
 */
@Service
@Transactional
public class RebaseHistoryService {

    private final Logger log = LoggerFactory.getLogger(RebaseHistoryService.class);

    private final RebaseHistoryRepository rebaseHistoryRepository;

    private final RebaseHistoryMapper rebaseHistoryMapper;

    public RebaseHistoryService(RebaseHistoryRepository rebaseHistoryRepository, RebaseHistoryMapper rebaseHistoryMapper) {
        this.rebaseHistoryRepository = rebaseHistoryRepository;
        this.rebaseHistoryMapper = rebaseHistoryMapper;
    }

    /**
     * Save a rebaseHistory.
     *
     * @param rebaseHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    public RebaseHistoryDTO save(RebaseHistoryDTO rebaseHistoryDTO) {
        log.debug("Request to save RebaseHistory : {}", rebaseHistoryDTO);
        RebaseHistory rebaseHistory = rebaseHistoryMapper.toEntity(rebaseHistoryDTO);
        rebaseHistory = rebaseHistoryRepository.save(rebaseHistory);
        return rebaseHistoryMapper.toDto(rebaseHistory);
    }

    /**
     * Partially update a rebaseHistory.
     *
     * @param rebaseHistoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RebaseHistoryDTO> partialUpdate(RebaseHistoryDTO rebaseHistoryDTO) {
        log.debug("Request to partially update RebaseHistory : {}", rebaseHistoryDTO);

        return rebaseHistoryRepository
            .findById(rebaseHistoryDTO.getId())
            .map(existingRebaseHistory -> {
                rebaseHistoryMapper.partialUpdate(existingRebaseHistory, rebaseHistoryDTO);

                return existingRebaseHistory;
            })
            .map(rebaseHistoryRepository::save)
            .map(rebaseHistoryMapper::toDto);
    }

    /**
     * Get all the rebaseHistories.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<RebaseHistoryDTO> findAll() {
        log.debug("Request to get all RebaseHistories");
        return rebaseHistoryRepository.findAll().stream().map(rebaseHistoryMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one rebaseHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RebaseHistoryDTO> findOne(Long id) {
        log.debug("Request to get RebaseHistory : {}", id);
        return rebaseHistoryRepository.findById(id).map(rebaseHistoryMapper::toDto);
    }

    /**
     * Delete the rebaseHistory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RebaseHistory : {}", id);
        rebaseHistoryRepository.deleteById(id);
    }
}
