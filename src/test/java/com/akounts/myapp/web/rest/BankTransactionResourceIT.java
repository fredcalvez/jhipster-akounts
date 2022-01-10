package com.akounts.myapp.web.rest;

import static com.akounts.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.akounts.myapp.IntegrationTest;
import com.akounts.myapp.domain.BankTransaction;
import com.akounts.myapp.domain.enumeration.Currency;
import com.akounts.myapp.domain.enumeration.Currency;
import com.akounts.myapp.repository.BankTransactionRepository;
import com.akounts.myapp.service.dto.BankTransactionDTO;
import com.akounts.myapp.service.mapper.BankTransactionMapper;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link BankTransactionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BankTransactionResourceIT {

    private static final String DEFAULT_TRANSACTION_ID = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_ID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_TRANSACTION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TRANSACTION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_LOCAL_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_LOCAL_AMOUNT = new BigDecimal(2);

    private static final Currency DEFAULT_LOCAL_CURRENCY = Currency.AED;
    private static final Currency UPDATED_LOCAL_CURRENCY = Currency.AFN;

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final Currency DEFAULT_CURRENCY = Currency.AED;
    private static final Currency UPDATED_CURRENCY = Currency.AFN;

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final Integer DEFAULT_MONTH = 1;
    private static final Integer UPDATED_MONTH = 2;

    private static final Integer DEFAULT_WEEK = 1;
    private static final Integer UPDATED_WEEK = 2;

    private static final Instant DEFAULT_CATEGORIZED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CATEGORIZED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ADD_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ADD_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_CHECKED = false;
    private static final Boolean UPDATED_CHECKED = true;

    private static final Instant DEFAULT_REBASED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REBASED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final String DEFAULT_TAG = "AAAAAAAAAA";
    private static final String UPDATED_TAG = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;

    private static final String ENTITY_API_URL = "/api/bank-transactions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BankTransactionRepository bankTransactionRepository;

    @Autowired
    private BankTransactionMapper bankTransactionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBankTransactionMockMvc;

    private BankTransaction bankTransaction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankTransaction createEntity(EntityManager em) {
        BankTransaction bankTransaction = new BankTransaction()
            .transactionId(DEFAULT_TRANSACTION_ID)
            .transactionDate(DEFAULT_TRANSACTION_DATE)
            .description(DEFAULT_DESCRIPTION)
            .localAmount(DEFAULT_LOCAL_AMOUNT)
            .localCurrency(DEFAULT_LOCAL_CURRENCY)
            .amount(DEFAULT_AMOUNT)
            .currency(DEFAULT_CURRENCY)
            .note(DEFAULT_NOTE)
            .year(DEFAULT_YEAR)
            .month(DEFAULT_MONTH)
            .week(DEFAULT_WEEK)
            .categorizedDate(DEFAULT_CATEGORIZED_DATE)
            .addDate(DEFAULT_ADD_DATE)
            .checked(DEFAULT_CHECKED)
            .rebasedDate(DEFAULT_REBASED_DATE)
            .deleted(DEFAULT_DELETED)
            .tag(DEFAULT_TAG)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedOn(DEFAULT_UPDATED_ON)
            .version(DEFAULT_VERSION);
        return bankTransaction;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankTransaction createUpdatedEntity(EntityManager em) {
        BankTransaction bankTransaction = new BankTransaction()
            .transactionId(UPDATED_TRANSACTION_ID)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .description(UPDATED_DESCRIPTION)
            .localAmount(UPDATED_LOCAL_AMOUNT)
            .localCurrency(UPDATED_LOCAL_CURRENCY)
            .amount(UPDATED_AMOUNT)
            .currency(UPDATED_CURRENCY)
            .note(UPDATED_NOTE)
            .year(UPDATED_YEAR)
            .month(UPDATED_MONTH)
            .week(UPDATED_WEEK)
            .categorizedDate(UPDATED_CATEGORIZED_DATE)
            .addDate(UPDATED_ADD_DATE)
            .checked(UPDATED_CHECKED)
            .rebasedDate(UPDATED_REBASED_DATE)
            .deleted(UPDATED_DELETED)
            .tag(UPDATED_TAG)
            .createdOn(UPDATED_CREATED_ON)
            .updatedOn(UPDATED_UPDATED_ON)
            .version(UPDATED_VERSION);
        return bankTransaction;
    }

    @BeforeEach
    public void initTest() {
        bankTransaction = createEntity(em);
    }

    @Test
    @Transactional
    void createBankTransaction() throws Exception {
        int databaseSizeBeforeCreate = bankTransactionRepository.findAll().size();
        // Create the BankTransaction
        BankTransactionDTO bankTransactionDTO = bankTransactionMapper.toDto(bankTransaction);
        restBankTransactionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankTransactionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BankTransaction in the database
        List<BankTransaction> bankTransactionList = bankTransactionRepository.findAll();
        assertThat(bankTransactionList).hasSize(databaseSizeBeforeCreate + 1);
        BankTransaction testBankTransaction = bankTransactionList.get(bankTransactionList.size() - 1);
        assertThat(testBankTransaction.getTransactionId()).isEqualTo(DEFAULT_TRANSACTION_ID);
        assertThat(testBankTransaction.getTransactionDate()).isEqualTo(DEFAULT_TRANSACTION_DATE);
        assertThat(testBankTransaction.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBankTransaction.getLocalAmount()).isEqualByComparingTo(DEFAULT_LOCAL_AMOUNT);
        assertThat(testBankTransaction.getLocalCurrency()).isEqualTo(DEFAULT_LOCAL_CURRENCY);
        assertThat(testBankTransaction.getAmount()).isEqualByComparingTo(DEFAULT_AMOUNT);
        assertThat(testBankTransaction.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testBankTransaction.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testBankTransaction.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testBankTransaction.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testBankTransaction.getWeek()).isEqualTo(DEFAULT_WEEK);
        assertThat(testBankTransaction.getCategorizedDate()).isEqualTo(DEFAULT_CATEGORIZED_DATE);
        assertThat(testBankTransaction.getAddDate()).isEqualTo(DEFAULT_ADD_DATE);
        assertThat(testBankTransaction.getChecked()).isEqualTo(DEFAULT_CHECKED);
        assertThat(testBankTransaction.getRebasedDate()).isEqualTo(DEFAULT_REBASED_DATE);
        assertThat(testBankTransaction.getDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testBankTransaction.getTag()).isEqualTo(DEFAULT_TAG);
        assertThat(testBankTransaction.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testBankTransaction.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testBankTransaction.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createBankTransactionWithExistingId() throws Exception {
        // Create the BankTransaction with an existing ID
        bankTransaction.setId(1L);
        BankTransactionDTO bankTransactionDTO = bankTransactionMapper.toDto(bankTransaction);

        int databaseSizeBeforeCreate = bankTransactionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankTransactionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankTransaction in the database
        List<BankTransaction> bankTransactionList = bankTransactionRepository.findAll();
        assertThat(bankTransactionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBankTransactions() throws Exception {
        // Initialize the database
        bankTransactionRepository.saveAndFlush(bankTransaction);

        // Get all the bankTransactionList
        restBankTransactionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactionId").value(hasItem(DEFAULT_TRANSACTION_ID)))
            .andExpect(jsonPath("$.[*].transactionDate").value(hasItem(DEFAULT_TRANSACTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].localAmount").value(hasItem(sameNumber(DEFAULT_LOCAL_AMOUNT))))
            .andExpect(jsonPath("$.[*].localCurrency").value(hasItem(DEFAULT_LOCAL_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(sameNumber(DEFAULT_AMOUNT))))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH)))
            .andExpect(jsonPath("$.[*].week").value(hasItem(DEFAULT_WEEK)))
            .andExpect(jsonPath("$.[*].categorizedDate").value(hasItem(DEFAULT_CATEGORIZED_DATE.toString())))
            .andExpect(jsonPath("$.[*].addDate").value(hasItem(DEFAULT_ADD_DATE.toString())))
            .andExpect(jsonPath("$.[*].checked").value(hasItem(DEFAULT_CHECKED.booleanValue())))
            .andExpect(jsonPath("$.[*].rebasedDate").value(hasItem(DEFAULT_REBASED_DATE.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].tag").value(hasItem(DEFAULT_TAG)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getBankTransaction() throws Exception {
        // Initialize the database
        bankTransactionRepository.saveAndFlush(bankTransaction);

        // Get the bankTransaction
        restBankTransactionMockMvc
            .perform(get(ENTITY_API_URL_ID, bankTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bankTransaction.getId().intValue()))
            .andExpect(jsonPath("$.transactionId").value(DEFAULT_TRANSACTION_ID))
            .andExpect(jsonPath("$.transactionDate").value(DEFAULT_TRANSACTION_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.localAmount").value(sameNumber(DEFAULT_LOCAL_AMOUNT)))
            .andExpect(jsonPath("$.localCurrency").value(DEFAULT_LOCAL_CURRENCY.toString()))
            .andExpect(jsonPath("$.amount").value(sameNumber(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH))
            .andExpect(jsonPath("$.week").value(DEFAULT_WEEK))
            .andExpect(jsonPath("$.categorizedDate").value(DEFAULT_CATEGORIZED_DATE.toString()))
            .andExpect(jsonPath("$.addDate").value(DEFAULT_ADD_DATE.toString()))
            .andExpect(jsonPath("$.checked").value(DEFAULT_CHECKED.booleanValue()))
            .andExpect(jsonPath("$.rebasedDate").value(DEFAULT_REBASED_DATE.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.tag").value(DEFAULT_TAG))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getNonExistingBankTransaction() throws Exception {
        // Get the bankTransaction
        restBankTransactionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBankTransaction() throws Exception {
        // Initialize the database
        bankTransactionRepository.saveAndFlush(bankTransaction);

        int databaseSizeBeforeUpdate = bankTransactionRepository.findAll().size();

        // Update the bankTransaction
        BankTransaction updatedBankTransaction = bankTransactionRepository.findById(bankTransaction.getId()).get();
        // Disconnect from session so that the updates on updatedBankTransaction are not directly saved in db
        em.detach(updatedBankTransaction);
        updatedBankTransaction
            .transactionId(UPDATED_TRANSACTION_ID)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .description(UPDATED_DESCRIPTION)
            .localAmount(UPDATED_LOCAL_AMOUNT)
            .localCurrency(UPDATED_LOCAL_CURRENCY)
            .amount(UPDATED_AMOUNT)
            .currency(UPDATED_CURRENCY)
            .note(UPDATED_NOTE)
            .year(UPDATED_YEAR)
            .month(UPDATED_MONTH)
            .week(UPDATED_WEEK)
            .categorizedDate(UPDATED_CATEGORIZED_DATE)
            .addDate(UPDATED_ADD_DATE)
            .checked(UPDATED_CHECKED)
            .rebasedDate(UPDATED_REBASED_DATE)
            .deleted(UPDATED_DELETED)
            .tag(UPDATED_TAG)
            .createdOn(UPDATED_CREATED_ON)
            .updatedOn(UPDATED_UPDATED_ON)
            .version(UPDATED_VERSION);
        BankTransactionDTO bankTransactionDTO = bankTransactionMapper.toDto(updatedBankTransaction);

        restBankTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankTransactionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankTransactionDTO))
            )
            .andExpect(status().isOk());

        // Validate the BankTransaction in the database
        List<BankTransaction> bankTransactionList = bankTransactionRepository.findAll();
        assertThat(bankTransactionList).hasSize(databaseSizeBeforeUpdate);
        BankTransaction testBankTransaction = bankTransactionList.get(bankTransactionList.size() - 1);
        assertThat(testBankTransaction.getTransactionId()).isEqualTo(UPDATED_TRANSACTION_ID);
        assertThat(testBankTransaction.getTransactionDate()).isEqualTo(UPDATED_TRANSACTION_DATE);
        assertThat(testBankTransaction.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBankTransaction.getLocalAmount()).isEqualTo(UPDATED_LOCAL_AMOUNT);
        assertThat(testBankTransaction.getLocalCurrency()).isEqualTo(UPDATED_LOCAL_CURRENCY);
        assertThat(testBankTransaction.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testBankTransaction.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testBankTransaction.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testBankTransaction.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testBankTransaction.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testBankTransaction.getWeek()).isEqualTo(UPDATED_WEEK);
        assertThat(testBankTransaction.getCategorizedDate()).isEqualTo(UPDATED_CATEGORIZED_DATE);
        assertThat(testBankTransaction.getAddDate()).isEqualTo(UPDATED_ADD_DATE);
        assertThat(testBankTransaction.getChecked()).isEqualTo(UPDATED_CHECKED);
        assertThat(testBankTransaction.getRebasedDate()).isEqualTo(UPDATED_REBASED_DATE);
        assertThat(testBankTransaction.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testBankTransaction.getTag()).isEqualTo(UPDATED_TAG);
        assertThat(testBankTransaction.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testBankTransaction.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testBankTransaction.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingBankTransaction() throws Exception {
        int databaseSizeBeforeUpdate = bankTransactionRepository.findAll().size();
        bankTransaction.setId(count.incrementAndGet());

        // Create the BankTransaction
        BankTransactionDTO bankTransactionDTO = bankTransactionMapper.toDto(bankTransaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankTransactionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankTransaction in the database
        List<BankTransaction> bankTransactionList = bankTransactionRepository.findAll();
        assertThat(bankTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBankTransaction() throws Exception {
        int databaseSizeBeforeUpdate = bankTransactionRepository.findAll().size();
        bankTransaction.setId(count.incrementAndGet());

        // Create the BankTransaction
        BankTransactionDTO bankTransactionDTO = bankTransactionMapper.toDto(bankTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankTransaction in the database
        List<BankTransaction> bankTransactionList = bankTransactionRepository.findAll();
        assertThat(bankTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBankTransaction() throws Exception {
        int databaseSizeBeforeUpdate = bankTransactionRepository.findAll().size();
        bankTransaction.setId(count.incrementAndGet());

        // Create the BankTransaction
        BankTransactionDTO bankTransactionDTO = bankTransactionMapper.toDto(bankTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankTransactionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankTransactionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankTransaction in the database
        List<BankTransaction> bankTransactionList = bankTransactionRepository.findAll();
        assertThat(bankTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBankTransactionWithPatch() throws Exception {
        // Initialize the database
        bankTransactionRepository.saveAndFlush(bankTransaction);

        int databaseSizeBeforeUpdate = bankTransactionRepository.findAll().size();

        // Update the bankTransaction using partial update
        BankTransaction partialUpdatedBankTransaction = new BankTransaction();
        partialUpdatedBankTransaction.setId(bankTransaction.getId());

        partialUpdatedBankTransaction
            .transactionId(UPDATED_TRANSACTION_ID)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .description(UPDATED_DESCRIPTION)
            .localAmount(UPDATED_LOCAL_AMOUNT)
            .localCurrency(UPDATED_LOCAL_CURRENCY)
            .amount(UPDATED_AMOUNT)
            .note(UPDATED_NOTE)
            .month(UPDATED_MONTH)
            .checked(UPDATED_CHECKED)
            .rebasedDate(UPDATED_REBASED_DATE)
            .tag(UPDATED_TAG)
            .updatedOn(UPDATED_UPDATED_ON);

        restBankTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankTransaction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankTransaction))
            )
            .andExpect(status().isOk());

        // Validate the BankTransaction in the database
        List<BankTransaction> bankTransactionList = bankTransactionRepository.findAll();
        assertThat(bankTransactionList).hasSize(databaseSizeBeforeUpdate);
        BankTransaction testBankTransaction = bankTransactionList.get(bankTransactionList.size() - 1);
        assertThat(testBankTransaction.getTransactionId()).isEqualTo(UPDATED_TRANSACTION_ID);
        assertThat(testBankTransaction.getTransactionDate()).isEqualTo(UPDATED_TRANSACTION_DATE);
        assertThat(testBankTransaction.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBankTransaction.getLocalAmount()).isEqualByComparingTo(UPDATED_LOCAL_AMOUNT);
        assertThat(testBankTransaction.getLocalCurrency()).isEqualTo(UPDATED_LOCAL_CURRENCY);
        assertThat(testBankTransaction.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
        assertThat(testBankTransaction.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testBankTransaction.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testBankTransaction.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testBankTransaction.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testBankTransaction.getWeek()).isEqualTo(DEFAULT_WEEK);
        assertThat(testBankTransaction.getCategorizedDate()).isEqualTo(DEFAULT_CATEGORIZED_DATE);
        assertThat(testBankTransaction.getAddDate()).isEqualTo(DEFAULT_ADD_DATE);
        assertThat(testBankTransaction.getChecked()).isEqualTo(UPDATED_CHECKED);
        assertThat(testBankTransaction.getRebasedDate()).isEqualTo(UPDATED_REBASED_DATE);
        assertThat(testBankTransaction.getDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testBankTransaction.getTag()).isEqualTo(UPDATED_TAG);
        assertThat(testBankTransaction.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testBankTransaction.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testBankTransaction.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateBankTransactionWithPatch() throws Exception {
        // Initialize the database
        bankTransactionRepository.saveAndFlush(bankTransaction);

        int databaseSizeBeforeUpdate = bankTransactionRepository.findAll().size();

        // Update the bankTransaction using partial update
        BankTransaction partialUpdatedBankTransaction = new BankTransaction();
        partialUpdatedBankTransaction.setId(bankTransaction.getId());

        partialUpdatedBankTransaction
            .transactionId(UPDATED_TRANSACTION_ID)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .description(UPDATED_DESCRIPTION)
            .localAmount(UPDATED_LOCAL_AMOUNT)
            .localCurrency(UPDATED_LOCAL_CURRENCY)
            .amount(UPDATED_AMOUNT)
            .currency(UPDATED_CURRENCY)
            .note(UPDATED_NOTE)
            .year(UPDATED_YEAR)
            .month(UPDATED_MONTH)
            .week(UPDATED_WEEK)
            .categorizedDate(UPDATED_CATEGORIZED_DATE)
            .addDate(UPDATED_ADD_DATE)
            .checked(UPDATED_CHECKED)
            .rebasedDate(UPDATED_REBASED_DATE)
            .deleted(UPDATED_DELETED)
            .tag(UPDATED_TAG)
            .createdOn(UPDATED_CREATED_ON)
            .updatedOn(UPDATED_UPDATED_ON)
            .version(UPDATED_VERSION);

        restBankTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankTransaction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankTransaction))
            )
            .andExpect(status().isOk());

        // Validate the BankTransaction in the database
        List<BankTransaction> bankTransactionList = bankTransactionRepository.findAll();
        assertThat(bankTransactionList).hasSize(databaseSizeBeforeUpdate);
        BankTransaction testBankTransaction = bankTransactionList.get(bankTransactionList.size() - 1);
        assertThat(testBankTransaction.getTransactionId()).isEqualTo(UPDATED_TRANSACTION_ID);
        assertThat(testBankTransaction.getTransactionDate()).isEqualTo(UPDATED_TRANSACTION_DATE);
        assertThat(testBankTransaction.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBankTransaction.getLocalAmount()).isEqualByComparingTo(UPDATED_LOCAL_AMOUNT);
        assertThat(testBankTransaction.getLocalCurrency()).isEqualTo(UPDATED_LOCAL_CURRENCY);
        assertThat(testBankTransaction.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
        assertThat(testBankTransaction.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testBankTransaction.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testBankTransaction.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testBankTransaction.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testBankTransaction.getWeek()).isEqualTo(UPDATED_WEEK);
        assertThat(testBankTransaction.getCategorizedDate()).isEqualTo(UPDATED_CATEGORIZED_DATE);
        assertThat(testBankTransaction.getAddDate()).isEqualTo(UPDATED_ADD_DATE);
        assertThat(testBankTransaction.getChecked()).isEqualTo(UPDATED_CHECKED);
        assertThat(testBankTransaction.getRebasedDate()).isEqualTo(UPDATED_REBASED_DATE);
        assertThat(testBankTransaction.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testBankTransaction.getTag()).isEqualTo(UPDATED_TAG);
        assertThat(testBankTransaction.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testBankTransaction.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testBankTransaction.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingBankTransaction() throws Exception {
        int databaseSizeBeforeUpdate = bankTransactionRepository.findAll().size();
        bankTransaction.setId(count.incrementAndGet());

        // Create the BankTransaction
        BankTransactionDTO bankTransactionDTO = bankTransactionMapper.toDto(bankTransaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bankTransactionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankTransaction in the database
        List<BankTransaction> bankTransactionList = bankTransactionRepository.findAll();
        assertThat(bankTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBankTransaction() throws Exception {
        int databaseSizeBeforeUpdate = bankTransactionRepository.findAll().size();
        bankTransaction.setId(count.incrementAndGet());

        // Create the BankTransaction
        BankTransactionDTO bankTransactionDTO = bankTransactionMapper.toDto(bankTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankTransaction in the database
        List<BankTransaction> bankTransactionList = bankTransactionRepository.findAll();
        assertThat(bankTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBankTransaction() throws Exception {
        int databaseSizeBeforeUpdate = bankTransactionRepository.findAll().size();
        bankTransaction.setId(count.incrementAndGet());

        // Create the BankTransaction
        BankTransactionDTO bankTransactionDTO = bankTransactionMapper.toDto(bankTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankTransactionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankTransaction in the database
        List<BankTransaction> bankTransactionList = bankTransactionRepository.findAll();
        assertThat(bankTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBankTransaction() throws Exception {
        // Initialize the database
        bankTransactionRepository.saveAndFlush(bankTransaction);

        int databaseSizeBeforeDelete = bankTransactionRepository.findAll().size();

        // Delete the bankTransaction
        restBankTransactionMockMvc
            .perform(delete(ENTITY_API_URL_ID, bankTransaction.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BankTransaction> bankTransactionList = bankTransactionRepository.findAll();
        assertThat(bankTransactionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
