package com.akounts.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.akounts.myapp.IntegrationTest;
import com.akounts.myapp.domain.PlaidRun;
import com.akounts.myapp.repository.PlaidRunRepository;
import com.akounts.myapp.service.dto.PlaidRunDTO;
import com.akounts.myapp.service.mapper.PlaidRunMapper;
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
 * Integration tests for the {@link PlaidRunResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlaidRunResourceIT {

    private static final String DEFAULT_RUN_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_RUN_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_RUN_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_RUN_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_RUN_ITEM_ID = "AAAAAAAAAA";
    private static final String UPDATED_RUN_ITEM_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_RUN_START = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RUN_START = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_RUN_END = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RUN_END = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/plaid-runs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PlaidRunRepository plaidRunRepository;

    @Autowired
    private PlaidRunMapper plaidRunMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlaidRunMockMvc;

    private PlaidRun plaidRun;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlaidRun createEntity(EntityManager em) {
        PlaidRun plaidRun = new PlaidRun()
            .runType(DEFAULT_RUN_TYPE)
            .runStatus(DEFAULT_RUN_STATUS)
            .runItemId(DEFAULT_RUN_ITEM_ID)
            .runStart(DEFAULT_RUN_START)
            .runEnd(DEFAULT_RUN_END);
        return plaidRun;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlaidRun createUpdatedEntity(EntityManager em) {
        PlaidRun plaidRun = new PlaidRun()
            .runType(UPDATED_RUN_TYPE)
            .runStatus(UPDATED_RUN_STATUS)
            .runItemId(UPDATED_RUN_ITEM_ID)
            .runStart(UPDATED_RUN_START)
            .runEnd(UPDATED_RUN_END);
        return plaidRun;
    }

    @BeforeEach
    public void initTest() {
        plaidRun = createEntity(em);
    }

    @Test
    @Transactional
    void createPlaidRun() throws Exception {
        int databaseSizeBeforeCreate = plaidRunRepository.findAll().size();
        // Create the PlaidRun
        PlaidRunDTO plaidRunDTO = plaidRunMapper.toDto(plaidRun);
        restPlaidRunMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plaidRunDTO)))
            .andExpect(status().isCreated());

        // Validate the PlaidRun in the database
        List<PlaidRun> plaidRunList = plaidRunRepository.findAll();
        assertThat(plaidRunList).hasSize(databaseSizeBeforeCreate + 1);
        PlaidRun testPlaidRun = plaidRunList.get(plaidRunList.size() - 1);
        assertThat(testPlaidRun.getRunType()).isEqualTo(DEFAULT_RUN_TYPE);
        assertThat(testPlaidRun.getRunStatus()).isEqualTo(DEFAULT_RUN_STATUS);
        assertThat(testPlaidRun.getRunItemId()).isEqualTo(DEFAULT_RUN_ITEM_ID);
        assertThat(testPlaidRun.getRunStart()).isEqualTo(DEFAULT_RUN_START);
        assertThat(testPlaidRun.getRunEnd()).isEqualTo(DEFAULT_RUN_END);
    }

    @Test
    @Transactional
    void createPlaidRunWithExistingId() throws Exception {
        // Create the PlaidRun with an existing ID
        plaidRun.setId(1L);
        PlaidRunDTO plaidRunDTO = plaidRunMapper.toDto(plaidRun);

        int databaseSizeBeforeCreate = plaidRunRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlaidRunMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plaidRunDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlaidRun in the database
        List<PlaidRun> plaidRunList = plaidRunRepository.findAll();
        assertThat(plaidRunList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPlaidRuns() throws Exception {
        // Initialize the database
        plaidRunRepository.saveAndFlush(plaidRun);

        // Get all the plaidRunList
        restPlaidRunMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plaidRun.getId().intValue())))
            .andExpect(jsonPath("$.[*].runType").value(hasItem(DEFAULT_RUN_TYPE)))
            .andExpect(jsonPath("$.[*].runStatus").value(hasItem(DEFAULT_RUN_STATUS)))
            .andExpect(jsonPath("$.[*].runItemId").value(hasItem(DEFAULT_RUN_ITEM_ID)))
            .andExpect(jsonPath("$.[*].runStart").value(hasItem(DEFAULT_RUN_START.toString())))
            .andExpect(jsonPath("$.[*].runEnd").value(hasItem(DEFAULT_RUN_END.toString())));
    }

    @Test
    @Transactional
    void getPlaidRun() throws Exception {
        // Initialize the database
        plaidRunRepository.saveAndFlush(plaidRun);

        // Get the plaidRun
        restPlaidRunMockMvc
            .perform(get(ENTITY_API_URL_ID, plaidRun.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(plaidRun.getId().intValue()))
            .andExpect(jsonPath("$.runType").value(DEFAULT_RUN_TYPE))
            .andExpect(jsonPath("$.runStatus").value(DEFAULT_RUN_STATUS))
            .andExpect(jsonPath("$.runItemId").value(DEFAULT_RUN_ITEM_ID))
            .andExpect(jsonPath("$.runStart").value(DEFAULT_RUN_START.toString()))
            .andExpect(jsonPath("$.runEnd").value(DEFAULT_RUN_END.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPlaidRun() throws Exception {
        // Get the plaidRun
        restPlaidRunMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPlaidRun() throws Exception {
        // Initialize the database
        plaidRunRepository.saveAndFlush(plaidRun);

        int databaseSizeBeforeUpdate = plaidRunRepository.findAll().size();

        // Update the plaidRun
        PlaidRun updatedPlaidRun = plaidRunRepository.findById(plaidRun.getId()).get();
        // Disconnect from session so that the updates on updatedPlaidRun are not directly saved in db
        em.detach(updatedPlaidRun);
        updatedPlaidRun
            .runType(UPDATED_RUN_TYPE)
            .runStatus(UPDATED_RUN_STATUS)
            .runItemId(UPDATED_RUN_ITEM_ID)
            .runStart(UPDATED_RUN_START)
            .runEnd(UPDATED_RUN_END);
        PlaidRunDTO plaidRunDTO = plaidRunMapper.toDto(updatedPlaidRun);

        restPlaidRunMockMvc
            .perform(
                put(ENTITY_API_URL_ID, plaidRunDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plaidRunDTO))
            )
            .andExpect(status().isOk());

        // Validate the PlaidRun in the database
        List<PlaidRun> plaidRunList = plaidRunRepository.findAll();
        assertThat(plaidRunList).hasSize(databaseSizeBeforeUpdate);
        PlaidRun testPlaidRun = plaidRunList.get(plaidRunList.size() - 1);
        assertThat(testPlaidRun.getRunType()).isEqualTo(UPDATED_RUN_TYPE);
        assertThat(testPlaidRun.getRunStatus()).isEqualTo(UPDATED_RUN_STATUS);
        assertThat(testPlaidRun.getRunItemId()).isEqualTo(UPDATED_RUN_ITEM_ID);
        assertThat(testPlaidRun.getRunStart()).isEqualTo(UPDATED_RUN_START);
        assertThat(testPlaidRun.getRunEnd()).isEqualTo(UPDATED_RUN_END);
    }

    @Test
    @Transactional
    void putNonExistingPlaidRun() throws Exception {
        int databaseSizeBeforeUpdate = plaidRunRepository.findAll().size();
        plaidRun.setId(count.incrementAndGet());

        // Create the PlaidRun
        PlaidRunDTO plaidRunDTO = plaidRunMapper.toDto(plaidRun);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlaidRunMockMvc
            .perform(
                put(ENTITY_API_URL_ID, plaidRunDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plaidRunDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlaidRun in the database
        List<PlaidRun> plaidRunList = plaidRunRepository.findAll();
        assertThat(plaidRunList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlaidRun() throws Exception {
        int databaseSizeBeforeUpdate = plaidRunRepository.findAll().size();
        plaidRun.setId(count.incrementAndGet());

        // Create the PlaidRun
        PlaidRunDTO plaidRunDTO = plaidRunMapper.toDto(plaidRun);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaidRunMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plaidRunDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlaidRun in the database
        List<PlaidRun> plaidRunList = plaidRunRepository.findAll();
        assertThat(plaidRunList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlaidRun() throws Exception {
        int databaseSizeBeforeUpdate = plaidRunRepository.findAll().size();
        plaidRun.setId(count.incrementAndGet());

        // Create the PlaidRun
        PlaidRunDTO plaidRunDTO = plaidRunMapper.toDto(plaidRun);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaidRunMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plaidRunDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlaidRun in the database
        List<PlaidRun> plaidRunList = plaidRunRepository.findAll();
        assertThat(plaidRunList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlaidRunWithPatch() throws Exception {
        // Initialize the database
        plaidRunRepository.saveAndFlush(plaidRun);

        int databaseSizeBeforeUpdate = plaidRunRepository.findAll().size();

        // Update the plaidRun using partial update
        PlaidRun partialUpdatedPlaidRun = new PlaidRun();
        partialUpdatedPlaidRun.setId(plaidRun.getId());

        restPlaidRunMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlaidRun.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlaidRun))
            )
            .andExpect(status().isOk());

        // Validate the PlaidRun in the database
        List<PlaidRun> plaidRunList = plaidRunRepository.findAll();
        assertThat(plaidRunList).hasSize(databaseSizeBeforeUpdate);
        PlaidRun testPlaidRun = plaidRunList.get(plaidRunList.size() - 1);
        assertThat(testPlaidRun.getRunType()).isEqualTo(DEFAULT_RUN_TYPE);
        assertThat(testPlaidRun.getRunStatus()).isEqualTo(DEFAULT_RUN_STATUS);
        assertThat(testPlaidRun.getRunItemId()).isEqualTo(DEFAULT_RUN_ITEM_ID);
        assertThat(testPlaidRun.getRunStart()).isEqualTo(DEFAULT_RUN_START);
        assertThat(testPlaidRun.getRunEnd()).isEqualTo(DEFAULT_RUN_END);
    }

    @Test
    @Transactional
    void fullUpdatePlaidRunWithPatch() throws Exception {
        // Initialize the database
        plaidRunRepository.saveAndFlush(plaidRun);

        int databaseSizeBeforeUpdate = plaidRunRepository.findAll().size();

        // Update the plaidRun using partial update
        PlaidRun partialUpdatedPlaidRun = new PlaidRun();
        partialUpdatedPlaidRun.setId(plaidRun.getId());

        partialUpdatedPlaidRun
            .runType(UPDATED_RUN_TYPE)
            .runStatus(UPDATED_RUN_STATUS)
            .runItemId(UPDATED_RUN_ITEM_ID)
            .runStart(UPDATED_RUN_START)
            .runEnd(UPDATED_RUN_END);

        restPlaidRunMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlaidRun.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlaidRun))
            )
            .andExpect(status().isOk());

        // Validate the PlaidRun in the database
        List<PlaidRun> plaidRunList = plaidRunRepository.findAll();
        assertThat(plaidRunList).hasSize(databaseSizeBeforeUpdate);
        PlaidRun testPlaidRun = plaidRunList.get(plaidRunList.size() - 1);
        assertThat(testPlaidRun.getRunType()).isEqualTo(UPDATED_RUN_TYPE);
        assertThat(testPlaidRun.getRunStatus()).isEqualTo(UPDATED_RUN_STATUS);
        assertThat(testPlaidRun.getRunItemId()).isEqualTo(UPDATED_RUN_ITEM_ID);
        assertThat(testPlaidRun.getRunStart()).isEqualTo(UPDATED_RUN_START);
        assertThat(testPlaidRun.getRunEnd()).isEqualTo(UPDATED_RUN_END);
    }

    @Test
    @Transactional
    void patchNonExistingPlaidRun() throws Exception {
        int databaseSizeBeforeUpdate = plaidRunRepository.findAll().size();
        plaidRun.setId(count.incrementAndGet());

        // Create the PlaidRun
        PlaidRunDTO plaidRunDTO = plaidRunMapper.toDto(plaidRun);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlaidRunMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, plaidRunDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plaidRunDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlaidRun in the database
        List<PlaidRun> plaidRunList = plaidRunRepository.findAll();
        assertThat(plaidRunList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlaidRun() throws Exception {
        int databaseSizeBeforeUpdate = plaidRunRepository.findAll().size();
        plaidRun.setId(count.incrementAndGet());

        // Create the PlaidRun
        PlaidRunDTO plaidRunDTO = plaidRunMapper.toDto(plaidRun);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaidRunMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plaidRunDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlaidRun in the database
        List<PlaidRun> plaidRunList = plaidRunRepository.findAll();
        assertThat(plaidRunList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlaidRun() throws Exception {
        int databaseSizeBeforeUpdate = plaidRunRepository.findAll().size();
        plaidRun.setId(count.incrementAndGet());

        // Create the PlaidRun
        PlaidRunDTO plaidRunDTO = plaidRunMapper.toDto(plaidRun);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaidRunMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(plaidRunDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlaidRun in the database
        List<PlaidRun> plaidRunList = plaidRunRepository.findAll();
        assertThat(plaidRunList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlaidRun() throws Exception {
        // Initialize the database
        plaidRunRepository.saveAndFlush(plaidRun);

        int databaseSizeBeforeDelete = plaidRunRepository.findAll().size();

        // Delete the plaidRun
        restPlaidRunMockMvc
            .perform(delete(ENTITY_API_URL_ID, plaidRun.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlaidRun> plaidRunList = plaidRunRepository.findAll();
        assertThat(plaidRunList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
