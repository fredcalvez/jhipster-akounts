package com.akounts.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.akounts.myapp.IntegrationTest;
import com.akounts.myapp.domain.BankStreamTransaction;
import com.akounts.myapp.repository.BankStreamTransactionRepository;
import com.akounts.myapp.service.dto.BankStreamTransactionDTO;
import com.akounts.myapp.service.mapper.BankStreamTransactionMapper;
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
 * Integration tests for the {@link BankStreamTransactionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BankStreamTransactionResourceIT {

    private static final String ENTITY_API_URL = "/api/bank-stream-transactions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BankStreamTransactionRepository bankStreamTransactionRepository;

    @Autowired
    private BankStreamTransactionMapper bankStreamTransactionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBankStreamTransactionMockMvc;

    private BankStreamTransaction bankStreamTransaction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankStreamTransaction createEntity(EntityManager em) {
        BankStreamTransaction bankStreamTransaction = new BankStreamTransaction();
        return bankStreamTransaction;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankStreamTransaction createUpdatedEntity(EntityManager em) {
        BankStreamTransaction bankStreamTransaction = new BankStreamTransaction();
        return bankStreamTransaction;
    }

    @BeforeEach
    public void initTest() {
        bankStreamTransaction = createEntity(em);
    }

    @Test
    @Transactional
    void createBankStreamTransaction() throws Exception {
        int databaseSizeBeforeCreate = bankStreamTransactionRepository.findAll().size();
        // Create the BankStreamTransaction
        BankStreamTransactionDTO bankStreamTransactionDTO = bankStreamTransactionMapper.toDto(bankStreamTransaction);
        restBankStreamTransactionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankStreamTransactionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BankStreamTransaction in the database
        List<BankStreamTransaction> bankStreamTransactionList = bankStreamTransactionRepository.findAll();
        assertThat(bankStreamTransactionList).hasSize(databaseSizeBeforeCreate + 1);
        BankStreamTransaction testBankStreamTransaction = bankStreamTransactionList.get(bankStreamTransactionList.size() - 1);
    }

    @Test
    @Transactional
    void createBankStreamTransactionWithExistingId() throws Exception {
        // Create the BankStreamTransaction with an existing ID
        bankStreamTransaction.setId(1L);
        BankStreamTransactionDTO bankStreamTransactionDTO = bankStreamTransactionMapper.toDto(bankStreamTransaction);

        int databaseSizeBeforeCreate = bankStreamTransactionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankStreamTransactionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankStreamTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankStreamTransaction in the database
        List<BankStreamTransaction> bankStreamTransactionList = bankStreamTransactionRepository.findAll();
        assertThat(bankStreamTransactionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBankStreamTransactions() throws Exception {
        // Initialize the database
        bankStreamTransactionRepository.saveAndFlush(bankStreamTransaction);

        // Get all the bankStreamTransactionList
        restBankStreamTransactionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankStreamTransaction.getId().intValue())));
    }

    @Test
    @Transactional
    void getBankStreamTransaction() throws Exception {
        // Initialize the database
        bankStreamTransactionRepository.saveAndFlush(bankStreamTransaction);

        // Get the bankStreamTransaction
        restBankStreamTransactionMockMvc
            .perform(get(ENTITY_API_URL_ID, bankStreamTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bankStreamTransaction.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingBankStreamTransaction() throws Exception {
        // Get the bankStreamTransaction
        restBankStreamTransactionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBankStreamTransaction() throws Exception {
        // Initialize the database
        bankStreamTransactionRepository.saveAndFlush(bankStreamTransaction);

        int databaseSizeBeforeUpdate = bankStreamTransactionRepository.findAll().size();

        // Update the bankStreamTransaction
        BankStreamTransaction updatedBankStreamTransaction = bankStreamTransactionRepository.findById(bankStreamTransaction.getId()).get();
        // Disconnect from session so that the updates on updatedBankStreamTransaction are not directly saved in db
        em.detach(updatedBankStreamTransaction);
        BankStreamTransactionDTO bankStreamTransactionDTO = bankStreamTransactionMapper.toDto(updatedBankStreamTransaction);

        restBankStreamTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankStreamTransactionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankStreamTransactionDTO))
            )
            .andExpect(status().isOk());

        // Validate the BankStreamTransaction in the database
        List<BankStreamTransaction> bankStreamTransactionList = bankStreamTransactionRepository.findAll();
        assertThat(bankStreamTransactionList).hasSize(databaseSizeBeforeUpdate);
        BankStreamTransaction testBankStreamTransaction = bankStreamTransactionList.get(bankStreamTransactionList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingBankStreamTransaction() throws Exception {
        int databaseSizeBeforeUpdate = bankStreamTransactionRepository.findAll().size();
        bankStreamTransaction.setId(count.incrementAndGet());

        // Create the BankStreamTransaction
        BankStreamTransactionDTO bankStreamTransactionDTO = bankStreamTransactionMapper.toDto(bankStreamTransaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankStreamTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankStreamTransactionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankStreamTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankStreamTransaction in the database
        List<BankStreamTransaction> bankStreamTransactionList = bankStreamTransactionRepository.findAll();
        assertThat(bankStreamTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBankStreamTransaction() throws Exception {
        int databaseSizeBeforeUpdate = bankStreamTransactionRepository.findAll().size();
        bankStreamTransaction.setId(count.incrementAndGet());

        // Create the BankStreamTransaction
        BankStreamTransactionDTO bankStreamTransactionDTO = bankStreamTransactionMapper.toDto(bankStreamTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankStreamTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankStreamTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankStreamTransaction in the database
        List<BankStreamTransaction> bankStreamTransactionList = bankStreamTransactionRepository.findAll();
        assertThat(bankStreamTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBankStreamTransaction() throws Exception {
        int databaseSizeBeforeUpdate = bankStreamTransactionRepository.findAll().size();
        bankStreamTransaction.setId(count.incrementAndGet());

        // Create the BankStreamTransaction
        BankStreamTransactionDTO bankStreamTransactionDTO = bankStreamTransactionMapper.toDto(bankStreamTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankStreamTransactionMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankStreamTransactionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankStreamTransaction in the database
        List<BankStreamTransaction> bankStreamTransactionList = bankStreamTransactionRepository.findAll();
        assertThat(bankStreamTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBankStreamTransactionWithPatch() throws Exception {
        // Initialize the database
        bankStreamTransactionRepository.saveAndFlush(bankStreamTransaction);

        int databaseSizeBeforeUpdate = bankStreamTransactionRepository.findAll().size();

        // Update the bankStreamTransaction using partial update
        BankStreamTransaction partialUpdatedBankStreamTransaction = new BankStreamTransaction();
        partialUpdatedBankStreamTransaction.setId(bankStreamTransaction.getId());

        restBankStreamTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankStreamTransaction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankStreamTransaction))
            )
            .andExpect(status().isOk());

        // Validate the BankStreamTransaction in the database
        List<BankStreamTransaction> bankStreamTransactionList = bankStreamTransactionRepository.findAll();
        assertThat(bankStreamTransactionList).hasSize(databaseSizeBeforeUpdate);
        BankStreamTransaction testBankStreamTransaction = bankStreamTransactionList.get(bankStreamTransactionList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateBankStreamTransactionWithPatch() throws Exception {
        // Initialize the database
        bankStreamTransactionRepository.saveAndFlush(bankStreamTransaction);

        int databaseSizeBeforeUpdate = bankStreamTransactionRepository.findAll().size();

        // Update the bankStreamTransaction using partial update
        BankStreamTransaction partialUpdatedBankStreamTransaction = new BankStreamTransaction();
        partialUpdatedBankStreamTransaction.setId(bankStreamTransaction.getId());

        restBankStreamTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankStreamTransaction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankStreamTransaction))
            )
            .andExpect(status().isOk());

        // Validate the BankStreamTransaction in the database
        List<BankStreamTransaction> bankStreamTransactionList = bankStreamTransactionRepository.findAll();
        assertThat(bankStreamTransactionList).hasSize(databaseSizeBeforeUpdate);
        BankStreamTransaction testBankStreamTransaction = bankStreamTransactionList.get(bankStreamTransactionList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingBankStreamTransaction() throws Exception {
        int databaseSizeBeforeUpdate = bankStreamTransactionRepository.findAll().size();
        bankStreamTransaction.setId(count.incrementAndGet());

        // Create the BankStreamTransaction
        BankStreamTransactionDTO bankStreamTransactionDTO = bankStreamTransactionMapper.toDto(bankStreamTransaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankStreamTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bankStreamTransactionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankStreamTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankStreamTransaction in the database
        List<BankStreamTransaction> bankStreamTransactionList = bankStreamTransactionRepository.findAll();
        assertThat(bankStreamTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBankStreamTransaction() throws Exception {
        int databaseSizeBeforeUpdate = bankStreamTransactionRepository.findAll().size();
        bankStreamTransaction.setId(count.incrementAndGet());

        // Create the BankStreamTransaction
        BankStreamTransactionDTO bankStreamTransactionDTO = bankStreamTransactionMapper.toDto(bankStreamTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankStreamTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankStreamTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankStreamTransaction in the database
        List<BankStreamTransaction> bankStreamTransactionList = bankStreamTransactionRepository.findAll();
        assertThat(bankStreamTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBankStreamTransaction() throws Exception {
        int databaseSizeBeforeUpdate = bankStreamTransactionRepository.findAll().size();
        bankStreamTransaction.setId(count.incrementAndGet());

        // Create the BankStreamTransaction
        BankStreamTransactionDTO bankStreamTransactionDTO = bankStreamTransactionMapper.toDto(bankStreamTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankStreamTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankStreamTransactionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankStreamTransaction in the database
        List<BankStreamTransaction> bankStreamTransactionList = bankStreamTransactionRepository.findAll();
        assertThat(bankStreamTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBankStreamTransaction() throws Exception {
        // Initialize the database
        bankStreamTransactionRepository.saveAndFlush(bankStreamTransaction);

        int databaseSizeBeforeDelete = bankStreamTransactionRepository.findAll().size();

        // Delete the bankStreamTransaction
        restBankStreamTransactionMockMvc
            .perform(delete(ENTITY_API_URL_ID, bankStreamTransaction.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BankStreamTransaction> bankStreamTransactionList = bankStreamTransactionRepository.findAll();
        assertThat(bankStreamTransactionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
