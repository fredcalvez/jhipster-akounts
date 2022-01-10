package com.akounts.myapp.web.rest;

import static com.akounts.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.akounts.myapp.IntegrationTest;
import com.akounts.myapp.domain.RebaseHistory;
import com.akounts.myapp.domain.enumeration.Currency;
import com.akounts.myapp.domain.enumeration.Currency;
import com.akounts.myapp.repository.RebaseHistoryRepository;
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
 * Integration tests for the {@link RebaseHistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RebaseHistoryResourceIT {

    private static final BigDecimal DEFAULT_OLD_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_OLD_VALUE = new BigDecimal(2);

    private static final Currency DEFAULT_OLD_CURRENCY = Currency.EUR;
    private static final Currency UPDATED_OLD_CURRENCY = Currency.USD;

    private static final BigDecimal DEFAULT_NEW_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_NEW_VALUE = new BigDecimal(2);

    private static final Currency DEFAULT_NEW_CURRENCY = Currency.EUR;
    private static final Currency UPDATED_NEW_CURRENCY = Currency.USD;

    private static final Instant DEFAULT_RUN_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RUN_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/rebase-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RebaseHistoryRepository rebaseHistoryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRebaseHistoryMockMvc;

    private RebaseHistory rebaseHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RebaseHistory createEntity(EntityManager em) {
        RebaseHistory rebaseHistory = new RebaseHistory()
            .oldValue(DEFAULT_OLD_VALUE)
            .oldCurrency(DEFAULT_OLD_CURRENCY)
            .newValue(DEFAULT_NEW_VALUE)
            .newCurrency(DEFAULT_NEW_CURRENCY)
            .runDate(DEFAULT_RUN_DATE);
        return rebaseHistory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RebaseHistory createUpdatedEntity(EntityManager em) {
        RebaseHistory rebaseHistory = new RebaseHistory()
            .oldValue(UPDATED_OLD_VALUE)
            .oldCurrency(UPDATED_OLD_CURRENCY)
            .newValue(UPDATED_NEW_VALUE)
            .newCurrency(UPDATED_NEW_CURRENCY)
            .runDate(UPDATED_RUN_DATE);
        return rebaseHistory;
    }

    @BeforeEach
    public void initTest() {
        rebaseHistory = createEntity(em);
    }

    @Test
    @Transactional
    void createRebaseHistory() throws Exception {
        int databaseSizeBeforeCreate = rebaseHistoryRepository.findAll().size();
        // Create the RebaseHistory
        restRebaseHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rebaseHistory)))
            .andExpect(status().isCreated());

        // Validate the RebaseHistory in the database
        List<RebaseHistory> rebaseHistoryList = rebaseHistoryRepository.findAll();
        assertThat(rebaseHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        RebaseHistory testRebaseHistory = rebaseHistoryList.get(rebaseHistoryList.size() - 1);
        assertThat(testRebaseHistory.getOldValue()).isEqualByComparingTo(DEFAULT_OLD_VALUE);
        assertThat(testRebaseHistory.getOldCurrency()).isEqualTo(DEFAULT_OLD_CURRENCY);
        assertThat(testRebaseHistory.getNewValue()).isEqualByComparingTo(DEFAULT_NEW_VALUE);
        assertThat(testRebaseHistory.getNewCurrency()).isEqualTo(DEFAULT_NEW_CURRENCY);
        assertThat(testRebaseHistory.getRunDate()).isEqualTo(DEFAULT_RUN_DATE);
    }

    @Test
    @Transactional
    void createRebaseHistoryWithExistingId() throws Exception {
        // Create the RebaseHistory with an existing ID
        rebaseHistory.setId(1L);

        int databaseSizeBeforeCreate = rebaseHistoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRebaseHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rebaseHistory)))
            .andExpect(status().isBadRequest());

        // Validate the RebaseHistory in the database
        List<RebaseHistory> rebaseHistoryList = rebaseHistoryRepository.findAll();
        assertThat(rebaseHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRebaseHistories() throws Exception {
        // Initialize the database
        rebaseHistoryRepository.saveAndFlush(rebaseHistory);

        // Get all the rebaseHistoryList
        restRebaseHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rebaseHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].oldValue").value(hasItem(sameNumber(DEFAULT_OLD_VALUE))))
            .andExpect(jsonPath("$.[*].oldCurrency").value(hasItem(DEFAULT_OLD_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].newValue").value(hasItem(sameNumber(DEFAULT_NEW_VALUE))))
            .andExpect(jsonPath("$.[*].newCurrency").value(hasItem(DEFAULT_NEW_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].runDate").value(hasItem(DEFAULT_RUN_DATE.toString())));
    }

    @Test
    @Transactional
    void getRebaseHistory() throws Exception {
        // Initialize the database
        rebaseHistoryRepository.saveAndFlush(rebaseHistory);

        // Get the rebaseHistory
        restRebaseHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, rebaseHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rebaseHistory.getId().intValue()))
            .andExpect(jsonPath("$.oldValue").value(sameNumber(DEFAULT_OLD_VALUE)))
            .andExpect(jsonPath("$.oldCurrency").value(DEFAULT_OLD_CURRENCY.toString()))
            .andExpect(jsonPath("$.newValue").value(sameNumber(DEFAULT_NEW_VALUE)))
            .andExpect(jsonPath("$.newCurrency").value(DEFAULT_NEW_CURRENCY.toString()))
            .andExpect(jsonPath("$.runDate").value(DEFAULT_RUN_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingRebaseHistory() throws Exception {
        // Get the rebaseHistory
        restRebaseHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRebaseHistory() throws Exception {
        // Initialize the database
        rebaseHistoryRepository.saveAndFlush(rebaseHistory);

        int databaseSizeBeforeUpdate = rebaseHistoryRepository.findAll().size();

        // Update the rebaseHistory
        RebaseHistory updatedRebaseHistory = rebaseHistoryRepository.findById(rebaseHistory.getId()).get();
        // Disconnect from session so that the updates on updatedRebaseHistory are not directly saved in db
        em.detach(updatedRebaseHistory);
        updatedRebaseHistory
            .oldValue(UPDATED_OLD_VALUE)
            .oldCurrency(UPDATED_OLD_CURRENCY)
            .newValue(UPDATED_NEW_VALUE)
            .newCurrency(UPDATED_NEW_CURRENCY)
            .runDate(UPDATED_RUN_DATE);

        restRebaseHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRebaseHistory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRebaseHistory))
            )
            .andExpect(status().isOk());

        // Validate the RebaseHistory in the database
        List<RebaseHistory> rebaseHistoryList = rebaseHistoryRepository.findAll();
        assertThat(rebaseHistoryList).hasSize(databaseSizeBeforeUpdate);
        RebaseHistory testRebaseHistory = rebaseHistoryList.get(rebaseHistoryList.size() - 1);
        assertThat(testRebaseHistory.getOldValue()).isEqualTo(UPDATED_OLD_VALUE);
        assertThat(testRebaseHistory.getOldCurrency()).isEqualTo(UPDATED_OLD_CURRENCY);
        assertThat(testRebaseHistory.getNewValue()).isEqualTo(UPDATED_NEW_VALUE);
        assertThat(testRebaseHistory.getNewCurrency()).isEqualTo(UPDATED_NEW_CURRENCY);
        assertThat(testRebaseHistory.getRunDate()).isEqualTo(UPDATED_RUN_DATE);
    }

    @Test
    @Transactional
    void putNonExistingRebaseHistory() throws Exception {
        int databaseSizeBeforeUpdate = rebaseHistoryRepository.findAll().size();
        rebaseHistory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRebaseHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rebaseHistory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rebaseHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the RebaseHistory in the database
        List<RebaseHistory> rebaseHistoryList = rebaseHistoryRepository.findAll();
        assertThat(rebaseHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRebaseHistory() throws Exception {
        int databaseSizeBeforeUpdate = rebaseHistoryRepository.findAll().size();
        rebaseHistory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRebaseHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rebaseHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the RebaseHistory in the database
        List<RebaseHistory> rebaseHistoryList = rebaseHistoryRepository.findAll();
        assertThat(rebaseHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRebaseHistory() throws Exception {
        int databaseSizeBeforeUpdate = rebaseHistoryRepository.findAll().size();
        rebaseHistory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRebaseHistoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rebaseHistory)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RebaseHistory in the database
        List<RebaseHistory> rebaseHistoryList = rebaseHistoryRepository.findAll();
        assertThat(rebaseHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRebaseHistoryWithPatch() throws Exception {
        // Initialize the database
        rebaseHistoryRepository.saveAndFlush(rebaseHistory);

        int databaseSizeBeforeUpdate = rebaseHistoryRepository.findAll().size();

        // Update the rebaseHistory using partial update
        RebaseHistory partialUpdatedRebaseHistory = new RebaseHistory();
        partialUpdatedRebaseHistory.setId(rebaseHistory.getId());

        partialUpdatedRebaseHistory.oldCurrency(UPDATED_OLD_CURRENCY);

        restRebaseHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRebaseHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRebaseHistory))
            )
            .andExpect(status().isOk());

        // Validate the RebaseHistory in the database
        List<RebaseHistory> rebaseHistoryList = rebaseHistoryRepository.findAll();
        assertThat(rebaseHistoryList).hasSize(databaseSizeBeforeUpdate);
        RebaseHistory testRebaseHistory = rebaseHistoryList.get(rebaseHistoryList.size() - 1);
        assertThat(testRebaseHistory.getOldValue()).isEqualByComparingTo(DEFAULT_OLD_VALUE);
        assertThat(testRebaseHistory.getOldCurrency()).isEqualTo(UPDATED_OLD_CURRENCY);
        assertThat(testRebaseHistory.getNewValue()).isEqualByComparingTo(DEFAULT_NEW_VALUE);
        assertThat(testRebaseHistory.getNewCurrency()).isEqualTo(DEFAULT_NEW_CURRENCY);
        assertThat(testRebaseHistory.getRunDate()).isEqualTo(DEFAULT_RUN_DATE);
    }

    @Test
    @Transactional
    void fullUpdateRebaseHistoryWithPatch() throws Exception {
        // Initialize the database
        rebaseHistoryRepository.saveAndFlush(rebaseHistory);

        int databaseSizeBeforeUpdate = rebaseHistoryRepository.findAll().size();

        // Update the rebaseHistory using partial update
        RebaseHistory partialUpdatedRebaseHistory = new RebaseHistory();
        partialUpdatedRebaseHistory.setId(rebaseHistory.getId());

        partialUpdatedRebaseHistory
            .oldValue(UPDATED_OLD_VALUE)
            .oldCurrency(UPDATED_OLD_CURRENCY)
            .newValue(UPDATED_NEW_VALUE)
            .newCurrency(UPDATED_NEW_CURRENCY)
            .runDate(UPDATED_RUN_DATE);

        restRebaseHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRebaseHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRebaseHistory))
            )
            .andExpect(status().isOk());

        // Validate the RebaseHistory in the database
        List<RebaseHistory> rebaseHistoryList = rebaseHistoryRepository.findAll();
        assertThat(rebaseHistoryList).hasSize(databaseSizeBeforeUpdate);
        RebaseHistory testRebaseHistory = rebaseHistoryList.get(rebaseHistoryList.size() - 1);
        assertThat(testRebaseHistory.getOldValue()).isEqualByComparingTo(UPDATED_OLD_VALUE);
        assertThat(testRebaseHistory.getOldCurrency()).isEqualTo(UPDATED_OLD_CURRENCY);
        assertThat(testRebaseHistory.getNewValue()).isEqualByComparingTo(UPDATED_NEW_VALUE);
        assertThat(testRebaseHistory.getNewCurrency()).isEqualTo(UPDATED_NEW_CURRENCY);
        assertThat(testRebaseHistory.getRunDate()).isEqualTo(UPDATED_RUN_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingRebaseHistory() throws Exception {
        int databaseSizeBeforeUpdate = rebaseHistoryRepository.findAll().size();
        rebaseHistory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRebaseHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rebaseHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rebaseHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the RebaseHistory in the database
        List<RebaseHistory> rebaseHistoryList = rebaseHistoryRepository.findAll();
        assertThat(rebaseHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRebaseHistory() throws Exception {
        int databaseSizeBeforeUpdate = rebaseHistoryRepository.findAll().size();
        rebaseHistory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRebaseHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rebaseHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the RebaseHistory in the database
        List<RebaseHistory> rebaseHistoryList = rebaseHistoryRepository.findAll();
        assertThat(rebaseHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRebaseHistory() throws Exception {
        int databaseSizeBeforeUpdate = rebaseHistoryRepository.findAll().size();
        rebaseHistory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRebaseHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(rebaseHistory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RebaseHistory in the database
        List<RebaseHistory> rebaseHistoryList = rebaseHistoryRepository.findAll();
        assertThat(rebaseHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRebaseHistory() throws Exception {
        // Initialize the database
        rebaseHistoryRepository.saveAndFlush(rebaseHistory);

        int databaseSizeBeforeDelete = rebaseHistoryRepository.findAll().size();

        // Delete the rebaseHistory
        restRebaseHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, rebaseHistory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RebaseHistory> rebaseHistoryList = rebaseHistoryRepository.findAll();
        assertThat(rebaseHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
