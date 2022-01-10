package com.akounts.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.akounts.myapp.IntegrationTest;
import com.akounts.myapp.domain.BankCategory;
import com.akounts.myapp.repository.BankCategoryRepository;
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
 * Integration tests for the {@link BankCategoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BankCategoryResourceIT {

    private static final Integer DEFAULT_PARENT = 1;
    private static final Integer UPDATED_PARENT = 2;

    private static final String DEFAULT_CATEGORIE_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIE_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORIE_DESC = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIE_DESC = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INCOME = false;
    private static final Boolean UPDATED_INCOME = true;

    private static final Boolean DEFAULT_ISEXPENSE = false;
    private static final Boolean UPDATED_ISEXPENSE = true;

    private static final String ENTITY_API_URL = "/api/bank-categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BankCategoryRepository bankCategoryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBankCategoryMockMvc;

    private BankCategory bankCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankCategory createEntity(EntityManager em) {
        BankCategory bankCategory = new BankCategory()
            .parent(DEFAULT_PARENT)
            .categorieLabel(DEFAULT_CATEGORIE_LABEL)
            .categorieDesc(DEFAULT_CATEGORIE_DESC)
            .income(DEFAULT_INCOME)
            .isexpense(DEFAULT_ISEXPENSE);
        return bankCategory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankCategory createUpdatedEntity(EntityManager em) {
        BankCategory bankCategory = new BankCategory()
            .parent(UPDATED_PARENT)
            .categorieLabel(UPDATED_CATEGORIE_LABEL)
            .categorieDesc(UPDATED_CATEGORIE_DESC)
            .income(UPDATED_INCOME)
            .isexpense(UPDATED_ISEXPENSE);
        return bankCategory;
    }

    @BeforeEach
    public void initTest() {
        bankCategory = createEntity(em);
    }

    @Test
    @Transactional
    void createBankCategory() throws Exception {
        int databaseSizeBeforeCreate = bankCategoryRepository.findAll().size();
        // Create the BankCategory
        restBankCategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankCategory)))
            .andExpect(status().isCreated());

        // Validate the BankCategory in the database
        List<BankCategory> bankCategoryList = bankCategoryRepository.findAll();
        assertThat(bankCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        BankCategory testBankCategory = bankCategoryList.get(bankCategoryList.size() - 1);
        assertThat(testBankCategory.getParent()).isEqualTo(DEFAULT_PARENT);
        assertThat(testBankCategory.getCategorieLabel()).isEqualTo(DEFAULT_CATEGORIE_LABEL);
        assertThat(testBankCategory.getCategorieDesc()).isEqualTo(DEFAULT_CATEGORIE_DESC);
        assertThat(testBankCategory.getIncome()).isEqualTo(DEFAULT_INCOME);
        assertThat(testBankCategory.getIsexpense()).isEqualTo(DEFAULT_ISEXPENSE);
    }

    @Test
    @Transactional
    void createBankCategoryWithExistingId() throws Exception {
        // Create the BankCategory with an existing ID
        bankCategory.setId(1L);

        int databaseSizeBeforeCreate = bankCategoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankCategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankCategory)))
            .andExpect(status().isBadRequest());

        // Validate the BankCategory in the database
        List<BankCategory> bankCategoryList = bankCategoryRepository.findAll();
        assertThat(bankCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBankCategories() throws Exception {
        // Initialize the database
        bankCategoryRepository.saveAndFlush(bankCategory);

        // Get all the bankCategoryList
        restBankCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].parent").value(hasItem(DEFAULT_PARENT)))
            .andExpect(jsonPath("$.[*].categorieLabel").value(hasItem(DEFAULT_CATEGORIE_LABEL)))
            .andExpect(jsonPath("$.[*].categorieDesc").value(hasItem(DEFAULT_CATEGORIE_DESC)))
            .andExpect(jsonPath("$.[*].income").value(hasItem(DEFAULT_INCOME.booleanValue())))
            .andExpect(jsonPath("$.[*].isexpense").value(hasItem(DEFAULT_ISEXPENSE.booleanValue())));
    }

    @Test
    @Transactional
    void getBankCategory() throws Exception {
        // Initialize the database
        bankCategoryRepository.saveAndFlush(bankCategory);

        // Get the bankCategory
        restBankCategoryMockMvc
            .perform(get(ENTITY_API_URL_ID, bankCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bankCategory.getId().intValue()))
            .andExpect(jsonPath("$.parent").value(DEFAULT_PARENT))
            .andExpect(jsonPath("$.categorieLabel").value(DEFAULT_CATEGORIE_LABEL))
            .andExpect(jsonPath("$.categorieDesc").value(DEFAULT_CATEGORIE_DESC))
            .andExpect(jsonPath("$.income").value(DEFAULT_INCOME.booleanValue()))
            .andExpect(jsonPath("$.isexpense").value(DEFAULT_ISEXPENSE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingBankCategory() throws Exception {
        // Get the bankCategory
        restBankCategoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBankCategory() throws Exception {
        // Initialize the database
        bankCategoryRepository.saveAndFlush(bankCategory);

        int databaseSizeBeforeUpdate = bankCategoryRepository.findAll().size();

        // Update the bankCategory
        BankCategory updatedBankCategory = bankCategoryRepository.findById(bankCategory.getId()).get();
        // Disconnect from session so that the updates on updatedBankCategory are not directly saved in db
        em.detach(updatedBankCategory);
        updatedBankCategory
            .parent(UPDATED_PARENT)
            .categorieLabel(UPDATED_CATEGORIE_LABEL)
            .categorieDesc(UPDATED_CATEGORIE_DESC)
            .income(UPDATED_INCOME)
            .isexpense(UPDATED_ISEXPENSE);

        restBankCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBankCategory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBankCategory))
            )
            .andExpect(status().isOk());

        // Validate the BankCategory in the database
        List<BankCategory> bankCategoryList = bankCategoryRepository.findAll();
        assertThat(bankCategoryList).hasSize(databaseSizeBeforeUpdate);
        BankCategory testBankCategory = bankCategoryList.get(bankCategoryList.size() - 1);
        assertThat(testBankCategory.getParent()).isEqualTo(UPDATED_PARENT);
        assertThat(testBankCategory.getCategorieLabel()).isEqualTo(UPDATED_CATEGORIE_LABEL);
        assertThat(testBankCategory.getCategorieDesc()).isEqualTo(UPDATED_CATEGORIE_DESC);
        assertThat(testBankCategory.getIncome()).isEqualTo(UPDATED_INCOME);
        assertThat(testBankCategory.getIsexpense()).isEqualTo(UPDATED_ISEXPENSE);
    }

    @Test
    @Transactional
    void putNonExistingBankCategory() throws Exception {
        int databaseSizeBeforeUpdate = bankCategoryRepository.findAll().size();
        bankCategory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankCategory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankCategory in the database
        List<BankCategory> bankCategoryList = bankCategoryRepository.findAll();
        assertThat(bankCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBankCategory() throws Exception {
        int databaseSizeBeforeUpdate = bankCategoryRepository.findAll().size();
        bankCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankCategory in the database
        List<BankCategory> bankCategoryList = bankCategoryRepository.findAll();
        assertThat(bankCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBankCategory() throws Exception {
        int databaseSizeBeforeUpdate = bankCategoryRepository.findAll().size();
        bankCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankCategoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankCategory)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankCategory in the database
        List<BankCategory> bankCategoryList = bankCategoryRepository.findAll();
        assertThat(bankCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBankCategoryWithPatch() throws Exception {
        // Initialize the database
        bankCategoryRepository.saveAndFlush(bankCategory);

        int databaseSizeBeforeUpdate = bankCategoryRepository.findAll().size();

        // Update the bankCategory using partial update
        BankCategory partialUpdatedBankCategory = new BankCategory();
        partialUpdatedBankCategory.setId(bankCategory.getId());

        partialUpdatedBankCategory.income(UPDATED_INCOME).isexpense(UPDATED_ISEXPENSE);

        restBankCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankCategory))
            )
            .andExpect(status().isOk());

        // Validate the BankCategory in the database
        List<BankCategory> bankCategoryList = bankCategoryRepository.findAll();
        assertThat(bankCategoryList).hasSize(databaseSizeBeforeUpdate);
        BankCategory testBankCategory = bankCategoryList.get(bankCategoryList.size() - 1);
        assertThat(testBankCategory.getParent()).isEqualTo(DEFAULT_PARENT);
        assertThat(testBankCategory.getCategorieLabel()).isEqualTo(DEFAULT_CATEGORIE_LABEL);
        assertThat(testBankCategory.getCategorieDesc()).isEqualTo(DEFAULT_CATEGORIE_DESC);
        assertThat(testBankCategory.getIncome()).isEqualTo(UPDATED_INCOME);
        assertThat(testBankCategory.getIsexpense()).isEqualTo(UPDATED_ISEXPENSE);
    }

    @Test
    @Transactional
    void fullUpdateBankCategoryWithPatch() throws Exception {
        // Initialize the database
        bankCategoryRepository.saveAndFlush(bankCategory);

        int databaseSizeBeforeUpdate = bankCategoryRepository.findAll().size();

        // Update the bankCategory using partial update
        BankCategory partialUpdatedBankCategory = new BankCategory();
        partialUpdatedBankCategory.setId(bankCategory.getId());

        partialUpdatedBankCategory
            .parent(UPDATED_PARENT)
            .categorieLabel(UPDATED_CATEGORIE_LABEL)
            .categorieDesc(UPDATED_CATEGORIE_DESC)
            .income(UPDATED_INCOME)
            .isexpense(UPDATED_ISEXPENSE);

        restBankCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankCategory))
            )
            .andExpect(status().isOk());

        // Validate the BankCategory in the database
        List<BankCategory> bankCategoryList = bankCategoryRepository.findAll();
        assertThat(bankCategoryList).hasSize(databaseSizeBeforeUpdate);
        BankCategory testBankCategory = bankCategoryList.get(bankCategoryList.size() - 1);
        assertThat(testBankCategory.getParent()).isEqualTo(UPDATED_PARENT);
        assertThat(testBankCategory.getCategorieLabel()).isEqualTo(UPDATED_CATEGORIE_LABEL);
        assertThat(testBankCategory.getCategorieDesc()).isEqualTo(UPDATED_CATEGORIE_DESC);
        assertThat(testBankCategory.getIncome()).isEqualTo(UPDATED_INCOME);
        assertThat(testBankCategory.getIsexpense()).isEqualTo(UPDATED_ISEXPENSE);
    }

    @Test
    @Transactional
    void patchNonExistingBankCategory() throws Exception {
        int databaseSizeBeforeUpdate = bankCategoryRepository.findAll().size();
        bankCategory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bankCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankCategory in the database
        List<BankCategory> bankCategoryList = bankCategoryRepository.findAll();
        assertThat(bankCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBankCategory() throws Exception {
        int databaseSizeBeforeUpdate = bankCategoryRepository.findAll().size();
        bankCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankCategory in the database
        List<BankCategory> bankCategoryList = bankCategoryRepository.findAll();
        assertThat(bankCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBankCategory() throws Exception {
        int databaseSizeBeforeUpdate = bankCategoryRepository.findAll().size();
        bankCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bankCategory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankCategory in the database
        List<BankCategory> bankCategoryList = bankCategoryRepository.findAll();
        assertThat(bankCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBankCategory() throws Exception {
        // Initialize the database
        bankCategoryRepository.saveAndFlush(bankCategory);

        int databaseSizeBeforeDelete = bankCategoryRepository.findAll().size();

        // Delete the bankCategory
        restBankCategoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, bankCategory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BankCategory> bankCategoryList = bankCategoryRepository.findAll();
        assertThat(bankCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
