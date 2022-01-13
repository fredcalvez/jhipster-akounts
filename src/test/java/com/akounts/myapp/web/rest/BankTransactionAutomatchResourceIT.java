package com.akounts.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.akounts.myapp.IntegrationTest;
import com.akounts.myapp.domain.BankTransactionAutomatch;
import com.akounts.myapp.repository.BankTransactionAutomatchRepository;
import com.akounts.myapp.service.dto.BankTransactionAutomatchDTO;
import com.akounts.myapp.service.mapper.BankTransactionAutomatchMapper;
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
 * Integration tests for the {@link BankTransactionAutomatchResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BankTransactionAutomatchResourceIT {

    private static final String ENTITY_API_URL = "/api/bank-transaction-automatches";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BankTransactionAutomatchRepository bankTransactionAutomatchRepository;

    @Autowired
    private BankTransactionAutomatchMapper bankTransactionAutomatchMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBankTransactionAutomatchMockMvc;

    private BankTransactionAutomatch bankTransactionAutomatch;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankTransactionAutomatch createEntity(EntityManager em) {
        BankTransactionAutomatch bankTransactionAutomatch = new BankTransactionAutomatch();
        return bankTransactionAutomatch;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankTransactionAutomatch createUpdatedEntity(EntityManager em) {
        BankTransactionAutomatch bankTransactionAutomatch = new BankTransactionAutomatch();
        return bankTransactionAutomatch;
    }

    @BeforeEach
    public void initTest() {
        bankTransactionAutomatch = createEntity(em);
    }

    @Test
    @Transactional
    void createBankTransactionAutomatch() throws Exception {
        int databaseSizeBeforeCreate = bankTransactionAutomatchRepository.findAll().size();
        // Create the BankTransactionAutomatch
        BankTransactionAutomatchDTO bankTransactionAutomatchDTO = bankTransactionAutomatchMapper.toDto(bankTransactionAutomatch);
        restBankTransactionAutomatchMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankTransactionAutomatchDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BankTransactionAutomatch in the database
        List<BankTransactionAutomatch> bankTransactionAutomatchList = bankTransactionAutomatchRepository.findAll();
        assertThat(bankTransactionAutomatchList).hasSize(databaseSizeBeforeCreate + 1);
        BankTransactionAutomatch testBankTransactionAutomatch = bankTransactionAutomatchList.get(bankTransactionAutomatchList.size() - 1);
    }

    @Test
    @Transactional
    void createBankTransactionAutomatchWithExistingId() throws Exception {
        // Create the BankTransactionAutomatch with an existing ID
        bankTransactionAutomatch.setId(1L);
        BankTransactionAutomatchDTO bankTransactionAutomatchDTO = bankTransactionAutomatchMapper.toDto(bankTransactionAutomatch);

        int databaseSizeBeforeCreate = bankTransactionAutomatchRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankTransactionAutomatchMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankTransactionAutomatchDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankTransactionAutomatch in the database
        List<BankTransactionAutomatch> bankTransactionAutomatchList = bankTransactionAutomatchRepository.findAll();
        assertThat(bankTransactionAutomatchList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBankTransactionAutomatches() throws Exception {
        // Initialize the database
        bankTransactionAutomatchRepository.saveAndFlush(bankTransactionAutomatch);

        // Get all the bankTransactionAutomatchList
        restBankTransactionAutomatchMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankTransactionAutomatch.getId().intValue())));
    }

    @Test
    @Transactional
    void getBankTransactionAutomatch() throws Exception {
        // Initialize the database
        bankTransactionAutomatchRepository.saveAndFlush(bankTransactionAutomatch);

        // Get the bankTransactionAutomatch
        restBankTransactionAutomatchMockMvc
            .perform(get(ENTITY_API_URL_ID, bankTransactionAutomatch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bankTransactionAutomatch.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingBankTransactionAutomatch() throws Exception {
        // Get the bankTransactionAutomatch
        restBankTransactionAutomatchMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBankTransactionAutomatch() throws Exception {
        // Initialize the database
        bankTransactionAutomatchRepository.saveAndFlush(bankTransactionAutomatch);

        int databaseSizeBeforeUpdate = bankTransactionAutomatchRepository.findAll().size();

        // Update the bankTransactionAutomatch
        BankTransactionAutomatch updatedBankTransactionAutomatch = bankTransactionAutomatchRepository
            .findById(bankTransactionAutomatch.getId())
            .get();
        // Disconnect from session so that the updates on updatedBankTransactionAutomatch are not directly saved in db
        em.detach(updatedBankTransactionAutomatch);
        BankTransactionAutomatchDTO bankTransactionAutomatchDTO = bankTransactionAutomatchMapper.toDto(updatedBankTransactionAutomatch);

        restBankTransactionAutomatchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankTransactionAutomatchDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankTransactionAutomatchDTO))
            )
            .andExpect(status().isOk());

        // Validate the BankTransactionAutomatch in the database
        List<BankTransactionAutomatch> bankTransactionAutomatchList = bankTransactionAutomatchRepository.findAll();
        assertThat(bankTransactionAutomatchList).hasSize(databaseSizeBeforeUpdate);
        BankTransactionAutomatch testBankTransactionAutomatch = bankTransactionAutomatchList.get(bankTransactionAutomatchList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingBankTransactionAutomatch() throws Exception {
        int databaseSizeBeforeUpdate = bankTransactionAutomatchRepository.findAll().size();
        bankTransactionAutomatch.setId(count.incrementAndGet());

        // Create the BankTransactionAutomatch
        BankTransactionAutomatchDTO bankTransactionAutomatchDTO = bankTransactionAutomatchMapper.toDto(bankTransactionAutomatch);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankTransactionAutomatchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankTransactionAutomatchDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankTransactionAutomatchDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankTransactionAutomatch in the database
        List<BankTransactionAutomatch> bankTransactionAutomatchList = bankTransactionAutomatchRepository.findAll();
        assertThat(bankTransactionAutomatchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBankTransactionAutomatch() throws Exception {
        int databaseSizeBeforeUpdate = bankTransactionAutomatchRepository.findAll().size();
        bankTransactionAutomatch.setId(count.incrementAndGet());

        // Create the BankTransactionAutomatch
        BankTransactionAutomatchDTO bankTransactionAutomatchDTO = bankTransactionAutomatchMapper.toDto(bankTransactionAutomatch);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankTransactionAutomatchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankTransactionAutomatchDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankTransactionAutomatch in the database
        List<BankTransactionAutomatch> bankTransactionAutomatchList = bankTransactionAutomatchRepository.findAll();
        assertThat(bankTransactionAutomatchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBankTransactionAutomatch() throws Exception {
        int databaseSizeBeforeUpdate = bankTransactionAutomatchRepository.findAll().size();
        bankTransactionAutomatch.setId(count.incrementAndGet());

        // Create the BankTransactionAutomatch
        BankTransactionAutomatchDTO bankTransactionAutomatchDTO = bankTransactionAutomatchMapper.toDto(bankTransactionAutomatch);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankTransactionAutomatchMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankTransactionAutomatchDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankTransactionAutomatch in the database
        List<BankTransactionAutomatch> bankTransactionAutomatchList = bankTransactionAutomatchRepository.findAll();
        assertThat(bankTransactionAutomatchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBankTransactionAutomatchWithPatch() throws Exception {
        // Initialize the database
        bankTransactionAutomatchRepository.saveAndFlush(bankTransactionAutomatch);

        int databaseSizeBeforeUpdate = bankTransactionAutomatchRepository.findAll().size();

        // Update the bankTransactionAutomatch using partial update
        BankTransactionAutomatch partialUpdatedBankTransactionAutomatch = new BankTransactionAutomatch();
        partialUpdatedBankTransactionAutomatch.setId(bankTransactionAutomatch.getId());

        restBankTransactionAutomatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankTransactionAutomatch.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankTransactionAutomatch))
            )
            .andExpect(status().isOk());

        // Validate the BankTransactionAutomatch in the database
        List<BankTransactionAutomatch> bankTransactionAutomatchList = bankTransactionAutomatchRepository.findAll();
        assertThat(bankTransactionAutomatchList).hasSize(databaseSizeBeforeUpdate);
        BankTransactionAutomatch testBankTransactionAutomatch = bankTransactionAutomatchList.get(bankTransactionAutomatchList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateBankTransactionAutomatchWithPatch() throws Exception {
        // Initialize the database
        bankTransactionAutomatchRepository.saveAndFlush(bankTransactionAutomatch);

        int databaseSizeBeforeUpdate = bankTransactionAutomatchRepository.findAll().size();

        // Update the bankTransactionAutomatch using partial update
        BankTransactionAutomatch partialUpdatedBankTransactionAutomatch = new BankTransactionAutomatch();
        partialUpdatedBankTransactionAutomatch.setId(bankTransactionAutomatch.getId());

        restBankTransactionAutomatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankTransactionAutomatch.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankTransactionAutomatch))
            )
            .andExpect(status().isOk());

        // Validate the BankTransactionAutomatch in the database
        List<BankTransactionAutomatch> bankTransactionAutomatchList = bankTransactionAutomatchRepository.findAll();
        assertThat(bankTransactionAutomatchList).hasSize(databaseSizeBeforeUpdate);
        BankTransactionAutomatch testBankTransactionAutomatch = bankTransactionAutomatchList.get(bankTransactionAutomatchList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingBankTransactionAutomatch() throws Exception {
        int databaseSizeBeforeUpdate = bankTransactionAutomatchRepository.findAll().size();
        bankTransactionAutomatch.setId(count.incrementAndGet());

        // Create the BankTransactionAutomatch
        BankTransactionAutomatchDTO bankTransactionAutomatchDTO = bankTransactionAutomatchMapper.toDto(bankTransactionAutomatch);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankTransactionAutomatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bankTransactionAutomatchDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankTransactionAutomatchDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankTransactionAutomatch in the database
        List<BankTransactionAutomatch> bankTransactionAutomatchList = bankTransactionAutomatchRepository.findAll();
        assertThat(bankTransactionAutomatchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBankTransactionAutomatch() throws Exception {
        int databaseSizeBeforeUpdate = bankTransactionAutomatchRepository.findAll().size();
        bankTransactionAutomatch.setId(count.incrementAndGet());

        // Create the BankTransactionAutomatch
        BankTransactionAutomatchDTO bankTransactionAutomatchDTO = bankTransactionAutomatchMapper.toDto(bankTransactionAutomatch);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankTransactionAutomatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankTransactionAutomatchDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankTransactionAutomatch in the database
        List<BankTransactionAutomatch> bankTransactionAutomatchList = bankTransactionAutomatchRepository.findAll();
        assertThat(bankTransactionAutomatchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBankTransactionAutomatch() throws Exception {
        int databaseSizeBeforeUpdate = bankTransactionAutomatchRepository.findAll().size();
        bankTransactionAutomatch.setId(count.incrementAndGet());

        // Create the BankTransactionAutomatch
        BankTransactionAutomatchDTO bankTransactionAutomatchDTO = bankTransactionAutomatchMapper.toDto(bankTransactionAutomatch);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankTransactionAutomatchMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankTransactionAutomatchDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankTransactionAutomatch in the database
        List<BankTransactionAutomatch> bankTransactionAutomatchList = bankTransactionAutomatchRepository.findAll();
        assertThat(bankTransactionAutomatchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBankTransactionAutomatch() throws Exception {
        // Initialize the database
        bankTransactionAutomatchRepository.saveAndFlush(bankTransactionAutomatch);

        int databaseSizeBeforeDelete = bankTransactionAutomatchRepository.findAll().size();

        // Delete the bankTransactionAutomatch
        restBankTransactionAutomatchMockMvc
            .perform(delete(ENTITY_API_URL_ID, bankTransactionAutomatch.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BankTransactionAutomatch> bankTransactionAutomatchList = bankTransactionAutomatchRepository.findAll();
        assertThat(bankTransactionAutomatchList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
