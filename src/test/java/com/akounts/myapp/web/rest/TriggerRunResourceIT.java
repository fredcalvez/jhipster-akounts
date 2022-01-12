package com.akounts.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.akounts.myapp.IntegrationTest;
import com.akounts.myapp.domain.TriggerRun;
import com.akounts.myapp.repository.TriggerRunRepository;
import com.akounts.myapp.service.dto.TriggerRunDTO;
import com.akounts.myapp.service.mapper.TriggerRunMapper;
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
 * Integration tests for the {@link TriggerRunResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TriggerRunResourceIT {

    private static final Integer DEFAULT_RUN_TYPE = 1;
    private static final Integer UPDATED_RUN_TYPE = 2;

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final Instant DEFAULT_ADD_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ADD_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/trigger-runs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TriggerRunRepository triggerRunRepository;

    @Autowired
    private TriggerRunMapper triggerRunMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTriggerRunMockMvc;

    private TriggerRun triggerRun;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TriggerRun createEntity(EntityManager em) {
        TriggerRun triggerRun = new TriggerRun()
            .runType(DEFAULT_RUN_TYPE)
            .status(DEFAULT_STATUS)
            .addDate(DEFAULT_ADD_DATE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return triggerRun;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TriggerRun createUpdatedEntity(EntityManager em) {
        TriggerRun triggerRun = new TriggerRun()
            .runType(UPDATED_RUN_TYPE)
            .status(UPDATED_STATUS)
            .addDate(UPDATED_ADD_DATE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        return triggerRun;
    }

    @BeforeEach
    public void initTest() {
        triggerRun = createEntity(em);
    }

    @Test
    @Transactional
    void createTriggerRun() throws Exception {
        int databaseSizeBeforeCreate = triggerRunRepository.findAll().size();
        // Create the TriggerRun
        TriggerRunDTO triggerRunDTO = triggerRunMapper.toDto(triggerRun);
        restTriggerRunMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(triggerRunDTO)))
            .andExpect(status().isCreated());

        // Validate the TriggerRun in the database
        List<TriggerRun> triggerRunList = triggerRunRepository.findAll();
        assertThat(triggerRunList).hasSize(databaseSizeBeforeCreate + 1);
        TriggerRun testTriggerRun = triggerRunList.get(triggerRunList.size() - 1);
        assertThat(testTriggerRun.getRunType()).isEqualTo(DEFAULT_RUN_TYPE);
        assertThat(testTriggerRun.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTriggerRun.getAddDate()).isEqualTo(DEFAULT_ADD_DATE);
        assertThat(testTriggerRun.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testTriggerRun.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    void createTriggerRunWithExistingId() throws Exception {
        // Create the TriggerRun with an existing ID
        triggerRun.setId(1L);
        TriggerRunDTO triggerRunDTO = triggerRunMapper.toDto(triggerRun);

        int databaseSizeBeforeCreate = triggerRunRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTriggerRunMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(triggerRunDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TriggerRun in the database
        List<TriggerRun> triggerRunList = triggerRunRepository.findAll();
        assertThat(triggerRunList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTriggerRuns() throws Exception {
        // Initialize the database
        triggerRunRepository.saveAndFlush(triggerRun);

        // Get all the triggerRunList
        restTriggerRunMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(triggerRun.getId().intValue())))
            .andExpect(jsonPath("$.[*].runType").value(hasItem(DEFAULT_RUN_TYPE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].addDate").value(hasItem(DEFAULT_ADD_DATE.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }

    @Test
    @Transactional
    void getTriggerRun() throws Exception {
        // Initialize the database
        triggerRunRepository.saveAndFlush(triggerRun);

        // Get the triggerRun
        restTriggerRunMockMvc
            .perform(get(ENTITY_API_URL_ID, triggerRun.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(triggerRun.getId().intValue()))
            .andExpect(jsonPath("$.runType").value(DEFAULT_RUN_TYPE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.addDate").value(DEFAULT_ADD_DATE.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTriggerRun() throws Exception {
        // Get the triggerRun
        restTriggerRunMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTriggerRun() throws Exception {
        // Initialize the database
        triggerRunRepository.saveAndFlush(triggerRun);

        int databaseSizeBeforeUpdate = triggerRunRepository.findAll().size();

        // Update the triggerRun
        TriggerRun updatedTriggerRun = triggerRunRepository.findById(triggerRun.getId()).get();
        // Disconnect from session so that the updates on updatedTriggerRun are not directly saved in db
        em.detach(updatedTriggerRun);
        updatedTriggerRun
            .runType(UPDATED_RUN_TYPE)
            .status(UPDATED_STATUS)
            .addDate(UPDATED_ADD_DATE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        TriggerRunDTO triggerRunDTO = triggerRunMapper.toDto(updatedTriggerRun);

        restTriggerRunMockMvc
            .perform(
                put(ENTITY_API_URL_ID, triggerRunDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(triggerRunDTO))
            )
            .andExpect(status().isOk());

        // Validate the TriggerRun in the database
        List<TriggerRun> triggerRunList = triggerRunRepository.findAll();
        assertThat(triggerRunList).hasSize(databaseSizeBeforeUpdate);
        TriggerRun testTriggerRun = triggerRunList.get(triggerRunList.size() - 1);
        assertThat(testTriggerRun.getRunType()).isEqualTo(UPDATED_RUN_TYPE);
        assertThat(testTriggerRun.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTriggerRun.getAddDate()).isEqualTo(UPDATED_ADD_DATE);
        assertThat(testTriggerRun.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTriggerRun.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void putNonExistingTriggerRun() throws Exception {
        int databaseSizeBeforeUpdate = triggerRunRepository.findAll().size();
        triggerRun.setId(count.incrementAndGet());

        // Create the TriggerRun
        TriggerRunDTO triggerRunDTO = triggerRunMapper.toDto(triggerRun);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTriggerRunMockMvc
            .perform(
                put(ENTITY_API_URL_ID, triggerRunDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(triggerRunDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TriggerRun in the database
        List<TriggerRun> triggerRunList = triggerRunRepository.findAll();
        assertThat(triggerRunList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTriggerRun() throws Exception {
        int databaseSizeBeforeUpdate = triggerRunRepository.findAll().size();
        triggerRun.setId(count.incrementAndGet());

        // Create the TriggerRun
        TriggerRunDTO triggerRunDTO = triggerRunMapper.toDto(triggerRun);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTriggerRunMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(triggerRunDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TriggerRun in the database
        List<TriggerRun> triggerRunList = triggerRunRepository.findAll();
        assertThat(triggerRunList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTriggerRun() throws Exception {
        int databaseSizeBeforeUpdate = triggerRunRepository.findAll().size();
        triggerRun.setId(count.incrementAndGet());

        // Create the TriggerRun
        TriggerRunDTO triggerRunDTO = triggerRunMapper.toDto(triggerRun);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTriggerRunMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(triggerRunDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TriggerRun in the database
        List<TriggerRun> triggerRunList = triggerRunRepository.findAll();
        assertThat(triggerRunList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTriggerRunWithPatch() throws Exception {
        // Initialize the database
        triggerRunRepository.saveAndFlush(triggerRun);

        int databaseSizeBeforeUpdate = triggerRunRepository.findAll().size();

        // Update the triggerRun using partial update
        TriggerRun partialUpdatedTriggerRun = new TriggerRun();
        partialUpdatedTriggerRun.setId(triggerRun.getId());

        partialUpdatedTriggerRun.runType(UPDATED_RUN_TYPE).addDate(UPDATED_ADD_DATE);

        restTriggerRunMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTriggerRun.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTriggerRun))
            )
            .andExpect(status().isOk());

        // Validate the TriggerRun in the database
        List<TriggerRun> triggerRunList = triggerRunRepository.findAll();
        assertThat(triggerRunList).hasSize(databaseSizeBeforeUpdate);
        TriggerRun testTriggerRun = triggerRunList.get(triggerRunList.size() - 1);
        assertThat(testTriggerRun.getRunType()).isEqualTo(UPDATED_RUN_TYPE);
        assertThat(testTriggerRun.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTriggerRun.getAddDate()).isEqualTo(UPDATED_ADD_DATE);
        assertThat(testTriggerRun.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testTriggerRun.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    void fullUpdateTriggerRunWithPatch() throws Exception {
        // Initialize the database
        triggerRunRepository.saveAndFlush(triggerRun);

        int databaseSizeBeforeUpdate = triggerRunRepository.findAll().size();

        // Update the triggerRun using partial update
        TriggerRun partialUpdatedTriggerRun = new TriggerRun();
        partialUpdatedTriggerRun.setId(triggerRun.getId());

        partialUpdatedTriggerRun
            .runType(UPDATED_RUN_TYPE)
            .status(UPDATED_STATUS)
            .addDate(UPDATED_ADD_DATE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);

        restTriggerRunMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTriggerRun.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTriggerRun))
            )
            .andExpect(status().isOk());

        // Validate the TriggerRun in the database
        List<TriggerRun> triggerRunList = triggerRunRepository.findAll();
        assertThat(triggerRunList).hasSize(databaseSizeBeforeUpdate);
        TriggerRun testTriggerRun = triggerRunList.get(triggerRunList.size() - 1);
        assertThat(testTriggerRun.getRunType()).isEqualTo(UPDATED_RUN_TYPE);
        assertThat(testTriggerRun.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTriggerRun.getAddDate()).isEqualTo(UPDATED_ADD_DATE);
        assertThat(testTriggerRun.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTriggerRun.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingTriggerRun() throws Exception {
        int databaseSizeBeforeUpdate = triggerRunRepository.findAll().size();
        triggerRun.setId(count.incrementAndGet());

        // Create the TriggerRun
        TriggerRunDTO triggerRunDTO = triggerRunMapper.toDto(triggerRun);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTriggerRunMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, triggerRunDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(triggerRunDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TriggerRun in the database
        List<TriggerRun> triggerRunList = triggerRunRepository.findAll();
        assertThat(triggerRunList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTriggerRun() throws Exception {
        int databaseSizeBeforeUpdate = triggerRunRepository.findAll().size();
        triggerRun.setId(count.incrementAndGet());

        // Create the TriggerRun
        TriggerRunDTO triggerRunDTO = triggerRunMapper.toDto(triggerRun);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTriggerRunMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(triggerRunDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TriggerRun in the database
        List<TriggerRun> triggerRunList = triggerRunRepository.findAll();
        assertThat(triggerRunList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTriggerRun() throws Exception {
        int databaseSizeBeforeUpdate = triggerRunRepository.findAll().size();
        triggerRun.setId(count.incrementAndGet());

        // Create the TriggerRun
        TriggerRunDTO triggerRunDTO = triggerRunMapper.toDto(triggerRun);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTriggerRunMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(triggerRunDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TriggerRun in the database
        List<TriggerRun> triggerRunList = triggerRunRepository.findAll();
        assertThat(triggerRunList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTriggerRun() throws Exception {
        // Initialize the database
        triggerRunRepository.saveAndFlush(triggerRun);

        int databaseSizeBeforeDelete = triggerRunRepository.findAll().size();

        // Delete the triggerRun
        restTriggerRunMockMvc
            .perform(delete(ENTITY_API_URL_ID, triggerRun.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TriggerRun> triggerRunList = triggerRunRepository.findAll();
        assertThat(triggerRunList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
