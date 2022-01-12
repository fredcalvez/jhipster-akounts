package com.akounts.myapp.web.rest;

import static com.akounts.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.akounts.myapp.IntegrationTest;
import com.akounts.myapp.domain.BridgeTransaction;
import com.akounts.myapp.domain.enumeration.Currency;
import com.akounts.myapp.repository.BridgeTransactionRepository;
import com.akounts.myapp.service.dto.BridgeTransactionDTO;
import com.akounts.myapp.service.mapper.BridgeTransactionMapper;
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
 * Integration tests for the {@link BridgeTransactionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BridgeTransactionResourceIT {

    private static final String DEFAULT_TRANSACTION_ID = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSACTION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_ID = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_ID = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final Currency DEFAULT_ISO_CURRENCY_CODE = Currency.AED;
    private static final Currency UPDATED_ISO_CURRENCY_CODE = Currency.AFN;

    private static final Instant DEFAULT_TRANSACTION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TRANSACTION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_FUTURE = false;
    private static final Boolean UPDATED_IS_FUTURE = true;

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    private static final Instant DEFAULT_ADDED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ADDED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_CHECKED = false;
    private static final Boolean UPDATED_CHECKED = true;

    private static final String ENTITY_API_URL = "/api/bridge-transactions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BridgeTransactionRepository bridgeTransactionRepository;

    @Autowired
    private BridgeTransactionMapper bridgeTransactionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBridgeTransactionMockMvc;

    private BridgeTransaction bridgeTransaction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BridgeTransaction createEntity(EntityManager em) {
        BridgeTransaction bridgeTransaction = new BridgeTransaction()
            .transactionId(DEFAULT_TRANSACTION_ID)
            .transactionType(DEFAULT_TRANSACTION_TYPE)
            .accountId(DEFAULT_ACCOUNT_ID)
            .amount(DEFAULT_AMOUNT)
            .isoCurrencyCode(DEFAULT_ISO_CURRENCY_CODE)
            .transactionDate(DEFAULT_TRANSACTION_DATE)
            .description(DEFAULT_DESCRIPTION)
            .isFuture(DEFAULT_IS_FUTURE)
            .isDeleted(DEFAULT_IS_DELETED)
            .addedDate(DEFAULT_ADDED_DATE)
            .updatedAt(DEFAULT_UPDATED_AT)
            .checked(DEFAULT_CHECKED);
        return bridgeTransaction;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BridgeTransaction createUpdatedEntity(EntityManager em) {
        BridgeTransaction bridgeTransaction = new BridgeTransaction()
            .transactionId(UPDATED_TRANSACTION_ID)
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .accountId(UPDATED_ACCOUNT_ID)
            .amount(UPDATED_AMOUNT)
            .isoCurrencyCode(UPDATED_ISO_CURRENCY_CODE)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .description(UPDATED_DESCRIPTION)
            .isFuture(UPDATED_IS_FUTURE)
            .isDeleted(UPDATED_IS_DELETED)
            .addedDate(UPDATED_ADDED_DATE)
            .updatedAt(UPDATED_UPDATED_AT)
            .checked(UPDATED_CHECKED);
        return bridgeTransaction;
    }

    @BeforeEach
    public void initTest() {
        bridgeTransaction = createEntity(em);
    }

    @Test
    @Transactional
    void createBridgeTransaction() throws Exception {
        int databaseSizeBeforeCreate = bridgeTransactionRepository.findAll().size();
        // Create the BridgeTransaction
        BridgeTransactionDTO bridgeTransactionDTO = bridgeTransactionMapper.toDto(bridgeTransaction);
        restBridgeTransactionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bridgeTransactionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BridgeTransaction in the database
        List<BridgeTransaction> bridgeTransactionList = bridgeTransactionRepository.findAll();
        assertThat(bridgeTransactionList).hasSize(databaseSizeBeforeCreate + 1);
        BridgeTransaction testBridgeTransaction = bridgeTransactionList.get(bridgeTransactionList.size() - 1);
        assertThat(testBridgeTransaction.getTransactionId()).isEqualTo(DEFAULT_TRANSACTION_ID);
        assertThat(testBridgeTransaction.getTransactionType()).isEqualTo(DEFAULT_TRANSACTION_TYPE);
        assertThat(testBridgeTransaction.getAccountId()).isEqualTo(DEFAULT_ACCOUNT_ID);
        assertThat(testBridgeTransaction.getAmount()).isEqualByComparingTo(DEFAULT_AMOUNT);
        assertThat(testBridgeTransaction.getIsoCurrencyCode()).isEqualTo(DEFAULT_ISO_CURRENCY_CODE);
        assertThat(testBridgeTransaction.getTransactionDate()).isEqualTo(DEFAULT_TRANSACTION_DATE);
        assertThat(testBridgeTransaction.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBridgeTransaction.getIsFuture()).isEqualTo(DEFAULT_IS_FUTURE);
        assertThat(testBridgeTransaction.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testBridgeTransaction.getAddedDate()).isEqualTo(DEFAULT_ADDED_DATE);
        assertThat(testBridgeTransaction.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testBridgeTransaction.getChecked()).isEqualTo(DEFAULT_CHECKED);
    }

    @Test
    @Transactional
    void createBridgeTransactionWithExistingId() throws Exception {
        // Create the BridgeTransaction with an existing ID
        bridgeTransaction.setId(1L);
        BridgeTransactionDTO bridgeTransactionDTO = bridgeTransactionMapper.toDto(bridgeTransaction);

        int databaseSizeBeforeCreate = bridgeTransactionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBridgeTransactionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bridgeTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BridgeTransaction in the database
        List<BridgeTransaction> bridgeTransactionList = bridgeTransactionRepository.findAll();
        assertThat(bridgeTransactionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBridgeTransactions() throws Exception {
        // Initialize the database
        bridgeTransactionRepository.saveAndFlush(bridgeTransaction);

        // Get all the bridgeTransactionList
        restBridgeTransactionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bridgeTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactionId").value(hasItem(DEFAULT_TRANSACTION_ID)))
            .andExpect(jsonPath("$.[*].transactionType").value(hasItem(DEFAULT_TRANSACTION_TYPE)))
            .andExpect(jsonPath("$.[*].accountId").value(hasItem(DEFAULT_ACCOUNT_ID)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(sameNumber(DEFAULT_AMOUNT))))
            .andExpect(jsonPath("$.[*].isoCurrencyCode").value(hasItem(DEFAULT_ISO_CURRENCY_CODE.toString())))
            .andExpect(jsonPath("$.[*].transactionDate").value(hasItem(DEFAULT_TRANSACTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isFuture").value(hasItem(DEFAULT_IS_FUTURE.booleanValue())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].addedDate").value(hasItem(DEFAULT_ADDED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].checked").value(hasItem(DEFAULT_CHECKED.booleanValue())));
    }

    @Test
    @Transactional
    void getBridgeTransaction() throws Exception {
        // Initialize the database
        bridgeTransactionRepository.saveAndFlush(bridgeTransaction);

        // Get the bridgeTransaction
        restBridgeTransactionMockMvc
            .perform(get(ENTITY_API_URL_ID, bridgeTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bridgeTransaction.getId().intValue()))
            .andExpect(jsonPath("$.transactionId").value(DEFAULT_TRANSACTION_ID))
            .andExpect(jsonPath("$.transactionType").value(DEFAULT_TRANSACTION_TYPE))
            .andExpect(jsonPath("$.accountId").value(DEFAULT_ACCOUNT_ID))
            .andExpect(jsonPath("$.amount").value(sameNumber(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.isoCurrencyCode").value(DEFAULT_ISO_CURRENCY_CODE.toString()))
            .andExpect(jsonPath("$.transactionDate").value(DEFAULT_TRANSACTION_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.isFuture").value(DEFAULT_IS_FUTURE.booleanValue()))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.addedDate").value(DEFAULT_ADDED_DATE.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.checked").value(DEFAULT_CHECKED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingBridgeTransaction() throws Exception {
        // Get the bridgeTransaction
        restBridgeTransactionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBridgeTransaction() throws Exception {
        // Initialize the database
        bridgeTransactionRepository.saveAndFlush(bridgeTransaction);

        int databaseSizeBeforeUpdate = bridgeTransactionRepository.findAll().size();

        // Update the bridgeTransaction
        BridgeTransaction updatedBridgeTransaction = bridgeTransactionRepository.findById(bridgeTransaction.getId()).get();
        // Disconnect from session so that the updates on updatedBridgeTransaction are not directly saved in db
        em.detach(updatedBridgeTransaction);
        updatedBridgeTransaction
            .transactionId(UPDATED_TRANSACTION_ID)
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .accountId(UPDATED_ACCOUNT_ID)
            .amount(UPDATED_AMOUNT)
            .isoCurrencyCode(UPDATED_ISO_CURRENCY_CODE)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .description(UPDATED_DESCRIPTION)
            .isFuture(UPDATED_IS_FUTURE)
            .isDeleted(UPDATED_IS_DELETED)
            .addedDate(UPDATED_ADDED_DATE)
            .updatedAt(UPDATED_UPDATED_AT)
            .checked(UPDATED_CHECKED);
        BridgeTransactionDTO bridgeTransactionDTO = bridgeTransactionMapper.toDto(updatedBridgeTransaction);

        restBridgeTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bridgeTransactionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bridgeTransactionDTO))
            )
            .andExpect(status().isOk());

        // Validate the BridgeTransaction in the database
        List<BridgeTransaction> bridgeTransactionList = bridgeTransactionRepository.findAll();
        assertThat(bridgeTransactionList).hasSize(databaseSizeBeforeUpdate);
        BridgeTransaction testBridgeTransaction = bridgeTransactionList.get(bridgeTransactionList.size() - 1);
        assertThat(testBridgeTransaction.getTransactionId()).isEqualTo(UPDATED_TRANSACTION_ID);
        assertThat(testBridgeTransaction.getTransactionType()).isEqualTo(UPDATED_TRANSACTION_TYPE);
        assertThat(testBridgeTransaction.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
        assertThat(testBridgeTransaction.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testBridgeTransaction.getIsoCurrencyCode()).isEqualTo(UPDATED_ISO_CURRENCY_CODE);
        assertThat(testBridgeTransaction.getTransactionDate()).isEqualTo(UPDATED_TRANSACTION_DATE);
        assertThat(testBridgeTransaction.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBridgeTransaction.getIsFuture()).isEqualTo(UPDATED_IS_FUTURE);
        assertThat(testBridgeTransaction.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testBridgeTransaction.getAddedDate()).isEqualTo(UPDATED_ADDED_DATE);
        assertThat(testBridgeTransaction.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testBridgeTransaction.getChecked()).isEqualTo(UPDATED_CHECKED);
    }

    @Test
    @Transactional
    void putNonExistingBridgeTransaction() throws Exception {
        int databaseSizeBeforeUpdate = bridgeTransactionRepository.findAll().size();
        bridgeTransaction.setId(count.incrementAndGet());

        // Create the BridgeTransaction
        BridgeTransactionDTO bridgeTransactionDTO = bridgeTransactionMapper.toDto(bridgeTransaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBridgeTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bridgeTransactionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bridgeTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BridgeTransaction in the database
        List<BridgeTransaction> bridgeTransactionList = bridgeTransactionRepository.findAll();
        assertThat(bridgeTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBridgeTransaction() throws Exception {
        int databaseSizeBeforeUpdate = bridgeTransactionRepository.findAll().size();
        bridgeTransaction.setId(count.incrementAndGet());

        // Create the BridgeTransaction
        BridgeTransactionDTO bridgeTransactionDTO = bridgeTransactionMapper.toDto(bridgeTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBridgeTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bridgeTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BridgeTransaction in the database
        List<BridgeTransaction> bridgeTransactionList = bridgeTransactionRepository.findAll();
        assertThat(bridgeTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBridgeTransaction() throws Exception {
        int databaseSizeBeforeUpdate = bridgeTransactionRepository.findAll().size();
        bridgeTransaction.setId(count.incrementAndGet());

        // Create the BridgeTransaction
        BridgeTransactionDTO bridgeTransactionDTO = bridgeTransactionMapper.toDto(bridgeTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBridgeTransactionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bridgeTransactionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BridgeTransaction in the database
        List<BridgeTransaction> bridgeTransactionList = bridgeTransactionRepository.findAll();
        assertThat(bridgeTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBridgeTransactionWithPatch() throws Exception {
        // Initialize the database
        bridgeTransactionRepository.saveAndFlush(bridgeTransaction);

        int databaseSizeBeforeUpdate = bridgeTransactionRepository.findAll().size();

        // Update the bridgeTransaction using partial update
        BridgeTransaction partialUpdatedBridgeTransaction = new BridgeTransaction();
        partialUpdatedBridgeTransaction.setId(bridgeTransaction.getId());

        partialUpdatedBridgeTransaction
            .accountId(UPDATED_ACCOUNT_ID)
            .amount(UPDATED_AMOUNT)
            .isFuture(UPDATED_IS_FUTURE)
            .updatedAt(UPDATED_UPDATED_AT)
            .checked(UPDATED_CHECKED);

        restBridgeTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBridgeTransaction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBridgeTransaction))
            )
            .andExpect(status().isOk());

        // Validate the BridgeTransaction in the database
        List<BridgeTransaction> bridgeTransactionList = bridgeTransactionRepository.findAll();
        assertThat(bridgeTransactionList).hasSize(databaseSizeBeforeUpdate);
        BridgeTransaction testBridgeTransaction = bridgeTransactionList.get(bridgeTransactionList.size() - 1);
        assertThat(testBridgeTransaction.getTransactionId()).isEqualTo(DEFAULT_TRANSACTION_ID);
        assertThat(testBridgeTransaction.getTransactionType()).isEqualTo(DEFAULT_TRANSACTION_TYPE);
        assertThat(testBridgeTransaction.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
        assertThat(testBridgeTransaction.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
        assertThat(testBridgeTransaction.getIsoCurrencyCode()).isEqualTo(DEFAULT_ISO_CURRENCY_CODE);
        assertThat(testBridgeTransaction.getTransactionDate()).isEqualTo(DEFAULT_TRANSACTION_DATE);
        assertThat(testBridgeTransaction.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBridgeTransaction.getIsFuture()).isEqualTo(UPDATED_IS_FUTURE);
        assertThat(testBridgeTransaction.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testBridgeTransaction.getAddedDate()).isEqualTo(DEFAULT_ADDED_DATE);
        assertThat(testBridgeTransaction.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testBridgeTransaction.getChecked()).isEqualTo(UPDATED_CHECKED);
    }

    @Test
    @Transactional
    void fullUpdateBridgeTransactionWithPatch() throws Exception {
        // Initialize the database
        bridgeTransactionRepository.saveAndFlush(bridgeTransaction);

        int databaseSizeBeforeUpdate = bridgeTransactionRepository.findAll().size();

        // Update the bridgeTransaction using partial update
        BridgeTransaction partialUpdatedBridgeTransaction = new BridgeTransaction();
        partialUpdatedBridgeTransaction.setId(bridgeTransaction.getId());

        partialUpdatedBridgeTransaction
            .transactionId(UPDATED_TRANSACTION_ID)
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .accountId(UPDATED_ACCOUNT_ID)
            .amount(UPDATED_AMOUNT)
            .isoCurrencyCode(UPDATED_ISO_CURRENCY_CODE)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .description(UPDATED_DESCRIPTION)
            .isFuture(UPDATED_IS_FUTURE)
            .isDeleted(UPDATED_IS_DELETED)
            .addedDate(UPDATED_ADDED_DATE)
            .updatedAt(UPDATED_UPDATED_AT)
            .checked(UPDATED_CHECKED);

        restBridgeTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBridgeTransaction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBridgeTransaction))
            )
            .andExpect(status().isOk());

        // Validate the BridgeTransaction in the database
        List<BridgeTransaction> bridgeTransactionList = bridgeTransactionRepository.findAll();
        assertThat(bridgeTransactionList).hasSize(databaseSizeBeforeUpdate);
        BridgeTransaction testBridgeTransaction = bridgeTransactionList.get(bridgeTransactionList.size() - 1);
        assertThat(testBridgeTransaction.getTransactionId()).isEqualTo(UPDATED_TRANSACTION_ID);
        assertThat(testBridgeTransaction.getTransactionType()).isEqualTo(UPDATED_TRANSACTION_TYPE);
        assertThat(testBridgeTransaction.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
        assertThat(testBridgeTransaction.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
        assertThat(testBridgeTransaction.getIsoCurrencyCode()).isEqualTo(UPDATED_ISO_CURRENCY_CODE);
        assertThat(testBridgeTransaction.getTransactionDate()).isEqualTo(UPDATED_TRANSACTION_DATE);
        assertThat(testBridgeTransaction.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBridgeTransaction.getIsFuture()).isEqualTo(UPDATED_IS_FUTURE);
        assertThat(testBridgeTransaction.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testBridgeTransaction.getAddedDate()).isEqualTo(UPDATED_ADDED_DATE);
        assertThat(testBridgeTransaction.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testBridgeTransaction.getChecked()).isEqualTo(UPDATED_CHECKED);
    }

    @Test
    @Transactional
    void patchNonExistingBridgeTransaction() throws Exception {
        int databaseSizeBeforeUpdate = bridgeTransactionRepository.findAll().size();
        bridgeTransaction.setId(count.incrementAndGet());

        // Create the BridgeTransaction
        BridgeTransactionDTO bridgeTransactionDTO = bridgeTransactionMapper.toDto(bridgeTransaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBridgeTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bridgeTransactionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bridgeTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BridgeTransaction in the database
        List<BridgeTransaction> bridgeTransactionList = bridgeTransactionRepository.findAll();
        assertThat(bridgeTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBridgeTransaction() throws Exception {
        int databaseSizeBeforeUpdate = bridgeTransactionRepository.findAll().size();
        bridgeTransaction.setId(count.incrementAndGet());

        // Create the BridgeTransaction
        BridgeTransactionDTO bridgeTransactionDTO = bridgeTransactionMapper.toDto(bridgeTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBridgeTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bridgeTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BridgeTransaction in the database
        List<BridgeTransaction> bridgeTransactionList = bridgeTransactionRepository.findAll();
        assertThat(bridgeTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBridgeTransaction() throws Exception {
        int databaseSizeBeforeUpdate = bridgeTransactionRepository.findAll().size();
        bridgeTransaction.setId(count.incrementAndGet());

        // Create the BridgeTransaction
        BridgeTransactionDTO bridgeTransactionDTO = bridgeTransactionMapper.toDto(bridgeTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBridgeTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bridgeTransactionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BridgeTransaction in the database
        List<BridgeTransaction> bridgeTransactionList = bridgeTransactionRepository.findAll();
        assertThat(bridgeTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBridgeTransaction() throws Exception {
        // Initialize the database
        bridgeTransactionRepository.saveAndFlush(bridgeTransaction);

        int databaseSizeBeforeDelete = bridgeTransactionRepository.findAll().size();

        // Delete the bridgeTransaction
        restBridgeTransactionMockMvc
            .perform(delete(ENTITY_API_URL_ID, bridgeTransaction.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BridgeTransaction> bridgeTransactionList = bridgeTransactionRepository.findAll();
        assertThat(bridgeTransactionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
