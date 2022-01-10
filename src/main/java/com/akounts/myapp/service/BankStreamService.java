package com.akounts.myapp.service;

import com.akounts.myapp.domain.BankStream;
import com.akounts.myapp.repository.BankStreamRepository;
import com.akounts.myapp.service.dto.BankStreamDTO;
import com.akounts.myapp.service.mapper.BankStreamMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BankStream}.
 */
@Service
@Transactional
public class BankStreamService {

    private final Logger log = LoggerFactory.getLogger(BankStreamService.class);

    private final BankStreamRepository bankStreamRepository;

    private final BankStreamMapper bankStreamMapper;

    public BankStreamService(BankStreamRepository bankStreamRepository, BankStreamMapper bankStreamMapper) {
        this.bankStreamRepository = bankStreamRepository;
        this.bankStreamMapper = bankStreamMapper;
    }

    /**
     * Save a bankStream.
     *
     * @param bankStreamDTO the entity to save.
     * @return the persisted entity.
     */
    public BankStreamDTO save(BankStreamDTO bankStreamDTO) {
        log.debug("Request to save BankStream : {}", bankStreamDTO);
        BankStream bankStream = bankStreamMapper.toEntity(bankStreamDTO);
        bankStream = bankStreamRepository.save(bankStream);
        return bankStreamMapper.toDto(bankStream);
    }

    /**
     * Partially update a bankStream.
     *
     * @param bankStreamDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BankStreamDTO> partialUpdate(BankStreamDTO bankStreamDTO) {
        log.debug("Request to partially update BankStream : {}", bankStreamDTO);

        return bankStreamRepository
            .findById(bankStreamDTO.getId())
            .map(existingBankStream -> {
                bankStreamMapper.partialUpdate(existingBankStream, bankStreamDTO);

                return existingBankStream;
            })
            .map(bankStreamRepository::save)
            .map(bankStreamMapper::toDto);
    }

    /**
     * Get all the bankStreams.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BankStreamDTO> findAll() {
        log.debug("Request to get all BankStreams");
        return bankStreamRepository.findAll().stream().map(bankStreamMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one bankStream by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BankStreamDTO> findOne(Long id) {
        log.debug("Request to get BankStream : {}", id);
        return bankStreamRepository.findById(id).map(bankStreamMapper::toDto);
    }

    /**
     * Delete the bankStream by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BankStream : {}", id);
        bankStreamRepository.deleteById(id);
    }
}
