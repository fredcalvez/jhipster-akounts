package com.akounts.myapp.service;

import com.akounts.myapp.domain.BankVendor;
import com.akounts.myapp.repository.BankVendorRepository;
import com.akounts.myapp.service.dto.BankVendorDTO;
import com.akounts.myapp.service.mapper.BankVendorMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BankVendor}.
 */
@Service
@Transactional
public class BankVendorService {

    private final Logger log = LoggerFactory.getLogger(BankVendorService.class);

    private final BankVendorRepository bankVendorRepository;

    private final BankVendorMapper bankVendorMapper;

    public BankVendorService(BankVendorRepository bankVendorRepository, BankVendorMapper bankVendorMapper) {
        this.bankVendorRepository = bankVendorRepository;
        this.bankVendorMapper = bankVendorMapper;
    }

    /**
     * Save a bankVendor.
     *
     * @param bankVendorDTO the entity to save.
     * @return the persisted entity.
     */
    public BankVendorDTO save(BankVendorDTO bankVendorDTO) {
        log.debug("Request to save BankVendor : {}", bankVendorDTO);
        BankVendor bankVendor = bankVendorMapper.toEntity(bankVendorDTO);
        bankVendor = bankVendorRepository.save(bankVendor);
        return bankVendorMapper.toDto(bankVendor);
    }

    /**
     * Partially update a bankVendor.
     *
     * @param bankVendorDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BankVendorDTO> partialUpdate(BankVendorDTO bankVendorDTO) {
        log.debug("Request to partially update BankVendor : {}", bankVendorDTO);

        return bankVendorRepository
            .findById(bankVendorDTO.getId())
            .map(existingBankVendor -> {
                bankVendorMapper.partialUpdate(existingBankVendor, bankVendorDTO);

                return existingBankVendor;
            })
            .map(bankVendorRepository::save)
            .map(bankVendorMapper::toDto);
    }

    /**
     * Get all the bankVendors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BankVendorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BankVendors");
        return bankVendorRepository.findAll(pageable).map(bankVendorMapper::toDto);
    }

    /**
     * Get one bankVendor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BankVendorDTO> findOne(Long id) {
        log.debug("Request to get BankVendor : {}", id);
        return bankVendorRepository.findById(id).map(bankVendorMapper::toDto);
    }

    /**
     * Delete the bankVendor by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BankVendor : {}", id);
        bankVendorRepository.deleteById(id);
    }
}
