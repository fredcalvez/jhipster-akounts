package com.akounts.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.akounts.myapp.IntegrationTest;
import com.akounts.myapp.domain.BankTag;
import com.akounts.myapp.repository.BankTagRepository;
import com.akounts.myapp.service.dto.BankTagDTO;
import com.akounts.myapp.service.mapper.BankTagMapper;
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
 * Integration tests for the {@link BankTagResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BankTagResourceIT {

    private static final String DEFAULT_TAG = "AAAAAAAAAA";
    private static final String UPDATED_TAG = "BBBBBBBBBB";

    private static final Integer DEFAULT_USE_COUNT = 1;
    private static final Integer UPDATED_USE_COUNT = 2;

    private static final String ENTITY_API_URL = "/api/bank-tags";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BankTagRepository bankTagRepository;

    @Autowired
    private BankTagMapper bankTagMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBankTagMockMvc;

    private BankTag bankTag;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankTag createEntity(EntityManager em) {
        BankTag bankTag = new BankTag().tag(DEFAULT_TAG).useCount(DEFAULT_USE_COUNT);
        return bankTag;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankTag createUpdatedEntity(EntityManager em) {
        BankTag bankTag = new BankTag().tag(UPDATED_TAG).useCount(UPDATED_USE_COUNT);
        return bankTag;
    }

    @BeforeEach
    public void initTest() {
        bankTag = createEntity(em);
    }

    @Test
    @Transactional
    void createBankTag() throws Exception {
        int databaseSizeBeforeCreate = bankTagRepository.findAll().size();
        // Create the BankTag
        BankTagDTO bankTagDTO = bankTagMapper.toDto(bankTag);
        restBankTagMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankTagDTO)))
            .andExpect(status().isCreated());

        // Validate the BankTag in the database
        List<BankTag> bankTagList = bankTagRepository.findAll();
        assertThat(bankTagList).hasSize(databaseSizeBeforeCreate + 1);
        BankTag testBankTag = bankTagList.get(bankTagList.size() - 1);
        assertThat(testBankTag.getTag()).isEqualTo(DEFAULT_TAG);
        assertThat(testBankTag.getUseCount()).isEqualTo(DEFAULT_USE_COUNT);
    }

    @Test
    @Transactional
    void createBankTagWithExistingId() throws Exception {
        // Create the BankTag with an existing ID
        bankTag.setId(1L);
        BankTagDTO bankTagDTO = bankTagMapper.toDto(bankTag);

        int databaseSizeBeforeCreate = bankTagRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankTagMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankTagDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BankTag in the database
        List<BankTag> bankTagList = bankTagRepository.findAll();
        assertThat(bankTagList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBankTags() throws Exception {
        // Initialize the database
        bankTagRepository.saveAndFlush(bankTag);

        // Get all the bankTagList
        restBankTagMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankTag.getId().intValue())))
            .andExpect(jsonPath("$.[*].tag").value(hasItem(DEFAULT_TAG)))
            .andExpect(jsonPath("$.[*].useCount").value(hasItem(DEFAULT_USE_COUNT)));
    }

    @Test
    @Transactional
    void getBankTag() throws Exception {
        // Initialize the database
        bankTagRepository.saveAndFlush(bankTag);

        // Get the bankTag
        restBankTagMockMvc
            .perform(get(ENTITY_API_URL_ID, bankTag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bankTag.getId().intValue()))
            .andExpect(jsonPath("$.tag").value(DEFAULT_TAG))
            .andExpect(jsonPath("$.useCount").value(DEFAULT_USE_COUNT));
    }

    @Test
    @Transactional
    void getNonExistingBankTag() throws Exception {
        // Get the bankTag
        restBankTagMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBankTag() throws Exception {
        // Initialize the database
        bankTagRepository.saveAndFlush(bankTag);

        int databaseSizeBeforeUpdate = bankTagRepository.findAll().size();

        // Update the bankTag
        BankTag updatedBankTag = bankTagRepository.findById(bankTag.getId()).get();
        // Disconnect from session so that the updates on updatedBankTag are not directly saved in db
        em.detach(updatedBankTag);
        updatedBankTag.tag(UPDATED_TAG).useCount(UPDATED_USE_COUNT);
        BankTagDTO bankTagDTO = bankTagMapper.toDto(updatedBankTag);

        restBankTagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankTagDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankTagDTO))
            )
            .andExpect(status().isOk());

        // Validate the BankTag in the database
        List<BankTag> bankTagList = bankTagRepository.findAll();
        assertThat(bankTagList).hasSize(databaseSizeBeforeUpdate);
        BankTag testBankTag = bankTagList.get(bankTagList.size() - 1);
        assertThat(testBankTag.getTag()).isEqualTo(UPDATED_TAG);
        assertThat(testBankTag.getUseCount()).isEqualTo(UPDATED_USE_COUNT);
    }

    @Test
    @Transactional
    void putNonExistingBankTag() throws Exception {
        int databaseSizeBeforeUpdate = bankTagRepository.findAll().size();
        bankTag.setId(count.incrementAndGet());

        // Create the BankTag
        BankTagDTO bankTagDTO = bankTagMapper.toDto(bankTag);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankTagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankTagDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankTagDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankTag in the database
        List<BankTag> bankTagList = bankTagRepository.findAll();
        assertThat(bankTagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBankTag() throws Exception {
        int databaseSizeBeforeUpdate = bankTagRepository.findAll().size();
        bankTag.setId(count.incrementAndGet());

        // Create the BankTag
        BankTagDTO bankTagDTO = bankTagMapper.toDto(bankTag);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankTagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankTagDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankTag in the database
        List<BankTag> bankTagList = bankTagRepository.findAll();
        assertThat(bankTagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBankTag() throws Exception {
        int databaseSizeBeforeUpdate = bankTagRepository.findAll().size();
        bankTag.setId(count.incrementAndGet());

        // Create the BankTag
        BankTagDTO bankTagDTO = bankTagMapper.toDto(bankTag);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankTagMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankTagDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankTag in the database
        List<BankTag> bankTagList = bankTagRepository.findAll();
        assertThat(bankTagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBankTagWithPatch() throws Exception {
        // Initialize the database
        bankTagRepository.saveAndFlush(bankTag);

        int databaseSizeBeforeUpdate = bankTagRepository.findAll().size();

        // Update the bankTag using partial update
        BankTag partialUpdatedBankTag = new BankTag();
        partialUpdatedBankTag.setId(bankTag.getId());

        partialUpdatedBankTag.tag(UPDATED_TAG);

        restBankTagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankTag.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankTag))
            )
            .andExpect(status().isOk());

        // Validate the BankTag in the database
        List<BankTag> bankTagList = bankTagRepository.findAll();
        assertThat(bankTagList).hasSize(databaseSizeBeforeUpdate);
        BankTag testBankTag = bankTagList.get(bankTagList.size() - 1);
        assertThat(testBankTag.getTag()).isEqualTo(UPDATED_TAG);
        assertThat(testBankTag.getUseCount()).isEqualTo(DEFAULT_USE_COUNT);
    }

    @Test
    @Transactional
    void fullUpdateBankTagWithPatch() throws Exception {
        // Initialize the database
        bankTagRepository.saveAndFlush(bankTag);

        int databaseSizeBeforeUpdate = bankTagRepository.findAll().size();

        // Update the bankTag using partial update
        BankTag partialUpdatedBankTag = new BankTag();
        partialUpdatedBankTag.setId(bankTag.getId());

        partialUpdatedBankTag.tag(UPDATED_TAG).useCount(UPDATED_USE_COUNT);

        restBankTagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankTag.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankTag))
            )
            .andExpect(status().isOk());

        // Validate the BankTag in the database
        List<BankTag> bankTagList = bankTagRepository.findAll();
        assertThat(bankTagList).hasSize(databaseSizeBeforeUpdate);
        BankTag testBankTag = bankTagList.get(bankTagList.size() - 1);
        assertThat(testBankTag.getTag()).isEqualTo(UPDATED_TAG);
        assertThat(testBankTag.getUseCount()).isEqualTo(UPDATED_USE_COUNT);
    }

    @Test
    @Transactional
    void patchNonExistingBankTag() throws Exception {
        int databaseSizeBeforeUpdate = bankTagRepository.findAll().size();
        bankTag.setId(count.incrementAndGet());

        // Create the BankTag
        BankTagDTO bankTagDTO = bankTagMapper.toDto(bankTag);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankTagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bankTagDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankTagDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankTag in the database
        List<BankTag> bankTagList = bankTagRepository.findAll();
        assertThat(bankTagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBankTag() throws Exception {
        int databaseSizeBeforeUpdate = bankTagRepository.findAll().size();
        bankTag.setId(count.incrementAndGet());

        // Create the BankTag
        BankTagDTO bankTagDTO = bankTagMapper.toDto(bankTag);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankTagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankTagDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankTag in the database
        List<BankTag> bankTagList = bankTagRepository.findAll();
        assertThat(bankTagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBankTag() throws Exception {
        int databaseSizeBeforeUpdate = bankTagRepository.findAll().size();
        bankTag.setId(count.incrementAndGet());

        // Create the BankTag
        BankTagDTO bankTagDTO = bankTagMapper.toDto(bankTag);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankTagMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bankTagDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankTag in the database
        List<BankTag> bankTagList = bankTagRepository.findAll();
        assertThat(bankTagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBankTag() throws Exception {
        // Initialize the database
        bankTagRepository.saveAndFlush(bankTag);

        int databaseSizeBeforeDelete = bankTagRepository.findAll().size();

        // Delete the bankTag
        restBankTagMockMvc
            .perform(delete(ENTITY_API_URL_ID, bankTag.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BankTag> bankTagList = bankTagRepository.findAll();
        assertThat(bankTagList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
