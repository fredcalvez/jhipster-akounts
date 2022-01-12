package com.akounts.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.akounts.myapp.IntegrationTest;
import com.akounts.myapp.domain.PlaidItem;
import com.akounts.myapp.repository.PlaidItemRepository;
import com.akounts.myapp.service.dto.PlaidItemDTO;
import com.akounts.myapp.service.mapper.PlaidItemMapper;
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
 * Integration tests for the {@link PlaidItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlaidItemResourceIT {

    private static final String DEFAULT_ITEM_ID = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_ID = "BBBBBBBBBB";

    private static final String DEFAULT_INSTITUTION_ID = "AAAAAAAAAA";
    private static final String UPDATED_INSTITUTION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ACCESS_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_ACCESS_TOKEN = "BBBBBBBBBB";

    private static final Instant DEFAULT_ADDED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ADDED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/plaid-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PlaidItemRepository plaidItemRepository;

    @Autowired
    private PlaidItemMapper plaidItemMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlaidItemMockMvc;

    private PlaidItem plaidItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlaidItem createEntity(EntityManager em) {
        PlaidItem plaidItem = new PlaidItem()
            .itemId(DEFAULT_ITEM_ID)
            .institutionId(DEFAULT_INSTITUTION_ID)
            .accessToken(DEFAULT_ACCESS_TOKEN)
            .addedDate(DEFAULT_ADDED_DATE)
            .updateDate(DEFAULT_UPDATE_DATE);
        return plaidItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlaidItem createUpdatedEntity(EntityManager em) {
        PlaidItem plaidItem = new PlaidItem()
            .itemId(UPDATED_ITEM_ID)
            .institutionId(UPDATED_INSTITUTION_ID)
            .accessToken(UPDATED_ACCESS_TOKEN)
            .addedDate(UPDATED_ADDED_DATE)
            .updateDate(UPDATED_UPDATE_DATE);
        return plaidItem;
    }

    @BeforeEach
    public void initTest() {
        plaidItem = createEntity(em);
    }

    @Test
    @Transactional
    void createPlaidItem() throws Exception {
        int databaseSizeBeforeCreate = plaidItemRepository.findAll().size();
        // Create the PlaidItem
        PlaidItemDTO plaidItemDTO = plaidItemMapper.toDto(plaidItem);
        restPlaidItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plaidItemDTO)))
            .andExpect(status().isCreated());

        // Validate the PlaidItem in the database
        List<PlaidItem> plaidItemList = plaidItemRepository.findAll();
        assertThat(plaidItemList).hasSize(databaseSizeBeforeCreate + 1);
        PlaidItem testPlaidItem = plaidItemList.get(plaidItemList.size() - 1);
        assertThat(testPlaidItem.getItemId()).isEqualTo(DEFAULT_ITEM_ID);
        assertThat(testPlaidItem.getInstitutionId()).isEqualTo(DEFAULT_INSTITUTION_ID);
        assertThat(testPlaidItem.getAccessToken()).isEqualTo(DEFAULT_ACCESS_TOKEN);
        assertThat(testPlaidItem.getAddedDate()).isEqualTo(DEFAULT_ADDED_DATE);
        assertThat(testPlaidItem.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    void createPlaidItemWithExistingId() throws Exception {
        // Create the PlaidItem with an existing ID
        plaidItem.setId(1L);
        PlaidItemDTO plaidItemDTO = plaidItemMapper.toDto(plaidItem);

        int databaseSizeBeforeCreate = plaidItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlaidItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plaidItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlaidItem in the database
        List<PlaidItem> plaidItemList = plaidItemRepository.findAll();
        assertThat(plaidItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPlaidItems() throws Exception {
        // Initialize the database
        plaidItemRepository.saveAndFlush(plaidItem);

        // Get all the plaidItemList
        restPlaidItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plaidItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].itemId").value(hasItem(DEFAULT_ITEM_ID)))
            .andExpect(jsonPath("$.[*].institutionId").value(hasItem(DEFAULT_INSTITUTION_ID)))
            .andExpect(jsonPath("$.[*].accessToken").value(hasItem(DEFAULT_ACCESS_TOKEN)))
            .andExpect(jsonPath("$.[*].addedDate").value(hasItem(DEFAULT_ADDED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }

    @Test
    @Transactional
    void getPlaidItem() throws Exception {
        // Initialize the database
        plaidItemRepository.saveAndFlush(plaidItem);

        // Get the plaidItem
        restPlaidItemMockMvc
            .perform(get(ENTITY_API_URL_ID, plaidItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(plaidItem.getId().intValue()))
            .andExpect(jsonPath("$.itemId").value(DEFAULT_ITEM_ID))
            .andExpect(jsonPath("$.institutionId").value(DEFAULT_INSTITUTION_ID))
            .andExpect(jsonPath("$.accessToken").value(DEFAULT_ACCESS_TOKEN))
            .andExpect(jsonPath("$.addedDate").value(DEFAULT_ADDED_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPlaidItem() throws Exception {
        // Get the plaidItem
        restPlaidItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPlaidItem() throws Exception {
        // Initialize the database
        plaidItemRepository.saveAndFlush(plaidItem);

        int databaseSizeBeforeUpdate = plaidItemRepository.findAll().size();

        // Update the plaidItem
        PlaidItem updatedPlaidItem = plaidItemRepository.findById(plaidItem.getId()).get();
        // Disconnect from session so that the updates on updatedPlaidItem are not directly saved in db
        em.detach(updatedPlaidItem);
        updatedPlaidItem
            .itemId(UPDATED_ITEM_ID)
            .institutionId(UPDATED_INSTITUTION_ID)
            .accessToken(UPDATED_ACCESS_TOKEN)
            .addedDate(UPDATED_ADDED_DATE)
            .updateDate(UPDATED_UPDATE_DATE);
        PlaidItemDTO plaidItemDTO = plaidItemMapper.toDto(updatedPlaidItem);

        restPlaidItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, plaidItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plaidItemDTO))
            )
            .andExpect(status().isOk());

        // Validate the PlaidItem in the database
        List<PlaidItem> plaidItemList = plaidItemRepository.findAll();
        assertThat(plaidItemList).hasSize(databaseSizeBeforeUpdate);
        PlaidItem testPlaidItem = plaidItemList.get(plaidItemList.size() - 1);
        assertThat(testPlaidItem.getItemId()).isEqualTo(UPDATED_ITEM_ID);
        assertThat(testPlaidItem.getInstitutionId()).isEqualTo(UPDATED_INSTITUTION_ID);
        assertThat(testPlaidItem.getAccessToken()).isEqualTo(UPDATED_ACCESS_TOKEN);
        assertThat(testPlaidItem.getAddedDate()).isEqualTo(UPDATED_ADDED_DATE);
        assertThat(testPlaidItem.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void putNonExistingPlaidItem() throws Exception {
        int databaseSizeBeforeUpdate = plaidItemRepository.findAll().size();
        plaidItem.setId(count.incrementAndGet());

        // Create the PlaidItem
        PlaidItemDTO plaidItemDTO = plaidItemMapper.toDto(plaidItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlaidItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, plaidItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plaidItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlaidItem in the database
        List<PlaidItem> plaidItemList = plaidItemRepository.findAll();
        assertThat(plaidItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlaidItem() throws Exception {
        int databaseSizeBeforeUpdate = plaidItemRepository.findAll().size();
        plaidItem.setId(count.incrementAndGet());

        // Create the PlaidItem
        PlaidItemDTO plaidItemDTO = plaidItemMapper.toDto(plaidItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaidItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plaidItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlaidItem in the database
        List<PlaidItem> plaidItemList = plaidItemRepository.findAll();
        assertThat(plaidItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlaidItem() throws Exception {
        int databaseSizeBeforeUpdate = plaidItemRepository.findAll().size();
        plaidItem.setId(count.incrementAndGet());

        // Create the PlaidItem
        PlaidItemDTO plaidItemDTO = plaidItemMapper.toDto(plaidItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaidItemMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plaidItemDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlaidItem in the database
        List<PlaidItem> plaidItemList = plaidItemRepository.findAll();
        assertThat(plaidItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlaidItemWithPatch() throws Exception {
        // Initialize the database
        plaidItemRepository.saveAndFlush(plaidItem);

        int databaseSizeBeforeUpdate = plaidItemRepository.findAll().size();

        // Update the plaidItem using partial update
        PlaidItem partialUpdatedPlaidItem = new PlaidItem();
        partialUpdatedPlaidItem.setId(plaidItem.getId());

        partialUpdatedPlaidItem
            .institutionId(UPDATED_INSTITUTION_ID)
            .accessToken(UPDATED_ACCESS_TOKEN)
            .addedDate(UPDATED_ADDED_DATE)
            .updateDate(UPDATED_UPDATE_DATE);

        restPlaidItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlaidItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlaidItem))
            )
            .andExpect(status().isOk());

        // Validate the PlaidItem in the database
        List<PlaidItem> plaidItemList = plaidItemRepository.findAll();
        assertThat(plaidItemList).hasSize(databaseSizeBeforeUpdate);
        PlaidItem testPlaidItem = plaidItemList.get(plaidItemList.size() - 1);
        assertThat(testPlaidItem.getItemId()).isEqualTo(DEFAULT_ITEM_ID);
        assertThat(testPlaidItem.getInstitutionId()).isEqualTo(UPDATED_INSTITUTION_ID);
        assertThat(testPlaidItem.getAccessToken()).isEqualTo(UPDATED_ACCESS_TOKEN);
        assertThat(testPlaidItem.getAddedDate()).isEqualTo(UPDATED_ADDED_DATE);
        assertThat(testPlaidItem.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void fullUpdatePlaidItemWithPatch() throws Exception {
        // Initialize the database
        plaidItemRepository.saveAndFlush(plaidItem);

        int databaseSizeBeforeUpdate = plaidItemRepository.findAll().size();

        // Update the plaidItem using partial update
        PlaidItem partialUpdatedPlaidItem = new PlaidItem();
        partialUpdatedPlaidItem.setId(plaidItem.getId());

        partialUpdatedPlaidItem
            .itemId(UPDATED_ITEM_ID)
            .institutionId(UPDATED_INSTITUTION_ID)
            .accessToken(UPDATED_ACCESS_TOKEN)
            .addedDate(UPDATED_ADDED_DATE)
            .updateDate(UPDATED_UPDATE_DATE);

        restPlaidItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlaidItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlaidItem))
            )
            .andExpect(status().isOk());

        // Validate the PlaidItem in the database
        List<PlaidItem> plaidItemList = plaidItemRepository.findAll();
        assertThat(plaidItemList).hasSize(databaseSizeBeforeUpdate);
        PlaidItem testPlaidItem = plaidItemList.get(plaidItemList.size() - 1);
        assertThat(testPlaidItem.getItemId()).isEqualTo(UPDATED_ITEM_ID);
        assertThat(testPlaidItem.getInstitutionId()).isEqualTo(UPDATED_INSTITUTION_ID);
        assertThat(testPlaidItem.getAccessToken()).isEqualTo(UPDATED_ACCESS_TOKEN);
        assertThat(testPlaidItem.getAddedDate()).isEqualTo(UPDATED_ADDED_DATE);
        assertThat(testPlaidItem.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingPlaidItem() throws Exception {
        int databaseSizeBeforeUpdate = plaidItemRepository.findAll().size();
        plaidItem.setId(count.incrementAndGet());

        // Create the PlaidItem
        PlaidItemDTO plaidItemDTO = plaidItemMapper.toDto(plaidItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlaidItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, plaidItemDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plaidItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlaidItem in the database
        List<PlaidItem> plaidItemList = plaidItemRepository.findAll();
        assertThat(plaidItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlaidItem() throws Exception {
        int databaseSizeBeforeUpdate = plaidItemRepository.findAll().size();
        plaidItem.setId(count.incrementAndGet());

        // Create the PlaidItem
        PlaidItemDTO plaidItemDTO = plaidItemMapper.toDto(plaidItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaidItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plaidItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlaidItem in the database
        List<PlaidItem> plaidItemList = plaidItemRepository.findAll();
        assertThat(plaidItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlaidItem() throws Exception {
        int databaseSizeBeforeUpdate = plaidItemRepository.findAll().size();
        plaidItem.setId(count.incrementAndGet());

        // Create the PlaidItem
        PlaidItemDTO plaidItemDTO = plaidItemMapper.toDto(plaidItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaidItemMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(plaidItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlaidItem in the database
        List<PlaidItem> plaidItemList = plaidItemRepository.findAll();
        assertThat(plaidItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlaidItem() throws Exception {
        // Initialize the database
        plaidItemRepository.saveAndFlush(plaidItem);

        int databaseSizeBeforeDelete = plaidItemRepository.findAll().size();

        // Delete the plaidItem
        restPlaidItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, plaidItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlaidItem> plaidItemList = plaidItemRepository.findAll();
        assertThat(plaidItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
