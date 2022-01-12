package com.akounts.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.akounts.myapp.IntegrationTest;
import com.akounts.myapp.domain.TransactionDuplicates;
import com.akounts.myapp.repository.TransactionDuplicatesRepository;
import com.akounts.myapp.service.dto.TransactionDuplicatesDTO;
import com.akounts.myapp.service.mapper.TransactionDuplicatesMapper;
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
 * Integration tests for the {@link TransactionDuplicatesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TransactionDuplicatesResourceIT {

    private static final Boolean DEFAULT_ISDUPLICATE = false;
    private static final Boolean UPDATED_ISDUPLICATE = true;

    private static final Instant DEFAULT_DATE_ADD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_ADD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_ACTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CHECKED = false;
    private static final Boolean UPDATED_CHECKED = true;

    private static final String ENTITY_API_URL = "/api/transaction-duplicates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TransactionDuplicatesRepository transactionDuplicatesRepository;

    @Autowired
    private TransactionDuplicatesMapper transactionDuplicatesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransactionDuplicatesMockMvc;

    private TransactionDuplicates transactionDuplicates;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionDuplicates createEntity(EntityManager em) {
        TransactionDuplicates transactionDuplicates = new TransactionDuplicates()
            .isduplicate(DEFAULT_ISDUPLICATE)
            .dateAdd(DEFAULT_DATE_ADD)
            .action(DEFAULT_ACTION)
            .checked(DEFAULT_CHECKED);
        return transactionDuplicates;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionDuplicates createUpdatedEntity(EntityManager em) {
        TransactionDuplicates transactionDuplicates = new TransactionDuplicates()
            .isduplicate(UPDATED_ISDUPLICATE)
            .dateAdd(UPDATED_DATE_ADD)
            .action(UPDATED_ACTION)
            .checked(UPDATED_CHECKED);
        return transactionDuplicates;
    }

    @BeforeEach
    public void initTest() {
        transactionDuplicates = createEntity(em);
    }

    @Test
    @Transactional
    void createTransactionDuplicates() throws Exception {
        int databaseSizeBeforeCreate = transactionDuplicatesRepository.findAll().size();
        // Create the TransactionDuplicates
        TransactionDuplicatesDTO transactionDuplicatesDTO = transactionDuplicatesMapper.toDto(transactionDuplicates);
        restTransactionDuplicatesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionDuplicatesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TransactionDuplicates in the database
        List<TransactionDuplicates> transactionDuplicatesList = transactionDuplicatesRepository.findAll();
        assertThat(transactionDuplicatesList).hasSize(databaseSizeBeforeCreate + 1);
        TransactionDuplicates testTransactionDuplicates = transactionDuplicatesList.get(transactionDuplicatesList.size() - 1);
        assertThat(testTransactionDuplicates.getIsduplicate()).isEqualTo(DEFAULT_ISDUPLICATE);
        assertThat(testTransactionDuplicates.getDateAdd()).isEqualTo(DEFAULT_DATE_ADD);
        assertThat(testTransactionDuplicates.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testTransactionDuplicates.getChecked()).isEqualTo(DEFAULT_CHECKED);
    }

    @Test
    @Transactional
    void createTransactionDuplicatesWithExistingId() throws Exception {
        // Create the TransactionDuplicates with an existing ID
        transactionDuplicates.setId(1L);
        TransactionDuplicatesDTO transactionDuplicatesDTO = transactionDuplicatesMapper.toDto(transactionDuplicates);

        int databaseSizeBeforeCreate = transactionDuplicatesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionDuplicatesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionDuplicatesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransactionDuplicates in the database
        List<TransactionDuplicates> transactionDuplicatesList = transactionDuplicatesRepository.findAll();
        assertThat(transactionDuplicatesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTransactionDuplicates() throws Exception {
        // Initialize the database
        transactionDuplicatesRepository.saveAndFlush(transactionDuplicates);

        // Get all the transactionDuplicatesList
        restTransactionDuplicatesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionDuplicates.getId().intValue())))
            .andExpect(jsonPath("$.[*].isduplicate").value(hasItem(DEFAULT_ISDUPLICATE.booleanValue())))
            .andExpect(jsonPath("$.[*].dateAdd").value(hasItem(DEFAULT_DATE_ADD.toString())))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION)))
            .andExpect(jsonPath("$.[*].checked").value(hasItem(DEFAULT_CHECKED.booleanValue())));
    }

    @Test
    @Transactional
    void getTransactionDuplicates() throws Exception {
        // Initialize the database
        transactionDuplicatesRepository.saveAndFlush(transactionDuplicates);

        // Get the transactionDuplicates
        restTransactionDuplicatesMockMvc
            .perform(get(ENTITY_API_URL_ID, transactionDuplicates.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transactionDuplicates.getId().intValue()))
            .andExpect(jsonPath("$.isduplicate").value(DEFAULT_ISDUPLICATE.booleanValue()))
            .andExpect(jsonPath("$.dateAdd").value(DEFAULT_DATE_ADD.toString()))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION))
            .andExpect(jsonPath("$.checked").value(DEFAULT_CHECKED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingTransactionDuplicates() throws Exception {
        // Get the transactionDuplicates
        restTransactionDuplicatesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTransactionDuplicates() throws Exception {
        // Initialize the database
        transactionDuplicatesRepository.saveAndFlush(transactionDuplicates);

        int databaseSizeBeforeUpdate = transactionDuplicatesRepository.findAll().size();

        // Update the transactionDuplicates
        TransactionDuplicates updatedTransactionDuplicates = transactionDuplicatesRepository.findById(transactionDuplicates.getId()).get();
        // Disconnect from session so that the updates on updatedTransactionDuplicates are not directly saved in db
        em.detach(updatedTransactionDuplicates);
        updatedTransactionDuplicates
            .isduplicate(UPDATED_ISDUPLICATE)
            .dateAdd(UPDATED_DATE_ADD)
            .action(UPDATED_ACTION)
            .checked(UPDATED_CHECKED);
        TransactionDuplicatesDTO transactionDuplicatesDTO = transactionDuplicatesMapper.toDto(updatedTransactionDuplicates);

        restTransactionDuplicatesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transactionDuplicatesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionDuplicatesDTO))
            )
            .andExpect(status().isOk());

        // Validate the TransactionDuplicates in the database
        List<TransactionDuplicates> transactionDuplicatesList = transactionDuplicatesRepository.findAll();
        assertThat(transactionDuplicatesList).hasSize(databaseSizeBeforeUpdate);
        TransactionDuplicates testTransactionDuplicates = transactionDuplicatesList.get(transactionDuplicatesList.size() - 1);
        assertThat(testTransactionDuplicates.getIsduplicate()).isEqualTo(UPDATED_ISDUPLICATE);
        assertThat(testTransactionDuplicates.getDateAdd()).isEqualTo(UPDATED_DATE_ADD);
        assertThat(testTransactionDuplicates.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testTransactionDuplicates.getChecked()).isEqualTo(UPDATED_CHECKED);
    }

    @Test
    @Transactional
    void putNonExistingTransactionDuplicates() throws Exception {
        int databaseSizeBeforeUpdate = transactionDuplicatesRepository.findAll().size();
        transactionDuplicates.setId(count.incrementAndGet());

        // Create the TransactionDuplicates
        TransactionDuplicatesDTO transactionDuplicatesDTO = transactionDuplicatesMapper.toDto(transactionDuplicates);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionDuplicatesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transactionDuplicatesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionDuplicatesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransactionDuplicates in the database
        List<TransactionDuplicates> transactionDuplicatesList = transactionDuplicatesRepository.findAll();
        assertThat(transactionDuplicatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTransactionDuplicates() throws Exception {
        int databaseSizeBeforeUpdate = transactionDuplicatesRepository.findAll().size();
        transactionDuplicates.setId(count.incrementAndGet());

        // Create the TransactionDuplicates
        TransactionDuplicatesDTO transactionDuplicatesDTO = transactionDuplicatesMapper.toDto(transactionDuplicates);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransactionDuplicatesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionDuplicatesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransactionDuplicates in the database
        List<TransactionDuplicates> transactionDuplicatesList = transactionDuplicatesRepository.findAll();
        assertThat(transactionDuplicatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTransactionDuplicates() throws Exception {
        int databaseSizeBeforeUpdate = transactionDuplicatesRepository.findAll().size();
        transactionDuplicates.setId(count.incrementAndGet());

        // Create the TransactionDuplicates
        TransactionDuplicatesDTO transactionDuplicatesDTO = transactionDuplicatesMapper.toDto(transactionDuplicates);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransactionDuplicatesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionDuplicatesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TransactionDuplicates in the database
        List<TransactionDuplicates> transactionDuplicatesList = transactionDuplicatesRepository.findAll();
        assertThat(transactionDuplicatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTransactionDuplicatesWithPatch() throws Exception {
        // Initialize the database
        transactionDuplicatesRepository.saveAndFlush(transactionDuplicates);

        int databaseSizeBeforeUpdate = transactionDuplicatesRepository.findAll().size();

        // Update the transactionDuplicates using partial update
        TransactionDuplicates partialUpdatedTransactionDuplicates = new TransactionDuplicates();
        partialUpdatedTransactionDuplicates.setId(transactionDuplicates.getId());

        partialUpdatedTransactionDuplicates.dateAdd(UPDATED_DATE_ADD).checked(UPDATED_CHECKED);

        restTransactionDuplicatesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransactionDuplicates.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransactionDuplicates))
            )
            .andExpect(status().isOk());

        // Validate the TransactionDuplicates in the database
        List<TransactionDuplicates> transactionDuplicatesList = transactionDuplicatesRepository.findAll();
        assertThat(transactionDuplicatesList).hasSize(databaseSizeBeforeUpdate);
        TransactionDuplicates testTransactionDuplicates = transactionDuplicatesList.get(transactionDuplicatesList.size() - 1);
        assertThat(testTransactionDuplicates.getIsduplicate()).isEqualTo(DEFAULT_ISDUPLICATE);
        assertThat(testTransactionDuplicates.getDateAdd()).isEqualTo(UPDATED_DATE_ADD);
        assertThat(testTransactionDuplicates.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testTransactionDuplicates.getChecked()).isEqualTo(UPDATED_CHECKED);
    }

    @Test
    @Transactional
    void fullUpdateTransactionDuplicatesWithPatch() throws Exception {
        // Initialize the database
        transactionDuplicatesRepository.saveAndFlush(transactionDuplicates);

        int databaseSizeBeforeUpdate = transactionDuplicatesRepository.findAll().size();

        // Update the transactionDuplicates using partial update
        TransactionDuplicates partialUpdatedTransactionDuplicates = new TransactionDuplicates();
        partialUpdatedTransactionDuplicates.setId(transactionDuplicates.getId());

        partialUpdatedTransactionDuplicates
            .isduplicate(UPDATED_ISDUPLICATE)
            .dateAdd(UPDATED_DATE_ADD)
            .action(UPDATED_ACTION)
            .checked(UPDATED_CHECKED);

        restTransactionDuplicatesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransactionDuplicates.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransactionDuplicates))
            )
            .andExpect(status().isOk());

        // Validate the TransactionDuplicates in the database
        List<TransactionDuplicates> transactionDuplicatesList = transactionDuplicatesRepository.findAll();
        assertThat(transactionDuplicatesList).hasSize(databaseSizeBeforeUpdate);
        TransactionDuplicates testTransactionDuplicates = transactionDuplicatesList.get(transactionDuplicatesList.size() - 1);
        assertThat(testTransactionDuplicates.getIsduplicate()).isEqualTo(UPDATED_ISDUPLICATE);
        assertThat(testTransactionDuplicates.getDateAdd()).isEqualTo(UPDATED_DATE_ADD);
        assertThat(testTransactionDuplicates.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testTransactionDuplicates.getChecked()).isEqualTo(UPDATED_CHECKED);
    }

    @Test
    @Transactional
    void patchNonExistingTransactionDuplicates() throws Exception {
        int databaseSizeBeforeUpdate = transactionDuplicatesRepository.findAll().size();
        transactionDuplicates.setId(count.incrementAndGet());

        // Create the TransactionDuplicates
        TransactionDuplicatesDTO transactionDuplicatesDTO = transactionDuplicatesMapper.toDto(transactionDuplicates);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionDuplicatesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, transactionDuplicatesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transactionDuplicatesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransactionDuplicates in the database
        List<TransactionDuplicates> transactionDuplicatesList = transactionDuplicatesRepository.findAll();
        assertThat(transactionDuplicatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTransactionDuplicates() throws Exception {
        int databaseSizeBeforeUpdate = transactionDuplicatesRepository.findAll().size();
        transactionDuplicates.setId(count.incrementAndGet());

        // Create the TransactionDuplicates
        TransactionDuplicatesDTO transactionDuplicatesDTO = transactionDuplicatesMapper.toDto(transactionDuplicates);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransactionDuplicatesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transactionDuplicatesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransactionDuplicates in the database
        List<TransactionDuplicates> transactionDuplicatesList = transactionDuplicatesRepository.findAll();
        assertThat(transactionDuplicatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTransactionDuplicates() throws Exception {
        int databaseSizeBeforeUpdate = transactionDuplicatesRepository.findAll().size();
        transactionDuplicates.setId(count.incrementAndGet());

        // Create the TransactionDuplicates
        TransactionDuplicatesDTO transactionDuplicatesDTO = transactionDuplicatesMapper.toDto(transactionDuplicates);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransactionDuplicatesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transactionDuplicatesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TransactionDuplicates in the database
        List<TransactionDuplicates> transactionDuplicatesList = transactionDuplicatesRepository.findAll();
        assertThat(transactionDuplicatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTransactionDuplicates() throws Exception {
        // Initialize the database
        transactionDuplicatesRepository.saveAndFlush(transactionDuplicates);

        int databaseSizeBeforeDelete = transactionDuplicatesRepository.findAll().size();

        // Delete the transactionDuplicates
        restTransactionDuplicatesMockMvc
            .perform(delete(ENTITY_API_URL_ID, transactionDuplicates.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TransactionDuplicates> transactionDuplicatesList = transactionDuplicatesRepository.findAll();
        assertThat(transactionDuplicatesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
