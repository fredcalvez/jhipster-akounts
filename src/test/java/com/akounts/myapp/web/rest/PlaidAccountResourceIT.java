package com.akounts.myapp.web.rest;

import static com.akounts.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.akounts.myapp.IntegrationTest;
import com.akounts.myapp.domain.PlaidAccount;
import com.akounts.myapp.domain.enumeration.Currency;
import com.akounts.myapp.repository.PlaidAccountRepository;
import com.akounts.myapp.service.dto.PlaidAccountDTO;
import com.akounts.myapp.service.mapper.PlaidAccountMapper;
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
 * Integration tests for the {@link PlaidAccountResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlaidAccountResourceIT {

    private static final String DEFAULT_PLAID_ACCOUNT_ID = "AAAAAAAAAA";
    private static final String UPDATED_PLAID_ACCOUNT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ITEM_ID = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_SUBTYPE = "AAAAAAAAAA";
    private static final String UPDATED_SUBTYPE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_BALANCE_AVAILABLE = new BigDecimal(1);
    private static final BigDecimal UPDATED_BALANCE_AVAILABLE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_BALANCE_CURRENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_BALANCE_CURRENT = new BigDecimal(2);

    private static final Currency DEFAULT_ISO_CURRENCY_CODE = Currency.AED;
    private static final Currency UPDATED_ISO_CURRENCY_CODE = Currency.AFN;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_OFFICIAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_OFFICIAL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IBAN = "AAAAAAAAAA";
    private static final String UPDATED_IBAN = "BBBBBBBBBB";

    private static final String DEFAULT_BIC = "AAAAAAAAAA";
    private static final String UPDATED_BIC = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/plaid-accounts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PlaidAccountRepository plaidAccountRepository;

    @Autowired
    private PlaidAccountMapper plaidAccountMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlaidAccountMockMvc;

    private PlaidAccount plaidAccount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlaidAccount createEntity(EntityManager em) {
        PlaidAccount plaidAccount = new PlaidAccount()
            .plaidAccountId(DEFAULT_PLAID_ACCOUNT_ID)
            .itemId(DEFAULT_ITEM_ID)
            .type(DEFAULT_TYPE)
            .subtype(DEFAULT_SUBTYPE)
            .balanceAvailable(DEFAULT_BALANCE_AVAILABLE)
            .balanceCurrent(DEFAULT_BALANCE_CURRENT)
            .isoCurrencyCode(DEFAULT_ISO_CURRENCY_CODE)
            .name(DEFAULT_NAME)
            .officialName(DEFAULT_OFFICIAL_NAME)
            .iban(DEFAULT_IBAN)
            .bic(DEFAULT_BIC);
        return plaidAccount;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlaidAccount createUpdatedEntity(EntityManager em) {
        PlaidAccount plaidAccount = new PlaidAccount()
            .plaidAccountId(UPDATED_PLAID_ACCOUNT_ID)
            .itemId(UPDATED_ITEM_ID)
            .type(UPDATED_TYPE)
            .subtype(UPDATED_SUBTYPE)
            .balanceAvailable(UPDATED_BALANCE_AVAILABLE)
            .balanceCurrent(UPDATED_BALANCE_CURRENT)
            .isoCurrencyCode(UPDATED_ISO_CURRENCY_CODE)
            .name(UPDATED_NAME)
            .officialName(UPDATED_OFFICIAL_NAME)
            .iban(UPDATED_IBAN)
            .bic(UPDATED_BIC);
        return plaidAccount;
    }

    @BeforeEach
    public void initTest() {
        plaidAccount = createEntity(em);
    }

    @Test
    @Transactional
    void createPlaidAccount() throws Exception {
        int databaseSizeBeforeCreate = plaidAccountRepository.findAll().size();
        // Create the PlaidAccount
        PlaidAccountDTO plaidAccountDTO = plaidAccountMapper.toDto(plaidAccount);
        restPlaidAccountMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plaidAccountDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PlaidAccount in the database
        List<PlaidAccount> plaidAccountList = plaidAccountRepository.findAll();
        assertThat(plaidAccountList).hasSize(databaseSizeBeforeCreate + 1);
        PlaidAccount testPlaidAccount = plaidAccountList.get(plaidAccountList.size() - 1);
        assertThat(testPlaidAccount.getPlaidAccountId()).isEqualTo(DEFAULT_PLAID_ACCOUNT_ID);
        assertThat(testPlaidAccount.getItemId()).isEqualTo(DEFAULT_ITEM_ID);
        assertThat(testPlaidAccount.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPlaidAccount.getSubtype()).isEqualTo(DEFAULT_SUBTYPE);
        assertThat(testPlaidAccount.getBalanceAvailable()).isEqualByComparingTo(DEFAULT_BALANCE_AVAILABLE);
        assertThat(testPlaidAccount.getBalanceCurrent()).isEqualByComparingTo(DEFAULT_BALANCE_CURRENT);
        assertThat(testPlaidAccount.getIsoCurrencyCode()).isEqualTo(DEFAULT_ISO_CURRENCY_CODE);
        assertThat(testPlaidAccount.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPlaidAccount.getOfficialName()).isEqualTo(DEFAULT_OFFICIAL_NAME);
        assertThat(testPlaidAccount.getIban()).isEqualTo(DEFAULT_IBAN);
        assertThat(testPlaidAccount.getBic()).isEqualTo(DEFAULT_BIC);
    }

    @Test
    @Transactional
    void createPlaidAccountWithExistingId() throws Exception {
        // Create the PlaidAccount with an existing ID
        plaidAccount.setId(1L);
        PlaidAccountDTO plaidAccountDTO = plaidAccountMapper.toDto(plaidAccount);

        int databaseSizeBeforeCreate = plaidAccountRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlaidAccountMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plaidAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlaidAccount in the database
        List<PlaidAccount> plaidAccountList = plaidAccountRepository.findAll();
        assertThat(plaidAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPlaidAccounts() throws Exception {
        // Initialize the database
        plaidAccountRepository.saveAndFlush(plaidAccount);

        // Get all the plaidAccountList
        restPlaidAccountMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plaidAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].plaidAccountId").value(hasItem(DEFAULT_PLAID_ACCOUNT_ID)))
            .andExpect(jsonPath("$.[*].itemId").value(hasItem(DEFAULT_ITEM_ID)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].subtype").value(hasItem(DEFAULT_SUBTYPE)))
            .andExpect(jsonPath("$.[*].balanceAvailable").value(hasItem(sameNumber(DEFAULT_BALANCE_AVAILABLE))))
            .andExpect(jsonPath("$.[*].balanceCurrent").value(hasItem(sameNumber(DEFAULT_BALANCE_CURRENT))))
            .andExpect(jsonPath("$.[*].isoCurrencyCode").value(hasItem(DEFAULT_ISO_CURRENCY_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].officialName").value(hasItem(DEFAULT_OFFICIAL_NAME)))
            .andExpect(jsonPath("$.[*].iban").value(hasItem(DEFAULT_IBAN)))
            .andExpect(jsonPath("$.[*].bic").value(hasItem(DEFAULT_BIC)));
    }

    @Test
    @Transactional
    void getPlaidAccount() throws Exception {
        // Initialize the database
        plaidAccountRepository.saveAndFlush(plaidAccount);

        // Get the plaidAccount
        restPlaidAccountMockMvc
            .perform(get(ENTITY_API_URL_ID, plaidAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(plaidAccount.getId().intValue()))
            .andExpect(jsonPath("$.plaidAccountId").value(DEFAULT_PLAID_ACCOUNT_ID))
            .andExpect(jsonPath("$.itemId").value(DEFAULT_ITEM_ID))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.subtype").value(DEFAULT_SUBTYPE))
            .andExpect(jsonPath("$.balanceAvailable").value(sameNumber(DEFAULT_BALANCE_AVAILABLE)))
            .andExpect(jsonPath("$.balanceCurrent").value(sameNumber(DEFAULT_BALANCE_CURRENT)))
            .andExpect(jsonPath("$.isoCurrencyCode").value(DEFAULT_ISO_CURRENCY_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.officialName").value(DEFAULT_OFFICIAL_NAME))
            .andExpect(jsonPath("$.iban").value(DEFAULT_IBAN))
            .andExpect(jsonPath("$.bic").value(DEFAULT_BIC));
    }

    @Test
    @Transactional
    void getNonExistingPlaidAccount() throws Exception {
        // Get the plaidAccount
        restPlaidAccountMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPlaidAccount() throws Exception {
        // Initialize the database
        plaidAccountRepository.saveAndFlush(plaidAccount);

        int databaseSizeBeforeUpdate = plaidAccountRepository.findAll().size();

        // Update the plaidAccount
        PlaidAccount updatedPlaidAccount = plaidAccountRepository.findById(plaidAccount.getId()).get();
        // Disconnect from session so that the updates on updatedPlaidAccount are not directly saved in db
        em.detach(updatedPlaidAccount);
        updatedPlaidAccount
            .plaidAccountId(UPDATED_PLAID_ACCOUNT_ID)
            .itemId(UPDATED_ITEM_ID)
            .type(UPDATED_TYPE)
            .subtype(UPDATED_SUBTYPE)
            .balanceAvailable(UPDATED_BALANCE_AVAILABLE)
            .balanceCurrent(UPDATED_BALANCE_CURRENT)
            .isoCurrencyCode(UPDATED_ISO_CURRENCY_CODE)
            .name(UPDATED_NAME)
            .officialName(UPDATED_OFFICIAL_NAME)
            .iban(UPDATED_IBAN)
            .bic(UPDATED_BIC);
        PlaidAccountDTO plaidAccountDTO = plaidAccountMapper.toDto(updatedPlaidAccount);

        restPlaidAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, plaidAccountDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plaidAccountDTO))
            )
            .andExpect(status().isOk());

        // Validate the PlaidAccount in the database
        List<PlaidAccount> plaidAccountList = plaidAccountRepository.findAll();
        assertThat(plaidAccountList).hasSize(databaseSizeBeforeUpdate);
        PlaidAccount testPlaidAccount = plaidAccountList.get(plaidAccountList.size() - 1);
        assertThat(testPlaidAccount.getPlaidAccountId()).isEqualTo(UPDATED_PLAID_ACCOUNT_ID);
        assertThat(testPlaidAccount.getItemId()).isEqualTo(UPDATED_ITEM_ID);
        assertThat(testPlaidAccount.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPlaidAccount.getSubtype()).isEqualTo(UPDATED_SUBTYPE);
        assertThat(testPlaidAccount.getBalanceAvailable()).isEqualTo(UPDATED_BALANCE_AVAILABLE);
        assertThat(testPlaidAccount.getBalanceCurrent()).isEqualTo(UPDATED_BALANCE_CURRENT);
        assertThat(testPlaidAccount.getIsoCurrencyCode()).isEqualTo(UPDATED_ISO_CURRENCY_CODE);
        assertThat(testPlaidAccount.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlaidAccount.getOfficialName()).isEqualTo(UPDATED_OFFICIAL_NAME);
        assertThat(testPlaidAccount.getIban()).isEqualTo(UPDATED_IBAN);
        assertThat(testPlaidAccount.getBic()).isEqualTo(UPDATED_BIC);
    }

    @Test
    @Transactional
    void putNonExistingPlaidAccount() throws Exception {
        int databaseSizeBeforeUpdate = plaidAccountRepository.findAll().size();
        plaidAccount.setId(count.incrementAndGet());

        // Create the PlaidAccount
        PlaidAccountDTO plaidAccountDTO = plaidAccountMapper.toDto(plaidAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlaidAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, plaidAccountDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plaidAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlaidAccount in the database
        List<PlaidAccount> plaidAccountList = plaidAccountRepository.findAll();
        assertThat(plaidAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlaidAccount() throws Exception {
        int databaseSizeBeforeUpdate = plaidAccountRepository.findAll().size();
        plaidAccount.setId(count.incrementAndGet());

        // Create the PlaidAccount
        PlaidAccountDTO plaidAccountDTO = plaidAccountMapper.toDto(plaidAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaidAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plaidAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlaidAccount in the database
        List<PlaidAccount> plaidAccountList = plaidAccountRepository.findAll();
        assertThat(plaidAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlaidAccount() throws Exception {
        int databaseSizeBeforeUpdate = plaidAccountRepository.findAll().size();
        plaidAccount.setId(count.incrementAndGet());

        // Create the PlaidAccount
        PlaidAccountDTO plaidAccountDTO = plaidAccountMapper.toDto(plaidAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaidAccountMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plaidAccountDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlaidAccount in the database
        List<PlaidAccount> plaidAccountList = plaidAccountRepository.findAll();
        assertThat(plaidAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlaidAccountWithPatch() throws Exception {
        // Initialize the database
        plaidAccountRepository.saveAndFlush(plaidAccount);

        int databaseSizeBeforeUpdate = plaidAccountRepository.findAll().size();

        // Update the plaidAccount using partial update
        PlaidAccount partialUpdatedPlaidAccount = new PlaidAccount();
        partialUpdatedPlaidAccount.setId(plaidAccount.getId());

        partialUpdatedPlaidAccount
            .itemId(UPDATED_ITEM_ID)
            .type(UPDATED_TYPE)
            .balanceCurrent(UPDATED_BALANCE_CURRENT)
            .isoCurrencyCode(UPDATED_ISO_CURRENCY_CODE)
            .officialName(UPDATED_OFFICIAL_NAME)
            .iban(UPDATED_IBAN);

        restPlaidAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlaidAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlaidAccount))
            )
            .andExpect(status().isOk());

        // Validate the PlaidAccount in the database
        List<PlaidAccount> plaidAccountList = plaidAccountRepository.findAll();
        assertThat(plaidAccountList).hasSize(databaseSizeBeforeUpdate);
        PlaidAccount testPlaidAccount = plaidAccountList.get(plaidAccountList.size() - 1);
        assertThat(testPlaidAccount.getPlaidAccountId()).isEqualTo(DEFAULT_PLAID_ACCOUNT_ID);
        assertThat(testPlaidAccount.getItemId()).isEqualTo(UPDATED_ITEM_ID);
        assertThat(testPlaidAccount.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPlaidAccount.getSubtype()).isEqualTo(DEFAULT_SUBTYPE);
        assertThat(testPlaidAccount.getBalanceAvailable()).isEqualByComparingTo(DEFAULT_BALANCE_AVAILABLE);
        assertThat(testPlaidAccount.getBalanceCurrent()).isEqualByComparingTo(UPDATED_BALANCE_CURRENT);
        assertThat(testPlaidAccount.getIsoCurrencyCode()).isEqualTo(UPDATED_ISO_CURRENCY_CODE);
        assertThat(testPlaidAccount.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPlaidAccount.getOfficialName()).isEqualTo(UPDATED_OFFICIAL_NAME);
        assertThat(testPlaidAccount.getIban()).isEqualTo(UPDATED_IBAN);
        assertThat(testPlaidAccount.getBic()).isEqualTo(DEFAULT_BIC);
    }

    @Test
    @Transactional
    void fullUpdatePlaidAccountWithPatch() throws Exception {
        // Initialize the database
        plaidAccountRepository.saveAndFlush(plaidAccount);

        int databaseSizeBeforeUpdate = plaidAccountRepository.findAll().size();

        // Update the plaidAccount using partial update
        PlaidAccount partialUpdatedPlaidAccount = new PlaidAccount();
        partialUpdatedPlaidAccount.setId(plaidAccount.getId());

        partialUpdatedPlaidAccount
            .plaidAccountId(UPDATED_PLAID_ACCOUNT_ID)
            .itemId(UPDATED_ITEM_ID)
            .type(UPDATED_TYPE)
            .subtype(UPDATED_SUBTYPE)
            .balanceAvailable(UPDATED_BALANCE_AVAILABLE)
            .balanceCurrent(UPDATED_BALANCE_CURRENT)
            .isoCurrencyCode(UPDATED_ISO_CURRENCY_CODE)
            .name(UPDATED_NAME)
            .officialName(UPDATED_OFFICIAL_NAME)
            .iban(UPDATED_IBAN)
            .bic(UPDATED_BIC);

        restPlaidAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlaidAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlaidAccount))
            )
            .andExpect(status().isOk());

        // Validate the PlaidAccount in the database
        List<PlaidAccount> plaidAccountList = plaidAccountRepository.findAll();
        assertThat(plaidAccountList).hasSize(databaseSizeBeforeUpdate);
        PlaidAccount testPlaidAccount = plaidAccountList.get(plaidAccountList.size() - 1);
        assertThat(testPlaidAccount.getPlaidAccountId()).isEqualTo(UPDATED_PLAID_ACCOUNT_ID);
        assertThat(testPlaidAccount.getItemId()).isEqualTo(UPDATED_ITEM_ID);
        assertThat(testPlaidAccount.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPlaidAccount.getSubtype()).isEqualTo(UPDATED_SUBTYPE);
        assertThat(testPlaidAccount.getBalanceAvailable()).isEqualByComparingTo(UPDATED_BALANCE_AVAILABLE);
        assertThat(testPlaidAccount.getBalanceCurrent()).isEqualByComparingTo(UPDATED_BALANCE_CURRENT);
        assertThat(testPlaidAccount.getIsoCurrencyCode()).isEqualTo(UPDATED_ISO_CURRENCY_CODE);
        assertThat(testPlaidAccount.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlaidAccount.getOfficialName()).isEqualTo(UPDATED_OFFICIAL_NAME);
        assertThat(testPlaidAccount.getIban()).isEqualTo(UPDATED_IBAN);
        assertThat(testPlaidAccount.getBic()).isEqualTo(UPDATED_BIC);
    }

    @Test
    @Transactional
    void patchNonExistingPlaidAccount() throws Exception {
        int databaseSizeBeforeUpdate = plaidAccountRepository.findAll().size();
        plaidAccount.setId(count.incrementAndGet());

        // Create the PlaidAccount
        PlaidAccountDTO plaidAccountDTO = plaidAccountMapper.toDto(plaidAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlaidAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, plaidAccountDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plaidAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlaidAccount in the database
        List<PlaidAccount> plaidAccountList = plaidAccountRepository.findAll();
        assertThat(plaidAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlaidAccount() throws Exception {
        int databaseSizeBeforeUpdate = plaidAccountRepository.findAll().size();
        plaidAccount.setId(count.incrementAndGet());

        // Create the PlaidAccount
        PlaidAccountDTO plaidAccountDTO = plaidAccountMapper.toDto(plaidAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaidAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plaidAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlaidAccount in the database
        List<PlaidAccount> plaidAccountList = plaidAccountRepository.findAll();
        assertThat(plaidAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlaidAccount() throws Exception {
        int databaseSizeBeforeUpdate = plaidAccountRepository.findAll().size();
        plaidAccount.setId(count.incrementAndGet());

        // Create the PlaidAccount
        PlaidAccountDTO plaidAccountDTO = plaidAccountMapper.toDto(plaidAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaidAccountMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plaidAccountDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlaidAccount in the database
        List<PlaidAccount> plaidAccountList = plaidAccountRepository.findAll();
        assertThat(plaidAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlaidAccount() throws Exception {
        // Initialize the database
        plaidAccountRepository.saveAndFlush(plaidAccount);

        int databaseSizeBeforeDelete = plaidAccountRepository.findAll().size();

        // Delete the plaidAccount
        restPlaidAccountMockMvc
            .perform(delete(ENTITY_API_URL_ID, plaidAccount.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlaidAccount> plaidAccountList = plaidAccountRepository.findAll();
        assertThat(plaidAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
