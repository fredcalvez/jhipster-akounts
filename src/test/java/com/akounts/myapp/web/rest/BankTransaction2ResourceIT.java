package com.akounts.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.akounts.myapp.IntegrationTest;
import com.akounts.myapp.domain.BankTransaction2;
import com.akounts.myapp.repository.BankTransaction2Repository;
import com.akounts.myapp.service.dto.BankTransaction2DTO;
import com.akounts.myapp.service.mapper.BankTransaction2Mapper;
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
 * Integration tests for the {@link BankTransaction2Resource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BankTransaction2ResourceIT {

    private static final String ENTITY_API_URL = "/api/bank-transaction-2-s";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BankTransaction2Repository bankTransaction2Repository;

    @Autowired
    private BankTransaction2Mapper bankTransaction2Mapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBankTransaction2MockMvc;

    private BankTransaction2 bankTransaction2;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankTransaction2 createEntity(EntityManager em) {
        BankTransaction2 bankTransaction2 = new BankTransaction2();
        return bankTransaction2;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankTransaction2 createUpdatedEntity(EntityManager em) {
        BankTransaction2 bankTransaction2 = new BankTransaction2();
        return bankTransaction2;
    }

    @BeforeEach
    public void initTest() {
        bankTransaction2 = createEntity(em);
    }

    @Test
    @Transactional
    void createBankTransaction2() throws Exception {
        int databaseSizeBeforeCreate = bankTransaction2Repository.findAll().size();
        // Create the BankTransaction2
        BankTransaction2DTO bankTransaction2DTO = bankTransaction2Mapper.toDto(bankTransaction2);
        restBankTransaction2MockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankTransaction2DTO))
            )
            .andExpect(status().isCreated());

        // Validate the BankTransaction2 in the database
        List<BankTransaction2> bankTransaction2List = bankTransaction2Repository.findAll();
        assertThat(bankTransaction2List).hasSize(databaseSizeBeforeCreate + 1);
        BankTransaction2 testBankTransaction2 = bankTransaction2List.get(bankTransaction2List.size() - 1);
    }

    @Test
    @Transactional
    void createBankTransaction2WithExistingId() throws Exception {
        // Create the BankTransaction2 with an existing ID
        bankTransaction2.setId(1L);
        BankTransaction2DTO bankTransaction2DTO = bankTransaction2Mapper.toDto(bankTransaction2);

        int databaseSizeBeforeCreate = bankTransaction2Repository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankTransaction2MockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankTransaction2DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankTransaction2 in the database
        List<BankTransaction2> bankTransaction2List = bankTransaction2Repository.findAll();
        assertThat(bankTransaction2List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBankTransaction2s() throws Exception {
        // Initialize the database
        bankTransaction2Repository.saveAndFlush(bankTransaction2);

        // Get all the bankTransaction2List
        restBankTransaction2MockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankTransaction2.getId().intValue())));
    }

    @Test
    @Transactional
    void getBankTransaction2() throws Exception {
        // Initialize the database
        bankTransaction2Repository.saveAndFlush(bankTransaction2);

        // Get the bankTransaction2
        restBankTransaction2MockMvc
            .perform(get(ENTITY_API_URL_ID, bankTransaction2.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bankTransaction2.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingBankTransaction2() throws Exception {
        // Get the bankTransaction2
        restBankTransaction2MockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBankTransaction2() throws Exception {
        // Initialize the database
        bankTransaction2Repository.saveAndFlush(bankTransaction2);

        int databaseSizeBeforeUpdate = bankTransaction2Repository.findAll().size();

        // Update the bankTransaction2
        BankTransaction2 updatedBankTransaction2 = bankTransaction2Repository.findById(bankTransaction2.getId()).get();
        // Disconnect from session so that the updates on updatedBankTransaction2 are not directly saved in db
        em.detach(updatedBankTransaction2);
        BankTransaction2DTO bankTransaction2DTO = bankTransaction2Mapper.toDto(updatedBankTransaction2);

        restBankTransaction2MockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankTransaction2DTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankTransaction2DTO))
            )
            .andExpect(status().isOk());

        // Validate the BankTransaction2 in the database
        List<BankTransaction2> bankTransaction2List = bankTransaction2Repository.findAll();
        assertThat(bankTransaction2List).hasSize(databaseSizeBeforeUpdate);
        BankTransaction2 testBankTransaction2 = bankTransaction2List.get(bankTransaction2List.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingBankTransaction2() throws Exception {
        int databaseSizeBeforeUpdate = bankTransaction2Repository.findAll().size();
        bankTransaction2.setId(count.incrementAndGet());

        // Create the BankTransaction2
        BankTransaction2DTO bankTransaction2DTO = bankTransaction2Mapper.toDto(bankTransaction2);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankTransaction2MockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankTransaction2DTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankTransaction2DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankTransaction2 in the database
        List<BankTransaction2> bankTransaction2List = bankTransaction2Repository.findAll();
        assertThat(bankTransaction2List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBankTransaction2() throws Exception {
        int databaseSizeBeforeUpdate = bankTransaction2Repository.findAll().size();
        bankTransaction2.setId(count.incrementAndGet());

        // Create the BankTransaction2
        BankTransaction2DTO bankTransaction2DTO = bankTransaction2Mapper.toDto(bankTransaction2);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankTransaction2MockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankTransaction2DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankTransaction2 in the database
        List<BankTransaction2> bankTransaction2List = bankTransaction2Repository.findAll();
        assertThat(bankTransaction2List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBankTransaction2() throws Exception {
        int databaseSizeBeforeUpdate = bankTransaction2Repository.findAll().size();
        bankTransaction2.setId(count.incrementAndGet());

        // Create the BankTransaction2
        BankTransaction2DTO bankTransaction2DTO = bankTransaction2Mapper.toDto(bankTransaction2);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankTransaction2MockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankTransaction2DTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankTransaction2 in the database
        List<BankTransaction2> bankTransaction2List = bankTransaction2Repository.findAll();
        assertThat(bankTransaction2List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBankTransaction2WithPatch() throws Exception {
        // Initialize the database
        bankTransaction2Repository.saveAndFlush(bankTransaction2);

        int databaseSizeBeforeUpdate = bankTransaction2Repository.findAll().size();

        // Update the bankTransaction2 using partial update
        BankTransaction2 partialUpdatedBankTransaction2 = new BankTransaction2();
        partialUpdatedBankTransaction2.setId(bankTransaction2.getId());

        restBankTransaction2MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankTransaction2.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankTransaction2))
            )
            .andExpect(status().isOk());

        // Validate the BankTransaction2 in the database
        List<BankTransaction2> bankTransaction2List = bankTransaction2Repository.findAll();
        assertThat(bankTransaction2List).hasSize(databaseSizeBeforeUpdate);
        BankTransaction2 testBankTransaction2 = bankTransaction2List.get(bankTransaction2List.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateBankTransaction2WithPatch() throws Exception {
        // Initialize the database
        bankTransaction2Repository.saveAndFlush(bankTransaction2);

        int databaseSizeBeforeUpdate = bankTransaction2Repository.findAll().size();

        // Update the bankTransaction2 using partial update
        BankTransaction2 partialUpdatedBankTransaction2 = new BankTransaction2();
        partialUpdatedBankTransaction2.setId(bankTransaction2.getId());

        restBankTransaction2MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankTransaction2.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankTransaction2))
            )
            .andExpect(status().isOk());

        // Validate the BankTransaction2 in the database
        List<BankTransaction2> bankTransaction2List = bankTransaction2Repository.findAll();
        assertThat(bankTransaction2List).hasSize(databaseSizeBeforeUpdate);
        BankTransaction2 testBankTransaction2 = bankTransaction2List.get(bankTransaction2List.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingBankTransaction2() throws Exception {
        int databaseSizeBeforeUpdate = bankTransaction2Repository.findAll().size();
        bankTransaction2.setId(count.incrementAndGet());

        // Create the BankTransaction2
        BankTransaction2DTO bankTransaction2DTO = bankTransaction2Mapper.toDto(bankTransaction2);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankTransaction2MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bankTransaction2DTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankTransaction2DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankTransaction2 in the database
        List<BankTransaction2> bankTransaction2List = bankTransaction2Repository.findAll();
        assertThat(bankTransaction2List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBankTransaction2() throws Exception {
        int databaseSizeBeforeUpdate = bankTransaction2Repository.findAll().size();
        bankTransaction2.setId(count.incrementAndGet());

        // Create the BankTransaction2
        BankTransaction2DTO bankTransaction2DTO = bankTransaction2Mapper.toDto(bankTransaction2);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankTransaction2MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankTransaction2DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankTransaction2 in the database
        List<BankTransaction2> bankTransaction2List = bankTransaction2Repository.findAll();
        assertThat(bankTransaction2List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBankTransaction2() throws Exception {
        int databaseSizeBeforeUpdate = bankTransaction2Repository.findAll().size();
        bankTransaction2.setId(count.incrementAndGet());

        // Create the BankTransaction2
        BankTransaction2DTO bankTransaction2DTO = bankTransaction2Mapper.toDto(bankTransaction2);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankTransaction2MockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankTransaction2DTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankTransaction2 in the database
        List<BankTransaction2> bankTransaction2List = bankTransaction2Repository.findAll();
        assertThat(bankTransaction2List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBankTransaction2() throws Exception {
        // Initialize the database
        bankTransaction2Repository.saveAndFlush(bankTransaction2);

        int databaseSizeBeforeDelete = bankTransaction2Repository.findAll().size();

        // Delete the bankTransaction2
        restBankTransaction2MockMvc
            .perform(delete(ENTITY_API_URL_ID, bankTransaction2.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BankTransaction2> bankTransaction2List = bankTransaction2Repository.findAll();
        assertThat(bankTransaction2List).hasSize(databaseSizeBeforeDelete - 1);
    }
}
