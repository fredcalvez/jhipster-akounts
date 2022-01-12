package com.akounts.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.akounts.myapp.IntegrationTest;
import com.akounts.myapp.domain.BridgeUser;
import com.akounts.myapp.repository.BridgeUserRepository;
import com.akounts.myapp.service.dto.BridgeUserDTO;
import com.akounts.myapp.service.mapper.BridgeUserMapper;
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
 * Integration tests for the {@link BridgeUserResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BridgeUserResourceIT {

    private static final String DEFAULT_UUID = "AAAAAAAAAA";
    private static final String UPDATED_UUID = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PASS = "AAAAAAAAAA";
    private static final String UPDATED_PASS = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_LOGIN_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_LOGIN_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/bridge-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BridgeUserRepository bridgeUserRepository;

    @Autowired
    private BridgeUserMapper bridgeUserMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBridgeUserMockMvc;

    private BridgeUser bridgeUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BridgeUser createEntity(EntityManager em) {
        BridgeUser bridgeUser = new BridgeUser()
            .uuid(DEFAULT_UUID)
            .email(DEFAULT_EMAIL)
            .pass(DEFAULT_PASS)
            .lastLoginDate(DEFAULT_LAST_LOGIN_DATE);
        return bridgeUser;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BridgeUser createUpdatedEntity(EntityManager em) {
        BridgeUser bridgeUser = new BridgeUser()
            .uuid(UPDATED_UUID)
            .email(UPDATED_EMAIL)
            .pass(UPDATED_PASS)
            .lastLoginDate(UPDATED_LAST_LOGIN_DATE);
        return bridgeUser;
    }

    @BeforeEach
    public void initTest() {
        bridgeUser = createEntity(em);
    }

    @Test
    @Transactional
    void createBridgeUser() throws Exception {
        int databaseSizeBeforeCreate = bridgeUserRepository.findAll().size();
        // Create the BridgeUser
        BridgeUserDTO bridgeUserDTO = bridgeUserMapper.toDto(bridgeUser);
        restBridgeUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bridgeUserDTO)))
            .andExpect(status().isCreated());

        // Validate the BridgeUser in the database
        List<BridgeUser> bridgeUserList = bridgeUserRepository.findAll();
        assertThat(bridgeUserList).hasSize(databaseSizeBeforeCreate + 1);
        BridgeUser testBridgeUser = bridgeUserList.get(bridgeUserList.size() - 1);
        assertThat(testBridgeUser.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testBridgeUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testBridgeUser.getPass()).isEqualTo(DEFAULT_PASS);
        assertThat(testBridgeUser.getLastLoginDate()).isEqualTo(DEFAULT_LAST_LOGIN_DATE);
    }

    @Test
    @Transactional
    void createBridgeUserWithExistingId() throws Exception {
        // Create the BridgeUser with an existing ID
        bridgeUser.setId(1L);
        BridgeUserDTO bridgeUserDTO = bridgeUserMapper.toDto(bridgeUser);

        int databaseSizeBeforeCreate = bridgeUserRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBridgeUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bridgeUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BridgeUser in the database
        List<BridgeUser> bridgeUserList = bridgeUserRepository.findAll();
        assertThat(bridgeUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBridgeUsers() throws Exception {
        // Initialize the database
        bridgeUserRepository.saveAndFlush(bridgeUser);

        // Get all the bridgeUserList
        restBridgeUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bridgeUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].pass").value(hasItem(DEFAULT_PASS)))
            .andExpect(jsonPath("$.[*].lastLoginDate").value(hasItem(DEFAULT_LAST_LOGIN_DATE.toString())));
    }

    @Test
    @Transactional
    void getBridgeUser() throws Exception {
        // Initialize the database
        bridgeUserRepository.saveAndFlush(bridgeUser);

        // Get the bridgeUser
        restBridgeUserMockMvc
            .perform(get(ENTITY_API_URL_ID, bridgeUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bridgeUser.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.pass").value(DEFAULT_PASS))
            .andExpect(jsonPath("$.lastLoginDate").value(DEFAULT_LAST_LOGIN_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingBridgeUser() throws Exception {
        // Get the bridgeUser
        restBridgeUserMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBridgeUser() throws Exception {
        // Initialize the database
        bridgeUserRepository.saveAndFlush(bridgeUser);

        int databaseSizeBeforeUpdate = bridgeUserRepository.findAll().size();

        // Update the bridgeUser
        BridgeUser updatedBridgeUser = bridgeUserRepository.findById(bridgeUser.getId()).get();
        // Disconnect from session so that the updates on updatedBridgeUser are not directly saved in db
        em.detach(updatedBridgeUser);
        updatedBridgeUser.uuid(UPDATED_UUID).email(UPDATED_EMAIL).pass(UPDATED_PASS).lastLoginDate(UPDATED_LAST_LOGIN_DATE);
        BridgeUserDTO bridgeUserDTO = bridgeUserMapper.toDto(updatedBridgeUser);

        restBridgeUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bridgeUserDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bridgeUserDTO))
            )
            .andExpect(status().isOk());

        // Validate the BridgeUser in the database
        List<BridgeUser> bridgeUserList = bridgeUserRepository.findAll();
        assertThat(bridgeUserList).hasSize(databaseSizeBeforeUpdate);
        BridgeUser testBridgeUser = bridgeUserList.get(bridgeUserList.size() - 1);
        assertThat(testBridgeUser.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testBridgeUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testBridgeUser.getPass()).isEqualTo(UPDATED_PASS);
        assertThat(testBridgeUser.getLastLoginDate()).isEqualTo(UPDATED_LAST_LOGIN_DATE);
    }

    @Test
    @Transactional
    void putNonExistingBridgeUser() throws Exception {
        int databaseSizeBeforeUpdate = bridgeUserRepository.findAll().size();
        bridgeUser.setId(count.incrementAndGet());

        // Create the BridgeUser
        BridgeUserDTO bridgeUserDTO = bridgeUserMapper.toDto(bridgeUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBridgeUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bridgeUserDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bridgeUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BridgeUser in the database
        List<BridgeUser> bridgeUserList = bridgeUserRepository.findAll();
        assertThat(bridgeUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBridgeUser() throws Exception {
        int databaseSizeBeforeUpdate = bridgeUserRepository.findAll().size();
        bridgeUser.setId(count.incrementAndGet());

        // Create the BridgeUser
        BridgeUserDTO bridgeUserDTO = bridgeUserMapper.toDto(bridgeUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBridgeUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bridgeUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BridgeUser in the database
        List<BridgeUser> bridgeUserList = bridgeUserRepository.findAll();
        assertThat(bridgeUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBridgeUser() throws Exception {
        int databaseSizeBeforeUpdate = bridgeUserRepository.findAll().size();
        bridgeUser.setId(count.incrementAndGet());

        // Create the BridgeUser
        BridgeUserDTO bridgeUserDTO = bridgeUserMapper.toDto(bridgeUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBridgeUserMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bridgeUserDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BridgeUser in the database
        List<BridgeUser> bridgeUserList = bridgeUserRepository.findAll();
        assertThat(bridgeUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBridgeUserWithPatch() throws Exception {
        // Initialize the database
        bridgeUserRepository.saveAndFlush(bridgeUser);

        int databaseSizeBeforeUpdate = bridgeUserRepository.findAll().size();

        // Update the bridgeUser using partial update
        BridgeUser partialUpdatedBridgeUser = new BridgeUser();
        partialUpdatedBridgeUser.setId(bridgeUser.getId());

        partialUpdatedBridgeUser.uuid(UPDATED_UUID).email(UPDATED_EMAIL);

        restBridgeUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBridgeUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBridgeUser))
            )
            .andExpect(status().isOk());

        // Validate the BridgeUser in the database
        List<BridgeUser> bridgeUserList = bridgeUserRepository.findAll();
        assertThat(bridgeUserList).hasSize(databaseSizeBeforeUpdate);
        BridgeUser testBridgeUser = bridgeUserList.get(bridgeUserList.size() - 1);
        assertThat(testBridgeUser.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testBridgeUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testBridgeUser.getPass()).isEqualTo(DEFAULT_PASS);
        assertThat(testBridgeUser.getLastLoginDate()).isEqualTo(DEFAULT_LAST_LOGIN_DATE);
    }

    @Test
    @Transactional
    void fullUpdateBridgeUserWithPatch() throws Exception {
        // Initialize the database
        bridgeUserRepository.saveAndFlush(bridgeUser);

        int databaseSizeBeforeUpdate = bridgeUserRepository.findAll().size();

        // Update the bridgeUser using partial update
        BridgeUser partialUpdatedBridgeUser = new BridgeUser();
        partialUpdatedBridgeUser.setId(bridgeUser.getId());

        partialUpdatedBridgeUser.uuid(UPDATED_UUID).email(UPDATED_EMAIL).pass(UPDATED_PASS).lastLoginDate(UPDATED_LAST_LOGIN_DATE);

        restBridgeUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBridgeUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBridgeUser))
            )
            .andExpect(status().isOk());

        // Validate the BridgeUser in the database
        List<BridgeUser> bridgeUserList = bridgeUserRepository.findAll();
        assertThat(bridgeUserList).hasSize(databaseSizeBeforeUpdate);
        BridgeUser testBridgeUser = bridgeUserList.get(bridgeUserList.size() - 1);
        assertThat(testBridgeUser.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testBridgeUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testBridgeUser.getPass()).isEqualTo(UPDATED_PASS);
        assertThat(testBridgeUser.getLastLoginDate()).isEqualTo(UPDATED_LAST_LOGIN_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingBridgeUser() throws Exception {
        int databaseSizeBeforeUpdate = bridgeUserRepository.findAll().size();
        bridgeUser.setId(count.incrementAndGet());

        // Create the BridgeUser
        BridgeUserDTO bridgeUserDTO = bridgeUserMapper.toDto(bridgeUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBridgeUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bridgeUserDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bridgeUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BridgeUser in the database
        List<BridgeUser> bridgeUserList = bridgeUserRepository.findAll();
        assertThat(bridgeUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBridgeUser() throws Exception {
        int databaseSizeBeforeUpdate = bridgeUserRepository.findAll().size();
        bridgeUser.setId(count.incrementAndGet());

        // Create the BridgeUser
        BridgeUserDTO bridgeUserDTO = bridgeUserMapper.toDto(bridgeUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBridgeUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bridgeUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BridgeUser in the database
        List<BridgeUser> bridgeUserList = bridgeUserRepository.findAll();
        assertThat(bridgeUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBridgeUser() throws Exception {
        int databaseSizeBeforeUpdate = bridgeUserRepository.findAll().size();
        bridgeUser.setId(count.incrementAndGet());

        // Create the BridgeUser
        BridgeUserDTO bridgeUserDTO = bridgeUserMapper.toDto(bridgeUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBridgeUserMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bridgeUserDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BridgeUser in the database
        List<BridgeUser> bridgeUserList = bridgeUserRepository.findAll();
        assertThat(bridgeUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBridgeUser() throws Exception {
        // Initialize the database
        bridgeUserRepository.saveAndFlush(bridgeUser);

        int databaseSizeBeforeDelete = bridgeUserRepository.findAll().size();

        // Delete the bridgeUser
        restBridgeUserMockMvc
            .perform(delete(ENTITY_API_URL_ID, bridgeUser.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BridgeUser> bridgeUserList = bridgeUserRepository.findAll();
        assertThat(bridgeUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
