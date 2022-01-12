package com.akounts.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.akounts.myapp.IntegrationTest;
import com.akounts.myapp.domain.BankTagTransaction;
import com.akounts.myapp.repository.BankTagTransactionRepository;
import com.akounts.myapp.service.dto.BankTagTransactionDTO;
import com.akounts.myapp.service.mapper.BankTagTransactionMapper;
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
 * Integration tests for the {@link BankTagTransactionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BankTagTransactionResourceIT {

    private static final String ENTITY_API_URL = "/api/bank-tag-transactions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BankTagTransactionRepository bankTagTransactionRepository;

    @Autowired
    private BankTagTransactionMapper bankTagTransactionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBankTagTransactionMockMvc;

    private BankTagTransaction bankTagTransaction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankTagTransaction createEntity(EntityManager em) {
        BankTagTransaction bankTagTransaction = new BankTagTransaction();
        return bankTagTransaction;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankTagTransaction createUpdatedEntity(EntityManager em) {
        BankTagTransaction bankTagTransaction = new BankTagTransaction();
        return bankTagTransaction;
    }

    @BeforeEach
    public void initTest() {
        bankTagTransaction = createEntity(em);
    }

    @Test
    @Transactional
    void createBankTagTransaction() throws Exception {
        int databaseSizeBeforeCreate = bankTagTransactionRepository.findAll().size();
        // Create the BankTagTransaction
        BankTagTransactionDTO bankTagTransactionDTO = bankTagTransactionMapper.toDto(bankTagTransaction);
        restBankTagTransactionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankTagTransactionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BankTagTransaction in the database
        List<BankTagTransaction> bankTagTransactionList = bankTagTransactionRepository.findAll();
        assertThat(bankTagTransactionList).hasSize(databaseSizeBeforeCreate + 1);
        BankTagTransaction testBankTagTransaction = bankTagTransactionList.get(bankTagTransactionList.size() - 1);
    }

    @Test
    @Transactional
    void createBankTagTransactionWithExistingId() throws Exception {
        // Create the BankTagTransaction with an existing ID
        bankTagTransaction.setId(1L);
        BankTagTransactionDTO bankTagTransactionDTO = bankTagTransactionMapper.toDto(bankTagTransaction);

        int databaseSizeBeforeCreate = bankTagTransactionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankTagTransactionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankTagTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankTagTransaction in the database
        List<BankTagTransaction> bankTagTransactionList = bankTagTransactionRepository.findAll();
        assertThat(bankTagTransactionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBankTagTransactions() throws Exception {
        // Initialize the database
        bankTagTransactionRepository.saveAndFlush(bankTagTransaction);

        // Get all the bankTagTransactionList
        restBankTagTransactionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankTagTransaction.getId().intValue())));
    }

    @Test
    @Transactional
    void getBankTagTransaction() throws Exception {
        // Initialize the database
        bankTagTransactionRepository.saveAndFlush(bankTagTransaction);

        // Get the bankTagTransaction
        restBankTagTransactionMockMvc
            .perform(get(ENTITY_API_URL_ID, bankTagTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bankTagTransaction.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingBankTagTransaction() throws Exception {
        // Get the bankTagTransaction
        restBankTagTransactionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBankTagTransaction() throws Exception {
        // Initialize the database
        bankTagTransactionRepository.saveAndFlush(bankTagTransaction);

        int databaseSizeBeforeUpdate = bankTagTransactionRepository.findAll().size();

        // Update the bankTagTransaction
        BankTagTransaction updatedBankTagTransaction = bankTagTransactionRepository.findById(bankTagTransaction.getId()).get();
        // Disconnect from session so that the updates on updatedBankTagTransaction are not directly saved in db
        em.detach(updatedBankTagTransaction);
        BankTagTransactionDTO bankTagTransactionDTO = bankTagTransactionMapper.toDto(updatedBankTagTransaction);

        restBankTagTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankTagTransactionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankTagTransactionDTO))
            )
            .andExpect(status().isOk());

        // Validate the BankTagTransaction in the database
        List<BankTagTransaction> bankTagTransactionList = bankTagTransactionRepository.findAll();
        assertThat(bankTagTransactionList).hasSize(databaseSizeBeforeUpdate);
        BankTagTransaction testBankTagTransaction = bankTagTransactionList.get(bankTagTransactionList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingBankTagTransaction() throws Exception {
        int databaseSizeBeforeUpdate = bankTagTransactionRepository.findAll().size();
        bankTagTransaction.setId(count.incrementAndGet());

        // Create the BankTagTransaction
        BankTagTransactionDTO bankTagTransactionDTO = bankTagTransactionMapper.toDto(bankTagTransaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankTagTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankTagTransactionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankTagTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankTagTransaction in the database
        List<BankTagTransaction> bankTagTransactionList = bankTagTransactionRepository.findAll();
        assertThat(bankTagTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBankTagTransaction() throws Exception {
        int databaseSizeBeforeUpdate = bankTagTransactionRepository.findAll().size();
        bankTagTransaction.setId(count.incrementAndGet());

        // Create the BankTagTransaction
        BankTagTransactionDTO bankTagTransactionDTO = bankTagTransactionMapper.toDto(bankTagTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankTagTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankTagTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankTagTransaction in the database
        List<BankTagTransaction> bankTagTransactionList = bankTagTransactionRepository.findAll();
        assertThat(bankTagTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBankTagTransaction() throws Exception {
        int databaseSizeBeforeUpdate = bankTagTransactionRepository.findAll().size();
        bankTagTransaction.setId(count.incrementAndGet());

        // Create the BankTagTransaction
        BankTagTransactionDTO bankTagTransactionDTO = bankTagTransactionMapper.toDto(bankTagTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankTagTransactionMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankTagTransactionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankTagTransaction in the database
        List<BankTagTransaction> bankTagTransactionList = bankTagTransactionRepository.findAll();
        assertThat(bankTagTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBankTagTransactionWithPatch() throws Exception {
        // Initialize the database
        bankTagTransactionRepository.saveAndFlush(bankTagTransaction);

        int databaseSizeBeforeUpdate = bankTagTransactionRepository.findAll().size();

        // Update the bankTagTransaction using partial update
        BankTagTransaction partialUpdatedBankTagTransaction = new BankTagTransaction();
        partialUpdatedBankTagTransaction.setId(bankTagTransaction.getId());

        restBankTagTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankTagTransaction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankTagTransaction))
            )
            .andExpect(status().isOk());

        // Validate the BankTagTransaction in the database
        List<BankTagTransaction> bankTagTransactionList = bankTagTransactionRepository.findAll();
        assertThat(bankTagTransactionList).hasSize(databaseSizeBeforeUpdate);
        BankTagTransaction testBankTagTransaction = bankTagTransactionList.get(bankTagTransactionList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateBankTagTransactionWithPatch() throws Exception {
        // Initialize the database
        bankTagTransactionRepository.saveAndFlush(bankTagTransaction);

        int databaseSizeBeforeUpdate = bankTagTransactionRepository.findAll().size();

        // Update the bankTagTransaction using partial update
        BankTagTransaction partialUpdatedBankTagTransaction = new BankTagTransaction();
        partialUpdatedBankTagTransaction.setId(bankTagTransaction.getId());

        restBankTagTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankTagTransaction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankTagTransaction))
            )
            .andExpect(status().isOk());

        // Validate the BankTagTransaction in the database
        List<BankTagTransaction> bankTagTransactionList = bankTagTransactionRepository.findAll();
        assertThat(bankTagTransactionList).hasSize(databaseSizeBeforeUpdate);
        BankTagTransaction testBankTagTransaction = bankTagTransactionList.get(bankTagTransactionList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingBankTagTransaction() throws Exception {
        int databaseSizeBeforeUpdate = bankTagTransactionRepository.findAll().size();
        bankTagTransaction.setId(count.incrementAndGet());

        // Create the BankTagTransaction
        BankTagTransactionDTO bankTagTransactionDTO = bankTagTransactionMapper.toDto(bankTagTransaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankTagTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bankTagTransactionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankTagTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankTagTransaction in the database
        List<BankTagTransaction> bankTagTransactionList = bankTagTransactionRepository.findAll();
        assertThat(bankTagTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBankTagTransaction() throws Exception {
        int databaseSizeBeforeUpdate = bankTagTransactionRepository.findAll().size();
        bankTagTransaction.setId(count.incrementAndGet());

        // Create the BankTagTransaction
        BankTagTransactionDTO bankTagTransactionDTO = bankTagTransactionMapper.toDto(bankTagTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankTagTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankTagTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankTagTransaction in the database
        List<BankTagTransaction> bankTagTransactionList = bankTagTransactionRepository.findAll();
        assertThat(bankTagTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBankTagTransaction() throws Exception {
        int databaseSizeBeforeUpdate = bankTagTransactionRepository.findAll().size();
        bankTagTransaction.setId(count.incrementAndGet());

        // Create the BankTagTransaction
        BankTagTransactionDTO bankTagTransactionDTO = bankTagTransactionMapper.toDto(bankTagTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankTagTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankTagTransactionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankTagTransaction in the database
        List<BankTagTransaction> bankTagTransactionList = bankTagTransactionRepository.findAll();
        assertThat(bankTagTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBankTagTransaction() throws Exception {
        // Initialize the database
        bankTagTransactionRepository.saveAndFlush(bankTagTransaction);

        int databaseSizeBeforeDelete = bankTagTransactionRepository.findAll().size();

        // Delete the bankTagTransaction
        restBankTagTransactionMockMvc
            .perform(delete(ENTITY_API_URL_ID, bankTagTransaction.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BankTagTransaction> bankTagTransactionList = bankTagTransactionRepository.findAll();
        assertThat(bankTagTransactionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
