package com.akounts.myapp.service;

import com.akounts.myapp.domain.TransactionDuplicates;
import com.akounts.myapp.repository.TransactionDuplicatesRepository;
import com.akounts.myapp.service.dto.TransactionDuplicatesDTO;
import com.akounts.myapp.service.mapper.TransactionDuplicatesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TransactionDuplicates}.
 */
@Service
@Transactional
public class TransactionDuplicatesService {

    private final Logger log = LoggerFactory.getLogger(TransactionDuplicatesService.class);

    private final TransactionDuplicatesRepository transactionDuplicatesRepository;

    private final TransactionDuplicatesMapper transactionDuplicatesMapper;

    public TransactionDuplicatesService(
        TransactionDuplicatesRepository transactionDuplicatesRepository,
        TransactionDuplicatesMapper transactionDuplicatesMapper
    ) {
        this.transactionDuplicatesRepository = transactionDuplicatesRepository;
        this.transactionDuplicatesMapper = transactionDuplicatesMapper;
    }

    /**
     * Save a transactionDuplicates.
     *
     * @param transactionDuplicatesDTO the entity to save.
     * @return the persisted entity.
     */
    public TransactionDuplicatesDTO save(TransactionDuplicatesDTO transactionDuplicatesDTO) {
        log.debug("Request to save TransactionDuplicates : {}", transactionDuplicatesDTO);
        TransactionDuplicates transactionDuplicates = transactionDuplicatesMapper.toEntity(transactionDuplicatesDTO);
        transactionDuplicates = transactionDuplicatesRepository.save(transactionDuplicates);
        return transactionDuplicatesMapper.toDto(transactionDuplicates);
    }

    /**
     * Partially update a transactionDuplicates.
     *
     * @param transactionDuplicatesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TransactionDuplicatesDTO> partialUpdate(TransactionDuplicatesDTO transactionDuplicatesDTO) {
        log.debug("Request to partially update TransactionDuplicates : {}", transactionDuplicatesDTO);

        return transactionDuplicatesRepository
            .findById(transactionDuplicatesDTO.getId())
            .map(existingTransactionDuplicates -> {
                transactionDuplicatesMapper.partialUpdate(existingTransactionDuplicates, transactionDuplicatesDTO);

                return existingTransactionDuplicates;
            })
            .map(transactionDuplicatesRepository::save)
            .map(transactionDuplicatesMapper::toDto);
    }

    /**
     * Get all the transactionDuplicates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TransactionDuplicatesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TransactionDuplicates");
        return transactionDuplicatesRepository.findAll(pageable).map(transactionDuplicatesMapper::toDto);
    }

    /**
     * Get one transactionDuplicates by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TransactionDuplicatesDTO> findOne(Long id) {
        log.debug("Request to get TransactionDuplicates : {}", id);
        return transactionDuplicatesRepository.findById(id).map(transactionDuplicatesMapper::toDto);
    }

    /**
     * Delete the transactionDuplicates by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TransactionDuplicates : {}", id);
        transactionDuplicatesRepository.deleteById(id);
    }
}
