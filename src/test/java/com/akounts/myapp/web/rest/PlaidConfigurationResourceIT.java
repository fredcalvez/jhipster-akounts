package com.akounts.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.akounts.myapp.IntegrationTest;
import com.akounts.myapp.domain.PlaidConfiguration;
import com.akounts.myapp.repository.PlaidConfigurationRepository;
import com.akounts.myapp.service.dto.PlaidConfigurationDTO;
import com.akounts.myapp.service.mapper.PlaidConfigurationMapper;
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
 * Integration tests for the {@link PlaidConfigurationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlaidConfigurationResourceIT {

    private static final String DEFAULT_ENVIRONEMENT = "AAAAAAAAAA";
    private static final String UPDATED_ENVIRONEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/plaid-configurations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PlaidConfigurationRepository plaidConfigurationRepository;

    @Autowired
    private PlaidConfigurationMapper plaidConfigurationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlaidConfigurationMockMvc;

    private PlaidConfiguration plaidConfiguration;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlaidConfiguration createEntity(EntityManager em) {
        PlaidConfiguration plaidConfiguration = new PlaidConfiguration()
            .environement(DEFAULT_ENVIRONEMENT)
            .key(DEFAULT_KEY)
            .value(DEFAULT_VALUE);
        return plaidConfiguration;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlaidConfiguration createUpdatedEntity(EntityManager em) {
        PlaidConfiguration plaidConfiguration = new PlaidConfiguration()
            .environement(UPDATED_ENVIRONEMENT)
            .key(UPDATED_KEY)
            .value(UPDATED_VALUE);
        return plaidConfiguration;
    }

    @BeforeEach
    public void initTest() {
        plaidConfiguration = createEntity(em);
    }

    @Test
    @Transactional
    void createPlaidConfiguration() throws Exception {
        int databaseSizeBeforeCreate = plaidConfigurationRepository.findAll().size();
        // Create the PlaidConfiguration
        PlaidConfigurationDTO plaidConfigurationDTO = plaidConfigurationMapper.toDto(plaidConfiguration);
        restPlaidConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plaidConfigurationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PlaidConfiguration in the database
        List<PlaidConfiguration> plaidConfigurationList = plaidConfigurationRepository.findAll();
        assertThat(plaidConfigurationList).hasSize(databaseSizeBeforeCreate + 1);
        PlaidConfiguration testPlaidConfiguration = plaidConfigurationList.get(plaidConfigurationList.size() - 1);
        assertThat(testPlaidConfiguration.getEnvironement()).isEqualTo(DEFAULT_ENVIRONEMENT);
        assertThat(testPlaidConfiguration.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testPlaidConfiguration.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void createPlaidConfigurationWithExistingId() throws Exception {
        // Create the PlaidConfiguration with an existing ID
        plaidConfiguration.setId(1L);
        PlaidConfigurationDTO plaidConfigurationDTO = plaidConfigurationMapper.toDto(plaidConfiguration);

        int databaseSizeBeforeCreate = plaidConfigurationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlaidConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plaidConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlaidConfiguration in the database
        List<PlaidConfiguration> plaidConfigurationList = plaidConfigurationRepository.findAll();
        assertThat(plaidConfigurationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPlaidConfigurations() throws Exception {
        // Initialize the database
        plaidConfigurationRepository.saveAndFlush(plaidConfiguration);

        // Get all the plaidConfigurationList
        restPlaidConfigurationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plaidConfiguration.getId().intValue())))
            .andExpect(jsonPath("$.[*].environement").value(hasItem(DEFAULT_ENVIRONEMENT)))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    void getPlaidConfiguration() throws Exception {
        // Initialize the database
        plaidConfigurationRepository.saveAndFlush(plaidConfiguration);

        // Get the plaidConfiguration
        restPlaidConfigurationMockMvc
            .perform(get(ENTITY_API_URL_ID, plaidConfiguration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(plaidConfiguration.getId().intValue()))
            .andExpect(jsonPath("$.environement").value(DEFAULT_ENVIRONEMENT))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    void getNonExistingPlaidConfiguration() throws Exception {
        // Get the plaidConfiguration
        restPlaidConfigurationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPlaidConfiguration() throws Exception {
        // Initialize the database
        plaidConfigurationRepository.saveAndFlush(plaidConfiguration);

        int databaseSizeBeforeUpdate = plaidConfigurationRepository.findAll().size();

        // Update the plaidConfiguration
        PlaidConfiguration updatedPlaidConfiguration = plaidConfigurationRepository.findById(plaidConfiguration.getId()).get();
        // Disconnect from session so that the updates on updatedPlaidConfiguration are not directly saved in db
        em.detach(updatedPlaidConfiguration);
        updatedPlaidConfiguration.environement(UPDATED_ENVIRONEMENT).key(UPDATED_KEY).value(UPDATED_VALUE);
        PlaidConfigurationDTO plaidConfigurationDTO = plaidConfigurationMapper.toDto(updatedPlaidConfiguration);

        restPlaidConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, plaidConfigurationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plaidConfigurationDTO))
            )
            .andExpect(status().isOk());

        // Validate the PlaidConfiguration in the database
        List<PlaidConfiguration> plaidConfigurationList = plaidConfigurationRepository.findAll();
        assertThat(plaidConfigurationList).hasSize(databaseSizeBeforeUpdate);
        PlaidConfiguration testPlaidConfiguration = plaidConfigurationList.get(plaidConfigurationList.size() - 1);
        assertThat(testPlaidConfiguration.getEnvironement()).isEqualTo(UPDATED_ENVIRONEMENT);
        assertThat(testPlaidConfiguration.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testPlaidConfiguration.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingPlaidConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = plaidConfigurationRepository.findAll().size();
        plaidConfiguration.setId(count.incrementAndGet());

        // Create the PlaidConfiguration
        PlaidConfigurationDTO plaidConfigurationDTO = plaidConfigurationMapper.toDto(plaidConfiguration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlaidConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, plaidConfigurationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plaidConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlaidConfiguration in the database
        List<PlaidConfiguration> plaidConfigurationList = plaidConfigurationRepository.findAll();
        assertThat(plaidConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlaidConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = plaidConfigurationRepository.findAll().size();
        plaidConfiguration.setId(count.incrementAndGet());

        // Create the PlaidConfiguration
        PlaidConfigurationDTO plaidConfigurationDTO = plaidConfigurationMapper.toDto(plaidConfiguration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaidConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plaidConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlaidConfiguration in the database
        List<PlaidConfiguration> plaidConfigurationList = plaidConfigurationRepository.findAll();
        assertThat(plaidConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlaidConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = plaidConfigurationRepository.findAll().size();
        plaidConfiguration.setId(count.incrementAndGet());

        // Create the PlaidConfiguration
        PlaidConfigurationDTO plaidConfigurationDTO = plaidConfigurationMapper.toDto(plaidConfiguration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaidConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plaidConfigurationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlaidConfiguration in the database
        List<PlaidConfiguration> plaidConfigurationList = plaidConfigurationRepository.findAll();
        assertThat(plaidConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlaidConfigurationWithPatch() throws Exception {
        // Initialize the database
        plaidConfigurationRepository.saveAndFlush(plaidConfiguration);

        int databaseSizeBeforeUpdate = plaidConfigurationRepository.findAll().size();

        // Update the plaidConfiguration using partial update
        PlaidConfiguration partialUpdatedPlaidConfiguration = new PlaidConfiguration();
        partialUpdatedPlaidConfiguration.setId(plaidConfiguration.getId());

        partialUpdatedPlaidConfiguration.key(UPDATED_KEY).value(UPDATED_VALUE);

        restPlaidConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlaidConfiguration.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlaidConfiguration))
            )
            .andExpect(status().isOk());

        // Validate the PlaidConfiguration in the database
        List<PlaidConfiguration> plaidConfigurationList = plaidConfigurationRepository.findAll();
        assertThat(plaidConfigurationList).hasSize(databaseSizeBeforeUpdate);
        PlaidConfiguration testPlaidConfiguration = plaidConfigurationList.get(plaidConfigurationList.size() - 1);
        assertThat(testPlaidConfiguration.getEnvironement()).isEqualTo(DEFAULT_ENVIRONEMENT);
        assertThat(testPlaidConfiguration.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testPlaidConfiguration.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void fullUpdatePlaidConfigurationWithPatch() throws Exception {
        // Initialize the database
        plaidConfigurationRepository.saveAndFlush(plaidConfiguration);

        int databaseSizeBeforeUpdate = plaidConfigurationRepository.findAll().size();

        // Update the plaidConfiguration using partial update
        PlaidConfiguration partialUpdatedPlaidConfiguration = new PlaidConfiguration();
        partialUpdatedPlaidConfiguration.setId(plaidConfiguration.getId());

        partialUpdatedPlaidConfiguration.environement(UPDATED_ENVIRONEMENT).key(UPDATED_KEY).value(UPDATED_VALUE);

        restPlaidConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlaidConfiguration.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlaidConfiguration))
            )
            .andExpect(status().isOk());

        // Validate the PlaidConfiguration in the database
        List<PlaidConfiguration> plaidConfigurationList = plaidConfigurationRepository.findAll();
        assertThat(plaidConfigurationList).hasSize(databaseSizeBeforeUpdate);
        PlaidConfiguration testPlaidConfiguration = plaidConfigurationList.get(plaidConfigurationList.size() - 1);
        assertThat(testPlaidConfiguration.getEnvironement()).isEqualTo(UPDATED_ENVIRONEMENT);
        assertThat(testPlaidConfiguration.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testPlaidConfiguration.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingPlaidConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = plaidConfigurationRepository.findAll().size();
        plaidConfiguration.setId(count.incrementAndGet());

        // Create the PlaidConfiguration
        PlaidConfigurationDTO plaidConfigurationDTO = plaidConfigurationMapper.toDto(plaidConfiguration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlaidConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, plaidConfigurationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plaidConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlaidConfiguration in the database
        List<PlaidConfiguration> plaidConfigurationList = plaidConfigurationRepository.findAll();
        assertThat(plaidConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlaidConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = plaidConfigurationRepository.findAll().size();
        plaidConfiguration.setId(count.incrementAndGet());

        // Create the PlaidConfiguration
        PlaidConfigurationDTO plaidConfigurationDTO = plaidConfigurationMapper.toDto(plaidConfiguration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaidConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plaidConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlaidConfiguration in the database
        List<PlaidConfiguration> plaidConfigurationList = plaidConfigurationRepository.findAll();
        assertThat(plaidConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlaidConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = plaidConfigurationRepository.findAll().size();
        plaidConfiguration.setId(count.incrementAndGet());

        // Create the PlaidConfiguration
        PlaidConfigurationDTO plaidConfigurationDTO = plaidConfigurationMapper.toDto(plaidConfiguration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaidConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plaidConfigurationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlaidConfiguration in the database
        List<PlaidConfiguration> plaidConfigurationList = plaidConfigurationRepository.findAll();
        assertThat(plaidConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlaidConfiguration() throws Exception {
        // Initialize the database
        plaidConfigurationRepository.saveAndFlush(plaidConfiguration);

        int databaseSizeBeforeDelete = plaidConfigurationRepository.findAll().size();

        // Delete the plaidConfiguration
        restPlaidConfigurationMockMvc
            .perform(delete(ENTITY_API_URL_ID, plaidConfiguration.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlaidConfiguration> plaidConfigurationList = plaidConfigurationRepository.findAll();
        assertThat(plaidConfigurationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
