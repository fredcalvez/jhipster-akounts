package com.akounts.myapp.web.rest;

import static com.akounts.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.akounts.myapp.IntegrationTest;
import com.akounts.myapp.domain.PlaidTransaction;
import com.akounts.myapp.repository.PlaidTransactionRepository;
import com.akounts.myapp.service.dto.PlaidTransactionDTO;
import com.akounts.myapp.service.mapper.PlaidTransactionMapper;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link PlaidTransactionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlaidTransactionResourceIT {

    private static final String DEFAULT_TRANSACTION_ID = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSACTION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_ID = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_ID = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final String DEFAULT_ISO_CURRENCY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ISO_CURRENCY_CODE = "BBBBBBBBBB";

    private static final Instant DEFAULT_TRANSACTION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TRANSACTION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGINAL_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINAL_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PENDING = false;
    private static final Boolean UPDATED_PENDING = true;

    private static final String DEFAULT_PENDING_TRANSACTION_ID = "AAAAAAAAAA";
    private static final String UPDATED_PENDING_TRANSACTION_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_ADDED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ADDED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_CHECKED = false;
    private static final Boolean UPDATED_CHECKED = true;

    private static final String ENTITY_API_URL = "/api/plaid-transactions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PlaidTransactionRepository plaidTransactionRepository;

    @Autowired
    private PlaidTransactionMapper plaidTransactionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlaidTransactionMockMvc;

    private PlaidTransaction plaidTransaction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlaidTransaction createEntity(EntityManager em) {
        PlaidTransaction plaidTransaction = new PlaidTransaction()
            .transactionId(DEFAULT_TRANSACTION_ID)
            .transactionType(DEFAULT_TRANSACTION_TYPE)
            .accountId(DEFAULT_ACCOUNT_ID)
            .amount(DEFAULT_AMOUNT)
            .isoCurrencyCode(DEFAULT_ISO_CURRENCY_CODE)
            .transactionDate(DEFAULT_TRANSACTION_DATE)
            .name(DEFAULT_NAME)
            .originalDescription(DEFAULT_ORIGINAL_DESCRIPTION)
            .pending(DEFAULT_PENDING)
            .pendingTransactionId(DEFAULT_PENDING_TRANSACTION_ID)
            .addedDate(DEFAULT_ADDED_DATE)
            .checked(DEFAULT_CHECKED);
        return plaidTransaction;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlaidTransaction createUpdatedEntity(EntityManager em) {
        PlaidTransaction plaidTransaction = new PlaidTransaction()
            .transactionId(UPDATED_TRANSACTION_ID)
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .accountId(UPDATED_ACCOUNT_ID)
            .amount(UPDATED_AMOUNT)
            .isoCurrencyCode(UPDATED_ISO_CURRENCY_CODE)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .name(UPDATED_NAME)
            .originalDescription(UPDATED_ORIGINAL_DESCRIPTION)
            .pending(UPDATED_PENDING)
            .pendingTransactionId(UPDATED_PENDING_TRANSACTION_ID)
            .addedDate(UPDATED_ADDED_DATE)
            .checked(UPDATED_CHECKED);
        return plaidTransaction;
    }

    @BeforeEach
    public void initTest() {
        plaidTransaction = createEntity(em);
    }

    @Test
    @Transactional
    void createPlaidTransaction() throws Exception {
        int databaseSizeBeforeCreate = plaidTransactionRepository.findAll().size();
        // Create the PlaidTransaction
        PlaidTransactionDTO plaidTransactionDTO = plaidTransactionMapper.toDto(plaidTransaction);
        restPlaidTransactionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plaidTransactionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PlaidTransaction in the database
        List<PlaidTransaction> plaidTransactionList = plaidTransactionRepository.findAll();
        assertThat(plaidTransactionList).hasSize(databaseSizeBeforeCreate + 1);
        PlaidTransaction testPlaidTransaction = plaidTransactionList.get(plaidTransactionList.size() - 1);
        assertThat(testPlaidTransaction.getTransactionId()).isEqualTo(DEFAULT_TRANSACTION_ID);
        assertThat(testPlaidTransaction.getTransactionType()).isEqualTo(DEFAULT_TRANSACTION_TYPE);
        assertThat(testPlaidTransaction.getAccountId()).isEqualTo(DEFAULT_ACCOUNT_ID);
        assertThat(testPlaidTransaction.getAmount()).isEqualByComparingTo(DEFAULT_AMOUNT);
        assertThat(testPlaidTransaction.getIsoCurrencyCode()).isEqualTo(DEFAULT_ISO_CURRENCY_CODE);
        assertThat(testPlaidTransaction.getTransactionDate()).isEqualTo(DEFAULT_TRANSACTION_DATE);
        assertThat(testPlaidTransaction.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPlaidTransaction.getOriginalDescription()).isEqualTo(DEFAULT_ORIGINAL_DESCRIPTION);
        assertThat(testPlaidTransaction.getPending()).isEqualTo(DEFAULT_PENDING);
        assertThat(testPlaidTransaction.getPendingTransactionId()).isEqualTo(DEFAULT_PENDING_TRANSACTION_ID);
        assertThat(testPlaidTransaction.getAddedDate()).isEqualTo(DEFAULT_ADDED_DATE);
        assertThat(testPlaidTransaction.getChecked()).isEqualTo(DEFAULT_CHECKED);
    }

    @Test
    @Transactional
    void createPlaidTransactionWithExistingId() throws Exception {
        // Create the PlaidTransaction with an existing ID
        plaidTransaction.setId(1L);
        PlaidTransactionDTO plaidTransactionDTO = plaidTransactionMapper.toDto(plaidTransaction);

        int databaseSizeBeforeCreate = plaidTransactionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlaidTransactionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plaidTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlaidTransaction in the database
        List<PlaidTransaction> plaidTransactionList = plaidTransactionRepository.findAll();
        assertThat(plaidTransactionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPlaidTransactions() throws Exception {
        // Initialize the database
        plaidTransactionRepository.saveAndFlush(plaidTransaction);

        // Get all the plaidTransactionList
        restPlaidTransactionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plaidTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactionId").value(hasItem(DEFAULT_TRANSACTION_ID)))
            .andExpect(jsonPath("$.[*].transactionType").value(hasItem(DEFAULT_TRANSACTION_TYPE)))
            .andExpect(jsonPath("$.[*].accountId").value(hasItem(DEFAULT_ACCOUNT_ID)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(sameNumber(DEFAULT_AMOUNT))))
            .andExpect(jsonPath("$.[*].isoCurrencyCode").value(hasItem(DEFAULT_ISO_CURRENCY_CODE)))
            .andExpect(jsonPath("$.[*].transactionDate").value(hasItem(DEFAULT_TRANSACTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].originalDescription").value(hasItem(DEFAULT_ORIGINAL_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].pending").value(hasItem(DEFAULT_PENDING.booleanValue())))
            .andExpect(jsonPath("$.[*].pendingTransactionId").value(hasItem(DEFAULT_PENDING_TRANSACTION_ID)))
            .andExpect(jsonPath("$.[*].addedDate").value(hasItem(DEFAULT_ADDED_DATE.toString())))
            .andExpect(jsonPath("$.[*].checked").value(hasItem(DEFAULT_CHECKED.booleanValue())));
    }

    @Test
    @Transactional
    void getPlaidTransaction() throws Exception {
        // Initialize the database
        plaidTransactionRepository.saveAndFlush(plaidTransaction);

        // Get the plaidTransaction
        restPlaidTransactionMockMvc
            .perform(get(ENTITY_API_URL_ID, plaidTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(plaidTransaction.getId().intValue()))
            .andExpect(jsonPath("$.transactionId").value(DEFAULT_TRANSACTION_ID))
            .andExpect(jsonPath("$.transactionType").value(DEFAULT_TRANSACTION_TYPE))
            .andExpect(jsonPath("$.accountId").value(DEFAULT_ACCOUNT_ID))
            .andExpect(jsonPath("$.amount").value(sameNumber(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.isoCurrencyCode").value(DEFAULT_ISO_CURRENCY_CODE))
            .andExpect(jsonPath("$.transactionDate").value(DEFAULT_TRANSACTION_DATE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.originalDescription").value(DEFAULT_ORIGINAL_DESCRIPTION))
            .andExpect(jsonPath("$.pending").value(DEFAULT_PENDING.booleanValue()))
            .andExpect(jsonPath("$.pendingTransactionId").value(DEFAULT_PENDING_TRANSACTION_ID))
            .andExpect(jsonPath("$.addedDate").value(DEFAULT_ADDED_DATE.toString()))
            .andExpect(jsonPath("$.checked").value(DEFAULT_CHECKED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingPlaidTransaction() throws Exception {
        // Get the plaidTransaction
        restPlaidTransactionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPlaidTransaction() throws Exception {
        // Initialize the database
        plaidTransactionRepository.saveAndFlush(plaidTransaction);

        int databaseSizeBeforeUpdate = plaidTransactionRepository.findAll().size();

        // Update the plaidTransaction
        PlaidTransaction updatedPlaidTransaction = plaidTransactionRepository.findById(plaidTransaction.getId()).get();
        // Disconnect from session so that the updates on updatedPlaidTransaction are not directly saved in db
        em.detach(updatedPlaidTransaction);
        updatedPlaidTransaction
            .transactionId(UPDATED_TRANSACTION_ID)
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .accountId(UPDATED_ACCOUNT_ID)
            .amount(UPDATED_AMOUNT)
            .isoCurrencyCode(UPDATED_ISO_CURRENCY_CODE)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .name(UPDATED_NAME)
            .originalDescription(UPDATED_ORIGINAL_DESCRIPTION)
            .pending(UPDATED_PENDING)
            .pendingTransactionId(UPDATED_PENDING_TRANSACTION_ID)
            .addedDate(UPDATED_ADDED_DATE)
            .checked(UPDATED_CHECKED);
        PlaidTransactionDTO plaidTransactionDTO = plaidTransactionMapper.toDto(updatedPlaidTransaction);

        restPlaidTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, plaidTransactionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plaidTransactionDTO))
            )
            .andExpect(status().isOk());

        // Validate the PlaidTransaction in the database
        List<PlaidTransaction> plaidTransactionList = plaidTransactionRepository.findAll();
        assertThat(plaidTransactionList).hasSize(databaseSizeBeforeUpdate);
        PlaidTransaction testPlaidTransaction = plaidTransactionList.get(plaidTransactionList.size() - 1);
        assertThat(testPlaidTransaction.getTransactionId()).isEqualTo(UPDATED_TRANSACTION_ID);
        assertThat(testPlaidTransaction.getTransactionType()).isEqualTo(UPDATED_TRANSACTION_TYPE);
        assertThat(testPlaidTransaction.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
        assertThat(testPlaidTransaction.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPlaidTransaction.getIsoCurrencyCode()).isEqualTo(UPDATED_ISO_CURRENCY_CODE);
        assertThat(testPlaidTransaction.getTransactionDate()).isEqualTo(UPDATED_TRANSACTION_DATE);
        assertThat(testPlaidTransaction.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlaidTransaction.getOriginalDescription()).isEqualTo(UPDATED_ORIGINAL_DESCRIPTION);
        assertThat(testPlaidTransaction.getPending()).isEqualTo(UPDATED_PENDING);
        assertThat(testPlaidTransaction.getPendingTransactionId()).isEqualTo(UPDATED_PENDING_TRANSACTION_ID);
        assertThat(testPlaidTransaction.getAddedDate()).isEqualTo(UPDATED_ADDED_DATE);
        assertThat(testPlaidTransaction.getChecked()).isEqualTo(UPDATED_CHECKED);
    }

    @Test
    @Transactional
    void putNonExistingPlaidTransaction() throws Exception {
        int databaseSizeBeforeUpdate = plaidTransactionRepository.findAll().size();
        plaidTransaction.setId(count.incrementAndGet());

        // Create the PlaidTransaction
        PlaidTransactionDTO plaidTransactionDTO = plaidTransactionMapper.toDto(plaidTransaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlaidTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, plaidTransactionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plaidTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlaidTransaction in the database
        List<PlaidTransaction> plaidTransactionList = plaidTransactionRepository.findAll();
        assertThat(plaidTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlaidTransaction() throws Exception {
        int databaseSizeBeforeUpdate = plaidTransactionRepository.findAll().size();
        plaidTransaction.setId(count.incrementAndGet());

        // Create the PlaidTransaction
        PlaidTransactionDTO plaidTransactionDTO = plaidTransactionMapper.toDto(plaidTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaidTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plaidTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlaidTransaction in the database
        List<PlaidTransaction> plaidTransactionList = plaidTransactionRepository.findAll();
        assertThat(plaidTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlaidTransaction() throws Exception {
        int databaseSizeBeforeUpdate = plaidTransactionRepository.findAll().size();
        plaidTransaction.setId(count.incrementAndGet());

        // Create the PlaidTransaction
        PlaidTransactionDTO plaidTransactionDTO = plaidTransactionMapper.toDto(plaidTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaidTransactionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plaidTransactionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlaidTransaction in the database
        List<PlaidTransaction> plaidTransactionList = plaidTransactionRepository.findAll();
        assertThat(plaidTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlaidTransactionWithPatch() throws Exception {
        // Initialize the database
        plaidTransactionRepository.saveAndFlush(plaidTransaction);

        int databaseSizeBeforeUpdate = plaidTransactionRepository.findAll().size();

        // Update the plaidTransaction using partial update
        PlaidTransaction partialUpdatedPlaidTransaction = new PlaidTransaction();
        partialUpdatedPlaidTransaction.setId(plaidTransaction.getId());

        partialUpdatedPlaidTransaction
            .amount(UPDATED_AMOUNT)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .pendingTransactionId(UPDATED_PENDING_TRANSACTION_ID)
            .checked(UPDATED_CHECKED);

        restPlaidTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlaidTransaction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlaidTransaction))
            )
            .andExpect(status().isOk());

        // Validate the PlaidTransaction in the database
        List<PlaidTransaction> plaidTransactionList = plaidTransactionRepository.findAll();
        assertThat(plaidTransactionList).hasSize(databaseSizeBeforeUpdate);
        PlaidTransaction testPlaidTransaction = plaidTransactionList.get(plaidTransactionList.size() - 1);
        assertThat(testPlaidTransaction.getTransactionId()).isEqualTo(DEFAULT_TRANSACTION_ID);
        assertThat(testPlaidTransaction.getTransactionType()).isEqualTo(DEFAULT_TRANSACTION_TYPE);
        assertThat(testPlaidTransaction.getAccountId()).isEqualTo(DEFAULT_ACCOUNT_ID);
        assertThat(testPlaidTransaction.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
        assertThat(testPlaidTransaction.getIsoCurrencyCode()).isEqualTo(DEFAULT_ISO_CURRENCY_CODE);
        assertThat(testPlaidTransaction.getTransactionDate()).isEqualTo(UPDATED_TRANSACTION_DATE);
        assertThat(testPlaidTransaction.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPlaidTransaction.getOriginalDescription()).isEqualTo(DEFAULT_ORIGINAL_DESCRIPTION);
        assertThat(testPlaidTransaction.getPending()).isEqualTo(DEFAULT_PENDING);
        assertThat(testPlaidTransaction.getPendingTransactionId()).isEqualTo(UPDATED_PENDING_TRANSACTION_ID);
        assertThat(testPlaidTransaction.getAddedDate()).isEqualTo(DEFAULT_ADDED_DATE);
        assertThat(testPlaidTransaction.getChecked()).isEqualTo(UPDATED_CHECKED);
    }

    @Test
    @Transactional
    void fullUpdatePlaidTransactionWithPatch() throws Exception {
        // Initialize the database
        plaidTransactionRepository.saveAndFlush(plaidTransaction);

        int databaseSizeBeforeUpdate = plaidTransactionRepository.findAll().size();

        // Update the plaidTransaction using partial update
        PlaidTransaction partialUpdatedPlaidTransaction = new PlaidTransaction();
        partialUpdatedPlaidTransaction.setId(plaidTransaction.getId());

        partialUpdatedPlaidTransaction
            .transactionId(UPDATED_TRANSACTION_ID)
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .accountId(UPDATED_ACCOUNT_ID)
            .amount(UPDATED_AMOUNT)
            .isoCurrencyCode(UPDATED_ISO_CURRENCY_CODE)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .name(UPDATED_NAME)
            .originalDescription(UPDATED_ORIGINAL_DESCRIPTION)
            .pending(UPDATED_PENDING)
            .pendingTransactionId(UPDATED_PENDING_TRANSACTION_ID)
            .addedDate(UPDATED_ADDED_DATE)
            .checked(UPDATED_CHECKED);

        restPlaidTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlaidTransaction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlaidTransaction))
            )
            .andExpect(status().isOk());

        // Validate the PlaidTransaction in the database
        List<PlaidTransaction> plaidTransactionList = plaidTransactionRepository.findAll();
        assertThat(plaidTransactionList).hasSize(databaseSizeBeforeUpdate);
        PlaidTransaction testPlaidTransaction = plaidTransactionList.get(plaidTransactionList.size() - 1);
        assertThat(testPlaidTransaction.getTransactionId()).isEqualTo(UPDATED_TRANSACTION_ID);
        assertThat(testPlaidTransaction.getTransactionType()).isEqualTo(UPDATED_TRANSACTION_TYPE);
        assertThat(testPlaidTransaction.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
        assertThat(testPlaidTransaction.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
        assertThat(testPlaidTransaction.getIsoCurrencyCode()).isEqualTo(UPDATED_ISO_CURRENCY_CODE);
        assertThat(testPlaidTransaction.getTransactionDate()).isEqualTo(UPDATED_TRANSACTION_DATE);
        assertThat(testPlaidTransaction.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlaidTransaction.getOriginalDescription()).isEqualTo(UPDATED_ORIGINAL_DESCRIPTION);
        assertThat(testPlaidTransaction.getPending()).isEqualTo(UPDATED_PENDING);
        assertThat(testPlaidTransaction.getPendingTransactionId()).isEqualTo(UPDATED_PENDING_TRANSACTION_ID);
        assertThat(testPlaidTransaction.getAddedDate()).isEqualTo(UPDATED_ADDED_DATE);
        assertThat(testPlaidTransaction.getChecked()).isEqualTo(UPDATED_CHECKED);
    }

    @Test
    @Transactional
    void patchNonExistingPlaidTransaction() throws Exception {
        int databaseSizeBeforeUpdate = plaidTransactionRepository.findAll().size();
        plaidTransaction.setId(count.incrementAndGet());

        // Create the PlaidTransaction
        PlaidTransactionDTO plaidTransactionDTO = plaidTransactionMapper.toDto(plaidTransaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlaidTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, plaidTransactionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plaidTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlaidTransaction in the database
        List<PlaidTransaction> plaidTransactionList = plaidTransactionRepository.findAll();
        assertThat(plaidTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlaidTransaction() throws Exception {
        int databaseSizeBeforeUpdate = plaidTransactionRepository.findAll().size();
        plaidTransaction.setId(count.incrementAndGet());

        // Create the PlaidTransaction
        PlaidTransactionDTO plaidTransactionDTO = plaidTransactionMapper.toDto(plaidTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaidTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plaidTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlaidTransaction in the database
        List<PlaidTransaction> plaidTransactionList = plaidTransactionRepository.findAll();
        assertThat(plaidTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlaidTransaction() throws Exception {
        int databaseSizeBeforeUpdate = plaidTransactionRepository.findAll().size();
        plaidTransaction.setId(count.incrementAndGet());

        // Create the PlaidTransaction
        PlaidTransactionDTO plaidTransactionDTO = plaidTransactionMapper.toDto(plaidTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaidTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plaidTransactionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlaidTransaction in the database
        List<PlaidTransaction> plaidTransactionList = plaidTransactionRepository.findAll();
        assertThat(plaidTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlaidTransaction() throws Exception {
        // Initialize the database
        plaidTransactionRepository.saveAndFlush(plaidTransaction);

        int databaseSizeBeforeDelete = plaidTransactionRepository.findAll().size();

        // Delete the plaidTransaction
        restPlaidTransactionMockMvc
            .perform(delete(ENTITY_API_URL_ID, plaidTransaction.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlaidTransaction> plaidTransactionList = plaidTransactionRepository.findAll();
        assertThat(plaidTransactionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
