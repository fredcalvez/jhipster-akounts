package com.akounts.myapp.web.rest;

import static com.akounts.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.akounts.myapp.IntegrationTest;
import com.akounts.myapp.domain.BankSaving;
import com.akounts.myapp.repository.BankSavingRepository;
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
 * Integration tests for the {@link BankSavingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BankSavingResourceIT {

    private static final Instant DEFAULT_SUMMARY_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SUMMARY_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_GOAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_GOAL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_REACH = new BigDecimal(1);
    private static final BigDecimal UPDATED_REACH = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/bank-savings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BankSavingRepository bankSavingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBankSavingMockMvc;

    private BankSaving bankSaving;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankSaving createEntity(EntityManager em) {
        BankSaving bankSaving = new BankSaving()
            .summaryDate(DEFAULT_SUMMARY_DATE)
            .amount(DEFAULT_AMOUNT)
            .goal(DEFAULT_GOAL)
            .reach(DEFAULT_REACH);
        return bankSaving;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankSaving createUpdatedEntity(EntityManager em) {
        BankSaving bankSaving = new BankSaving()
            .summaryDate(UPDATED_SUMMARY_DATE)
            .amount(UPDATED_AMOUNT)
            .goal(UPDATED_GOAL)
            .reach(UPDATED_REACH);
        return bankSaving;
    }

    @BeforeEach
    public void initTest() {
        bankSaving = createEntity(em);
    }

    @Test
    @Transactional
    void createBankSaving() throws Exception {
        int databaseSizeBeforeCreate = bankSavingRepository.findAll().size();
        // Create the BankSaving
        restBankSavingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankSaving)))
            .andExpect(status().isCreated());

        // Validate the BankSaving in the database
        List<BankSaving> bankSavingList = bankSavingRepository.findAll();
        assertThat(bankSavingList).hasSize(databaseSizeBeforeCreate + 1);
        BankSaving testBankSaving = bankSavingList.get(bankSavingList.size() - 1);
        assertThat(testBankSaving.getSummaryDate()).isEqualTo(DEFAULT_SUMMARY_DATE);
        assertThat(testBankSaving.getAmount()).isEqualByComparingTo(DEFAULT_AMOUNT);
        assertThat(testBankSaving.getGoal()).isEqualByComparingTo(DEFAULT_GOAL);
        assertThat(testBankSaving.getReach()).isEqualByComparingTo(DEFAULT_REACH);
    }

    @Test
    @Transactional
    void createBankSavingWithExistingId() throws Exception {
        // Create the BankSaving with an existing ID
        bankSaving.setId(1L);

        int databaseSizeBeforeCreate = bankSavingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankSavingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankSaving)))
            .andExpect(status().isBadRequest());

        // Validate the BankSaving in the database
        List<BankSaving> bankSavingList = bankSavingRepository.findAll();
        assertThat(bankSavingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBankSavings() throws Exception {
        // Initialize the database
        bankSavingRepository.saveAndFlush(bankSaving);

        // Get all the bankSavingList
        restBankSavingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankSaving.getId().intValue())))
            .andExpect(jsonPath("$.[*].summaryDate").value(hasItem(DEFAULT_SUMMARY_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(sameNumber(DEFAULT_AMOUNT))))
            .andExpect(jsonPath("$.[*].goal").value(hasItem(sameNumber(DEFAULT_GOAL))))
            .andExpect(jsonPath("$.[*].reach").value(hasItem(sameNumber(DEFAULT_REACH))));
    }

    @Test
    @Transactional
    void getBankSaving() throws Exception {
        // Initialize the database
        bankSavingRepository.saveAndFlush(bankSaving);

        // Get the bankSaving
        restBankSavingMockMvc
            .perform(get(ENTITY_API_URL_ID, bankSaving.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bankSaving.getId().intValue()))
            .andExpect(jsonPath("$.summaryDate").value(DEFAULT_SUMMARY_DATE.toString()))
            .andExpect(jsonPath("$.amount").value(sameNumber(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.goal").value(sameNumber(DEFAULT_GOAL)))
            .andExpect(jsonPath("$.reach").value(sameNumber(DEFAULT_REACH)));
    }

    @Test
    @Transactional
    void getNonExistingBankSaving() throws Exception {
        // Get the bankSaving
        restBankSavingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBankSaving() throws Exception {
        // Initialize the database
        bankSavingRepository.saveAndFlush(bankSaving);

        int databaseSizeBeforeUpdate = bankSavingRepository.findAll().size();

        // Update the bankSaving
        BankSaving updatedBankSaving = bankSavingRepository.findById(bankSaving.getId()).get();
        // Disconnect from session so that the updates on updatedBankSaving are not directly saved in db
        em.detach(updatedBankSaving);
        updatedBankSaving.summaryDate(UPDATED_SUMMARY_DATE).amount(UPDATED_AMOUNT).goal(UPDATED_GOAL).reach(UPDATED_REACH);

        restBankSavingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBankSaving.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBankSaving))
            )
            .andExpect(status().isOk());

        // Validate the BankSaving in the database
        List<BankSaving> bankSavingList = bankSavingRepository.findAll();
        assertThat(bankSavingList).hasSize(databaseSizeBeforeUpdate);
        BankSaving testBankSaving = bankSavingList.get(bankSavingList.size() - 1);
        assertThat(testBankSaving.getSummaryDate()).isEqualTo(UPDATED_SUMMARY_DATE);
        assertThat(testBankSaving.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testBankSaving.getGoal()).isEqualTo(UPDATED_GOAL);
        assertThat(testBankSaving.getReach()).isEqualTo(UPDATED_REACH);
    }

    @Test
    @Transactional
    void putNonExistingBankSaving() throws Exception {
        int databaseSizeBeforeUpdate = bankSavingRepository.findAll().size();
        bankSaving.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankSavingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankSaving.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankSaving))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankSaving in the database
        List<BankSaving> bankSavingList = bankSavingRepository.findAll();
        assertThat(bankSavingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBankSaving() throws Exception {
        int databaseSizeBeforeUpdate = bankSavingRepository.findAll().size();
        bankSaving.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankSavingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankSaving))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankSaving in the database
        List<BankSaving> bankSavingList = bankSavingRepository.findAll();
        assertThat(bankSavingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBankSaving() throws Exception {
        int databaseSizeBeforeUpdate = bankSavingRepository.findAll().size();
        bankSaving.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankSavingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankSaving)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankSaving in the database
        List<BankSaving> bankSavingList = bankSavingRepository.findAll();
        assertThat(bankSavingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBankSavingWithPatch() throws Exception {
        // Initialize the database
        bankSavingRepository.saveAndFlush(bankSaving);

        int databaseSizeBeforeUpdate = bankSavingRepository.findAll().size();

        // Update the bankSaving using partial update
        BankSaving partialUpdatedBankSaving = new BankSaving();
        partialUpdatedBankSaving.setId(bankSaving.getId());

        partialUpdatedBankSaving.goal(UPDATED_GOAL);

        restBankSavingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankSaving.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankSaving))
            )
            .andExpect(status().isOk());

        // Validate the BankSaving in the database
        List<BankSaving> bankSavingList = bankSavingRepository.findAll();
        assertThat(bankSavingList).hasSize(databaseSizeBeforeUpdate);
        BankSaving testBankSaving = bankSavingList.get(bankSavingList.size() - 1);
        assertThat(testBankSaving.getSummaryDate()).isEqualTo(DEFAULT_SUMMARY_DATE);
        assertThat(testBankSaving.getAmount()).isEqualByComparingTo(DEFAULT_AMOUNT);
        assertThat(testBankSaving.getGoal()).isEqualByComparingTo(UPDATED_GOAL);
        assertThat(testBankSaving.getReach()).isEqualByComparingTo(DEFAULT_REACH);
    }

    @Test
    @Transactional
    void fullUpdateBankSavingWithPatch() throws Exception {
        // Initialize the database
        bankSavingRepository.saveAndFlush(bankSaving);

        int databaseSizeBeforeUpdate = bankSavingRepository.findAll().size();

        // Update the bankSaving using partial update
        BankSaving partialUpdatedBankSaving = new BankSaving();
        partialUpdatedBankSaving.setId(bankSaving.getId());

        partialUpdatedBankSaving.summaryDate(UPDATED_SUMMARY_DATE).amount(UPDATED_AMOUNT).goal(UPDATED_GOAL).reach(UPDATED_REACH);

        restBankSavingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankSaving.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankSaving))
            )
            .andExpect(status().isOk());

        // Validate the BankSaving in the database
        List<BankSaving> bankSavingList = bankSavingRepository.findAll();
        assertThat(bankSavingList).hasSize(databaseSizeBeforeUpdate);
        BankSaving testBankSaving = bankSavingList.get(bankSavingList.size() - 1);
        assertThat(testBankSaving.getSummaryDate()).isEqualTo(UPDATED_SUMMARY_DATE);
        assertThat(testBankSaving.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
        assertThat(testBankSaving.getGoal()).isEqualByComparingTo(UPDATED_GOAL);
        assertThat(testBankSaving.getReach()).isEqualByComparingTo(UPDATED_REACH);
    }

    @Test
    @Transactional
    void patchNonExistingBankSaving() throws Exception {
        int databaseSizeBeforeUpdate = bankSavingRepository.findAll().size();
        bankSaving.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankSavingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bankSaving.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankSaving))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankSaving in the database
        List<BankSaving> bankSavingList = bankSavingRepository.findAll();
        assertThat(bankSavingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBankSaving() throws Exception {
        int databaseSizeBeforeUpdate = bankSavingRepository.findAll().size();
        bankSaving.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankSavingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankSaving))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankSaving in the database
        List<BankSaving> bankSavingList = bankSavingRepository.findAll();
        assertThat(bankSavingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBankSaving() throws Exception {
        int databaseSizeBeforeUpdate = bankSavingRepository.findAll().size();
        bankSaving.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankSavingMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bankSaving))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankSaving in the database
        List<BankSaving> bankSavingList = bankSavingRepository.findAll();
        assertThat(bankSavingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBankSaving() throws Exception {
        // Initialize the database
        bankSavingRepository.saveAndFlush(bankSaving);

        int databaseSizeBeforeDelete = bankSavingRepository.findAll().size();

        // Delete the bankSaving
        restBankSavingMockMvc
            .perform(delete(ENTITY_API_URL_ID, bankSaving.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BankSaving> bankSavingList = bankSavingRepository.findAll();
        assertThat(bankSavingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
