package com.akounts.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.akounts.myapp.IntegrationTest;
import com.akounts.myapp.domain.Automatch;
import com.akounts.myapp.repository.AutomatchRepository;
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
 * Integration tests for the {@link AutomatchResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AutomatchResourceIT {

    private static final String DEFAULT_MATCHSTRING = "AAAAAAAAAA";
    private static final String UPDATED_MATCHSTRING = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_USED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_USED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_USE_COUNT = 1;
    private static final Integer UPDATED_USE_COUNT = 2;

    private static final String DEFAULT_DEFAULT_TAG = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULT_TAG = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/automatches";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AutomatchRepository automatchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAutomatchMockMvc;

    private Automatch automatch;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Automatch createEntity(EntityManager em) {
        Automatch automatch = new Automatch()
            .matchstring(DEFAULT_MATCHSTRING)
            .updateTime(DEFAULT_UPDATE_TIME)
            .lastUsedTime(DEFAULT_LAST_USED_TIME)
            .useCount(DEFAULT_USE_COUNT)
            .defaultTag(DEFAULT_DEFAULT_TAG);
        return automatch;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Automatch createUpdatedEntity(EntityManager em) {
        Automatch automatch = new Automatch()
            .matchstring(UPDATED_MATCHSTRING)
            .updateTime(UPDATED_UPDATE_TIME)
            .lastUsedTime(UPDATED_LAST_USED_TIME)
            .useCount(UPDATED_USE_COUNT)
            .defaultTag(UPDATED_DEFAULT_TAG);
        return automatch;
    }

    @BeforeEach
    public void initTest() {
        automatch = createEntity(em);
    }

    @Test
    @Transactional
    void createAutomatch() throws Exception {
        int databaseSizeBeforeCreate = automatchRepository.findAll().size();
        // Create the Automatch
        restAutomatchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(automatch)))
            .andExpect(status().isCreated());

        // Validate the Automatch in the database
        List<Automatch> automatchList = automatchRepository.findAll();
        assertThat(automatchList).hasSize(databaseSizeBeforeCreate + 1);
        Automatch testAutomatch = automatchList.get(automatchList.size() - 1);
        assertThat(testAutomatch.getMatchstring()).isEqualTo(DEFAULT_MATCHSTRING);
        assertThat(testAutomatch.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testAutomatch.getLastUsedTime()).isEqualTo(DEFAULT_LAST_USED_TIME);
        assertThat(testAutomatch.getUseCount()).isEqualTo(DEFAULT_USE_COUNT);
        assertThat(testAutomatch.getDefaultTag()).isEqualTo(DEFAULT_DEFAULT_TAG);
    }

    @Test
    @Transactional
    void createAutomatchWithExistingId() throws Exception {
        // Create the Automatch with an existing ID
        automatch.setId(1L);

        int databaseSizeBeforeCreate = automatchRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAutomatchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(automatch)))
            .andExpect(status().isBadRequest());

        // Validate the Automatch in the database
        List<Automatch> automatchList = automatchRepository.findAll();
        assertThat(automatchList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAutomatches() throws Exception {
        // Initialize the database
        automatchRepository.saveAndFlush(automatch);

        // Get all the automatchList
        restAutomatchMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(automatch.getId().intValue())))
            .andExpect(jsonPath("$.[*].matchstring").value(hasItem(DEFAULT_MATCHSTRING)))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].lastUsedTime").value(hasItem(DEFAULT_LAST_USED_TIME.toString())))
            .andExpect(jsonPath("$.[*].useCount").value(hasItem(DEFAULT_USE_COUNT)))
            .andExpect(jsonPath("$.[*].defaultTag").value(hasItem(DEFAULT_DEFAULT_TAG)));
    }

    @Test
    @Transactional
    void getAutomatch() throws Exception {
        // Initialize the database
        automatchRepository.saveAndFlush(automatch);

        // Get the automatch
        restAutomatchMockMvc
            .perform(get(ENTITY_API_URL_ID, automatch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(automatch.getId().intValue()))
            .andExpect(jsonPath("$.matchstring").value(DEFAULT_MATCHSTRING))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()))
            .andExpect(jsonPath("$.lastUsedTime").value(DEFAULT_LAST_USED_TIME.toString()))
            .andExpect(jsonPath("$.useCount").value(DEFAULT_USE_COUNT))
            .andExpect(jsonPath("$.defaultTag").value(DEFAULT_DEFAULT_TAG));
    }

    @Test
    @Transactional
    void getNonExistingAutomatch() throws Exception {
        // Get the automatch
        restAutomatchMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAutomatch() throws Exception {
        // Initialize the database
        automatchRepository.saveAndFlush(automatch);

        int databaseSizeBeforeUpdate = automatchRepository.findAll().size();

        // Update the automatch
        Automatch updatedAutomatch = automatchRepository.findById(automatch.getId()).get();
        // Disconnect from session so that the updates on updatedAutomatch are not directly saved in db
        em.detach(updatedAutomatch);
        updatedAutomatch
            .matchstring(UPDATED_MATCHSTRING)
            .updateTime(UPDATED_UPDATE_TIME)
            .lastUsedTime(UPDATED_LAST_USED_TIME)
            .useCount(UPDATED_USE_COUNT)
            .defaultTag(UPDATED_DEFAULT_TAG);

        restAutomatchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAutomatch.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAutomatch))
            )
            .andExpect(status().isOk());

        // Validate the Automatch in the database
        List<Automatch> automatchList = automatchRepository.findAll();
        assertThat(automatchList).hasSize(databaseSizeBeforeUpdate);
        Automatch testAutomatch = automatchList.get(automatchList.size() - 1);
        assertThat(testAutomatch.getMatchstring()).isEqualTo(UPDATED_MATCHSTRING);
        assertThat(testAutomatch.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testAutomatch.getLastUsedTime()).isEqualTo(UPDATED_LAST_USED_TIME);
        assertThat(testAutomatch.getUseCount()).isEqualTo(UPDATED_USE_COUNT);
        assertThat(testAutomatch.getDefaultTag()).isEqualTo(UPDATED_DEFAULT_TAG);
    }

    @Test
    @Transactional
    void putNonExistingAutomatch() throws Exception {
        int databaseSizeBeforeUpdate = automatchRepository.findAll().size();
        automatch.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutomatchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, automatch.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(automatch))
            )
            .andExpect(status().isBadRequest());

        // Validate the Automatch in the database
        List<Automatch> automatchList = automatchRepository.findAll();
        assertThat(automatchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAutomatch() throws Exception {
        int databaseSizeBeforeUpdate = automatchRepository.findAll().size();
        automatch.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutomatchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(automatch))
            )
            .andExpect(status().isBadRequest());

        // Validate the Automatch in the database
        List<Automatch> automatchList = automatchRepository.findAll();
        assertThat(automatchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAutomatch() throws Exception {
        int databaseSizeBeforeUpdate = automatchRepository.findAll().size();
        automatch.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutomatchMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(automatch)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Automatch in the database
        List<Automatch> automatchList = automatchRepository.findAll();
        assertThat(automatchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAutomatchWithPatch() throws Exception {
        // Initialize the database
        automatchRepository.saveAndFlush(automatch);

        int databaseSizeBeforeUpdate = automatchRepository.findAll().size();

        // Update the automatch using partial update
        Automatch partialUpdatedAutomatch = new Automatch();
        partialUpdatedAutomatch.setId(automatch.getId());

        partialUpdatedAutomatch.updateTime(UPDATED_UPDATE_TIME).defaultTag(UPDATED_DEFAULT_TAG);

        restAutomatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAutomatch.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAutomatch))
            )
            .andExpect(status().isOk());

        // Validate the Automatch in the database
        List<Automatch> automatchList = automatchRepository.findAll();
        assertThat(automatchList).hasSize(databaseSizeBeforeUpdate);
        Automatch testAutomatch = automatchList.get(automatchList.size() - 1);
        assertThat(testAutomatch.getMatchstring()).isEqualTo(DEFAULT_MATCHSTRING);
        assertThat(testAutomatch.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testAutomatch.getLastUsedTime()).isEqualTo(DEFAULT_LAST_USED_TIME);
        assertThat(testAutomatch.getUseCount()).isEqualTo(DEFAULT_USE_COUNT);
        assertThat(testAutomatch.getDefaultTag()).isEqualTo(UPDATED_DEFAULT_TAG);
    }

    @Test
    @Transactional
    void fullUpdateAutomatchWithPatch() throws Exception {
        // Initialize the database
        automatchRepository.saveAndFlush(automatch);

        int databaseSizeBeforeUpdate = automatchRepository.findAll().size();

        // Update the automatch using partial update
        Automatch partialUpdatedAutomatch = new Automatch();
        partialUpdatedAutomatch.setId(automatch.getId());

        partialUpdatedAutomatch
            .matchstring(UPDATED_MATCHSTRING)
            .updateTime(UPDATED_UPDATE_TIME)
            .lastUsedTime(UPDATED_LAST_USED_TIME)
            .useCount(UPDATED_USE_COUNT)
            .defaultTag(UPDATED_DEFAULT_TAG);

        restAutomatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAutomatch.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAutomatch))
            )
            .andExpect(status().isOk());

        // Validate the Automatch in the database
        List<Automatch> automatchList = automatchRepository.findAll();
        assertThat(automatchList).hasSize(databaseSizeBeforeUpdate);
        Automatch testAutomatch = automatchList.get(automatchList.size() - 1);
        assertThat(testAutomatch.getMatchstring()).isEqualTo(UPDATED_MATCHSTRING);
        assertThat(testAutomatch.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testAutomatch.getLastUsedTime()).isEqualTo(UPDATED_LAST_USED_TIME);
        assertThat(testAutomatch.getUseCount()).isEqualTo(UPDATED_USE_COUNT);
        assertThat(testAutomatch.getDefaultTag()).isEqualTo(UPDATED_DEFAULT_TAG);
    }

    @Test
    @Transactional
    void patchNonExistingAutomatch() throws Exception {
        int databaseSizeBeforeUpdate = automatchRepository.findAll().size();
        automatch.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutomatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, automatch.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(automatch))
            )
            .andExpect(status().isBadRequest());

        // Validate the Automatch in the database
        List<Automatch> automatchList = automatchRepository.findAll();
        assertThat(automatchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAutomatch() throws Exception {
        int databaseSizeBeforeUpdate = automatchRepository.findAll().size();
        automatch.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutomatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(automatch))
            )
            .andExpect(status().isBadRequest());

        // Validate the Automatch in the database
        List<Automatch> automatchList = automatchRepository.findAll();
        assertThat(automatchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAutomatch() throws Exception {
        int databaseSizeBeforeUpdate = automatchRepository.findAll().size();
        automatch.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutomatchMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(automatch))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Automatch in the database
        List<Automatch> automatchList = automatchRepository.findAll();
        assertThat(automatchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAutomatch() throws Exception {
        // Initialize the database
        automatchRepository.saveAndFlush(automatch);

        int databaseSizeBeforeDelete = automatchRepository.findAll().size();

        // Delete the automatch
        restAutomatchMockMvc
            .perform(delete(ENTITY_API_URL_ID, automatch.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Automatch> automatchList = automatchRepository.findAll();
        assertThat(automatchList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
