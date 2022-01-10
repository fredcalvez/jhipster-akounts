package com.akounts.myapp.web.rest;

import static com.akounts.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.akounts.myapp.IntegrationTest;
import com.akounts.myapp.domain.BankProject;
import com.akounts.myapp.repository.BankProjectRepository;
import java.math.BigDecimal;
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
 * Integration tests for the {@link BankProjectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BankProjectResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_TYPE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_INITIAL_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_INITIAL_VALUE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CURRENT_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_CURRENT_VALUE = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/bank-projects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BankProjectRepository bankProjectRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBankProjectMockMvc;

    private BankProject bankProject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankProject createEntity(EntityManager em) {
        BankProject bankProject = new BankProject()
            .name(DEFAULT_NAME)
            .projectType(DEFAULT_PROJECT_TYPE)
            .initialValue(DEFAULT_INITIAL_VALUE)
            .currentValue(DEFAULT_CURRENT_VALUE);
        return bankProject;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankProject createUpdatedEntity(EntityManager em) {
        BankProject bankProject = new BankProject()
            .name(UPDATED_NAME)
            .projectType(UPDATED_PROJECT_TYPE)
            .initialValue(UPDATED_INITIAL_VALUE)
            .currentValue(UPDATED_CURRENT_VALUE);
        return bankProject;
    }

    @BeforeEach
    public void initTest() {
        bankProject = createEntity(em);
    }

    @Test
    @Transactional
    void createBankProject() throws Exception {
        int databaseSizeBeforeCreate = bankProjectRepository.findAll().size();
        // Create the BankProject
        restBankProjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankProject)))
            .andExpect(status().isCreated());

        // Validate the BankProject in the database
        List<BankProject> bankProjectList = bankProjectRepository.findAll();
        assertThat(bankProjectList).hasSize(databaseSizeBeforeCreate + 1);
        BankProject testBankProject = bankProjectList.get(bankProjectList.size() - 1);
        assertThat(testBankProject.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBankProject.getProjectType()).isEqualTo(DEFAULT_PROJECT_TYPE);
        assertThat(testBankProject.getInitialValue()).isEqualByComparingTo(DEFAULT_INITIAL_VALUE);
        assertThat(testBankProject.getCurrentValue()).isEqualByComparingTo(DEFAULT_CURRENT_VALUE);
    }

    @Test
    @Transactional
    void createBankProjectWithExistingId() throws Exception {
        // Create the BankProject with an existing ID
        bankProject.setId(1L);

        int databaseSizeBeforeCreate = bankProjectRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankProjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankProject)))
            .andExpect(status().isBadRequest());

        // Validate the BankProject in the database
        List<BankProject> bankProjectList = bankProjectRepository.findAll();
        assertThat(bankProjectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBankProjects() throws Exception {
        // Initialize the database
        bankProjectRepository.saveAndFlush(bankProject);

        // Get all the bankProjectList
        restBankProjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankProject.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].projectType").value(hasItem(DEFAULT_PROJECT_TYPE)))
            .andExpect(jsonPath("$.[*].initialValue").value(hasItem(sameNumber(DEFAULT_INITIAL_VALUE))))
            .andExpect(jsonPath("$.[*].currentValue").value(hasItem(sameNumber(DEFAULT_CURRENT_VALUE))));
    }

    @Test
    @Transactional
    void getBankProject() throws Exception {
        // Initialize the database
        bankProjectRepository.saveAndFlush(bankProject);

        // Get the bankProject
        restBankProjectMockMvc
            .perform(get(ENTITY_API_URL_ID, bankProject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bankProject.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.projectType").value(DEFAULT_PROJECT_TYPE))
            .andExpect(jsonPath("$.initialValue").value(sameNumber(DEFAULT_INITIAL_VALUE)))
            .andExpect(jsonPath("$.currentValue").value(sameNumber(DEFAULT_CURRENT_VALUE)));
    }

    @Test
    @Transactional
    void getNonExistingBankProject() throws Exception {
        // Get the bankProject
        restBankProjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBankProject() throws Exception {
        // Initialize the database
        bankProjectRepository.saveAndFlush(bankProject);

        int databaseSizeBeforeUpdate = bankProjectRepository.findAll().size();

        // Update the bankProject
        BankProject updatedBankProject = bankProjectRepository.findById(bankProject.getId()).get();
        // Disconnect from session so that the updates on updatedBankProject are not directly saved in db
        em.detach(updatedBankProject);
        updatedBankProject
            .name(UPDATED_NAME)
            .projectType(UPDATED_PROJECT_TYPE)
            .initialValue(UPDATED_INITIAL_VALUE)
            .currentValue(UPDATED_CURRENT_VALUE);

        restBankProjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBankProject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBankProject))
            )
            .andExpect(status().isOk());

        // Validate the BankProject in the database
        List<BankProject> bankProjectList = bankProjectRepository.findAll();
        assertThat(bankProjectList).hasSize(databaseSizeBeforeUpdate);
        BankProject testBankProject = bankProjectList.get(bankProjectList.size() - 1);
        assertThat(testBankProject.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBankProject.getProjectType()).isEqualTo(UPDATED_PROJECT_TYPE);
        assertThat(testBankProject.getInitialValue()).isEqualTo(UPDATED_INITIAL_VALUE);
        assertThat(testBankProject.getCurrentValue()).isEqualTo(UPDATED_CURRENT_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingBankProject() throws Exception {
        int databaseSizeBeforeUpdate = bankProjectRepository.findAll().size();
        bankProject.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankProjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankProject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankProject))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankProject in the database
        List<BankProject> bankProjectList = bankProjectRepository.findAll();
        assertThat(bankProjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBankProject() throws Exception {
        int databaseSizeBeforeUpdate = bankProjectRepository.findAll().size();
        bankProject.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankProjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankProject))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankProject in the database
        List<BankProject> bankProjectList = bankProjectRepository.findAll();
        assertThat(bankProjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBankProject() throws Exception {
        int databaseSizeBeforeUpdate = bankProjectRepository.findAll().size();
        bankProject.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankProjectMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankProject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankProject in the database
        List<BankProject> bankProjectList = bankProjectRepository.findAll();
        assertThat(bankProjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBankProjectWithPatch() throws Exception {
        // Initialize the database
        bankProjectRepository.saveAndFlush(bankProject);

        int databaseSizeBeforeUpdate = bankProjectRepository.findAll().size();

        // Update the bankProject using partial update
        BankProject partialUpdatedBankProject = new BankProject();
        partialUpdatedBankProject.setId(bankProject.getId());

        partialUpdatedBankProject.projectType(UPDATED_PROJECT_TYPE);

        restBankProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankProject.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankProject))
            )
            .andExpect(status().isOk());

        // Validate the BankProject in the database
        List<BankProject> bankProjectList = bankProjectRepository.findAll();
        assertThat(bankProjectList).hasSize(databaseSizeBeforeUpdate);
        BankProject testBankProject = bankProjectList.get(bankProjectList.size() - 1);
        assertThat(testBankProject.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBankProject.getProjectType()).isEqualTo(UPDATED_PROJECT_TYPE);
        assertThat(testBankProject.getInitialValue()).isEqualByComparingTo(DEFAULT_INITIAL_VALUE);
        assertThat(testBankProject.getCurrentValue()).isEqualByComparingTo(DEFAULT_CURRENT_VALUE);
    }

    @Test
    @Transactional
    void fullUpdateBankProjectWithPatch() throws Exception {
        // Initialize the database
        bankProjectRepository.saveAndFlush(bankProject);

        int databaseSizeBeforeUpdate = bankProjectRepository.findAll().size();

        // Update the bankProject using partial update
        BankProject partialUpdatedBankProject = new BankProject();
        partialUpdatedBankProject.setId(bankProject.getId());

        partialUpdatedBankProject
            .name(UPDATED_NAME)
            .projectType(UPDATED_PROJECT_TYPE)
            .initialValue(UPDATED_INITIAL_VALUE)
            .currentValue(UPDATED_CURRENT_VALUE);

        restBankProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankProject.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankProject))
            )
            .andExpect(status().isOk());

        // Validate the BankProject in the database
        List<BankProject> bankProjectList = bankProjectRepository.findAll();
        assertThat(bankProjectList).hasSize(databaseSizeBeforeUpdate);
        BankProject testBankProject = bankProjectList.get(bankProjectList.size() - 1);
        assertThat(testBankProject.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBankProject.getProjectType()).isEqualTo(UPDATED_PROJECT_TYPE);
        assertThat(testBankProject.getInitialValue()).isEqualByComparingTo(UPDATED_INITIAL_VALUE);
        assertThat(testBankProject.getCurrentValue()).isEqualByComparingTo(UPDATED_CURRENT_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingBankProject() throws Exception {
        int databaseSizeBeforeUpdate = bankProjectRepository.findAll().size();
        bankProject.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bankProject.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankProject))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankProject in the database
        List<BankProject> bankProjectList = bankProjectRepository.findAll();
        assertThat(bankProjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBankProject() throws Exception {
        int databaseSizeBeforeUpdate = bankProjectRepository.findAll().size();
        bankProject.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankProject))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankProject in the database
        List<BankProject> bankProjectList = bankProjectRepository.findAll();
        assertThat(bankProjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBankProject() throws Exception {
        int databaseSizeBeforeUpdate = bankProjectRepository.findAll().size();
        bankProject.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankProjectMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bankProject))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankProject in the database
        List<BankProject> bankProjectList = bankProjectRepository.findAll();
        assertThat(bankProjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBankProject() throws Exception {
        // Initialize the database
        bankProjectRepository.saveAndFlush(bankProject);

        int databaseSizeBeforeDelete = bankProjectRepository.findAll().size();

        // Delete the bankProject
        restBankProjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, bankProject.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BankProject> bankProjectList = bankProjectRepository.findAll();
        assertThat(bankProjectList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
