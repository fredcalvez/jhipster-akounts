package com.akounts.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.akounts.myapp.IntegrationTest;
import com.akounts.myapp.domain.BankVendor;
import com.akounts.myapp.repository.BankVendorRepository;
import com.akounts.myapp.service.dto.BankVendorDTO;
import com.akounts.myapp.service.mapper.BankVendorMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BankVendorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BankVendorResourceIT {

    private static final String DEFAULT_VENDOR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_USE_COUNT = 1;
    private static final Integer UPDATED_USE_COUNT = 2;

    private static final String ENTITY_API_URL = "/api/bank-vendors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BankVendorRepository bankVendorRepository;

    @Autowired
    private BankVendorMapper bankVendorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBankVendorMockMvc;

    private BankVendor bankVendor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankVendor createEntity(EntityManager em) {
        BankVendor bankVendor = new BankVendor().vendorName(DEFAULT_VENDOR_NAME).useCount(DEFAULT_USE_COUNT);
        return bankVendor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankVendor createUpdatedEntity(EntityManager em) {
        BankVendor bankVendor = new BankVendor().vendorName(UPDATED_VENDOR_NAME).useCount(UPDATED_USE_COUNT);
        return bankVendor;
    }

    @BeforeEach
    public void initTest() {
        bankVendor = createEntity(em);
    }

    @Test
    @Transactional
    void createBankVendor() throws Exception {
        int databaseSizeBeforeCreate = bankVendorRepository.findAll().size();
        // Create the BankVendor
        BankVendorDTO bankVendorDTO = bankVendorMapper.toDto(bankVendor);
        restBankVendorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankVendorDTO)))
            .andExpect(status().isCreated());

        // Validate the BankVendor in the database
        List<BankVendor> bankVendorList = bankVendorRepository.findAll();
        assertThat(bankVendorList).hasSize(databaseSizeBeforeCreate + 1);
        BankVendor testBankVendor = bankVendorList.get(bankVendorList.size() - 1);
        assertThat(testBankVendor.getVendorName()).isEqualTo(DEFAULT_VENDOR_NAME);
        assertThat(testBankVendor.getUseCount()).isEqualTo(DEFAULT_USE_COUNT);
    }

    @Test
    @Transactional
    void createBankVendorWithExistingId() throws Exception {
        // Create the BankVendor with an existing ID
        bankVendor.setId(1L);
        BankVendorDTO bankVendorDTO = bankVendorMapper.toDto(bankVendor);

        int databaseSizeBeforeCreate = bankVendorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankVendorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankVendorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BankVendor in the database
        List<BankVendor> bankVendorList = bankVendorRepository.findAll();
        assertThat(bankVendorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBankVendors() throws Exception {
        // Initialize the database
        bankVendorRepository.saveAndFlush(bankVendor);

        // Get all the bankVendorList
        restBankVendorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankVendor.getId().intValue())))
            .andExpect(jsonPath("$.[*].vendorName").value(hasItem(DEFAULT_VENDOR_NAME)))
            .andExpect(jsonPath("$.[*].useCount").value(hasItem(DEFAULT_USE_COUNT)));
    }

    @Test
    @Transactional
    void getBankVendor() throws Exception {
        // Initialize the database
        bankVendorRepository.saveAndFlush(bankVendor);

        // Get the bankVendor
        restBankVendorMockMvc
            .perform(get(ENTITY_API_URL_ID, bankVendor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bankVendor.getId().intValue()))
            .andExpect(jsonPath("$.vendorName").value(DEFAULT_VENDOR_NAME))
            .andExpect(jsonPath("$.useCount").value(DEFAULT_USE_COUNT));
    }

    @Test
    @Transactional
    void getNonExistingBankVendor() throws Exception {
        // Get the bankVendor
        restBankVendorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBankVendor() throws Exception {
        // Initialize the database
        bankVendorRepository.saveAndFlush(bankVendor);

        int databaseSizeBeforeUpdate = bankVendorRepository.findAll().size();

        // Update the bankVendor
        BankVendor updatedBankVendor = bankVendorRepository.findById(bankVendor.getId()).get();
        // Disconnect from session so that the updates on updatedBankVendor are not directly saved in db
        em.detach(updatedBankVendor);
        updatedBankVendor.vendorName(UPDATED_VENDOR_NAME).useCount(UPDATED_USE_COUNT);
        BankVendorDTO bankVendorDTO = bankVendorMapper.toDto(updatedBankVendor);

        restBankVendorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankVendorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankVendorDTO))
            )
            .andExpect(status().isOk());

        // Validate the BankVendor in the database
        List<BankVendor> bankVendorList = bankVendorRepository.findAll();
        assertThat(bankVendorList).hasSize(databaseSizeBeforeUpdate);
        BankVendor testBankVendor = bankVendorList.get(bankVendorList.size() - 1);
        assertThat(testBankVendor.getVendorName()).isEqualTo(UPDATED_VENDOR_NAME);
        assertThat(testBankVendor.getUseCount()).isEqualTo(UPDATED_USE_COUNT);
    }

    @Test
    @Transactional
    void putNonExistingBankVendor() throws Exception {
        int databaseSizeBeforeUpdate = bankVendorRepository.findAll().size();
        bankVendor.setId(count.incrementAndGet());

        // Create the BankVendor
        BankVendorDTO bankVendorDTO = bankVendorMapper.toDto(bankVendor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankVendorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankVendorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankVendorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankVendor in the database
        List<BankVendor> bankVendorList = bankVendorRepository.findAll();
        assertThat(bankVendorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBankVendor() throws Exception {
        int databaseSizeBeforeUpdate = bankVendorRepository.findAll().size();
        bankVendor.setId(count.incrementAndGet());

        // Create the BankVendor
        BankVendorDTO bankVendorDTO = bankVendorMapper.toDto(bankVendor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankVendorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankVendorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankVendor in the database
        List<BankVendor> bankVendorList = bankVendorRepository.findAll();
        assertThat(bankVendorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBankVendor() throws Exception {
        int databaseSizeBeforeUpdate = bankVendorRepository.findAll().size();
        bankVendor.setId(count.incrementAndGet());

        // Create the BankVendor
        BankVendorDTO bankVendorDTO = bankVendorMapper.toDto(bankVendor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankVendorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankVendorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankVendor in the database
        List<BankVendor> bankVendorList = bankVendorRepository.findAll();
        assertThat(bankVendorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBankVendorWithPatch() throws Exception {
        // Initialize the database
        bankVendorRepository.saveAndFlush(bankVendor);

        int databaseSizeBeforeUpdate = bankVendorRepository.findAll().size();

        // Update the bankVendor using partial update
        BankVendor partialUpdatedBankVendor = new BankVendor();
        partialUpdatedBankVendor.setId(bankVendor.getId());

        restBankVendorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankVendor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankVendor))
            )
            .andExpect(status().isOk());

        // Validate the BankVendor in the database
        List<BankVendor> bankVendorList = bankVendorRepository.findAll();
        assertThat(bankVendorList).hasSize(databaseSizeBeforeUpdate);
        BankVendor testBankVendor = bankVendorList.get(bankVendorList.size() - 1);
        assertThat(testBankVendor.getVendorName()).isEqualTo(DEFAULT_VENDOR_NAME);
        assertThat(testBankVendor.getUseCount()).isEqualTo(DEFAULT_USE_COUNT);
    }

    @Test
    @Transactional
    void fullUpdateBankVendorWithPatch() throws Exception {
        // Initialize the database
        bankVendorRepository.saveAndFlush(bankVendor);

        int databaseSizeBeforeUpdate = bankVendorRepository.findAll().size();

        // Update the bankVendor using partial update
        BankVendor partialUpdatedBankVendor = new BankVendor();
        partialUpdatedBankVendor.setId(bankVendor.getId());

        partialUpdatedBankVendor.vendorName(UPDATED_VENDOR_NAME).useCount(UPDATED_USE_COUNT);

        restBankVendorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankVendor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankVendor))
            )
            .andExpect(status().isOk());

        // Validate the BankVendor in the database
        List<BankVendor> bankVendorList = bankVendorRepository.findAll();
        assertThat(bankVendorList).hasSize(databaseSizeBeforeUpdate);
        BankVendor testBankVendor = bankVendorList.get(bankVendorList.size() - 1);
        assertThat(testBankVendor.getVendorName()).isEqualTo(UPDATED_VENDOR_NAME);
        assertThat(testBankVendor.getUseCount()).isEqualTo(UPDATED_USE_COUNT);
    }

    @Test
    @Transactional
    void patchNonExistingBankVendor() throws Exception {
        int databaseSizeBeforeUpdate = bankVendorRepository.findAll().size();
        bankVendor.setId(count.incrementAndGet());

        // Create the BankVendor
        BankVendorDTO bankVendorDTO = bankVendorMapper.toDto(bankVendor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankVendorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bankVendorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankVendorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankVendor in the database
        List<BankVendor> bankVendorList = bankVendorRepository.findAll();
        assertThat(bankVendorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBankVendor() throws Exception {
        int databaseSizeBeforeUpdate = bankVendorRepository.findAll().size();
        bankVendor.setId(count.incrementAndGet());

        // Create the BankVendor
        BankVendorDTO bankVendorDTO = bankVendorMapper.toDto(bankVendor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankVendorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankVendorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankVendor in the database
        List<BankVendor> bankVendorList = bankVendorRepository.findAll();
        assertThat(bankVendorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBankVendor() throws Exception {
        int databaseSizeBeforeUpdate = bankVendorRepository.findAll().size();
        bankVendor.setId(count.incrementAndGet());

        // Create the BankVendor
        BankVendorDTO bankVendorDTO = bankVendorMapper.toDto(bankVendor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankVendorMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bankVendorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankVendor in the database
        List<BankVendor> bankVendorList = bankVendorRepository.findAll();
        assertThat(bankVendorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBankVendor() throws Exception {
        // Initialize the database
        bankVendorRepository.saveAndFlush(bankVendor);

        int databaseSizeBeforeDelete = bankVendorRepository.findAll().size();

        // Delete the bankVendor
        restBankVendorMockMvc
            .perform(delete(ENTITY_API_URL_ID, bankVendor.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BankVendor> bankVendorList = bankVendorRepository.findAll();
        assertThat(bankVendorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
