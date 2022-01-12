package com.akounts.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.akounts.myapp.IntegrationTest;
import com.akounts.myapp.domain.Bridge;
import com.akounts.myapp.repository.BridgeRepository;
import com.akounts.myapp.service.dto.BridgeDTO;
import com.akounts.myapp.service.mapper.BridgeMapper;
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
 * Integration tests for the {@link BridgeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BridgeResourceIT {

    private static final String ENTITY_API_URL = "/api/bridges";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BridgeRepository bridgeRepository;

    @Autowired
    private BridgeMapper bridgeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBridgeMockMvc;

    private Bridge bridge;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bridge createEntity(EntityManager em) {
        Bridge bridge = new Bridge();
        return bridge;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bridge createUpdatedEntity(EntityManager em) {
        Bridge bridge = new Bridge();
        return bridge;
    }

    @BeforeEach
    public void initTest() {
        bridge = createEntity(em);
    }

    @Test
    @Transactional
    void createBridge() throws Exception {
        int databaseSizeBeforeCreate = bridgeRepository.findAll().size();
        // Create the Bridge
        BridgeDTO bridgeDTO = bridgeMapper.toDto(bridge);
        restBridgeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bridgeDTO)))
            .andExpect(status().isCreated());

        // Validate the Bridge in the database
        List<Bridge> bridgeList = bridgeRepository.findAll();
        assertThat(bridgeList).hasSize(databaseSizeBeforeCreate + 1);
        Bridge testBridge = bridgeList.get(bridgeList.size() - 1);
    }

    @Test
    @Transactional
    void createBridgeWithExistingId() throws Exception {
        // Create the Bridge with an existing ID
        bridge.setId(1L);
        BridgeDTO bridgeDTO = bridgeMapper.toDto(bridge);

        int databaseSizeBeforeCreate = bridgeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBridgeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bridgeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bridge in the database
        List<Bridge> bridgeList = bridgeRepository.findAll();
        assertThat(bridgeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBridges() throws Exception {
        // Initialize the database
        bridgeRepository.saveAndFlush(bridge);

        // Get all the bridgeList
        restBridgeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bridge.getId().intValue())));
    }

    @Test
    @Transactional
    void getBridge() throws Exception {
        // Initialize the database
        bridgeRepository.saveAndFlush(bridge);

        // Get the bridge
        restBridgeMockMvc
            .perform(get(ENTITY_API_URL_ID, bridge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bridge.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingBridge() throws Exception {
        // Get the bridge
        restBridgeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBridge() throws Exception {
        // Initialize the database
        bridgeRepository.saveAndFlush(bridge);

        int databaseSizeBeforeUpdate = bridgeRepository.findAll().size();

        // Update the bridge
        Bridge updatedBridge = bridgeRepository.findById(bridge.getId()).get();
        // Disconnect from session so that the updates on updatedBridge are not directly saved in db
        em.detach(updatedBridge);
        BridgeDTO bridgeDTO = bridgeMapper.toDto(updatedBridge);

        restBridgeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bridgeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bridgeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Bridge in the database
        List<Bridge> bridgeList = bridgeRepository.findAll();
        assertThat(bridgeList).hasSize(databaseSizeBeforeUpdate);
        Bridge testBridge = bridgeList.get(bridgeList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingBridge() throws Exception {
        int databaseSizeBeforeUpdate = bridgeRepository.findAll().size();
        bridge.setId(count.incrementAndGet());

        // Create the Bridge
        BridgeDTO bridgeDTO = bridgeMapper.toDto(bridge);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBridgeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bridgeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bridgeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bridge in the database
        List<Bridge> bridgeList = bridgeRepository.findAll();
        assertThat(bridgeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBridge() throws Exception {
        int databaseSizeBeforeUpdate = bridgeRepository.findAll().size();
        bridge.setId(count.incrementAndGet());

        // Create the Bridge
        BridgeDTO bridgeDTO = bridgeMapper.toDto(bridge);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBridgeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bridgeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bridge in the database
        List<Bridge> bridgeList = bridgeRepository.findAll();
        assertThat(bridgeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBridge() throws Exception {
        int databaseSizeBeforeUpdate = bridgeRepository.findAll().size();
        bridge.setId(count.incrementAndGet());

        // Create the Bridge
        BridgeDTO bridgeDTO = bridgeMapper.toDto(bridge);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBridgeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bridgeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bridge in the database
        List<Bridge> bridgeList = bridgeRepository.findAll();
        assertThat(bridgeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBridgeWithPatch() throws Exception {
        // Initialize the database
        bridgeRepository.saveAndFlush(bridge);

        int databaseSizeBeforeUpdate = bridgeRepository.findAll().size();

        // Update the bridge using partial update
        Bridge partialUpdatedBridge = new Bridge();
        partialUpdatedBridge.setId(bridge.getId());

        restBridgeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBridge.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBridge))
            )
            .andExpect(status().isOk());

        // Validate the Bridge in the database
        List<Bridge> bridgeList = bridgeRepository.findAll();
        assertThat(bridgeList).hasSize(databaseSizeBeforeUpdate);
        Bridge testBridge = bridgeList.get(bridgeList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateBridgeWithPatch() throws Exception {
        // Initialize the database
        bridgeRepository.saveAndFlush(bridge);

        int databaseSizeBeforeUpdate = bridgeRepository.findAll().size();

        // Update the bridge using partial update
        Bridge partialUpdatedBridge = new Bridge();
        partialUpdatedBridge.setId(bridge.getId());

        restBridgeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBridge.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBridge))
            )
            .andExpect(status().isOk());

        // Validate the Bridge in the database
        List<Bridge> bridgeList = bridgeRepository.findAll();
        assertThat(bridgeList).hasSize(databaseSizeBeforeUpdate);
        Bridge testBridge = bridgeList.get(bridgeList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingBridge() throws Exception {
        int databaseSizeBeforeUpdate = bridgeRepository.findAll().size();
        bridge.setId(count.incrementAndGet());

        // Create the Bridge
        BridgeDTO bridgeDTO = bridgeMapper.toDto(bridge);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBridgeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bridgeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bridgeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bridge in the database
        List<Bridge> bridgeList = bridgeRepository.findAll();
        assertThat(bridgeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBridge() throws Exception {
        int databaseSizeBeforeUpdate = bridgeRepository.findAll().size();
        bridge.setId(count.incrementAndGet());

        // Create the Bridge
        BridgeDTO bridgeDTO = bridgeMapper.toDto(bridge);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBridgeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bridgeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bridge in the database
        List<Bridge> bridgeList = bridgeRepository.findAll();
        assertThat(bridgeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBridge() throws Exception {
        int databaseSizeBeforeUpdate = bridgeRepository.findAll().size();
        bridge.setId(count.incrementAndGet());

        // Create the Bridge
        BridgeDTO bridgeDTO = bridgeMapper.toDto(bridge);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBridgeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bridgeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bridge in the database
        List<Bridge> bridgeList = bridgeRepository.findAll();
        assertThat(bridgeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBridge() throws Exception {
        // Initialize the database
        bridgeRepository.saveAndFlush(bridge);

        int databaseSizeBeforeDelete = bridgeRepository.findAll().size();

        // Delete the bridge
        restBridgeMockMvc
            .perform(delete(ENTITY_API_URL_ID, bridge.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bridge> bridgeList = bridgeRepository.findAll();
        assertThat(bridgeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
