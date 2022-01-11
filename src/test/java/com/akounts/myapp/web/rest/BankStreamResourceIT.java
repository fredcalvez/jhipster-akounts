package com.akounts.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.akounts.myapp.IntegrationTest;
import com.akounts.myapp.domain.BankStream;
import com.akounts.myapp.repository.BankStreamRepository;
import com.akounts.myapp.service.dto.BankStreamDTO;
import com.akounts.myapp.service.mapper.BankStreamMapper;
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
 * Integration tests for the {@link BankStreamResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BankStreamResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STREAM_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_STREAM_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bank-streams";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BankStreamRepository bankStreamRepository;

    @Autowired
    private BankStreamMapper bankStreamMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBankStreamMockMvc;

    private BankStream bankStream;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankStream createEntity(EntityManager em) {
        BankStream bankStream = new BankStream().name(DEFAULT_NAME).streamType(DEFAULT_STREAM_TYPE);
        return bankStream;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankStream createUpdatedEntity(EntityManager em) {
        BankStream bankStream = new BankStream().name(UPDATED_NAME).streamType(UPDATED_STREAM_TYPE);
        return bankStream;
    }

    @BeforeEach
    public void initTest() {
        bankStream = createEntity(em);
    }

    @Test
    @Transactional
    void createBankStream() throws Exception {
        int databaseSizeBeforeCreate = bankStreamRepository.findAll().size();
        // Create the BankStream
        BankStreamDTO bankStreamDTO = bankStreamMapper.toDto(bankStream);
        restBankStreamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankStreamDTO)))
            .andExpect(status().isCreated());

        // Validate the BankStream in the database
        List<BankStream> bankStreamList = bankStreamRepository.findAll();
        assertThat(bankStreamList).hasSize(databaseSizeBeforeCreate + 1);
        BankStream testBankStream = bankStreamList.get(bankStreamList.size() - 1);
        assertThat(testBankStream.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBankStream.getStreamType()).isEqualTo(DEFAULT_STREAM_TYPE);
    }

    @Test
    @Transactional
    void createBankStreamWithExistingId() throws Exception {
        // Create the BankStream with an existing ID
        bankStream.setId(1L);
        BankStreamDTO bankStreamDTO = bankStreamMapper.toDto(bankStream);

        int databaseSizeBeforeCreate = bankStreamRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankStreamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankStreamDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BankStream in the database
        List<BankStream> bankStreamList = bankStreamRepository.findAll();
        assertThat(bankStreamList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBankStreams() throws Exception {
        // Initialize the database
        bankStreamRepository.saveAndFlush(bankStream);

        // Get all the bankStreamList
        restBankStreamMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankStream.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].streamType").value(hasItem(DEFAULT_STREAM_TYPE)));
    }

    @Test
    @Transactional
    void getBankStream() throws Exception {
        // Initialize the database
        bankStreamRepository.saveAndFlush(bankStream);

        // Get the bankStream
        restBankStreamMockMvc
            .perform(get(ENTITY_API_URL_ID, bankStream.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bankStream.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.streamType").value(DEFAULT_STREAM_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingBankStream() throws Exception {
        // Get the bankStream
        restBankStreamMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBankStream() throws Exception {
        // Initialize the database
        bankStreamRepository.saveAndFlush(bankStream);

        int databaseSizeBeforeUpdate = bankStreamRepository.findAll().size();

        // Update the bankStream
        BankStream updatedBankStream = bankStreamRepository.findById(bankStream.getId()).get();
        // Disconnect from session so that the updates on updatedBankStream are not directly saved in db
        em.detach(updatedBankStream);
        updatedBankStream.name(UPDATED_NAME).streamType(UPDATED_STREAM_TYPE);
        BankStreamDTO bankStreamDTO = bankStreamMapper.toDto(updatedBankStream);

        restBankStreamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankStreamDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankStreamDTO))
            )
            .andExpect(status().isOk());

        // Validate the BankStream in the database
        List<BankStream> bankStreamList = bankStreamRepository.findAll();
        assertThat(bankStreamList).hasSize(databaseSizeBeforeUpdate);
        BankStream testBankStream = bankStreamList.get(bankStreamList.size() - 1);
        assertThat(testBankStream.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBankStream.getStreamType()).isEqualTo(UPDATED_STREAM_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingBankStream() throws Exception {
        int databaseSizeBeforeUpdate = bankStreamRepository.findAll().size();
        bankStream.setId(count.incrementAndGet());

        // Create the BankStream
        BankStreamDTO bankStreamDTO = bankStreamMapper.toDto(bankStream);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankStreamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankStreamDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankStreamDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankStream in the database
        List<BankStream> bankStreamList = bankStreamRepository.findAll();
        assertThat(bankStreamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBankStream() throws Exception {
        int databaseSizeBeforeUpdate = bankStreamRepository.findAll().size();
        bankStream.setId(count.incrementAndGet());

        // Create the BankStream
        BankStreamDTO bankStreamDTO = bankStreamMapper.toDto(bankStream);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankStreamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankStreamDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankStream in the database
        List<BankStream> bankStreamList = bankStreamRepository.findAll();
        assertThat(bankStreamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBankStream() throws Exception {
        int databaseSizeBeforeUpdate = bankStreamRepository.findAll().size();
        bankStream.setId(count.incrementAndGet());

        // Create the BankStream
        BankStreamDTO bankStreamDTO = bankStreamMapper.toDto(bankStream);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankStreamMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankStreamDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankStream in the database
        List<BankStream> bankStreamList = bankStreamRepository.findAll();
        assertThat(bankStreamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBankStreamWithPatch() throws Exception {
        // Initialize the database
        bankStreamRepository.saveAndFlush(bankStream);

        int databaseSizeBeforeUpdate = bankStreamRepository.findAll().size();

        // Update the bankStream using partial update
        BankStream partialUpdatedBankStream = new BankStream();
        partialUpdatedBankStream.setId(bankStream.getId());

        partialUpdatedBankStream.name(UPDATED_NAME).streamType(UPDATED_STREAM_TYPE);

        restBankStreamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankStream.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankStream))
            )
            .andExpect(status().isOk());

        // Validate the BankStream in the database
        List<BankStream> bankStreamList = bankStreamRepository.findAll();
        assertThat(bankStreamList).hasSize(databaseSizeBeforeUpdate);
        BankStream testBankStream = bankStreamList.get(bankStreamList.size() - 1);
        assertThat(testBankStream.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBankStream.getStreamType()).isEqualTo(UPDATED_STREAM_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateBankStreamWithPatch() throws Exception {
        // Initialize the database
        bankStreamRepository.saveAndFlush(bankStream);

        int databaseSizeBeforeUpdate = bankStreamRepository.findAll().size();

        // Update the bankStream using partial update
        BankStream partialUpdatedBankStream = new BankStream();
        partialUpdatedBankStream.setId(bankStream.getId());

        partialUpdatedBankStream.name(UPDATED_NAME).streamType(UPDATED_STREAM_TYPE);

        restBankStreamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankStream.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankStream))
            )
            .andExpect(status().isOk());

        // Validate the BankStream in the database
        List<BankStream> bankStreamList = bankStreamRepository.findAll();
        assertThat(bankStreamList).hasSize(databaseSizeBeforeUpdate);
        BankStream testBankStream = bankStreamList.get(bankStreamList.size() - 1);
        assertThat(testBankStream.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBankStream.getStreamType()).isEqualTo(UPDATED_STREAM_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingBankStream() throws Exception {
        int databaseSizeBeforeUpdate = bankStreamRepository.findAll().size();
        bankStream.setId(count.incrementAndGet());

        // Create the BankStream
        BankStreamDTO bankStreamDTO = bankStreamMapper.toDto(bankStream);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankStreamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bankStreamDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankStreamDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankStream in the database
        List<BankStream> bankStreamList = bankStreamRepository.findAll();
        assertThat(bankStreamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBankStream() throws Exception {
        int databaseSizeBeforeUpdate = bankStreamRepository.findAll().size();
        bankStream.setId(count.incrementAndGet());

        // Create the BankStream
        BankStreamDTO bankStreamDTO = bankStreamMapper.toDto(bankStream);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankStreamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankStreamDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankStream in the database
        List<BankStream> bankStreamList = bankStreamRepository.findAll();
        assertThat(bankStreamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBankStream() throws Exception {
        int databaseSizeBeforeUpdate = bankStreamRepository.findAll().size();
        bankStream.setId(count.incrementAndGet());

        // Create the BankStream
        BankStreamDTO bankStreamDTO = bankStreamMapper.toDto(bankStream);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankStreamMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bankStreamDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankStream in the database
        List<BankStream> bankStreamList = bankStreamRepository.findAll();
        assertThat(bankStreamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBankStream() throws Exception {
        // Initialize the database
        bankStreamRepository.saveAndFlush(bankStream);

        int databaseSizeBeforeDelete = bankStreamRepository.findAll().size();

        // Delete the bankStream
        restBankStreamMockMvc
            .perform(delete(ENTITY_API_URL_ID, bankStream.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BankStream> bankStreamList = bankStreamRepository.findAll();
        assertThat(bankStreamList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
