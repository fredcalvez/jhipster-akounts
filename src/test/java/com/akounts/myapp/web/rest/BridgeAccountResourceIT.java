package com.akounts.myapp.web.rest;

import static com.akounts.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.akounts.myapp.IntegrationTest;
import com.akounts.myapp.domain.BridgeAccount;
import com.akounts.myapp.domain.enumeration.Currency;
import com.akounts.myapp.repository.BridgeAccountRepository;
import com.akounts.myapp.service.dto.BridgeAccountDTO;
import com.akounts.myapp.service.mapper.BridgeAccountMapper;
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
 * Integration tests for the {@link BridgeAccountResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BridgeAccountResourceIT {

    private static final String DEFAULT_BRIDGE_ACCOUNT_ID = "AAAAAAAAAA";
    private static final String UPDATED_BRIDGE_ACCOUNT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_BALANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_BALANCE = new BigDecimal(2);

    private static final Currency DEFAULT_ISO_CURRENCY_CODE = Currency.AED;
    private static final Currency UPDATED_ISO_CURRENCY_CODE = Currency.AFN;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bridge-accounts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BridgeAccountRepository bridgeAccountRepository;

    @Autowired
    private BridgeAccountMapper bridgeAccountMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBridgeAccountMockMvc;

    private BridgeAccount bridgeAccount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BridgeAccount createEntity(EntityManager em) {
        BridgeAccount bridgeAccount = new BridgeAccount()
            .bridgeAccountId(DEFAULT_BRIDGE_ACCOUNT_ID)
            .type(DEFAULT_TYPE)
            .status(DEFAULT_STATUS)
            .balance(DEFAULT_BALANCE)
            .isoCurrencyCode(DEFAULT_ISO_CURRENCY_CODE)
            .name(DEFAULT_NAME);
        return bridgeAccount;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BridgeAccount createUpdatedEntity(EntityManager em) {
        BridgeAccount bridgeAccount = new BridgeAccount()
            .bridgeAccountId(UPDATED_BRIDGE_ACCOUNT_ID)
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS)
            .balance(UPDATED_BALANCE)
            .isoCurrencyCode(UPDATED_ISO_CURRENCY_CODE)
            .name(UPDATED_NAME);
        return bridgeAccount;
    }

    @BeforeEach
    public void initTest() {
        bridgeAccount = createEntity(em);
    }

    @Test
    @Transactional
    void createBridgeAccount() throws Exception {
        int databaseSizeBeforeCreate = bridgeAccountRepository.findAll().size();
        // Create the BridgeAccount
        BridgeAccountDTO bridgeAccountDTO = bridgeAccountMapper.toDto(bridgeAccount);
        restBridgeAccountMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bridgeAccountDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BridgeAccount in the database
        List<BridgeAccount> bridgeAccountList = bridgeAccountRepository.findAll();
        assertThat(bridgeAccountList).hasSize(databaseSizeBeforeCreate + 1);
        BridgeAccount testBridgeAccount = bridgeAccountList.get(bridgeAccountList.size() - 1);
        assertThat(testBridgeAccount.getBridgeAccountId()).isEqualTo(DEFAULT_BRIDGE_ACCOUNT_ID);
        assertThat(testBridgeAccount.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testBridgeAccount.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testBridgeAccount.getBalance()).isEqualByComparingTo(DEFAULT_BALANCE);
        assertThat(testBridgeAccount.getIsoCurrencyCode()).isEqualTo(DEFAULT_ISO_CURRENCY_CODE);
        assertThat(testBridgeAccount.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createBridgeAccountWithExistingId() throws Exception {
        // Create the BridgeAccount with an existing ID
        bridgeAccount.setId(1L);
        BridgeAccountDTO bridgeAccountDTO = bridgeAccountMapper.toDto(bridgeAccount);

        int databaseSizeBeforeCreate = bridgeAccountRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBridgeAccountMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bridgeAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BridgeAccount in the database
        List<BridgeAccount> bridgeAccountList = bridgeAccountRepository.findAll();
        assertThat(bridgeAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBridgeAccounts() throws Exception {
        // Initialize the database
        bridgeAccountRepository.saveAndFlush(bridgeAccount);

        // Get all the bridgeAccountList
        restBridgeAccountMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bridgeAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].bridgeAccountId").value(hasItem(DEFAULT_BRIDGE_ACCOUNT_ID)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(sameNumber(DEFAULT_BALANCE))))
            .andExpect(jsonPath("$.[*].isoCurrencyCode").value(hasItem(DEFAULT_ISO_CURRENCY_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getBridgeAccount() throws Exception {
        // Initialize the database
        bridgeAccountRepository.saveAndFlush(bridgeAccount);

        // Get the bridgeAccount
        restBridgeAccountMockMvc
            .perform(get(ENTITY_API_URL_ID, bridgeAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bridgeAccount.getId().intValue()))
            .andExpect(jsonPath("$.bridgeAccountId").value(DEFAULT_BRIDGE_ACCOUNT_ID))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.balance").value(sameNumber(DEFAULT_BALANCE)))
            .andExpect(jsonPath("$.isoCurrencyCode").value(DEFAULT_ISO_CURRENCY_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingBridgeAccount() throws Exception {
        // Get the bridgeAccount
        restBridgeAccountMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBridgeAccount() throws Exception {
        // Initialize the database
        bridgeAccountRepository.saveAndFlush(bridgeAccount);

        int databaseSizeBeforeUpdate = bridgeAccountRepository.findAll().size();

        // Update the bridgeAccount
        BridgeAccount updatedBridgeAccount = bridgeAccountRepository.findById(bridgeAccount.getId()).get();
        // Disconnect from session so that the updates on updatedBridgeAccount are not directly saved in db
        em.detach(updatedBridgeAccount);
        updatedBridgeAccount
            .bridgeAccountId(UPDATED_BRIDGE_ACCOUNT_ID)
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS)
            .balance(UPDATED_BALANCE)
            .isoCurrencyCode(UPDATED_ISO_CURRENCY_CODE)
            .name(UPDATED_NAME);
        BridgeAccountDTO bridgeAccountDTO = bridgeAccountMapper.toDto(updatedBridgeAccount);

        restBridgeAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bridgeAccountDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bridgeAccountDTO))
            )
            .andExpect(status().isOk());

        // Validate the BridgeAccount in the database
        List<BridgeAccount> bridgeAccountList = bridgeAccountRepository.findAll();
        assertThat(bridgeAccountList).hasSize(databaseSizeBeforeUpdate);
        BridgeAccount testBridgeAccount = bridgeAccountList.get(bridgeAccountList.size() - 1);
        assertThat(testBridgeAccount.getBridgeAccountId()).isEqualTo(UPDATED_BRIDGE_ACCOUNT_ID);
        assertThat(testBridgeAccount.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testBridgeAccount.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBridgeAccount.getBalance()).isEqualTo(UPDATED_BALANCE);
        assertThat(testBridgeAccount.getIsoCurrencyCode()).isEqualTo(UPDATED_ISO_CURRENCY_CODE);
        assertThat(testBridgeAccount.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingBridgeAccount() throws Exception {
        int databaseSizeBeforeUpdate = bridgeAccountRepository.findAll().size();
        bridgeAccount.setId(count.incrementAndGet());

        // Create the BridgeAccount
        BridgeAccountDTO bridgeAccountDTO = bridgeAccountMapper.toDto(bridgeAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBridgeAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bridgeAccountDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bridgeAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BridgeAccount in the database
        List<BridgeAccount> bridgeAccountList = bridgeAccountRepository.findAll();
        assertThat(bridgeAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBridgeAccount() throws Exception {
        int databaseSizeBeforeUpdate = bridgeAccountRepository.findAll().size();
        bridgeAccount.setId(count.incrementAndGet());

        // Create the BridgeAccount
        BridgeAccountDTO bridgeAccountDTO = bridgeAccountMapper.toDto(bridgeAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBridgeAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bridgeAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BridgeAccount in the database
        List<BridgeAccount> bridgeAccountList = bridgeAccountRepository.findAll();
        assertThat(bridgeAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBridgeAccount() throws Exception {
        int databaseSizeBeforeUpdate = bridgeAccountRepository.findAll().size();
        bridgeAccount.setId(count.incrementAndGet());

        // Create the BridgeAccount
        BridgeAccountDTO bridgeAccountDTO = bridgeAccountMapper.toDto(bridgeAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBridgeAccountMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bridgeAccountDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BridgeAccount in the database
        List<BridgeAccount> bridgeAccountList = bridgeAccountRepository.findAll();
        assertThat(bridgeAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBridgeAccountWithPatch() throws Exception {
        // Initialize the database
        bridgeAccountRepository.saveAndFlush(bridgeAccount);

        int databaseSizeBeforeUpdate = bridgeAccountRepository.findAll().size();

        // Update the bridgeAccount using partial update
        BridgeAccount partialUpdatedBridgeAccount = new BridgeAccount();
        partialUpdatedBridgeAccount.setId(bridgeAccount.getId());

        partialUpdatedBridgeAccount.status(UPDATED_STATUS).balance(UPDATED_BALANCE);

        restBridgeAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBridgeAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBridgeAccount))
            )
            .andExpect(status().isOk());

        // Validate the BridgeAccount in the database
        List<BridgeAccount> bridgeAccountList = bridgeAccountRepository.findAll();
        assertThat(bridgeAccountList).hasSize(databaseSizeBeforeUpdate);
        BridgeAccount testBridgeAccount = bridgeAccountList.get(bridgeAccountList.size() - 1);
        assertThat(testBridgeAccount.getBridgeAccountId()).isEqualTo(DEFAULT_BRIDGE_ACCOUNT_ID);
        assertThat(testBridgeAccount.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testBridgeAccount.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBridgeAccount.getBalance()).isEqualByComparingTo(UPDATED_BALANCE);
        assertThat(testBridgeAccount.getIsoCurrencyCode()).isEqualTo(DEFAULT_ISO_CURRENCY_CODE);
        assertThat(testBridgeAccount.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateBridgeAccountWithPatch() throws Exception {
        // Initialize the database
        bridgeAccountRepository.saveAndFlush(bridgeAccount);

        int databaseSizeBeforeUpdate = bridgeAccountRepository.findAll().size();

        // Update the bridgeAccount using partial update
        BridgeAccount partialUpdatedBridgeAccount = new BridgeAccount();
        partialUpdatedBridgeAccount.setId(bridgeAccount.getId());

        partialUpdatedBridgeAccount
            .bridgeAccountId(UPDATED_BRIDGE_ACCOUNT_ID)
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS)
            .balance(UPDATED_BALANCE)
            .isoCurrencyCode(UPDATED_ISO_CURRENCY_CODE)
            .name(UPDATED_NAME);

        restBridgeAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBridgeAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBridgeAccount))
            )
            .andExpect(status().isOk());

        // Validate the BridgeAccount in the database
        List<BridgeAccount> bridgeAccountList = bridgeAccountRepository.findAll();
        assertThat(bridgeAccountList).hasSize(databaseSizeBeforeUpdate);
        BridgeAccount testBridgeAccount = bridgeAccountList.get(bridgeAccountList.size() - 1);
        assertThat(testBridgeAccount.getBridgeAccountId()).isEqualTo(UPDATED_BRIDGE_ACCOUNT_ID);
        assertThat(testBridgeAccount.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testBridgeAccount.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBridgeAccount.getBalance()).isEqualByComparingTo(UPDATED_BALANCE);
        assertThat(testBridgeAccount.getIsoCurrencyCode()).isEqualTo(UPDATED_ISO_CURRENCY_CODE);
        assertThat(testBridgeAccount.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingBridgeAccount() throws Exception {
        int databaseSizeBeforeUpdate = bridgeAccountRepository.findAll().size();
        bridgeAccount.setId(count.incrementAndGet());

        // Create the BridgeAccount
        BridgeAccountDTO bridgeAccountDTO = bridgeAccountMapper.toDto(bridgeAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBridgeAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bridgeAccountDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bridgeAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BridgeAccount in the database
        List<BridgeAccount> bridgeAccountList = bridgeAccountRepository.findAll();
        assertThat(bridgeAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBridgeAccount() throws Exception {
        int databaseSizeBeforeUpdate = bridgeAccountRepository.findAll().size();
        bridgeAccount.setId(count.incrementAndGet());

        // Create the BridgeAccount
        BridgeAccountDTO bridgeAccountDTO = bridgeAccountMapper.toDto(bridgeAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBridgeAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bridgeAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BridgeAccount in the database
        List<BridgeAccount> bridgeAccountList = bridgeAccountRepository.findAll();
        assertThat(bridgeAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBridgeAccount() throws Exception {
        int databaseSizeBeforeUpdate = bridgeAccountRepository.findAll().size();
        bridgeAccount.setId(count.incrementAndGet());

        // Create the BridgeAccount
        BridgeAccountDTO bridgeAccountDTO = bridgeAccountMapper.toDto(bridgeAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBridgeAccountMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bridgeAccountDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BridgeAccount in the database
        List<BridgeAccount> bridgeAccountList = bridgeAccountRepository.findAll();
        assertThat(bridgeAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBridgeAccount() throws Exception {
        // Initialize the database
        bridgeAccountRepository.saveAndFlush(bridgeAccount);

        int databaseSizeBeforeDelete = bridgeAccountRepository.findAll().size();

        // Delete the bridgeAccount
        restBridgeAccountMockMvc
            .perform(delete(ENTITY_API_URL_ID, bridgeAccount.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BridgeAccount> bridgeAccountList = bridgeAccountRepository.findAll();
        assertThat(bridgeAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
