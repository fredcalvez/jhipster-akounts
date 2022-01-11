package com.akounts.myapp.service;

import com.akounts.myapp.domain.BankTag;
import com.akounts.myapp.repository.BankTagRepository;
import com.akounts.myapp.service.dto.BankTagDTO;
import com.akounts.myapp.service.mapper.BankTagMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BankTag}.
 */
@Service
@Transactional
public class BankTagService {

    private final Logger log = LoggerFactory.getLogger(BankTagService.class);

    private final BankTagRepository bankTagRepository;

    private final BankTagMapper bankTagMapper;

    public BankTagService(BankTagRepository bankTagRepository, BankTagMapper bankTagMapper) {
        this.bankTagRepository = bankTagRepository;
        this.bankTagMapper = bankTagMapper;
    }

    /**
     * Save a bankTag.
     *
     * @param bankTagDTO the entity to save.
     * @return the persisted entity.
     */
    public BankTagDTO save(BankTagDTO bankTagDTO) {
        log.debug("Request to save BankTag : {}", bankTagDTO);
        BankTag bankTag = bankTagMapper.toEntity(bankTagDTO);
        bankTag = bankTagRepository.save(bankTag);
        return bankTagMapper.toDto(bankTag);
    }

    /**
     * Partially update a bankTag.
     *
     * @param bankTagDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BankTagDTO> partialUpdate(BankTagDTO bankTagDTO) {
        log.debug("Request to partially update BankTag : {}", bankTagDTO);

        return bankTagRepository
            .findById(bankTagDTO.getId())
            .map(existingBankTag -> {
                bankTagMapper.partialUpdate(existingBankTag, bankTagDTO);

                return existingBankTag;
            })
            .map(bankTagRepository::save)
            .map(bankTagMapper::toDto);
    }

    /**
     * Get all the bankTags.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BankTagDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BankTags");
        return bankTagRepository.findAll(pageable).map(bankTagMapper::toDto);
    }

    /**
     * Get one bankTag by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BankTagDTO> findOne(Long id) {
        log.debug("Request to get BankTag : {}", id);
        return bankTagRepository.findById(id).map(bankTagMapper::toDto);
    }

    /**
     * Delete the bankTag by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BankTag : {}", id);
        bankTagRepository.deleteById(id);
    }
}
