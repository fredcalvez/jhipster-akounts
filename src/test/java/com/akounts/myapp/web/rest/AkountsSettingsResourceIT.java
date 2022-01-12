package com.akounts.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.akounts.myapp.IntegrationTest;
import com.akounts.myapp.domain.AkountsSettings;
import com.akounts.myapp.repository.AkountsSettingsRepository;
import com.akounts.myapp.service.dto.AkountsSettingsDTO;
import com.akounts.myapp.service.mapper.AkountsSettingsMapper;
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
 * Integration tests for the {@link AkountsSettingsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AkountsSettingsResourceIT {

    private static final String DEFAULT_SETTING_KEY = "AAAAAAAAAA";
    private static final String UPDATED_SETTING_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_SETTING_VAL = "AAAAAAAAAA";
    private static final String UPDATED_SETTING_VAL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/akounts-settings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AkountsSettingsRepository akountsSettingsRepository;

    @Autowired
    private AkountsSettingsMapper akountsSettingsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAkountsSettingsMockMvc;

    private AkountsSettings akountsSettings;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AkountsSettings createEntity(EntityManager em) {
        AkountsSettings akountsSettings = new AkountsSettings().settingKey(DEFAULT_SETTING_KEY).settingVal(DEFAULT_SETTING_VAL);
        return akountsSettings;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AkountsSettings createUpdatedEntity(EntityManager em) {
        AkountsSettings akountsSettings = new AkountsSettings().settingKey(UPDATED_SETTING_KEY).settingVal(UPDATED_SETTING_VAL);
        return akountsSettings;
    }

    @BeforeEach
    public void initTest() {
        akountsSettings = createEntity(em);
    }

    @Test
    @Transactional
    void createAkountsSettings() throws Exception {
        int databaseSizeBeforeCreate = akountsSettingsRepository.findAll().size();
        // Create the AkountsSettings
        AkountsSettingsDTO akountsSettingsDTO = akountsSettingsMapper.toDto(akountsSettings);
        restAkountsSettingsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(akountsSettingsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AkountsSettings in the database
        List<AkountsSettings> akountsSettingsList = akountsSettingsRepository.findAll();
        assertThat(akountsSettingsList).hasSize(databaseSizeBeforeCreate + 1);
        AkountsSettings testAkountsSettings = akountsSettingsList.get(akountsSettingsList.size() - 1);
        assertThat(testAkountsSettings.getSettingKey()).isEqualTo(DEFAULT_SETTING_KEY);
        assertThat(testAkountsSettings.getSettingVal()).isEqualTo(DEFAULT_SETTING_VAL);
    }

    @Test
    @Transactional
    void createAkountsSettingsWithExistingId() throws Exception {
        // Create the AkountsSettings with an existing ID
        akountsSettings.setId(1L);
        AkountsSettingsDTO akountsSettingsDTO = akountsSettingsMapper.toDto(akountsSettings);

        int databaseSizeBeforeCreate = akountsSettingsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAkountsSettingsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(akountsSettingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AkountsSettings in the database
        List<AkountsSettings> akountsSettingsList = akountsSettingsRepository.findAll();
        assertThat(akountsSettingsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAkountsSettings() throws Exception {
        // Initialize the database
        akountsSettingsRepository.saveAndFlush(akountsSettings);

        // Get all the akountsSettingsList
        restAkountsSettingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(akountsSettings.getId().intValue())))
            .andExpect(jsonPath("$.[*].settingKey").value(hasItem(DEFAULT_SETTING_KEY)))
            .andExpect(jsonPath("$.[*].settingVal").value(hasItem(DEFAULT_SETTING_VAL)));
    }

    @Test
    @Transactional
    void getAkountsSettings() throws Exception {
        // Initialize the database
        akountsSettingsRepository.saveAndFlush(akountsSettings);

        // Get the akountsSettings
        restAkountsSettingsMockMvc
            .perform(get(ENTITY_API_URL_ID, akountsSettings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(akountsSettings.getId().intValue()))
            .andExpect(jsonPath("$.settingKey").value(DEFAULT_SETTING_KEY))
            .andExpect(jsonPath("$.settingVal").value(DEFAULT_SETTING_VAL));
    }

    @Test
    @Transactional
    void getNonExistingAkountsSettings() throws Exception {
        // Get the akountsSettings
        restAkountsSettingsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAkountsSettings() throws Exception {
        // Initialize the database
        akountsSettingsRepository.saveAndFlush(akountsSettings);

        int databaseSizeBeforeUpdate = akountsSettingsRepository.findAll().size();

        // Update the akountsSettings
        AkountsSettings updatedAkountsSettings = akountsSettingsRepository.findById(akountsSettings.getId()).get();
        // Disconnect from session so that the updates on updatedAkountsSettings are not directly saved in db
        em.detach(updatedAkountsSettings);
        updatedAkountsSettings.settingKey(UPDATED_SETTING_KEY).settingVal(UPDATED_SETTING_VAL);
        AkountsSettingsDTO akountsSettingsDTO = akountsSettingsMapper.toDto(updatedAkountsSettings);

        restAkountsSettingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, akountsSettingsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(akountsSettingsDTO))
            )
            .andExpect(status().isOk());

        // Validate the AkountsSettings in the database
        List<AkountsSettings> akountsSettingsList = akountsSettingsRepository.findAll();
        assertThat(akountsSettingsList).hasSize(databaseSizeBeforeUpdate);
        AkountsSettings testAkountsSettings = akountsSettingsList.get(akountsSettingsList.size() - 1);
        assertThat(testAkountsSettings.getSettingKey()).isEqualTo(UPDATED_SETTING_KEY);
        assertThat(testAkountsSettings.getSettingVal()).isEqualTo(UPDATED_SETTING_VAL);
    }

    @Test
    @Transactional
    void putNonExistingAkountsSettings() throws Exception {
        int databaseSizeBeforeUpdate = akountsSettingsRepository.findAll().size();
        akountsSettings.setId(count.incrementAndGet());

        // Create the AkountsSettings
        AkountsSettingsDTO akountsSettingsDTO = akountsSettingsMapper.toDto(akountsSettings);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAkountsSettingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, akountsSettingsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(akountsSettingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AkountsSettings in the database
        List<AkountsSettings> akountsSettingsList = akountsSettingsRepository.findAll();
        assertThat(akountsSettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAkountsSettings() throws Exception {
        int databaseSizeBeforeUpdate = akountsSettingsRepository.findAll().size();
        akountsSettings.setId(count.incrementAndGet());

        // Create the AkountsSettings
        AkountsSettingsDTO akountsSettingsDTO = akountsSettingsMapper.toDto(akountsSettings);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAkountsSettingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(akountsSettingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AkountsSettings in the database
        List<AkountsSettings> akountsSettingsList = akountsSettingsRepository.findAll();
        assertThat(akountsSettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAkountsSettings() throws Exception {
        int databaseSizeBeforeUpdate = akountsSettingsRepository.findAll().size();
        akountsSettings.setId(count.incrementAndGet());

        // Create the AkountsSettings
        AkountsSettingsDTO akountsSettingsDTO = akountsSettingsMapper.toDto(akountsSettings);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAkountsSettingsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(akountsSettingsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AkountsSettings in the database
        List<AkountsSettings> akountsSettingsList = akountsSettingsRepository.findAll();
        assertThat(akountsSettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAkountsSettingsWithPatch() throws Exception {
        // Initialize the database
        akountsSettingsRepository.saveAndFlush(akountsSettings);

        int databaseSizeBeforeUpdate = akountsSettingsRepository.findAll().size();

        // Update the akountsSettings using partial update
        AkountsSettings partialUpdatedAkountsSettings = new AkountsSettings();
        partialUpdatedAkountsSettings.setId(akountsSettings.getId());

        partialUpdatedAkountsSettings.settingKey(UPDATED_SETTING_KEY).settingVal(UPDATED_SETTING_VAL);

        restAkountsSettingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAkountsSettings.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAkountsSettings))
            )
            .andExpect(status().isOk());

        // Validate the AkountsSettings in the database
        List<AkountsSettings> akountsSettingsList = akountsSettingsRepository.findAll();
        assertThat(akountsSettingsList).hasSize(databaseSizeBeforeUpdate);
        AkountsSettings testAkountsSettings = akountsSettingsList.get(akountsSettingsList.size() - 1);
        assertThat(testAkountsSettings.getSettingKey()).isEqualTo(UPDATED_SETTING_KEY);
        assertThat(testAkountsSettings.getSettingVal()).isEqualTo(UPDATED_SETTING_VAL);
    }

    @Test
    @Transactional
    void fullUpdateAkountsSettingsWithPatch() throws Exception {
        // Initialize the database
        akountsSettingsRepository.saveAndFlush(akountsSettings);

        int databaseSizeBeforeUpdate = akountsSettingsRepository.findAll().size();

        // Update the akountsSettings using partial update
        AkountsSettings partialUpdatedAkountsSettings = new AkountsSettings();
        partialUpdatedAkountsSettings.setId(akountsSettings.getId());

        partialUpdatedAkountsSettings.settingKey(UPDATED_SETTING_KEY).settingVal(UPDATED_SETTING_VAL);

        restAkountsSettingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAkountsSettings.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAkountsSettings))
            )
            .andExpect(status().isOk());

        // Validate the AkountsSettings in the database
        List<AkountsSettings> akountsSettingsList = akountsSettingsRepository.findAll();
        assertThat(akountsSettingsList).hasSize(databaseSizeBeforeUpdate);
        AkountsSettings testAkountsSettings = akountsSettingsList.get(akountsSettingsList.size() - 1);
        assertThat(testAkountsSettings.getSettingKey()).isEqualTo(UPDATED_SETTING_KEY);
        assertThat(testAkountsSettings.getSettingVal()).isEqualTo(UPDATED_SETTING_VAL);
    }

    @Test
    @Transactional
    void patchNonExistingAkountsSettings() throws Exception {
        int databaseSizeBeforeUpdate = akountsSettingsRepository.findAll().size();
        akountsSettings.setId(count.incrementAndGet());

        // Create the AkountsSettings
        AkountsSettingsDTO akountsSettingsDTO = akountsSettingsMapper.toDto(akountsSettings);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAkountsSettingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, akountsSettingsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(akountsSettingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AkountsSettings in the database
        List<AkountsSettings> akountsSettingsList = akountsSettingsRepository.findAll();
        assertThat(akountsSettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAkountsSettings() throws Exception {
        int databaseSizeBeforeUpdate = akountsSettingsRepository.findAll().size();
        akountsSettings.setId(count.incrementAndGet());

        // Create the AkountsSettings
        AkountsSettingsDTO akountsSettingsDTO = akountsSettingsMapper.toDto(akountsSettings);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAkountsSettingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(akountsSettingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AkountsSettings in the database
        List<AkountsSettings> akountsSettingsList = akountsSettingsRepository.findAll();
        assertThat(akountsSettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAkountsSettings() throws Exception {
        int databaseSizeBeforeUpdate = akountsSettingsRepository.findAll().size();
        akountsSettings.setId(count.incrementAndGet());

        // Create the AkountsSettings
        AkountsSettingsDTO akountsSettingsDTO = akountsSettingsMapper.toDto(akountsSettings);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAkountsSettingsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(akountsSettingsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AkountsSettings in the database
        List<AkountsSettings> akountsSettingsList = akountsSettingsRepository.findAll();
        assertThat(akountsSettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAkountsSettings() throws Exception {
        // Initialize the database
        akountsSettingsRepository.saveAndFlush(akountsSettings);

        int databaseSizeBeforeDelete = akountsSettingsRepository.findAll().size();

        // Delete the akountsSettings
        restAkountsSettingsMockMvc
            .perform(delete(ENTITY_API_URL_ID, akountsSettings.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AkountsSettings> akountsSettingsList = akountsSettingsRepository.findAll();
        assertThat(akountsSettingsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
