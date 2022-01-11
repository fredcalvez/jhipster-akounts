package com.akounts.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.akounts.myapp.IntegrationTest;
import com.akounts.myapp.domain.BankInstitution;
import com.akounts.myapp.domain.enumeration.Currency;
import com.akounts.myapp.repository.BankInstitutionRepository;
import com.akounts.myapp.service.dto.BankInstitutionDTO;
import com.akounts.myapp.service.mapper.BankInstitutionMapper;
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
 * Integration tests for the {@link BankInstitutionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BankInstitutionResourceIT {

    private static final String DEFAULT_INSTITUTION_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_INSTITUTION_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final Currency DEFAULT_CURRENCY = Currency.AED;
    private static final Currency UPDATED_CURRENCY = Currency.AFN;

    private static final String DEFAULT_ISO_COUNTRY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ISO_COUNTRY_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bank-institutions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BankInstitutionRepository bankInstitutionRepository;

    @Autowired
    private BankInstitutionMapper bankInstitutionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBankInstitutionMockMvc;

    private BankInstitution bankInstitution;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankInstitution createEntity(EntityManager em) {
        BankInstitution bankInstitution = new BankInstitution()
            .institutionLabel(DEFAULT_INSTITUTION_LABEL)
            .code(DEFAULT_CODE)
            .active(DEFAULT_ACTIVE)
            .currency(DEFAULT_CURRENCY)
            .isoCountryCode(DEFAULT_ISO_COUNTRY_CODE);
        return bankInstitution;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankInstitution createUpdatedEntity(EntityManager em) {
        BankInstitution bankInstitution = new BankInstitution()
            .institutionLabel(UPDATED_INSTITUTION_LABEL)
            .code(UPDATED_CODE)
            .active(UPDATED_ACTIVE)
            .currency(UPDATED_CURRENCY)
            .isoCountryCode(UPDATED_ISO_COUNTRY_CODE);
        return bankInstitution;
    }

    @BeforeEach
    public void initTest() {
        bankInstitution = createEntity(em);
    }

    @Test
    @Transactional
    void createBankInstitution() throws Exception {
        int databaseSizeBeforeCreate = bankInstitutionRepository.findAll().size();
        // Create the BankInstitution
        BankInstitutionDTO bankInstitutionDTO = bankInstitutionMapper.toDto(bankInstitution);
        restBankInstitutionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankInstitutionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BankInstitution in the database
        List<BankInstitution> bankInstitutionList = bankInstitutionRepository.findAll();
        assertThat(bankInstitutionList).hasSize(databaseSizeBeforeCreate + 1);
        BankInstitution testBankInstitution = bankInstitutionList.get(bankInstitutionList.size() - 1);
        assertThat(testBankInstitution.getInstitutionLabel()).isEqualTo(DEFAULT_INSTITUTION_LABEL);
        assertThat(testBankInstitution.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testBankInstitution.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testBankInstitution.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testBankInstitution.getIsoCountryCode()).isEqualTo(DEFAULT_ISO_COUNTRY_CODE);
    }

    @Test
    @Transactional
    void createBankInstitutionWithExistingId() throws Exception {
        // Create the BankInstitution with an existing ID
        bankInstitution.setId(1L);
        BankInstitutionDTO bankInstitutionDTO = bankInstitutionMapper.toDto(bankInstitution);

        int databaseSizeBeforeCreate = bankInstitutionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankInstitutionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankInstitutionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankInstitution in the database
        List<BankInstitution> bankInstitutionList = bankInstitutionRepository.findAll();
        assertThat(bankInstitutionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBankInstitutions() throws Exception {
        // Initialize the database
        bankInstitutionRepository.saveAndFlush(bankInstitution);

        // Get all the bankInstitutionList
        restBankInstitutionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankInstitution.getId().intValue())))
            .andExpect(jsonPath("$.[*].institutionLabel").value(hasItem(DEFAULT_INSTITUTION_LABEL)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].isoCountryCode").value(hasItem(DEFAULT_ISO_COUNTRY_CODE)));
    }

    @Test
    @Transactional
    void getBankInstitution() throws Exception {
        // Initialize the database
        bankInstitutionRepository.saveAndFlush(bankInstitution);

        // Get the bankInstitution
        restBankInstitutionMockMvc
            .perform(get(ENTITY_API_URL_ID, bankInstitution.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bankInstitution.getId().intValue()))
            .andExpect(jsonPath("$.institutionLabel").value(DEFAULT_INSTITUTION_LABEL))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY.toString()))
            .andExpect(jsonPath("$.isoCountryCode").value(DEFAULT_ISO_COUNTRY_CODE));
    }

    @Test
    @Transactional
    void getNonExistingBankInstitution() throws Exception {
        // Get the bankInstitution
        restBankInstitutionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBankInstitution() throws Exception {
        // Initialize the database
        bankInstitutionRepository.saveAndFlush(bankInstitution);

        int databaseSizeBeforeUpdate = bankInstitutionRepository.findAll().size();

        // Update the bankInstitution
        BankInstitution updatedBankInstitution = bankInstitutionRepository.findById(bankInstitution.getId()).get();
        // Disconnect from session so that the updates on updatedBankInstitution are not directly saved in db
        em.detach(updatedBankInstitution);
        updatedBankInstitution
            .institutionLabel(UPDATED_INSTITUTION_LABEL)
            .code(UPDATED_CODE)
            .active(UPDATED_ACTIVE)
            .currency(UPDATED_CURRENCY)
            .isoCountryCode(UPDATED_ISO_COUNTRY_CODE);
        BankInstitutionDTO bankInstitutionDTO = bankInstitutionMapper.toDto(updatedBankInstitution);

        restBankInstitutionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankInstitutionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankInstitutionDTO))
            )
            .andExpect(status().isOk());

        // Validate the BankInstitution in the database
        List<BankInstitution> bankInstitutionList = bankInstitutionRepository.findAll();
        assertThat(bankInstitutionList).hasSize(databaseSizeBeforeUpdate);
        BankInstitution testBankInstitution = bankInstitutionList.get(bankInstitutionList.size() - 1);
        assertThat(testBankInstitution.getInstitutionLabel()).isEqualTo(UPDATED_INSTITUTION_LABEL);
        assertThat(testBankInstitution.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testBankInstitution.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testBankInstitution.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testBankInstitution.getIsoCountryCode()).isEqualTo(UPDATED_ISO_COUNTRY_CODE);
    }

    @Test
    @Transactional
    void putNonExistingBankInstitution() throws Exception {
        int databaseSizeBeforeUpdate = bankInstitutionRepository.findAll().size();
        bankInstitution.setId(count.incrementAndGet());

        // Create the BankInstitution
        BankInstitutionDTO bankInstitutionDTO = bankInstitutionMapper.toDto(bankInstitution);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankInstitutionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankInstitutionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankInstitutionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankInstitution in the database
        List<BankInstitution> bankInstitutionList = bankInstitutionRepository.findAll();
        assertThat(bankInstitutionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBankInstitution() throws Exception {
        int databaseSizeBeforeUpdate = bankInstitutionRepository.findAll().size();
        bankInstitution.setId(count.incrementAndGet());

        // Create the BankInstitution
        BankInstitutionDTO bankInstitutionDTO = bankInstitutionMapper.toDto(bankInstitution);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankInstitutionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankInstitutionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankInstitution in the database
        List<BankInstitution> bankInstitutionList = bankInstitutionRepository.findAll();
        assertThat(bankInstitutionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBankInstitution() throws Exception {
        int databaseSizeBeforeUpdate = bankInstitutionRepository.findAll().size();
        bankInstitution.setId(count.incrementAndGet());

        // Create the BankInstitution
        BankInstitutionDTO bankInstitutionDTO = bankInstitutionMapper.toDto(bankInstitution);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankInstitutionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankInstitutionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankInstitution in the database
        List<BankInstitution> bankInstitutionList = bankInstitutionRepository.findAll();
        assertThat(bankInstitutionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBankInstitutionWithPatch() throws Exception {
        // Initialize the database
        bankInstitutionRepository.saveAndFlush(bankInstitution);

        int databaseSizeBeforeUpdate = bankInstitutionRepository.findAll().size();

        // Update the bankInstitution using partial update
        BankInstitution partialUpdatedBankInstitution = new BankInstitution();
        partialUpdatedBankInstitution.setId(bankInstitution.getId());

        partialUpdatedBankInstitution.active(UPDATED_ACTIVE).currency(UPDATED_CURRENCY).isoCountryCode(UPDATED_ISO_COUNTRY_CODE);

        restBankInstitutionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankInstitution.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankInstitution))
            )
            .andExpect(status().isOk());

        // Validate the BankInstitution in the database
        List<BankInstitution> bankInstitutionList = bankInstitutionRepository.findAll();
        assertThat(bankInstitutionList).hasSize(databaseSizeBeforeUpdate);
        BankInstitution testBankInstitution = bankInstitutionList.get(bankInstitutionList.size() - 1);
        assertThat(testBankInstitution.getInstitutionLabel()).isEqualTo(DEFAULT_INSTITUTION_LABEL);
        assertThat(testBankInstitution.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testBankInstitution.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testBankInstitution.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testBankInstitution.getIsoCountryCode()).isEqualTo(UPDATED_ISO_COUNTRY_CODE);
    }

    @Test
    @Transactional
    void fullUpdateBankInstitutionWithPatch() throws Exception {
        // Initialize the database
        bankInstitutionRepository.saveAndFlush(bankInstitution);

        int databaseSizeBeforeUpdate = bankInstitutionRepository.findAll().size();

        // Update the bankInstitution using partial update
        BankInstitution partialUpdatedBankInstitution = new BankInstitution();
        partialUpdatedBankInstitution.setId(bankInstitution.getId());

        partialUpdatedBankInstitution
            .institutionLabel(UPDATED_INSTITUTION_LABEL)
            .code(UPDATED_CODE)
            .active(UPDATED_ACTIVE)
            .currency(UPDATED_CURRENCY)
            .isoCountryCode(UPDATED_ISO_COUNTRY_CODE);

        restBankInstitutionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankInstitution.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankInstitution))
            )
            .andExpect(status().isOk());

        // Validate the BankInstitution in the database
        List<BankInstitution> bankInstitutionList = bankInstitutionRepository.findAll();
        assertThat(bankInstitutionList).hasSize(databaseSizeBeforeUpdate);
        BankInstitution testBankInstitution = bankInstitutionList.get(bankInstitutionList.size() - 1);
        assertThat(testBankInstitution.getInstitutionLabel()).isEqualTo(UPDATED_INSTITUTION_LABEL);
        assertThat(testBankInstitution.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testBankInstitution.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testBankInstitution.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testBankInstitution.getIsoCountryCode()).isEqualTo(UPDATED_ISO_COUNTRY_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingBankInstitution() throws Exception {
        int databaseSizeBeforeUpdate = bankInstitutionRepository.findAll().size();
        bankInstitution.setId(count.incrementAndGet());

        // Create the BankInstitution
        BankInstitutionDTO bankInstitutionDTO = bankInstitutionMapper.toDto(bankInstitution);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankInstitutionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bankInstitutionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankInstitutionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankInstitution in the database
        List<BankInstitution> bankInstitutionList = bankInstitutionRepository.findAll();
        assertThat(bankInstitutionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBankInstitution() throws Exception {
        int databaseSizeBeforeUpdate = bankInstitutionRepository.findAll().size();
        bankInstitution.setId(count.incrementAndGet());

        // Create the BankInstitution
        BankInstitutionDTO bankInstitutionDTO = bankInstitutionMapper.toDto(bankInstitution);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankInstitutionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankInstitutionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankInstitution in the database
        List<BankInstitution> bankInstitutionList = bankInstitutionRepository.findAll();
        assertThat(bankInstitutionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBankInstitution() throws Exception {
        int databaseSizeBeforeUpdate = bankInstitutionRepository.findAll().size();
        bankInstitution.setId(count.incrementAndGet());

        // Create the BankInstitution
        BankInstitutionDTO bankInstitutionDTO = bankInstitutionMapper.toDto(bankInstitution);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankInstitutionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankInstitutionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankInstitution in the database
        List<BankInstitution> bankInstitutionList = bankInstitutionRepository.findAll();
        assertThat(bankInstitutionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBankInstitution() throws Exception {
        // Initialize the database
        bankInstitutionRepository.saveAndFlush(bankInstitution);

        int databaseSizeBeforeDelete = bankInstitutionRepository.findAll().size();

        // Delete the bankInstitution
        restBankInstitutionMockMvc
            .perform(delete(ENTITY_API_URL_ID, bankInstitution.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BankInstitution> bankInstitutionList = bankInstitutionRepository.findAll();
        assertThat(bankInstitutionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
