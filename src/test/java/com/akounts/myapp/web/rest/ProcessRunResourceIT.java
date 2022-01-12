package com.akounts.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.akounts.myapp.IntegrationTest;
import com.akounts.myapp.domain.ProcessRun;
import com.akounts.myapp.domain.enumeration.Status;
import com.akounts.myapp.repository.ProcessRunRepository;
import com.akounts.myapp.service.dto.ProcessRunDTO;
import com.akounts.myapp.service.mapper.ProcessRunMapper;
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
 * Integration tests for the {@link ProcessRunResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProcessRunResourceIT {

    private static final String DEFAULT_PROCESS = "AAAAAAAAAA";
    private static final String UPDATED_PROCESS = "BBBBBBBBBB";

    private static final Integer DEFAULT_PARENT_ID = 1;
    private static final Integer UPDATED_PARENT_ID = 2;

    private static final Status DEFAULT_STATUS = Status.ToDo;
    private static final Status UPDATED_STATUS = Status.Doing;

    private static final String DEFAULT_RETURNS = "AAAAAAAAAA";
    private static final String UPDATED_RETURNS = "BBBBBBBBBB";

    private static final String DEFAULT_ERROR = "AAAAAAAAAA";
    private static final String UPDATED_ERROR = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/process-runs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProcessRunRepository processRunRepository;

    @Autowired
    private ProcessRunMapper processRunMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProcessRunMockMvc;

    private ProcessRun processRun;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProcessRun createEntity(EntityManager em) {
        ProcessRun processRun = new ProcessRun()
            .process(DEFAULT_PROCESS)
            .parentId(DEFAULT_PARENT_ID)
            .status(DEFAULT_STATUS)
            .returns(DEFAULT_RETURNS)
            .error(DEFAULT_ERROR)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME);
        return processRun;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProcessRun createUpdatedEntity(EntityManager em) {
        ProcessRun processRun = new ProcessRun()
            .process(UPDATED_PROCESS)
            .parentId(UPDATED_PARENT_ID)
            .status(UPDATED_STATUS)
            .returns(UPDATED_RETURNS)
            .error(UPDATED_ERROR)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        return processRun;
    }

    @BeforeEach
    public void initTest() {
        processRun = createEntity(em);
    }

    @Test
    @Transactional
    void createProcessRun() throws Exception {
        int databaseSizeBeforeCreate = processRunRepository.findAll().size();
        // Create the ProcessRun
        ProcessRunDTO processRunDTO = processRunMapper.toDto(processRun);
        restProcessRunMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(processRunDTO)))
            .andExpect(status().isCreated());

        // Validate the ProcessRun in the database
        List<ProcessRun> processRunList = processRunRepository.findAll();
        assertThat(processRunList).hasSize(databaseSizeBeforeCreate + 1);
        ProcessRun testProcessRun = processRunList.get(processRunList.size() - 1);
        assertThat(testProcessRun.getProcess()).isEqualTo(DEFAULT_PROCESS);
        assertThat(testProcessRun.getParentId()).isEqualTo(DEFAULT_PARENT_ID);
        assertThat(testProcessRun.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProcessRun.getReturns()).isEqualTo(DEFAULT_RETURNS);
        assertThat(testProcessRun.getError()).isEqualTo(DEFAULT_ERROR);
        assertThat(testProcessRun.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testProcessRun.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createProcessRunWithExistingId() throws Exception {
        // Create the ProcessRun with an existing ID
        processRun.setId(1L);
        ProcessRunDTO processRunDTO = processRunMapper.toDto(processRun);

        int databaseSizeBeforeCreate = processRunRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcessRunMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(processRunDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProcessRun in the database
        List<ProcessRun> processRunList = processRunRepository.findAll();
        assertThat(processRunList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProcessRuns() throws Exception {
        // Initialize the database
        processRunRepository.saveAndFlush(processRun);

        // Get all the processRunList
        restProcessRunMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(processRun.getId().intValue())))
            .andExpect(jsonPath("$.[*].process").value(hasItem(DEFAULT_PROCESS)))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].returns").value(hasItem(DEFAULT_RETURNS)))
            .andExpect(jsonPath("$.[*].error").value(hasItem(DEFAULT_ERROR)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }

    @Test
    @Transactional
    void getProcessRun() throws Exception {
        // Initialize the database
        processRunRepository.saveAndFlush(processRun);

        // Get the processRun
        restProcessRunMockMvc
            .perform(get(ENTITY_API_URL_ID, processRun.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(processRun.getId().intValue()))
            .andExpect(jsonPath("$.process").value(DEFAULT_PROCESS))
            .andExpect(jsonPath("$.parentId").value(DEFAULT_PARENT_ID))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.returns").value(DEFAULT_RETURNS))
            .andExpect(jsonPath("$.error").value(DEFAULT_ERROR))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProcessRun() throws Exception {
        // Get the processRun
        restProcessRunMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProcessRun() throws Exception {
        // Initialize the database
        processRunRepository.saveAndFlush(processRun);

        int databaseSizeBeforeUpdate = processRunRepository.findAll().size();

        // Update the processRun
        ProcessRun updatedProcessRun = processRunRepository.findById(processRun.getId()).get();
        // Disconnect from session so that the updates on updatedProcessRun are not directly saved in db
        em.detach(updatedProcessRun);
        updatedProcessRun
            .process(UPDATED_PROCESS)
            .parentId(UPDATED_PARENT_ID)
            .status(UPDATED_STATUS)
            .returns(UPDATED_RETURNS)
            .error(UPDATED_ERROR)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        ProcessRunDTO processRunDTO = processRunMapper.toDto(updatedProcessRun);

        restProcessRunMockMvc
            .perform(
                put(ENTITY_API_URL_ID, processRunDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(processRunDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProcessRun in the database
        List<ProcessRun> processRunList = processRunRepository.findAll();
        assertThat(processRunList).hasSize(databaseSizeBeforeUpdate);
        ProcessRun testProcessRun = processRunList.get(processRunList.size() - 1);
        assertThat(testProcessRun.getProcess()).isEqualTo(UPDATED_PROCESS);
        assertThat(testProcessRun.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testProcessRun.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProcessRun.getReturns()).isEqualTo(UPDATED_RETURNS);
        assertThat(testProcessRun.getError()).isEqualTo(UPDATED_ERROR);
        assertThat(testProcessRun.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testProcessRun.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingProcessRun() throws Exception {
        int databaseSizeBeforeUpdate = processRunRepository.findAll().size();
        processRun.setId(count.incrementAndGet());

        // Create the ProcessRun
        ProcessRunDTO processRunDTO = processRunMapper.toDto(processRun);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcessRunMockMvc
            .perform(
                put(ENTITY_API_URL_ID, processRunDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(processRunDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProcessRun in the database
        List<ProcessRun> processRunList = processRunRepository.findAll();
        assertThat(processRunList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProcessRun() throws Exception {
        int databaseSizeBeforeUpdate = processRunRepository.findAll().size();
        processRun.setId(count.incrementAndGet());

        // Create the ProcessRun
        ProcessRunDTO processRunDTO = processRunMapper.toDto(processRun);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcessRunMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(processRunDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProcessRun in the database
        List<ProcessRun> processRunList = processRunRepository.findAll();
        assertThat(processRunList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProcessRun() throws Exception {
        int databaseSizeBeforeUpdate = processRunRepository.findAll().size();
        processRun.setId(count.incrementAndGet());

        // Create the ProcessRun
        ProcessRunDTO processRunDTO = processRunMapper.toDto(processRun);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcessRunMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(processRunDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProcessRun in the database
        List<ProcessRun> processRunList = processRunRepository.findAll();
        assertThat(processRunList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProcessRunWithPatch() throws Exception {
        // Initialize the database
        processRunRepository.saveAndFlush(processRun);

        int databaseSizeBeforeUpdate = processRunRepository.findAll().size();

        // Update the processRun using partial update
        ProcessRun partialUpdatedProcessRun = new ProcessRun();
        partialUpdatedProcessRun.setId(processRun.getId());

        partialUpdatedProcessRun
            .process(UPDATED_PROCESS)
            .parentId(UPDATED_PARENT_ID)
            .status(UPDATED_STATUS)
            .error(UPDATED_ERROR)
            .endTime(UPDATED_END_TIME);

        restProcessRunMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProcessRun.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProcessRun))
            )
            .andExpect(status().isOk());

        // Validate the ProcessRun in the database
        List<ProcessRun> processRunList = processRunRepository.findAll();
        assertThat(processRunList).hasSize(databaseSizeBeforeUpdate);
        ProcessRun testProcessRun = processRunList.get(processRunList.size() - 1);
        assertThat(testProcessRun.getProcess()).isEqualTo(UPDATED_PROCESS);
        assertThat(testProcessRun.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testProcessRun.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProcessRun.getReturns()).isEqualTo(DEFAULT_RETURNS);
        assertThat(testProcessRun.getError()).isEqualTo(UPDATED_ERROR);
        assertThat(testProcessRun.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testProcessRun.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdateProcessRunWithPatch() throws Exception {
        // Initialize the database
        processRunRepository.saveAndFlush(processRun);

        int databaseSizeBeforeUpdate = processRunRepository.findAll().size();

        // Update the processRun using partial update
        ProcessRun partialUpdatedProcessRun = new ProcessRun();
        partialUpdatedProcessRun.setId(processRun.getId());

        partialUpdatedProcessRun
            .process(UPDATED_PROCESS)
            .parentId(UPDATED_PARENT_ID)
            .status(UPDATED_STATUS)
            .returns(UPDATED_RETURNS)
            .error(UPDATED_ERROR)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restProcessRunMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProcessRun.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProcessRun))
            )
            .andExpect(status().isOk());

        // Validate the ProcessRun in the database
        List<ProcessRun> processRunList = processRunRepository.findAll();
        assertThat(processRunList).hasSize(databaseSizeBeforeUpdate);
        ProcessRun testProcessRun = processRunList.get(processRunList.size() - 1);
        assertThat(testProcessRun.getProcess()).isEqualTo(UPDATED_PROCESS);
        assertThat(testProcessRun.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testProcessRun.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProcessRun.getReturns()).isEqualTo(UPDATED_RETURNS);
        assertThat(testProcessRun.getError()).isEqualTo(UPDATED_ERROR);
        assertThat(testProcessRun.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testProcessRun.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingProcessRun() throws Exception {
        int databaseSizeBeforeUpdate = processRunRepository.findAll().size();
        processRun.setId(count.incrementAndGet());

        // Create the ProcessRun
        ProcessRunDTO processRunDTO = processRunMapper.toDto(processRun);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcessRunMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, processRunDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(processRunDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProcessRun in the database
        List<ProcessRun> processRunList = processRunRepository.findAll();
        assertThat(processRunList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProcessRun() throws Exception {
        int databaseSizeBeforeUpdate = processRunRepository.findAll().size();
        processRun.setId(count.incrementAndGet());

        // Create the ProcessRun
        ProcessRunDTO processRunDTO = processRunMapper.toDto(processRun);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcessRunMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(processRunDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProcessRun in the database
        List<ProcessRun> processRunList = processRunRepository.findAll();
        assertThat(processRunList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProcessRun() throws Exception {
        int databaseSizeBeforeUpdate = processRunRepository.findAll().size();
        processRun.setId(count.incrementAndGet());

        // Create the ProcessRun
        ProcessRunDTO processRunDTO = processRunMapper.toDto(processRun);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcessRunMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(processRunDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProcessRun in the database
        List<ProcessRun> processRunList = processRunRepository.findAll();
        assertThat(processRunList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProcessRun() throws Exception {
        // Initialize the database
        processRunRepository.saveAndFlush(processRun);

        int databaseSizeBeforeDelete = processRunRepository.findAll().size();

        // Delete the processRun
        restProcessRunMockMvc
            .perform(delete(ENTITY_API_URL_ID, processRun.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProcessRun> processRunList = processRunRepository.findAll();
        assertThat(processRunList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
