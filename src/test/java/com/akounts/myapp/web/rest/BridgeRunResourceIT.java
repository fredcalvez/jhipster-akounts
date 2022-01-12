package com.akounts.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.akounts.myapp.IntegrationTest;
import com.akounts.myapp.domain.BridgeRun;
import com.akounts.myapp.domain.enumeration.Status;
import com.akounts.myapp.repository.BridgeRunRepository;
import com.akounts.myapp.service.dto.BridgeRunDTO;
import com.akounts.myapp.service.mapper.BridgeRunMapper;
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
 * Integration tests for the {@link BridgeRunResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BridgeRunResourceIT {

    private static final String DEFAULT_RUN_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_RUN_TYPE = "BBBBBBBBBB";

    private static final Status DEFAULT_RUN_STATUS = Status.ToDo;
    private static final Status UPDATED_RUN_STATUS = Status.Doing;

    private static final Instant DEFAULT_RUN_START = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RUN_START = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_RUN_END = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RUN_END = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/bridge-runs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BridgeRunRepository bridgeRunRepository;

    @Autowired
    private BridgeRunMapper bridgeRunMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBridgeRunMockMvc;

    private BridgeRun bridgeRun;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BridgeRun createEntity(EntityManager em) {
        BridgeRun bridgeRun = new BridgeRun()
            .runType(DEFAULT_RUN_TYPE)
            .runStatus(DEFAULT_RUN_STATUS)
            .runStart(DEFAULT_RUN_START)
            .runEnd(DEFAULT_RUN_END);
        return bridgeRun;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BridgeRun createUpdatedEntity(EntityManager em) {
        BridgeRun bridgeRun = new BridgeRun()
            .runType(UPDATED_RUN_TYPE)
            .runStatus(UPDATED_RUN_STATUS)
            .runStart(UPDATED_RUN_START)
            .runEnd(UPDATED_RUN_END);
        return bridgeRun;
    }

    @BeforeEach
    public void initTest() {
        bridgeRun = createEntity(em);
    }

    @Test
    @Transactional
    void createBridgeRun() throws Exception {
        int databaseSizeBeforeCreate = bridgeRunRepository.findAll().size();
        // Create the BridgeRun
        BridgeRunDTO bridgeRunDTO = bridgeRunMapper.toDto(bridgeRun);
        restBridgeRunMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bridgeRunDTO)))
            .andExpect(status().isCreated());

        // Validate the BridgeRun in the database
        List<BridgeRun> bridgeRunList = bridgeRunRepository.findAll();
        assertThat(bridgeRunList).hasSize(databaseSizeBeforeCreate + 1);
        BridgeRun testBridgeRun = bridgeRunList.get(bridgeRunList.size() - 1);
        assertThat(testBridgeRun.getRunType()).isEqualTo(DEFAULT_RUN_TYPE);
        assertThat(testBridgeRun.getRunStatus()).isEqualTo(DEFAULT_RUN_STATUS);
        assertThat(testBridgeRun.getRunStart()).isEqualTo(DEFAULT_RUN_START);
        assertThat(testBridgeRun.getRunEnd()).isEqualTo(DEFAULT_RUN_END);
    }

    @Test
    @Transactional
    void createBridgeRunWithExistingId() throws Exception {
        // Create the BridgeRun with an existing ID
        bridgeRun.setId(1L);
        BridgeRunDTO bridgeRunDTO = bridgeRunMapper.toDto(bridgeRun);

        int databaseSizeBeforeCreate = bridgeRunRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBridgeRunMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bridgeRunDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BridgeRun in the database
        List<BridgeRun> bridgeRunList = bridgeRunRepository.findAll();
        assertThat(bridgeRunList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBridgeRuns() throws Exception {
        // Initialize the database
        bridgeRunRepository.saveAndFlush(bridgeRun);

        // Get all the bridgeRunList
        restBridgeRunMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bridgeRun.getId().intValue())))
            .andExpect(jsonPath("$.[*].runType").value(hasItem(DEFAULT_RUN_TYPE)))
            .andExpect(jsonPath("$.[*].runStatus").value(hasItem(DEFAULT_RUN_STATUS.toString())))
            .andExpect(jsonPath("$.[*].runStart").value(hasItem(DEFAULT_RUN_START.toString())))
            .andExpect(jsonPath("$.[*].runEnd").value(hasItem(DEFAULT_RUN_END.toString())));
    }

    @Test
    @Transactional
    void getBridgeRun() throws Exception {
        // Initialize the database
        bridgeRunRepository.saveAndFlush(bridgeRun);

        // Get the bridgeRun
        restBridgeRunMockMvc
            .perform(get(ENTITY_API_URL_ID, bridgeRun.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bridgeRun.getId().intValue()))
            .andExpect(jsonPath("$.runType").value(DEFAULT_RUN_TYPE))
            .andExpect(jsonPath("$.runStatus").value(DEFAULT_RUN_STATUS.toString()))
            .andExpect(jsonPath("$.runStart").value(DEFAULT_RUN_START.toString()))
            .andExpect(jsonPath("$.runEnd").value(DEFAULT_RUN_END.toString()));
    }

    @Test
    @Transactional
    void getNonExistingBridgeRun() throws Exception {
        // Get the bridgeRun
        restBridgeRunMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBridgeRun() throws Exception {
        // Initialize the database
        bridgeRunRepository.saveAndFlush(bridgeRun);

        int databaseSizeBeforeUpdate = bridgeRunRepository.findAll().size();

        // Update the bridgeRun
        BridgeRun updatedBridgeRun = bridgeRunRepository.findById(bridgeRun.getId()).get();
        // Disconnect from session so that the updates on updatedBridgeRun are not directly saved in db
        em.detach(updatedBridgeRun);
        updatedBridgeRun.runType(UPDATED_RUN_TYPE).runStatus(UPDATED_RUN_STATUS).runStart(UPDATED_RUN_START).runEnd(UPDATED_RUN_END);
        BridgeRunDTO bridgeRunDTO = bridgeRunMapper.toDto(updatedBridgeRun);

        restBridgeRunMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bridgeRunDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bridgeRunDTO))
            )
            .andExpect(status().isOk());

        // Validate the BridgeRun in the database
        List<BridgeRun> bridgeRunList = bridgeRunRepository.findAll();
        assertThat(bridgeRunList).hasSize(databaseSizeBeforeUpdate);
        BridgeRun testBridgeRun = bridgeRunList.get(bridgeRunList.size() - 1);
        assertThat(testBridgeRun.getRunType()).isEqualTo(UPDATED_RUN_TYPE);
        assertThat(testBridgeRun.getRunStatus()).isEqualTo(UPDATED_RUN_STATUS);
        assertThat(testBridgeRun.getRunStart()).isEqualTo(UPDATED_RUN_START);
        assertThat(testBridgeRun.getRunEnd()).isEqualTo(UPDATED_RUN_END);
    }

    @Test
    @Transactional
    void putNonExistingBridgeRun() throws Exception {
        int databaseSizeBeforeUpdate = bridgeRunRepository.findAll().size();
        bridgeRun.setId(count.incrementAndGet());

        // Create the BridgeRun
        BridgeRunDTO bridgeRunDTO = bridgeRunMapper.toDto(bridgeRun);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBridgeRunMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bridgeRunDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bridgeRunDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BridgeRun in the database
        List<BridgeRun> bridgeRunList = bridgeRunRepository.findAll();
        assertThat(bridgeRunList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBridgeRun() throws Exception {
        int databaseSizeBeforeUpdate = bridgeRunRepository.findAll().size();
        bridgeRun.setId(count.incrementAndGet());

        // Create the BridgeRun
        BridgeRunDTO bridgeRunDTO = bridgeRunMapper.toDto(bridgeRun);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBridgeRunMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bridgeRunDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BridgeRun in the database
        List<BridgeRun> bridgeRunList = bridgeRunRepository.findAll();
        assertThat(bridgeRunList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBridgeRun() throws Exception {
        int databaseSizeBeforeUpdate = bridgeRunRepository.findAll().size();
        bridgeRun.setId(count.incrementAndGet());

        // Create the BridgeRun
        BridgeRunDTO bridgeRunDTO = bridgeRunMapper.toDto(bridgeRun);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBridgeRunMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bridgeRunDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BridgeRun in the database
        List<BridgeRun> bridgeRunList = bridgeRunRepository.findAll();
        assertThat(bridgeRunList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBridgeRunWithPatch() throws Exception {
        // Initialize the database
        bridgeRunRepository.saveAndFlush(bridgeRun);

        int databaseSizeBeforeUpdate = bridgeRunRepository.findAll().size();

        // Update the bridgeRun using partial update
        BridgeRun partialUpdatedBridgeRun = new BridgeRun();
        partialUpdatedBridgeRun.setId(bridgeRun.getId());

        partialUpdatedBridgeRun.runStatus(UPDATED_RUN_STATUS).runStart(UPDATED_RUN_START);

        restBridgeRunMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBridgeRun.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBridgeRun))
            )
            .andExpect(status().isOk());

        // Validate the BridgeRun in the database
        List<BridgeRun> bridgeRunList = bridgeRunRepository.findAll();
        assertThat(bridgeRunList).hasSize(databaseSizeBeforeUpdate);
        BridgeRun testBridgeRun = bridgeRunList.get(bridgeRunList.size() - 1);
        assertThat(testBridgeRun.getRunType()).isEqualTo(DEFAULT_RUN_TYPE);
        assertThat(testBridgeRun.getRunStatus()).isEqualTo(UPDATED_RUN_STATUS);
        assertThat(testBridgeRun.getRunStart()).isEqualTo(UPDATED_RUN_START);
        assertThat(testBridgeRun.getRunEnd()).isEqualTo(DEFAULT_RUN_END);
    }

    @Test
    @Transactional
    void fullUpdateBridgeRunWithPatch() throws Exception {
        // Initialize the database
        bridgeRunRepository.saveAndFlush(bridgeRun);

        int databaseSizeBeforeUpdate = bridgeRunRepository.findAll().size();

        // Update the bridgeRun using partial update
        BridgeRun partialUpdatedBridgeRun = new BridgeRun();
        partialUpdatedBridgeRun.setId(bridgeRun.getId());

        partialUpdatedBridgeRun.runType(UPDATED_RUN_TYPE).runStatus(UPDATED_RUN_STATUS).runStart(UPDATED_RUN_START).runEnd(UPDATED_RUN_END);

        restBridgeRunMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBridgeRun.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBridgeRun))
            )
            .andExpect(status().isOk());

        // Validate the BridgeRun in the database
        List<BridgeRun> bridgeRunList = bridgeRunRepository.findAll();
        assertThat(bridgeRunList).hasSize(databaseSizeBeforeUpdate);
        BridgeRun testBridgeRun = bridgeRunList.get(bridgeRunList.size() - 1);
        assertThat(testBridgeRun.getRunType()).isEqualTo(UPDATED_RUN_TYPE);
        assertThat(testBridgeRun.getRunStatus()).isEqualTo(UPDATED_RUN_STATUS);
        assertThat(testBridgeRun.getRunStart()).isEqualTo(UPDATED_RUN_START);
        assertThat(testBridgeRun.getRunEnd()).isEqualTo(UPDATED_RUN_END);
    }

    @Test
    @Transactional
    void patchNonExistingBridgeRun() throws Exception {
        int databaseSizeBeforeUpdate = bridgeRunRepository.findAll().size();
        bridgeRun.setId(count.incrementAndGet());

        // Create the BridgeRun
        BridgeRunDTO bridgeRunDTO = bridgeRunMapper.toDto(bridgeRun);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBridgeRunMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bridgeRunDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bridgeRunDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BridgeRun in the database
        List<BridgeRun> bridgeRunList = bridgeRunRepository.findAll();
        assertThat(bridgeRunList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBridgeRun() throws Exception {
        int databaseSizeBeforeUpdate = bridgeRunRepository.findAll().size();
        bridgeRun.setId(count.incrementAndGet());

        // Create the BridgeRun
        BridgeRunDTO bridgeRunDTO = bridgeRunMapper.toDto(bridgeRun);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBridgeRunMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bridgeRunDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BridgeRun in the database
        List<BridgeRun> bridgeRunList = bridgeRunRepository.findAll();
        assertThat(bridgeRunList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBridgeRun() throws Exception {
        int databaseSizeBeforeUpdate = bridgeRunRepository.findAll().size();
        bridgeRun.setId(count.incrementAndGet());

        // Create the BridgeRun
        BridgeRunDTO bridgeRunDTO = bridgeRunMapper.toDto(bridgeRun);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBridgeRunMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bridgeRunDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BridgeRun in the database
        List<BridgeRun> bridgeRunList = bridgeRunRepository.findAll();
        assertThat(bridgeRunList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBridgeRun() throws Exception {
        // Initialize the database
        bridgeRunRepository.saveAndFlush(bridgeRun);

        int databaseSizeBeforeDelete = bridgeRunRepository.findAll().size();

        // Delete the bridgeRun
        restBridgeRunMockMvc
            .perform(delete(ENTITY_API_URL_ID, bridgeRun.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BridgeRun> bridgeRunList = bridgeRunRepository.findAll();
        assertThat(bridgeRunList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
