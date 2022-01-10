package com.akounts.myapp.web.rest;

import static com.akounts.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.akounts.myapp.IntegrationTest;
import com.akounts.myapp.domain.BankAccount;
import com.akounts.myapp.domain.enumeration.AccountType;
import com.akounts.myapp.domain.enumeration.Currency;
import com.akounts.myapp.repository.BankAccountRepository;
import java.math.BigDecimal;
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
 * Integration tests for the {@link BankAccountResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BankAccountResourceIT {

    private static final String DEFAULT_ACCOUNT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final Currency DEFAULT_CURRENCY = Currency.EUR;
    private static final Currency UPDATED_CURRENCY = Currency.USD;

    private static final BigDecimal DEFAULT_INITIAL_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_INITIAL_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_INITIAL_AMOUNT_LOCAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_INITIAL_AMOUNT_LOCAL = new BigDecimal(2);

    private static final AccountType DEFAULT_ACCOUNT_TYPE = AccountType.Check;
    private static final AccountType UPDATED_ACCOUNT_TYPE = AccountType.Loan;

    private static final BigDecimal DEFAULT_INTEREST = new BigDecimal(1);
    private static final BigDecimal UPDATED_INTEREST = new BigDecimal(2);

    private static final String DEFAULT_NICKNAME = "AAAAAAAAAA";
    private static final String UPDATED_NICKNAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bank-accounts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBankAccountMockMvc;

    private BankAccount bankAccount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankAccount createEntity(EntityManager em) {
        BankAccount bankAccount = new BankAccount()
            .accountLabel(DEFAULT_ACCOUNT_LABEL)
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .active(DEFAULT_ACTIVE)
            .currency(DEFAULT_CURRENCY)
            .initialAmount(DEFAULT_INITIAL_AMOUNT)
            .initialAmountLocal(DEFAULT_INITIAL_AMOUNT_LOCAL)
            .accountType(DEFAULT_ACCOUNT_TYPE)
            .interest(DEFAULT_INTEREST)
            .nickname(DEFAULT_NICKNAME);
        return bankAccount;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankAccount createUpdatedEntity(EntityManager em) {
        BankAccount bankAccount = new BankAccount()
            .accountLabel(UPDATED_ACCOUNT_LABEL)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .active(UPDATED_ACTIVE)
            .currency(UPDATED_CURRENCY)
            .initialAmount(UPDATED_INITIAL_AMOUNT)
            .initialAmountLocal(UPDATED_INITIAL_AMOUNT_LOCAL)
            .accountType(UPDATED_ACCOUNT_TYPE)
            .interest(UPDATED_INTEREST)
            .nickname(UPDATED_NICKNAME);
        return bankAccount;
    }

    @BeforeEach
    public void initTest() {
        bankAccount = createEntity(em);
    }

    @Test
    @Transactional
    void createBankAccount() throws Exception {
        int databaseSizeBeforeCreate = bankAccountRepository.findAll().size();
        // Create the BankAccount
        restBankAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankAccount)))
            .andExpect(status().isCreated());

        // Validate the BankAccount in the database
        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeCreate + 1);
        BankAccount testBankAccount = bankAccountList.get(bankAccountList.size() - 1);
        assertThat(testBankAccount.getAccountLabel()).isEqualTo(DEFAULT_ACCOUNT_LABEL);
        assertThat(testBankAccount.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testBankAccount.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testBankAccount.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testBankAccount.getInitialAmount()).isEqualByComparingTo(DEFAULT_INITIAL_AMOUNT);
        assertThat(testBankAccount.getInitialAmountLocal()).isEqualByComparingTo(DEFAULT_INITIAL_AMOUNT_LOCAL);
        assertThat(testBankAccount.getAccountType()).isEqualTo(DEFAULT_ACCOUNT_TYPE);
        assertThat(testBankAccount.getInterest()).isEqualByComparingTo(DEFAULT_INTEREST);
        assertThat(testBankAccount.getNickname()).isEqualTo(DEFAULT_NICKNAME);
    }

    @Test
    @Transactional
    void createBankAccountWithExistingId() throws Exception {
        // Create the BankAccount with an existing ID
        bankAccount.setId(1L);

        int databaseSizeBeforeCreate = bankAccountRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankAccount)))
            .andExpect(status().isBadRequest());

        // Validate the BankAccount in the database
        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBankAccounts() throws Exception {
        // Initialize the database
        bankAccountRepository.saveAndFlush(bankAccount);

        // Get all the bankAccountList
        restBankAccountMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountLabel").value(hasItem(DEFAULT_ACCOUNT_LABEL)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].initialAmount").value(hasItem(sameNumber(DEFAULT_INITIAL_AMOUNT))))
            .andExpect(jsonPath("$.[*].initialAmountLocal").value(hasItem(sameNumber(DEFAULT_INITIAL_AMOUNT_LOCAL))))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].interest").value(hasItem(sameNumber(DEFAULT_INTEREST))))
            .andExpect(jsonPath("$.[*].nickname").value(hasItem(DEFAULT_NICKNAME)));
    }

    @Test
    @Transactional
    void getBankAccount() throws Exception {
        // Initialize the database
        bankAccountRepository.saveAndFlush(bankAccount);

        // Get the bankAccount
        restBankAccountMockMvc
            .perform(get(ENTITY_API_URL_ID, bankAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bankAccount.getId().intValue()))
            .andExpect(jsonPath("$.accountLabel").value(DEFAULT_ACCOUNT_LABEL))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY.toString()))
            .andExpect(jsonPath("$.initialAmount").value(sameNumber(DEFAULT_INITIAL_AMOUNT)))
            .andExpect(jsonPath("$.initialAmountLocal").value(sameNumber(DEFAULT_INITIAL_AMOUNT_LOCAL)))
            .andExpect(jsonPath("$.accountType").value(DEFAULT_ACCOUNT_TYPE.toString()))
            .andExpect(jsonPath("$.interest").value(sameNumber(DEFAULT_INTEREST)))
            .andExpect(jsonPath("$.nickname").value(DEFAULT_NICKNAME));
    }

    @Test
    @Transactional
    void getNonExistingBankAccount() throws Exception {
        // Get the bankAccount
        restBankAccountMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBankAccount() throws Exception {
        // Initialize the database
        bankAccountRepository.saveAndFlush(bankAccount);

        int databaseSizeBeforeUpdate = bankAccountRepository.findAll().size();

        // Update the bankAccount
        BankAccount updatedBankAccount = bankAccountRepository.findById(bankAccount.getId()).get();
        // Disconnect from session so that the updates on updatedBankAccount are not directly saved in db
        em.detach(updatedBankAccount);
        updatedBankAccount
            .accountLabel(UPDATED_ACCOUNT_LABEL)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .active(UPDATED_ACTIVE)
            .currency(UPDATED_CURRENCY)
            .initialAmount(UPDATED_INITIAL_AMOUNT)
            .initialAmountLocal(UPDATED_INITIAL_AMOUNT_LOCAL)
            .accountType(UPDATED_ACCOUNT_TYPE)
            .interest(UPDATED_INTEREST)
            .nickname(UPDATED_NICKNAME);

        restBankAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBankAccount.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBankAccount))
            )
            .andExpect(status().isOk());

        // Validate the BankAccount in the database
        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeUpdate);
        BankAccount testBankAccount = bankAccountList.get(bankAccountList.size() - 1);
        assertThat(testBankAccount.getAccountLabel()).isEqualTo(UPDATED_ACCOUNT_LABEL);
        assertThat(testBankAccount.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testBankAccount.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testBankAccount.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testBankAccount.getInitialAmount()).isEqualTo(UPDATED_INITIAL_AMOUNT);
        assertThat(testBankAccount.getInitialAmountLocal()).isEqualTo(UPDATED_INITIAL_AMOUNT_LOCAL);
        assertThat(testBankAccount.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
        assertThat(testBankAccount.getInterest()).isEqualTo(UPDATED_INTEREST);
        assertThat(testBankAccount.getNickname()).isEqualTo(UPDATED_NICKNAME);
    }

    @Test
    @Transactional
    void putNonExistingBankAccount() throws Exception {
        int databaseSizeBeforeUpdate = bankAccountRepository.findAll().size();
        bankAccount.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankAccount.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankAccount))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankAccount in the database
        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBankAccount() throws Exception {
        int databaseSizeBeforeUpdate = bankAccountRepository.findAll().size();
        bankAccount.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankAccount))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankAccount in the database
        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBankAccount() throws Exception {
        int databaseSizeBeforeUpdate = bankAccountRepository.findAll().size();
        bankAccount.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankAccountMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankAccount)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankAccount in the database
        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBankAccountWithPatch() throws Exception {
        // Initialize the database
        bankAccountRepository.saveAndFlush(bankAccount);

        int databaseSizeBeforeUpdate = bankAccountRepository.findAll().size();

        // Update the bankAccount using partial update
        BankAccount partialUpdatedBankAccount = new BankAccount();
        partialUpdatedBankAccount.setId(bankAccount.getId());

        partialUpdatedBankAccount
            .accountLabel(UPDATED_ACCOUNT_LABEL)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .currency(UPDATED_CURRENCY)
            .initialAmountLocal(UPDATED_INITIAL_AMOUNT_LOCAL)
            .interest(UPDATED_INTEREST);

        restBankAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankAccount))
            )
            .andExpect(status().isOk());

        // Validate the BankAccount in the database
        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeUpdate);
        BankAccount testBankAccount = bankAccountList.get(bankAccountList.size() - 1);
        assertThat(testBankAccount.getAccountLabel()).isEqualTo(UPDATED_ACCOUNT_LABEL);
        assertThat(testBankAccount.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testBankAccount.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testBankAccount.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testBankAccount.getInitialAmount()).isEqualByComparingTo(DEFAULT_INITIAL_AMOUNT);
        assertThat(testBankAccount.getInitialAmountLocal()).isEqualByComparingTo(UPDATED_INITIAL_AMOUNT_LOCAL);
        assertThat(testBankAccount.getAccountType()).isEqualTo(DEFAULT_ACCOUNT_TYPE);
        assertThat(testBankAccount.getInterest()).isEqualByComparingTo(UPDATED_INTEREST);
        assertThat(testBankAccount.getNickname()).isEqualTo(DEFAULT_NICKNAME);
    }

    @Test
    @Transactional
    void fullUpdateBankAccountWithPatch() throws Exception {
        // Initialize the database
        bankAccountRepository.saveAndFlush(bankAccount);

        int databaseSizeBeforeUpdate = bankAccountRepository.findAll().size();

        // Update the bankAccount using partial update
        BankAccount partialUpdatedBankAccount = new BankAccount();
        partialUpdatedBankAccount.setId(bankAccount.getId());

        partialUpdatedBankAccount
            .accountLabel(UPDATED_ACCOUNT_LABEL)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .active(UPDATED_ACTIVE)
            .currency(UPDATED_CURRENCY)
            .initialAmount(UPDATED_INITIAL_AMOUNT)
            .initialAmountLocal(UPDATED_INITIAL_AMOUNT_LOCAL)
            .accountType(UPDATED_ACCOUNT_TYPE)
            .interest(UPDATED_INTEREST)
            .nickname(UPDATED_NICKNAME);

        restBankAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankAccount))
            )
            .andExpect(status().isOk());

        // Validate the BankAccount in the database
        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeUpdate);
        BankAccount testBankAccount = bankAccountList.get(bankAccountList.size() - 1);
        assertThat(testBankAccount.getAccountLabel()).isEqualTo(UPDATED_ACCOUNT_LABEL);
        assertThat(testBankAccount.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testBankAccount.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testBankAccount.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testBankAccount.getInitialAmount()).isEqualByComparingTo(UPDATED_INITIAL_AMOUNT);
        assertThat(testBankAccount.getInitialAmountLocal()).isEqualByComparingTo(UPDATED_INITIAL_AMOUNT_LOCAL);
        assertThat(testBankAccount.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
        assertThat(testBankAccount.getInterest()).isEqualByComparingTo(UPDATED_INTEREST);
        assertThat(testBankAccount.getNickname()).isEqualTo(UPDATED_NICKNAME);
    }

    @Test
    @Transactional
    void patchNonExistingBankAccount() throws Exception {
        int databaseSizeBeforeUpdate = bankAccountRepository.findAll().size();
        bankAccount.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bankAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankAccount))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankAccount in the database
        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBankAccount() throws Exception {
        int databaseSizeBeforeUpdate = bankAccountRepository.findAll().size();
        bankAccount.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankAccount))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankAccount in the database
        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBankAccount() throws Exception {
        int databaseSizeBeforeUpdate = bankAccountRepository.findAll().size();
        bankAccount.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankAccountMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bankAccount))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankAccount in the database
        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBankAccount() throws Exception {
        // Initialize the database
        bankAccountRepository.saveAndFlush(bankAccount);

        int databaseSizeBeforeDelete = bankAccountRepository.findAll().size();

        // Delete the bankAccount
        restBankAccountMockMvc
            .perform(delete(ENTITY_API_URL_ID, bankAccount.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
