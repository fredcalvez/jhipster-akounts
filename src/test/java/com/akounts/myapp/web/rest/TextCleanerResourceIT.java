package com.akounts.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.akounts.myapp.IntegrationTest;
import com.akounts.myapp.domain.TextCleaner;
import com.akounts.myapp.repository.TextCleanerRepository;
import com.akounts.myapp.service.dto.TextCleanerDTO;
import com.akounts.myapp.service.mapper.TextCleanerMapper;
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
 * Integration tests for the {@link TextCleanerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TextCleanerResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_RULE = "AAAAAAAAAA";
    private static final String UPDATED_RULE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/text-cleaners";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TextCleanerRepository textCleanerRepository;

    @Autowired
    private TextCleanerMapper textCleanerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTextCleanerMockMvc;

    private TextCleaner textCleaner;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TextCleaner createEntity(EntityManager em) {
        TextCleaner textCleaner = new TextCleaner().type(DEFAULT_TYPE).rule(DEFAULT_RULE);
        return textCleaner;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TextCleaner createUpdatedEntity(EntityManager em) {
        TextCleaner textCleaner = new TextCleaner().type(UPDATED_TYPE).rule(UPDATED_RULE);
        return textCleaner;
    }

    @BeforeEach
    public void initTest() {
        textCleaner = createEntity(em);
    }

    @Test
    @Transactional
    void createTextCleaner() throws Exception {
        int databaseSizeBeforeCreate = textCleanerRepository.findAll().size();
        // Create the TextCleaner
        TextCleanerDTO textCleanerDTO = textCleanerMapper.toDto(textCleaner);
        restTextCleanerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(textCleanerDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TextCleaner in the database
        List<TextCleaner> textCleanerList = textCleanerRepository.findAll();
        assertThat(textCleanerList).hasSize(databaseSizeBeforeCreate + 1);
        TextCleaner testTextCleaner = textCleanerList.get(textCleanerList.size() - 1);
        assertThat(testTextCleaner.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTextCleaner.getRule()).isEqualTo(DEFAULT_RULE);
    }

    @Test
    @Transactional
    void createTextCleanerWithExistingId() throws Exception {
        // Create the TextCleaner with an existing ID
        textCleaner.setId(1L);
        TextCleanerDTO textCleanerDTO = textCleanerMapper.toDto(textCleaner);

        int databaseSizeBeforeCreate = textCleanerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTextCleanerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(textCleanerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TextCleaner in the database
        List<TextCleaner> textCleanerList = textCleanerRepository.findAll();
        assertThat(textCleanerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTextCleaners() throws Exception {
        // Initialize the database
        textCleanerRepository.saveAndFlush(textCleaner);

        // Get all the textCleanerList
        restTextCleanerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(textCleaner.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].rule").value(hasItem(DEFAULT_RULE)));
    }

    @Test
    @Transactional
    void getTextCleaner() throws Exception {
        // Initialize the database
        textCleanerRepository.saveAndFlush(textCleaner);

        // Get the textCleaner
        restTextCleanerMockMvc
            .perform(get(ENTITY_API_URL_ID, textCleaner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(textCleaner.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.rule").value(DEFAULT_RULE));
    }

    @Test
    @Transactional
    void getNonExistingTextCleaner() throws Exception {
        // Get the textCleaner
        restTextCleanerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTextCleaner() throws Exception {
        // Initialize the database
        textCleanerRepository.saveAndFlush(textCleaner);

        int databaseSizeBeforeUpdate = textCleanerRepository.findAll().size();

        // Update the textCleaner
        TextCleaner updatedTextCleaner = textCleanerRepository.findById(textCleaner.getId()).get();
        // Disconnect from session so that the updates on updatedTextCleaner are not directly saved in db
        em.detach(updatedTextCleaner);
        updatedTextCleaner.type(UPDATED_TYPE).rule(UPDATED_RULE);
        TextCleanerDTO textCleanerDTO = textCleanerMapper.toDto(updatedTextCleaner);

        restTextCleanerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, textCleanerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(textCleanerDTO))
            )
            .andExpect(status().isOk());

        // Validate the TextCleaner in the database
        List<TextCleaner> textCleanerList = textCleanerRepository.findAll();
        assertThat(textCleanerList).hasSize(databaseSizeBeforeUpdate);
        TextCleaner testTextCleaner = textCleanerList.get(textCleanerList.size() - 1);
        assertThat(testTextCleaner.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTextCleaner.getRule()).isEqualTo(UPDATED_RULE);
    }

    @Test
    @Transactional
    void putNonExistingTextCleaner() throws Exception {
        int databaseSizeBeforeUpdate = textCleanerRepository.findAll().size();
        textCleaner.setId(count.incrementAndGet());

        // Create the TextCleaner
        TextCleanerDTO textCleanerDTO = textCleanerMapper.toDto(textCleaner);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTextCleanerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, textCleanerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(textCleanerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TextCleaner in the database
        List<TextCleaner> textCleanerList = textCleanerRepository.findAll();
        assertThat(textCleanerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTextCleaner() throws Exception {
        int databaseSizeBeforeUpdate = textCleanerRepository.findAll().size();
        textCleaner.setId(count.incrementAndGet());

        // Create the TextCleaner
        TextCleanerDTO textCleanerDTO = textCleanerMapper.toDto(textCleaner);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTextCleanerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(textCleanerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TextCleaner in the database
        List<TextCleaner> textCleanerList = textCleanerRepository.findAll();
        assertThat(textCleanerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTextCleaner() throws Exception {
        int databaseSizeBeforeUpdate = textCleanerRepository.findAll().size();
        textCleaner.setId(count.incrementAndGet());

        // Create the TextCleaner
        TextCleanerDTO textCleanerDTO = textCleanerMapper.toDto(textCleaner);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTextCleanerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(textCleanerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TextCleaner in the database
        List<TextCleaner> textCleanerList = textCleanerRepository.findAll();
        assertThat(textCleanerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTextCleanerWithPatch() throws Exception {
        // Initialize the database
        textCleanerRepository.saveAndFlush(textCleaner);

        int databaseSizeBeforeUpdate = textCleanerRepository.findAll().size();

        // Update the textCleaner using partial update
        TextCleaner partialUpdatedTextCleaner = new TextCleaner();
        partialUpdatedTextCleaner.setId(textCleaner.getId());

        restTextCleanerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTextCleaner.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTextCleaner))
            )
            .andExpect(status().isOk());

        // Validate the TextCleaner in the database
        List<TextCleaner> textCleanerList = textCleanerRepository.findAll();
        assertThat(textCleanerList).hasSize(databaseSizeBeforeUpdate);
        TextCleaner testTextCleaner = textCleanerList.get(textCleanerList.size() - 1);
        assertThat(testTextCleaner.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTextCleaner.getRule()).isEqualTo(DEFAULT_RULE);
    }

    @Test
    @Transactional
    void fullUpdateTextCleanerWithPatch() throws Exception {
        // Initialize the database
        textCleanerRepository.saveAndFlush(textCleaner);

        int databaseSizeBeforeUpdate = textCleanerRepository.findAll().size();

        // Update the textCleaner using partial update
        TextCleaner partialUpdatedTextCleaner = new TextCleaner();
        partialUpdatedTextCleaner.setId(textCleaner.getId());

        partialUpdatedTextCleaner.type(UPDATED_TYPE).rule(UPDATED_RULE);

        restTextCleanerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTextCleaner.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTextCleaner))
            )
            .andExpect(status().isOk());

        // Validate the TextCleaner in the database
        List<TextCleaner> textCleanerList = textCleanerRepository.findAll();
        assertThat(textCleanerList).hasSize(databaseSizeBeforeUpdate);
        TextCleaner testTextCleaner = textCleanerList.get(textCleanerList.size() - 1);
        assertThat(testTextCleaner.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTextCleaner.getRule()).isEqualTo(UPDATED_RULE);
    }

    @Test
    @Transactional
    void patchNonExistingTextCleaner() throws Exception {
        int databaseSizeBeforeUpdate = textCleanerRepository.findAll().size();
        textCleaner.setId(count.incrementAndGet());

        // Create the TextCleaner
        TextCleanerDTO textCleanerDTO = textCleanerMapper.toDto(textCleaner);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTextCleanerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, textCleanerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(textCleanerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TextCleaner in the database
        List<TextCleaner> textCleanerList = textCleanerRepository.findAll();
        assertThat(textCleanerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTextCleaner() throws Exception {
        int databaseSizeBeforeUpdate = textCleanerRepository.findAll().size();
        textCleaner.setId(count.incrementAndGet());

        // Create the TextCleaner
        TextCleanerDTO textCleanerDTO = textCleanerMapper.toDto(textCleaner);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTextCleanerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(textCleanerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TextCleaner in the database
        List<TextCleaner> textCleanerList = textCleanerRepository.findAll();
        assertThat(textCleanerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTextCleaner() throws Exception {
        int databaseSizeBeforeUpdate = textCleanerRepository.findAll().size();
        textCleaner.setId(count.incrementAndGet());

        // Create the TextCleaner
        TextCleanerDTO textCleanerDTO = textCleanerMapper.toDto(textCleaner);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTextCleanerMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(textCleanerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TextCleaner in the database
        List<TextCleaner> textCleanerList = textCleanerRepository.findAll();
        assertThat(textCleanerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTextCleaner() throws Exception {
        // Initialize the database
        textCleanerRepository.saveAndFlush(textCleaner);

        int databaseSizeBeforeDelete = textCleanerRepository.findAll().size();

        // Delete the textCleaner
        restTextCleanerMockMvc
            .perform(delete(ENTITY_API_URL_ID, textCleaner.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TextCleaner> textCleanerList = textCleanerRepository.findAll();
        assertThat(textCleanerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
