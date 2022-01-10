package com.akounts.myapp.web.rest;

import static com.akounts.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.akounts.myapp.IntegrationTest;
import com.akounts.myapp.domain.BankAccountInterest;
import com.akounts.myapp.repository.BankAccountInterestRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link BankAccountInterestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BankAccountInterestResourceIT {

    private static final BigDecimal DEFAULT_INTEREST = new BigDecimal(1);
    private static final BigDecimal UPDATED_INTEREST = new BigDecimal(2);

    private static final String DEFAULT_PERIOD = "AAAAAAAAAA";
    private static final String UPDATED_PERIOD = "BBBBBBBBBB";

    private static final String DEFAULT_INTEREST_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_INTEREST_TYPE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_UNITS = new BigDecimal(1);
    private static final BigDecimal UPDATED_UNITS = new BigDecimal(2);

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_SCRAPPING_URL = "AAAAAAAAAA";
    private static final String UPDATED_SCRAPPING_URL = "BBBBBBBBBB";

    private static final String DEFAULT_SCRAPPING_TAG = "AAAAAAAAAA";
    private static final String UPDATED_SCRAPPING_TAG = "BBBBBBBBBB";

    private static final String DEFAULT_SCRAPPING_TAG_BIS = "AAAAAAAAAA";
    private static final String UPDATED_SCRAPPING_TAG_BIS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bank-account-interests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BankAccountInterestRepository bankAccountInterestRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBankAccountInterestMockMvc;

    private BankAccountInterest bankAccountInterest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankAccountInterest createEntity(EntityManager em) {
        BankAccountInterest bankAccountInterest = new BankAccountInterest()
            .interest(DEFAULT_INTEREST)
            .period(DEFAULT_PERIOD)
            .interestType(DEFAULT_INTEREST_TYPE)
            .units(DEFAULT_UNITS)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .scrappingURL(DEFAULT_SCRAPPING_URL)
            .scrappingTag(DEFAULT_SCRAPPING_TAG)
            .scrappingTagBis(DEFAULT_SCRAPPING_TAG_BIS);
        return bankAccountInterest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankAccountInterest createUpdatedEntity(EntityManager em) {
        BankAccountInterest bankAccountInterest = new BankAccountInterest()
            .interest(UPDATED_INTEREST)
            .period(UPDATED_PERIOD)
            .interestType(UPDATED_INTEREST_TYPE)
            .units(UPDATED_UNITS)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .scrappingURL(UPDATED_SCRAPPING_URL)
            .scrappingTag(UPDATED_SCRAPPING_TAG)
            .scrappingTagBis(UPDATED_SCRAPPING_TAG_BIS);
        return bankAccountInterest;
    }

    @BeforeEach
    public void initTest() {
        bankAccountInterest = createEntity(em);
    }

    @Test
    @Transactional
    void createBankAccountInterest() throws Exception {
        int databaseSizeBeforeCreate = bankAccountInterestRepository.findAll().size();
        // Create the BankAccountInterest
        restBankAccountInterestMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankAccountInterest))
            )
            .andExpect(status().isCreated());

        // Validate the BankAccountInterest in the database
        List<BankAccountInterest> bankAccountInterestList = bankAccountInterestRepository.findAll();
        assertThat(bankAccountInterestList).hasSize(databaseSizeBeforeCreate + 1);
        BankAccountInterest testBankAccountInterest = bankAccountInterestList.get(bankAccountInterestList.size() - 1);
        assertThat(testBankAccountInterest.getInterest()).isEqualByComparingTo(DEFAULT_INTEREST);
        assertThat(testBankAccountInterest.getPeriod()).isEqualTo(DEFAULT_PERIOD);
        assertThat(testBankAccountInterest.getInterestType()).isEqualTo(DEFAULT_INTEREST_TYPE);
        assertThat(testBankAccountInterest.getUnits()).isEqualByComparingTo(DEFAULT_UNITS);
        assertThat(testBankAccountInterest.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testBankAccountInterest.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testBankAccountInterest.getScrappingURL()).isEqualTo(DEFAULT_SCRAPPING_URL);
        assertThat(testBankAccountInterest.getScrappingTag()).isEqualTo(DEFAULT_SCRAPPING_TAG);
        assertThat(testBankAccountInterest.getScrappingTagBis()).isEqualTo(DEFAULT_SCRAPPING_TAG_BIS);
    }

    @Test
    @Transactional
    void createBankAccountInterestWithExistingId() throws Exception {
        // Create the BankAccountInterest with an existing ID
        bankAccountInterest.setId(1L);

        int databaseSizeBeforeCreate = bankAccountInterestRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankAccountInterestMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankAccountInterest))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankAccountInterest in the database
        List<BankAccountInterest> bankAccountInterestList = bankAccountInterestRepository.findAll();
        assertThat(bankAccountInterestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBankAccountInterests() throws Exception {
        // Initialize the database
        bankAccountInterestRepository.saveAndFlush(bankAccountInterest);

        // Get all the bankAccountInterestList
        restBankAccountInterestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankAccountInterest.getId().intValue())))
            .andExpect(jsonPath("$.[*].interest").value(hasItem(sameNumber(DEFAULT_INTEREST))))
            .andExpect(jsonPath("$.[*].period").value(hasItem(DEFAULT_PERIOD)))
            .andExpect(jsonPath("$.[*].interestType").value(hasItem(DEFAULT_INTEREST_TYPE)))
            .andExpect(jsonPath("$.[*].units").value(hasItem(sameNumber(DEFAULT_UNITS))))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].scrappingURL").value(hasItem(DEFAULT_SCRAPPING_URL)))
            .andExpect(jsonPath("$.[*].scrappingTag").value(hasItem(DEFAULT_SCRAPPING_TAG)))
            .andExpect(jsonPath("$.[*].scrappingTagBis").value(hasItem(DEFAULT_SCRAPPING_TAG_BIS)));
    }

    @Test
    @Transactional
    void getBankAccountInterest() throws Exception {
        // Initialize the database
        bankAccountInterestRepository.saveAndFlush(bankAccountInterest);

        // Get the bankAccountInterest
        restBankAccountInterestMockMvc
            .perform(get(ENTITY_API_URL_ID, bankAccountInterest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bankAccountInterest.getId().intValue()))
            .andExpect(jsonPath("$.interest").value(sameNumber(DEFAULT_INTEREST)))
            .andExpect(jsonPath("$.period").value(DEFAULT_PERIOD))
            .andExpect(jsonPath("$.interestType").value(DEFAULT_INTEREST_TYPE))
            .andExpect(jsonPath("$.units").value(sameNumber(DEFAULT_UNITS)))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.scrappingURL").value(DEFAULT_SCRAPPING_URL))
            .andExpect(jsonPath("$.scrappingTag").value(DEFAULT_SCRAPPING_TAG))
            .andExpect(jsonPath("$.scrappingTagBis").value(DEFAULT_SCRAPPING_TAG_BIS));
    }

    @Test
    @Transactional
    void getNonExistingBankAccountInterest() throws Exception {
        // Get the bankAccountInterest
        restBankAccountInterestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBankAccountInterest() throws Exception {
        // Initialize the database
        bankAccountInterestRepository.saveAndFlush(bankAccountInterest);

        int databaseSizeBeforeUpdate = bankAccountInterestRepository.findAll().size();

        // Update the bankAccountInterest
        BankAccountInterest updatedBankAccountInterest = bankAccountInterestRepository.findById(bankAccountInterest.getId()).get();
        // Disconnect from session so that the updates on updatedBankAccountInterest are not directly saved in db
        em.detach(updatedBankAccountInterest);
        updatedBankAccountInterest
            .interest(UPDATED_INTEREST)
            .period(UPDATED_PERIOD)
            .interestType(UPDATED_INTEREST_TYPE)
            .units(UPDATED_UNITS)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .scrappingURL(UPDATED_SCRAPPING_URL)
            .scrappingTag(UPDATED_SCRAPPING_TAG)
            .scrappingTagBis(UPDATED_SCRAPPING_TAG_BIS);

        restBankAccountInterestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBankAccountInterest.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBankAccountInterest))
            )
            .andExpect(status().isOk());

        // Validate the BankAccountInterest in the database
        List<BankAccountInterest> bankAccountInterestList = bankAccountInterestRepository.findAll();
        assertThat(bankAccountInterestList).hasSize(databaseSizeBeforeUpdate);
        BankAccountInterest testBankAccountInterest = bankAccountInterestList.get(bankAccountInterestList.size() - 1);
        assertThat(testBankAccountInterest.getInterest()).isEqualTo(UPDATED_INTEREST);
        assertThat(testBankAccountInterest.getPeriod()).isEqualTo(UPDATED_PERIOD);
        assertThat(testBankAccountInterest.getInterestType()).isEqualTo(UPDATED_INTEREST_TYPE);
        assertThat(testBankAccountInterest.getUnits()).isEqualTo(UPDATED_UNITS);
        assertThat(testBankAccountInterest.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testBankAccountInterest.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testBankAccountInterest.getScrappingURL()).isEqualTo(UPDATED_SCRAPPING_URL);
        assertThat(testBankAccountInterest.getScrappingTag()).isEqualTo(UPDATED_SCRAPPING_TAG);
        assertThat(testBankAccountInterest.getScrappingTagBis()).isEqualTo(UPDATED_SCRAPPING_TAG_BIS);
    }

    @Test
    @Transactional
    void putNonExistingBankAccountInterest() throws Exception {
        int databaseSizeBeforeUpdate = bankAccountInterestRepository.findAll().size();
        bankAccountInterest.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankAccountInterestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankAccountInterest.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankAccountInterest))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankAccountInterest in the database
        List<BankAccountInterest> bankAccountInterestList = bankAccountInterestRepository.findAll();
        assertThat(bankAccountInterestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBankAccountInterest() throws Exception {
        int databaseSizeBeforeUpdate = bankAccountInterestRepository.findAll().size();
        bankAccountInterest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankAccountInterestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankAccountInterest))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankAccountInterest in the database
        List<BankAccountInterest> bankAccountInterestList = bankAccountInterestRepository.findAll();
        assertThat(bankAccountInterestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBankAccountInterest() throws Exception {
        int databaseSizeBeforeUpdate = bankAccountInterestRepository.findAll().size();
        bankAccountInterest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankAccountInterestMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankAccountInterest))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankAccountInterest in the database
        List<BankAccountInterest> bankAccountInterestList = bankAccountInterestRepository.findAll();
        assertThat(bankAccountInterestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBankAccountInterestWithPatch() throws Exception {
        // Initialize the database
        bankAccountInterestRepository.saveAndFlush(bankAccountInterest);

        int databaseSizeBeforeUpdate = bankAccountInterestRepository.findAll().size();

        // Update the bankAccountInterest using partial update
        BankAccountInterest partialUpdatedBankAccountInterest = new BankAccountInterest();
        partialUpdatedBankAccountInterest.setId(bankAccountInterest.getId());

        partialUpdatedBankAccountInterest
            .units(UPDATED_UNITS)
            .endDate(UPDATED_END_DATE)
            .scrappingURL(UPDATED_SCRAPPING_URL)
            .scrappingTag(UPDATED_SCRAPPING_TAG)
            .scrappingTagBis(UPDATED_SCRAPPING_TAG_BIS);

        restBankAccountInterestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankAccountInterest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankAccountInterest))
            )
            .andExpect(status().isOk());

        // Validate the BankAccountInterest in the database
        List<BankAccountInterest> bankAccountInterestList = bankAccountInterestRepository.findAll();
        assertThat(bankAccountInterestList).hasSize(databaseSizeBeforeUpdate);
        BankAccountInterest testBankAccountInterest = bankAccountInterestList.get(bankAccountInterestList.size() - 1);
        assertThat(testBankAccountInterest.getInterest()).isEqualByComparingTo(DEFAULT_INTEREST);
        assertThat(testBankAccountInterest.getPeriod()).isEqualTo(DEFAULT_PERIOD);
        assertThat(testBankAccountInterest.getInterestType()).isEqualTo(DEFAULT_INTEREST_TYPE);
        assertThat(testBankAccountInterest.getUnits()).isEqualByComparingTo(UPDATED_UNITS);
        assertThat(testBankAccountInterest.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testBankAccountInterest.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testBankAccountInterest.getScrappingURL()).isEqualTo(UPDATED_SCRAPPING_URL);
        assertThat(testBankAccountInterest.getScrappingTag()).isEqualTo(UPDATED_SCRAPPING_TAG);
        assertThat(testBankAccountInterest.getScrappingTagBis()).isEqualTo(UPDATED_SCRAPPING_TAG_BIS);
    }

    @Test
    @Transactional
    void fullUpdateBankAccountInterestWithPatch() throws Exception {
        // Initialize the database
        bankAccountInterestRepository.saveAndFlush(bankAccountInterest);

        int databaseSizeBeforeUpdate = bankAccountInterestRepository.findAll().size();

        // Update the bankAccountInterest using partial update
        BankAccountInterest partialUpdatedBankAccountInterest = new BankAccountInterest();
        partialUpdatedBankAccountInterest.setId(bankAccountInterest.getId());

        partialUpdatedBankAccountInterest
            .interest(UPDATED_INTEREST)
            .period(UPDATED_PERIOD)
            .interestType(UPDATED_INTEREST_TYPE)
            .units(UPDATED_UNITS)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .scrappingURL(UPDATED_SCRAPPING_URL)
            .scrappingTag(UPDATED_SCRAPPING_TAG)
            .scrappingTagBis(UPDATED_SCRAPPING_TAG_BIS);

        restBankAccountInterestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankAccountInterest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankAccountInterest))
            )
            .andExpect(status().isOk());

        // Validate the BankAccountInterest in the database
        List<BankAccountInterest> bankAccountInterestList = bankAccountInterestRepository.findAll();
        assertThat(bankAccountInterestList).hasSize(databaseSizeBeforeUpdate);
        BankAccountInterest testBankAccountInterest = bankAccountInterestList.get(bankAccountInterestList.size() - 1);
        assertThat(testBankAccountInterest.getInterest()).isEqualByComparingTo(UPDATED_INTEREST);
        assertThat(testBankAccountInterest.getPeriod()).isEqualTo(UPDATED_PERIOD);
        assertThat(testBankAccountInterest.getInterestType()).isEqualTo(UPDATED_INTEREST_TYPE);
        assertThat(testBankAccountInterest.getUnits()).isEqualByComparingTo(UPDATED_UNITS);
        assertThat(testBankAccountInterest.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testBankAccountInterest.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testBankAccountInterest.getScrappingURL()).isEqualTo(UPDATED_SCRAPPING_URL);
        assertThat(testBankAccountInterest.getScrappingTag()).isEqualTo(UPDATED_SCRAPPING_TAG);
        assertThat(testBankAccountInterest.getScrappingTagBis()).isEqualTo(UPDATED_SCRAPPING_TAG_BIS);
    }

    @Test
    @Transactional
    void patchNonExistingBankAccountInterest() throws Exception {
        int databaseSizeBeforeUpdate = bankAccountInterestRepository.findAll().size();
        bankAccountInterest.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankAccountInterestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bankAccountInterest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankAccountInterest))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankAccountInterest in the database
        List<BankAccountInterest> bankAccountInterestList = bankAccountInterestRepository.findAll();
        assertThat(bankAccountInterestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBankAccountInterest() throws Exception {
        int databaseSizeBeforeUpdate = bankAccountInterestRepository.findAll().size();
        bankAccountInterest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankAccountInterestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankAccountInterest))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankAccountInterest in the database
        List<BankAccountInterest> bankAccountInterestList = bankAccountInterestRepository.findAll();
        assertThat(bankAccountInterestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBankAccountInterest() throws Exception {
        int databaseSizeBeforeUpdate = bankAccountInterestRepository.findAll().size();
        bankAccountInterest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankAccountInterestMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankAccountInterest))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankAccountInterest in the database
        List<BankAccountInterest> bankAccountInterestList = bankAccountInterestRepository.findAll();
        assertThat(bankAccountInterestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBankAccountInterest() throws Exception {
        // Initialize the database
        bankAccountInterestRepository.saveAndFlush(bankAccountInterest);

        int databaseSizeBeforeDelete = bankAccountInterestRepository.findAll().size();

        // Delete the bankAccountInterest
        restBankAccountInterestMockMvc
            .perform(delete(ENTITY_API_URL_ID, bankAccountInterest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BankAccountInterest> bankAccountInterestList = bankAccountInterestRepository.findAll();
        assertThat(bankAccountInterestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
