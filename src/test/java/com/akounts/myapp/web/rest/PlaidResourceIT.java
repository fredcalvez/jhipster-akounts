package com.akounts.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.akounts.myapp.IntegrationTest;
import com.akounts.myapp.domain.Plaid;
import com.akounts.myapp.repository.PlaidRepository;
import com.akounts.myapp.service.dto.PlaidDTO;
import com.akounts.myapp.service.mapper.PlaidMapper;
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
 * Integration tests for the {@link PlaidResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlaidResourceIT {

    private static final String ENTITY_API_URL = "/api/plaids";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PlaidRepository plaidRepository;

    @Autowired
    private PlaidMapper plaidMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlaidMockMvc;

    private Plaid plaid;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plaid createEntity(EntityManager em) {
        Plaid plaid = new Plaid();
        return plaid;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plaid createUpdatedEntity(EntityManager em) {
        Plaid plaid = new Plaid();
        return plaid;
    }

    @BeforeEach
    public void initTest() {
        plaid = createEntity(em);
    }

    @Test
    @Transactional
    void createPlaid() throws Exception {
        int databaseSizeBeforeCreate = plaidRepository.findAll().size();
        // Create the Plaid
        PlaidDTO plaidDTO = plaidMapper.toDto(plaid);
        restPlaidMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plaidDTO)))
            .andExpect(status().isCreated());

        // Validate the Plaid in the database
        List<Plaid> plaidList = plaidRepository.findAll();
        assertThat(plaidList).hasSize(databaseSizeBeforeCreate + 1);
        Plaid testPlaid = plaidList.get(plaidList.size() - 1);
    }

    @Test
    @Transactional
    void createPlaidWithExistingId() throws Exception {
        // Create the Plaid with an existing ID
        plaid.setId(1L);
        PlaidDTO plaidDTO = plaidMapper.toDto(plaid);

        int databaseSizeBeforeCreate = plaidRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlaidMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plaidDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Plaid in the database
        List<Plaid> plaidList = plaidRepository.findAll();
        assertThat(plaidList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPlaids() throws Exception {
        // Initialize the database
        plaidRepository.saveAndFlush(plaid);

        // Get all the plaidList
        restPlaidMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plaid.getId().intValue())));
    }

    @Test
    @Transactional
    void getPlaid() throws Exception {
        // Initialize the database
        plaidRepository.saveAndFlush(plaid);

        // Get the plaid
        restPlaidMockMvc
            .perform(get(ENTITY_API_URL_ID, plaid.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(plaid.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingPlaid() throws Exception {
        // Get the plaid
        restPlaidMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPlaid() throws Exception {
        // Initialize the database
        plaidRepository.saveAndFlush(plaid);

        int databaseSizeBeforeUpdate = plaidRepository.findAll().size();

        // Update the plaid
        Plaid updatedPlaid = plaidRepository.findById(plaid.getId()).get();
        // Disconnect from session so that the updates on updatedPlaid are not directly saved in db
        em.detach(updatedPlaid);
        PlaidDTO plaidDTO = plaidMapper.toDto(updatedPlaid);

        restPlaidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, plaidDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plaidDTO))
            )
            .andExpect(status().isOk());

        // Validate the Plaid in the database
        List<Plaid> plaidList = plaidRepository.findAll();
        assertThat(plaidList).hasSize(databaseSizeBeforeUpdate);
        Plaid testPlaid = plaidList.get(plaidList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingPlaid() throws Exception {
        int databaseSizeBeforeUpdate = plaidRepository.findAll().size();
        plaid.setId(count.incrementAndGet());

        // Create the Plaid
        PlaidDTO plaidDTO = plaidMapper.toDto(plaid);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlaidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, plaidDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plaidDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plaid in the database
        List<Plaid> plaidList = plaidRepository.findAll();
        assertThat(plaidList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlaid() throws Exception {
        int databaseSizeBeforeUpdate = plaidRepository.findAll().size();
        plaid.setId(count.incrementAndGet());

        // Create the Plaid
        PlaidDTO plaidDTO = plaidMapper.toDto(plaid);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plaidDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plaid in the database
        List<Plaid> plaidList = plaidRepository.findAll();
        assertThat(plaidList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlaid() throws Exception {
        int databaseSizeBeforeUpdate = plaidRepository.findAll().size();
        plaid.setId(count.incrementAndGet());

        // Create the Plaid
        PlaidDTO plaidDTO = plaidMapper.toDto(plaid);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaidMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plaidDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Plaid in the database
        List<Plaid> plaidList = plaidRepository.findAll();
        assertThat(plaidList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlaidWithPatch() throws Exception {
        // Initialize the database
        plaidRepository.saveAndFlush(plaid);

        int databaseSizeBeforeUpdate = plaidRepository.findAll().size();

        // Update the plaid using partial update
        Plaid partialUpdatedPlaid = new Plaid();
        partialUpdatedPlaid.setId(plaid.getId());

        restPlaidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlaid.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlaid))
            )
            .andExpect(status().isOk());

        // Validate the Plaid in the database
        List<Plaid> plaidList = plaidRepository.findAll();
        assertThat(plaidList).hasSize(databaseSizeBeforeUpdate);
        Plaid testPlaid = plaidList.get(plaidList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdatePlaidWithPatch() throws Exception {
        // Initialize the database
        plaidRepository.saveAndFlush(plaid);

        int databaseSizeBeforeUpdate = plaidRepository.findAll().size();

        // Update the plaid using partial update
        Plaid partialUpdatedPlaid = new Plaid();
        partialUpdatedPlaid.setId(plaid.getId());

        restPlaidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlaid.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlaid))
            )
            .andExpect(status().isOk());

        // Validate the Plaid in the database
        List<Plaid> plaidList = plaidRepository.findAll();
        assertThat(plaidList).hasSize(databaseSizeBeforeUpdate);
        Plaid testPlaid = plaidList.get(plaidList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingPlaid() throws Exception {
        int databaseSizeBeforeUpdate = plaidRepository.findAll().size();
        plaid.setId(count.incrementAndGet());

        // Create the Plaid
        PlaidDTO plaidDTO = plaidMapper.toDto(plaid);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlaidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, plaidDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plaidDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plaid in the database
        List<Plaid> plaidList = plaidRepository.findAll();
        assertThat(plaidList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlaid() throws Exception {
        int databaseSizeBeforeUpdate = plaidRepository.findAll().size();
        plaid.setId(count.incrementAndGet());

        // Create the Plaid
        PlaidDTO plaidDTO = plaidMapper.toDto(plaid);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plaidDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plaid in the database
        List<Plaid> plaidList = plaidRepository.findAll();
        assertThat(plaidList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlaid() throws Exception {
        int databaseSizeBeforeUpdate = plaidRepository.findAll().size();
        plaid.setId(count.incrementAndGet());

        // Create the Plaid
        PlaidDTO plaidDTO = plaidMapper.toDto(plaid);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaidMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(plaidDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Plaid in the database
        List<Plaid> plaidList = plaidRepository.findAll();
        assertThat(plaidList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlaid() throws Exception {
        // Initialize the database
        plaidRepository.saveAndFlush(plaid);

        int databaseSizeBeforeDelete = plaidRepository.findAll().size();

        // Delete the plaid
        restPlaidMockMvc
            .perform(delete(ENTITY_API_URL_ID, plaid.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Plaid> plaidList = plaidRepository.findAll();
        assertThat(plaidList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
