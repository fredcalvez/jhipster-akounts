package com.akounts.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.akounts.myapp.IntegrationTest;
import com.akounts.myapp.domain.BankProjectTransaction;
import com.akounts.myapp.repository.BankProjectTransactionRepository;
import com.akounts.myapp.service.dto.BankProjectTransactionDTO;
import com.akounts.myapp.service.mapper.BankProjectTransactionMapper;
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
 * Integration tests for the {@link BankProjectTransactionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BankProjectTransactionResourceIT {

    private static final String ENTITY_API_URL = "/api/bank-project-transactions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BankProjectTransactionRepository bankProjectTransactionRepository;

    @Autowired
    private BankProjectTransactionMapper bankProjectTransactionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBankProjectTransactionMockMvc;

    private BankProjectTransaction bankProjectTransaction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankProjectTransaction createEntity(EntityManager em) {
        BankProjectTransaction bankProjectTransaction = new BankProjectTransaction();
        return bankProjectTransaction;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankProjectTransaction createUpdatedEntity(EntityManager em) {
        BankProjectTransaction bankProjectTransaction = new BankProjectTransaction();
        return bankProjectTransaction;
    }

    @BeforeEach
    public void initTest() {
        bankProjectTransaction = createEntity(em);
    }

    @Test
    @Transactional
    void createBankProjectTransaction() throws Exception {
        int databaseSizeBeforeCreate = bankProjectTransactionRepository.findAll().size();
        // Create the BankProjectTransaction
        BankProjectTransactionDTO bankProjectTransactionDTO = bankProjectTransactionMapper.toDto(bankProjectTransaction);
        restBankProjectTransactionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankProjectTransactionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BankProjectTransaction in the database
        List<BankProjectTransaction> bankProjectTransactionList = bankProjectTransactionRepository.findAll();
        assertThat(bankProjectTransactionList).hasSize(databaseSizeBeforeCreate + 1);
        BankProjectTransaction testBankProjectTransaction = bankProjectTransactionList.get(bankProjectTransactionList.size() - 1);
    }

    @Test
    @Transactional
    void createBankProjectTransactionWithExistingId() throws Exception {
        // Create the BankProjectTransaction with an existing ID
        bankProjectTransaction.setId(1L);
        BankProjectTransactionDTO bankProjectTransactionDTO = bankProjectTransactionMapper.toDto(bankProjectTransaction);

        int databaseSizeBeforeCreate = bankProjectTransactionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankProjectTransactionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankProjectTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankProjectTransaction in the database
        List<BankProjectTransaction> bankProjectTransactionList = bankProjectTransactionRepository.findAll();
        assertThat(bankProjectTransactionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBankProjectTransactions() throws Exception {
        // Initialize the database
        bankProjectTransactionRepository.saveAndFlush(bankProjectTransaction);

        // Get all the bankProjectTransactionList
        restBankProjectTransactionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankProjectTransaction.getId().intValue())));
    }

    @Test
    @Transactional
    void getBankProjectTransaction() throws Exception {
        // Initialize the database
        bankProjectTransactionRepository.saveAndFlush(bankProjectTransaction);

        // Get the bankProjectTransaction
        restBankProjectTransactionMockMvc
            .perform(get(ENTITY_API_URL_ID, bankProjectTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bankProjectTransaction.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingBankProjectTransaction() throws Exception {
        // Get the bankProjectTransaction
        restBankProjectTransactionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBankProjectTransaction() throws Exception {
        // Initialize the database
        bankProjectTransactionRepository.saveAndFlush(bankProjectTransaction);

        int databaseSizeBeforeUpdate = bankProjectTransactionRepository.findAll().size();

        // Update the bankProjectTransaction
        BankProjectTransaction updatedBankProjectTransaction = bankProjectTransactionRepository
            .findById(bankProjectTransaction.getId())
            .get();
        // Disconnect from session so that the updates on updatedBankProjectTransaction are not directly saved in db
        em.detach(updatedBankProjectTransaction);
        BankProjectTransactionDTO bankProjectTransactionDTO = bankProjectTransactionMapper.toDto(updatedBankProjectTransaction);

        restBankProjectTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankProjectTransactionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankProjectTransactionDTO))
            )
            .andExpect(status().isOk());

        // Validate the BankProjectTransaction in the database
        List<BankProjectTransaction> bankProjectTransactionList = bankProjectTransactionRepository.findAll();
        assertThat(bankProjectTransactionList).hasSize(databaseSizeBeforeUpdate);
        BankProjectTransaction testBankProjectTransaction = bankProjectTransactionList.get(bankProjectTransactionList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingBankProjectTransaction() throws Exception {
        int databaseSizeBeforeUpdate = bankProjectTransactionRepository.findAll().size();
        bankProjectTransaction.setId(count.incrementAndGet());

        // Create the BankProjectTransaction
        BankProjectTransactionDTO bankProjectTransactionDTO = bankProjectTransactionMapper.toDto(bankProjectTransaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankProjectTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankProjectTransactionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankProjectTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankProjectTransaction in the database
        List<BankProjectTransaction> bankProjectTransactionList = bankProjectTransactionRepository.findAll();
        assertThat(bankProjectTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBankProjectTransaction() throws Exception {
        int databaseSizeBeforeUpdate = bankProjectTransactionRepository.findAll().size();
        bankProjectTransaction.setId(count.incrementAndGet());

        // Create the BankProjectTransaction
        BankProjectTransactionDTO bankProjectTransactionDTO = bankProjectTransactionMapper.toDto(bankProjectTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankProjectTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankProjectTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankProjectTransaction in the database
        List<BankProjectTransaction> bankProjectTransactionList = bankProjectTransactionRepository.findAll();
        assertThat(bankProjectTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBankProjectTransaction() throws Exception {
        int databaseSizeBeforeUpdate = bankProjectTransactionRepository.findAll().size();
        bankProjectTransaction.setId(count.incrementAndGet());

        // Create the BankProjectTransaction
        BankProjectTransactionDTO bankProjectTransactionDTO = bankProjectTransactionMapper.toDto(bankProjectTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankProjectTransactionMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankProjectTransactionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankProjectTransaction in the database
        List<BankProjectTransaction> bankProjectTransactionList = bankProjectTransactionRepository.findAll();
        assertThat(bankProjectTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBankProjectTransactionWithPatch() throws Exception {
        // Initialize the database
        bankProjectTransactionRepository.saveAndFlush(bankProjectTransaction);

        int databaseSizeBeforeUpdate = bankProjectTransactionRepository.findAll().size();

        // Update the bankProjectTransaction using partial update
        BankProjectTransaction partialUpdatedBankProjectTransaction = new BankProjectTransaction();
        partialUpdatedBankProjectTransaction.setId(bankProjectTransaction.getId());

        restBankProjectTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankProjectTransaction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankProjectTransaction))
            )
            .andExpect(status().isOk());

        // Validate the BankProjectTransaction in the database
        List<BankProjectTransaction> bankProjectTransactionList = bankProjectTransactionRepository.findAll();
        assertThat(bankProjectTransactionList).hasSize(databaseSizeBeforeUpdate);
        BankProjectTransaction testBankProjectTransaction = bankProjectTransactionList.get(bankProjectTransactionList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateBankProjectTransactionWithPatch() throws Exception {
        // Initialize the database
        bankProjectTransactionRepository.saveAndFlush(bankProjectTransaction);

        int databaseSizeBeforeUpdate = bankProjectTransactionRepository.findAll().size();

        // Update the bankProjectTransaction using partial update
        BankProjectTransaction partialUpdatedBankProjectTransaction = new BankProjectTransaction();
        partialUpdatedBankProjectTransaction.setId(bankProjectTransaction.getId());

        restBankProjectTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankProjectTransaction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankProjectTransaction))
            )
            .andExpect(status().isOk());

        // Validate the BankProjectTransaction in the database
        List<BankProjectTransaction> bankProjectTransactionList = bankProjectTransactionRepository.findAll();
        assertThat(bankProjectTransactionList).hasSize(databaseSizeBeforeUpdate);
        BankProjectTransaction testBankProjectTransaction = bankProjectTransactionList.get(bankProjectTransactionList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingBankProjectTransaction() throws Exception {
        int databaseSizeBeforeUpdate = bankProjectTransactionRepository.findAll().size();
        bankProjectTransaction.setId(count.incrementAndGet());

        // Create the BankProjectTransaction
        BankProjectTransactionDTO bankProjectTransactionDTO = bankProjectTransactionMapper.toDto(bankProjectTransaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankProjectTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bankProjectTransactionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankProjectTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankProjectTransaction in the database
        List<BankProjectTransaction> bankProjectTransactionList = bankProjectTransactionRepository.findAll();
        assertThat(bankProjectTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBankProjectTransaction() throws Exception {
        int databaseSizeBeforeUpdate = bankProjectTransactionRepository.findAll().size();
        bankProjectTransaction.setId(count.incrementAndGet());

        // Create the BankProjectTransaction
        BankProjectTransactionDTO bankProjectTransactionDTO = bankProjectTransactionMapper.toDto(bankProjectTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankProjectTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankProjectTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankProjectTransaction in the database
        List<BankProjectTransaction> bankProjectTransactionList = bankProjectTransactionRepository.findAll();
        assertThat(bankProjectTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBankProjectTransaction() throws Exception {
        int databaseSizeBeforeUpdate = bankProjectTransactionRepository.findAll().size();
        bankProjectTransaction.setId(count.incrementAndGet());

        // Create the BankProjectTransaction
        BankProjectTransactionDTO bankProjectTransactionDTO = bankProjectTransactionMapper.toDto(bankProjectTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankProjectTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankProjectTransactionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankProjectTransaction in the database
        List<BankProjectTransaction> bankProjectTransactionList = bankProjectTransactionRepository.findAll();
        assertThat(bankProjectTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBankProjectTransaction() throws Exception {
        // Initialize the database
        bankProjectTransactionRepository.saveAndFlush(bankProjectTransaction);

        int databaseSizeBeforeDelete = bankProjectTransactionRepository.findAll().size();

        // Delete the bankProjectTransaction
        restBankProjectTransactionMockMvc
            .perform(delete(ENTITY_API_URL_ID, bankProjectTransaction.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BankProjectTransaction> bankProjectTransactionList = bankProjectTransactionRepository.findAll();
        assertThat(bankProjectTransactionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
