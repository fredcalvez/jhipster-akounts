package com.akounts.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.akounts.myapp.IntegrationTest;
import com.akounts.myapp.domain.FileConfig;
import com.akounts.myapp.repository.FileConfigRepository;
import com.akounts.myapp.service.dto.FileConfigDTO;
import com.akounts.myapp.service.mapper.FileConfigMapper;
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
 * Integration tests for the {@link FileConfigResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FileConfigResourceIT {

    private static final String DEFAULT_FILENAME = "AAAAAAAAAA";
    private static final String UPDATED_FILENAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_AMOUNT = 1;
    private static final Integer UPDATED_AMOUNT = 2;

    private static final Integer DEFAULT_AMOUNT_IN = 1;
    private static final Integer UPDATED_AMOUNT_IN = 2;

    private static final Integer DEFAULT_AMOUNT_OUT = 1;
    private static final Integer UPDATED_AMOUNT_OUT = 2;

    private static final Integer DEFAULT_ACCOUNT_NUM = 1;
    private static final Integer UPDATED_ACCOUNT_NUM = 2;

    private static final Integer DEFAULT_TRANSACTION_DATE = 1;
    private static final Integer UPDATED_TRANSACTION_DATE = 2;

    private static final String DEFAULT_DATE_FORMAT = "AAAAAAAAAA";
    private static final String UPDATED_DATE_FORMAT = "BBBBBBBBBB";

    private static final String DEFAULT_FIELD_SEPARATOR = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_SEPARATOR = "BBBBBBBBBB";

    private static final Integer DEFAULT_HAS_HEADER = 1;
    private static final Integer UPDATED_HAS_HEADER = 2;

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final Integer DEFAULT_LOCALE = 1;
    private static final Integer UPDATED_LOCALE = 2;

    private static final Integer DEFAULT_MULTIPLE_ACCOUNT = 1;
    private static final Integer UPDATED_MULTIPLE_ACCOUNT = 2;

    private static final String DEFAULT_ENCODING = "AAAAAAAAAA";
    private static final String UPDATED_ENCODING = "BBBBBBBBBB";

    private static final Integer DEFAULT_SIGN = 1;
    private static final Integer UPDATED_SIGN = 2;

    private static final String ENTITY_API_URL = "/api/file-configs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FileConfigRepository fileConfigRepository;

    @Autowired
    private FileConfigMapper fileConfigMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFileConfigMockMvc;

    private FileConfig fileConfig;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FileConfig createEntity(EntityManager em) {
        FileConfig fileConfig = new FileConfig()
            .filename(DEFAULT_FILENAME)
            .description(DEFAULT_DESCRIPTION)
            .amount(DEFAULT_AMOUNT)
            .amountIn(DEFAULT_AMOUNT_IN)
            .amountOut(DEFAULT_AMOUNT_OUT)
            .accountNum(DEFAULT_ACCOUNT_NUM)
            .transactionDate(DEFAULT_TRANSACTION_DATE)
            .dateFormat(DEFAULT_DATE_FORMAT)
            .fieldSeparator(DEFAULT_FIELD_SEPARATOR)
            .hasHeader(DEFAULT_HAS_HEADER)
            .note(DEFAULT_NOTE)
            .locale(DEFAULT_LOCALE)
            .multipleAccount(DEFAULT_MULTIPLE_ACCOUNT)
            .encoding(DEFAULT_ENCODING)
            .sign(DEFAULT_SIGN);
        return fileConfig;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FileConfig createUpdatedEntity(EntityManager em) {
        FileConfig fileConfig = new FileConfig()
            .filename(UPDATED_FILENAME)
            .description(UPDATED_DESCRIPTION)
            .amount(UPDATED_AMOUNT)
            .amountIn(UPDATED_AMOUNT_IN)
            .amountOut(UPDATED_AMOUNT_OUT)
            .accountNum(UPDATED_ACCOUNT_NUM)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .dateFormat(UPDATED_DATE_FORMAT)
            .fieldSeparator(UPDATED_FIELD_SEPARATOR)
            .hasHeader(UPDATED_HAS_HEADER)
            .note(UPDATED_NOTE)
            .locale(UPDATED_LOCALE)
            .multipleAccount(UPDATED_MULTIPLE_ACCOUNT)
            .encoding(UPDATED_ENCODING)
            .sign(UPDATED_SIGN);
        return fileConfig;
    }

    @BeforeEach
    public void initTest() {
        fileConfig = createEntity(em);
    }

    @Test
    @Transactional
    void createFileConfig() throws Exception {
        int databaseSizeBeforeCreate = fileConfigRepository.findAll().size();
        // Create the FileConfig
        FileConfigDTO fileConfigDTO = fileConfigMapper.toDto(fileConfig);
        restFileConfigMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fileConfigDTO)))
            .andExpect(status().isCreated());

        // Validate the FileConfig in the database
        List<FileConfig> fileConfigList = fileConfigRepository.findAll();
        assertThat(fileConfigList).hasSize(databaseSizeBeforeCreate + 1);
        FileConfig testFileConfig = fileConfigList.get(fileConfigList.size() - 1);
        assertThat(testFileConfig.getFilename()).isEqualTo(DEFAULT_FILENAME);
        assertThat(testFileConfig.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFileConfig.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testFileConfig.getAmountIn()).isEqualTo(DEFAULT_AMOUNT_IN);
        assertThat(testFileConfig.getAmountOut()).isEqualTo(DEFAULT_AMOUNT_OUT);
        assertThat(testFileConfig.getAccountNum()).isEqualTo(DEFAULT_ACCOUNT_NUM);
        assertThat(testFileConfig.getTransactionDate()).isEqualTo(DEFAULT_TRANSACTION_DATE);
        assertThat(testFileConfig.getDateFormat()).isEqualTo(DEFAULT_DATE_FORMAT);
        assertThat(testFileConfig.getFieldSeparator()).isEqualTo(DEFAULT_FIELD_SEPARATOR);
        assertThat(testFileConfig.getHasHeader()).isEqualTo(DEFAULT_HAS_HEADER);
        assertThat(testFileConfig.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testFileConfig.getLocale()).isEqualTo(DEFAULT_LOCALE);
        assertThat(testFileConfig.getMultipleAccount()).isEqualTo(DEFAULT_MULTIPLE_ACCOUNT);
        assertThat(testFileConfig.getEncoding()).isEqualTo(DEFAULT_ENCODING);
        assertThat(testFileConfig.getSign()).isEqualTo(DEFAULT_SIGN);
    }

    @Test
    @Transactional
    void createFileConfigWithExistingId() throws Exception {
        // Create the FileConfig with an existing ID
        fileConfig.setId(1L);
        FileConfigDTO fileConfigDTO = fileConfigMapper.toDto(fileConfig);

        int databaseSizeBeforeCreate = fileConfigRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFileConfigMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fileConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FileConfig in the database
        List<FileConfig> fileConfigList = fileConfigRepository.findAll();
        assertThat(fileConfigList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFileConfigs() throws Exception {
        // Initialize the database
        fileConfigRepository.saveAndFlush(fileConfig);

        // Get all the fileConfigList
        restFileConfigMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].filename").value(hasItem(DEFAULT_FILENAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.[*].amountIn").value(hasItem(DEFAULT_AMOUNT_IN)))
            .andExpect(jsonPath("$.[*].amountOut").value(hasItem(DEFAULT_AMOUNT_OUT)))
            .andExpect(jsonPath("$.[*].accountNum").value(hasItem(DEFAULT_ACCOUNT_NUM)))
            .andExpect(jsonPath("$.[*].transactionDate").value(hasItem(DEFAULT_TRANSACTION_DATE)))
            .andExpect(jsonPath("$.[*].dateFormat").value(hasItem(DEFAULT_DATE_FORMAT)))
            .andExpect(jsonPath("$.[*].fieldSeparator").value(hasItem(DEFAULT_FIELD_SEPARATOR)))
            .andExpect(jsonPath("$.[*].hasHeader").value(hasItem(DEFAULT_HAS_HEADER)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].locale").value(hasItem(DEFAULT_LOCALE)))
            .andExpect(jsonPath("$.[*].multipleAccount").value(hasItem(DEFAULT_MULTIPLE_ACCOUNT)))
            .andExpect(jsonPath("$.[*].encoding").value(hasItem(DEFAULT_ENCODING)))
            .andExpect(jsonPath("$.[*].sign").value(hasItem(DEFAULT_SIGN)));
    }

    @Test
    @Transactional
    void getFileConfig() throws Exception {
        // Initialize the database
        fileConfigRepository.saveAndFlush(fileConfig);

        // Get the fileConfig
        restFileConfigMockMvc
            .perform(get(ENTITY_API_URL_ID, fileConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fileConfig.getId().intValue()))
            .andExpect(jsonPath("$.filename").value(DEFAULT_FILENAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT))
            .andExpect(jsonPath("$.amountIn").value(DEFAULT_AMOUNT_IN))
            .andExpect(jsonPath("$.amountOut").value(DEFAULT_AMOUNT_OUT))
            .andExpect(jsonPath("$.accountNum").value(DEFAULT_ACCOUNT_NUM))
            .andExpect(jsonPath("$.transactionDate").value(DEFAULT_TRANSACTION_DATE))
            .andExpect(jsonPath("$.dateFormat").value(DEFAULT_DATE_FORMAT))
            .andExpect(jsonPath("$.fieldSeparator").value(DEFAULT_FIELD_SEPARATOR))
            .andExpect(jsonPath("$.hasHeader").value(DEFAULT_HAS_HEADER))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE))
            .andExpect(jsonPath("$.locale").value(DEFAULT_LOCALE))
            .andExpect(jsonPath("$.multipleAccount").value(DEFAULT_MULTIPLE_ACCOUNT))
            .andExpect(jsonPath("$.encoding").value(DEFAULT_ENCODING))
            .andExpect(jsonPath("$.sign").value(DEFAULT_SIGN));
    }

    @Test
    @Transactional
    void getNonExistingFileConfig() throws Exception {
        // Get the fileConfig
        restFileConfigMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFileConfig() throws Exception {
        // Initialize the database
        fileConfigRepository.saveAndFlush(fileConfig);

        int databaseSizeBeforeUpdate = fileConfigRepository.findAll().size();

        // Update the fileConfig
        FileConfig updatedFileConfig = fileConfigRepository.findById(fileConfig.getId()).get();
        // Disconnect from session so that the updates on updatedFileConfig are not directly saved in db
        em.detach(updatedFileConfig);
        updatedFileConfig
            .filename(UPDATED_FILENAME)
            .description(UPDATED_DESCRIPTION)
            .amount(UPDATED_AMOUNT)
            .amountIn(UPDATED_AMOUNT_IN)
            .amountOut(UPDATED_AMOUNT_OUT)
            .accountNum(UPDATED_ACCOUNT_NUM)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .dateFormat(UPDATED_DATE_FORMAT)
            .fieldSeparator(UPDATED_FIELD_SEPARATOR)
            .hasHeader(UPDATED_HAS_HEADER)
            .note(UPDATED_NOTE)
            .locale(UPDATED_LOCALE)
            .multipleAccount(UPDATED_MULTIPLE_ACCOUNT)
            .encoding(UPDATED_ENCODING)
            .sign(UPDATED_SIGN);
        FileConfigDTO fileConfigDTO = fileConfigMapper.toDto(updatedFileConfig);

        restFileConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fileConfigDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fileConfigDTO))
            )
            .andExpect(status().isOk());

        // Validate the FileConfig in the database
        List<FileConfig> fileConfigList = fileConfigRepository.findAll();
        assertThat(fileConfigList).hasSize(databaseSizeBeforeUpdate);
        FileConfig testFileConfig = fileConfigList.get(fileConfigList.size() - 1);
        assertThat(testFileConfig.getFilename()).isEqualTo(UPDATED_FILENAME);
        assertThat(testFileConfig.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFileConfig.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testFileConfig.getAmountIn()).isEqualTo(UPDATED_AMOUNT_IN);
        assertThat(testFileConfig.getAmountOut()).isEqualTo(UPDATED_AMOUNT_OUT);
        assertThat(testFileConfig.getAccountNum()).isEqualTo(UPDATED_ACCOUNT_NUM);
        assertThat(testFileConfig.getTransactionDate()).isEqualTo(UPDATED_TRANSACTION_DATE);
        assertThat(testFileConfig.getDateFormat()).isEqualTo(UPDATED_DATE_FORMAT);
        assertThat(testFileConfig.getFieldSeparator()).isEqualTo(UPDATED_FIELD_SEPARATOR);
        assertThat(testFileConfig.getHasHeader()).isEqualTo(UPDATED_HAS_HEADER);
        assertThat(testFileConfig.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testFileConfig.getLocale()).isEqualTo(UPDATED_LOCALE);
        assertThat(testFileConfig.getMultipleAccount()).isEqualTo(UPDATED_MULTIPLE_ACCOUNT);
        assertThat(testFileConfig.getEncoding()).isEqualTo(UPDATED_ENCODING);
        assertThat(testFileConfig.getSign()).isEqualTo(UPDATED_SIGN);
    }

    @Test
    @Transactional
    void putNonExistingFileConfig() throws Exception {
        int databaseSizeBeforeUpdate = fileConfigRepository.findAll().size();
        fileConfig.setId(count.incrementAndGet());

        // Create the FileConfig
        FileConfigDTO fileConfigDTO = fileConfigMapper.toDto(fileConfig);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFileConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fileConfigDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fileConfigDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FileConfig in the database
        List<FileConfig> fileConfigList = fileConfigRepository.findAll();
        assertThat(fileConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFileConfig() throws Exception {
        int databaseSizeBeforeUpdate = fileConfigRepository.findAll().size();
        fileConfig.setId(count.incrementAndGet());

        // Create the FileConfig
        FileConfigDTO fileConfigDTO = fileConfigMapper.toDto(fileConfig);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fileConfigDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FileConfig in the database
        List<FileConfig> fileConfigList = fileConfigRepository.findAll();
        assertThat(fileConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFileConfig() throws Exception {
        int databaseSizeBeforeUpdate = fileConfigRepository.findAll().size();
        fileConfig.setId(count.incrementAndGet());

        // Create the FileConfig
        FileConfigDTO fileConfigDTO = fileConfigMapper.toDto(fileConfig);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileConfigMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fileConfigDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FileConfig in the database
        List<FileConfig> fileConfigList = fileConfigRepository.findAll();
        assertThat(fileConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFileConfigWithPatch() throws Exception {
        // Initialize the database
        fileConfigRepository.saveAndFlush(fileConfig);

        int databaseSizeBeforeUpdate = fileConfigRepository.findAll().size();

        // Update the fileConfig using partial update
        FileConfig partialUpdatedFileConfig = new FileConfig();
        partialUpdatedFileConfig.setId(fileConfig.getId());

        partialUpdatedFileConfig
            .filename(UPDATED_FILENAME)
            .amount(UPDATED_AMOUNT)
            .amountIn(UPDATED_AMOUNT_IN)
            .amountOut(UPDATED_AMOUNT_OUT)
            .accountNum(UPDATED_ACCOUNT_NUM)
            .dateFormat(UPDATED_DATE_FORMAT)
            .fieldSeparator(UPDATED_FIELD_SEPARATOR)
            .sign(UPDATED_SIGN);

        restFileConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFileConfig.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFileConfig))
            )
            .andExpect(status().isOk());

        // Validate the FileConfig in the database
        List<FileConfig> fileConfigList = fileConfigRepository.findAll();
        assertThat(fileConfigList).hasSize(databaseSizeBeforeUpdate);
        FileConfig testFileConfig = fileConfigList.get(fileConfigList.size() - 1);
        assertThat(testFileConfig.getFilename()).isEqualTo(UPDATED_FILENAME);
        assertThat(testFileConfig.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFileConfig.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testFileConfig.getAmountIn()).isEqualTo(UPDATED_AMOUNT_IN);
        assertThat(testFileConfig.getAmountOut()).isEqualTo(UPDATED_AMOUNT_OUT);
        assertThat(testFileConfig.getAccountNum()).isEqualTo(UPDATED_ACCOUNT_NUM);
        assertThat(testFileConfig.getTransactionDate()).isEqualTo(DEFAULT_TRANSACTION_DATE);
        assertThat(testFileConfig.getDateFormat()).isEqualTo(UPDATED_DATE_FORMAT);
        assertThat(testFileConfig.getFieldSeparator()).isEqualTo(UPDATED_FIELD_SEPARATOR);
        assertThat(testFileConfig.getHasHeader()).isEqualTo(DEFAULT_HAS_HEADER);
        assertThat(testFileConfig.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testFileConfig.getLocale()).isEqualTo(DEFAULT_LOCALE);
        assertThat(testFileConfig.getMultipleAccount()).isEqualTo(DEFAULT_MULTIPLE_ACCOUNT);
        assertThat(testFileConfig.getEncoding()).isEqualTo(DEFAULT_ENCODING);
        assertThat(testFileConfig.getSign()).isEqualTo(UPDATED_SIGN);
    }

    @Test
    @Transactional
    void fullUpdateFileConfigWithPatch() throws Exception {
        // Initialize the database
        fileConfigRepository.saveAndFlush(fileConfig);

        int databaseSizeBeforeUpdate = fileConfigRepository.findAll().size();

        // Update the fileConfig using partial update
        FileConfig partialUpdatedFileConfig = new FileConfig();
        partialUpdatedFileConfig.setId(fileConfig.getId());

        partialUpdatedFileConfig
            .filename(UPDATED_FILENAME)
            .description(UPDATED_DESCRIPTION)
            .amount(UPDATED_AMOUNT)
            .amountIn(UPDATED_AMOUNT_IN)
            .amountOut(UPDATED_AMOUNT_OUT)
            .accountNum(UPDATED_ACCOUNT_NUM)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .dateFormat(UPDATED_DATE_FORMAT)
            .fieldSeparator(UPDATED_FIELD_SEPARATOR)
            .hasHeader(UPDATED_HAS_HEADER)
            .note(UPDATED_NOTE)
            .locale(UPDATED_LOCALE)
            .multipleAccount(UPDATED_MULTIPLE_ACCOUNT)
            .encoding(UPDATED_ENCODING)
            .sign(UPDATED_SIGN);

        restFileConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFileConfig.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFileConfig))
            )
            .andExpect(status().isOk());

        // Validate the FileConfig in the database
        List<FileConfig> fileConfigList = fileConfigRepository.findAll();
        assertThat(fileConfigList).hasSize(databaseSizeBeforeUpdate);
        FileConfig testFileConfig = fileConfigList.get(fileConfigList.size() - 1);
        assertThat(testFileConfig.getFilename()).isEqualTo(UPDATED_FILENAME);
        assertThat(testFileConfig.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFileConfig.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testFileConfig.getAmountIn()).isEqualTo(UPDATED_AMOUNT_IN);
        assertThat(testFileConfig.getAmountOut()).isEqualTo(UPDATED_AMOUNT_OUT);
        assertThat(testFileConfig.getAccountNum()).isEqualTo(UPDATED_ACCOUNT_NUM);
        assertThat(testFileConfig.getTransactionDate()).isEqualTo(UPDATED_TRANSACTION_DATE);
        assertThat(testFileConfig.getDateFormat()).isEqualTo(UPDATED_DATE_FORMAT);
        assertThat(testFileConfig.getFieldSeparator()).isEqualTo(UPDATED_FIELD_SEPARATOR);
        assertThat(testFileConfig.getHasHeader()).isEqualTo(UPDATED_HAS_HEADER);
        assertThat(testFileConfig.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testFileConfig.getLocale()).isEqualTo(UPDATED_LOCALE);
        assertThat(testFileConfig.getMultipleAccount()).isEqualTo(UPDATED_MULTIPLE_ACCOUNT);
        assertThat(testFileConfig.getEncoding()).isEqualTo(UPDATED_ENCODING);
        assertThat(testFileConfig.getSign()).isEqualTo(UPDATED_SIGN);
    }

    @Test
    @Transactional
    void patchNonExistingFileConfig() throws Exception {
        int databaseSizeBeforeUpdate = fileConfigRepository.findAll().size();
        fileConfig.setId(count.incrementAndGet());

        // Create the FileConfig
        FileConfigDTO fileConfigDTO = fileConfigMapper.toDto(fileConfig);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFileConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fileConfigDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fileConfigDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FileConfig in the database
        List<FileConfig> fileConfigList = fileConfigRepository.findAll();
        assertThat(fileConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFileConfig() throws Exception {
        int databaseSizeBeforeUpdate = fileConfigRepository.findAll().size();
        fileConfig.setId(count.incrementAndGet());

        // Create the FileConfig
        FileConfigDTO fileConfigDTO = fileConfigMapper.toDto(fileConfig);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fileConfigDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FileConfig in the database
        List<FileConfig> fileConfigList = fileConfigRepository.findAll();
        assertThat(fileConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFileConfig() throws Exception {
        int databaseSizeBeforeUpdate = fileConfigRepository.findAll().size();
        fileConfig.setId(count.incrementAndGet());

        // Create the FileConfig
        FileConfigDTO fileConfigDTO = fileConfigMapper.toDto(fileConfig);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileConfigMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fileConfigDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FileConfig in the database
        List<FileConfig> fileConfigList = fileConfigRepository.findAll();
        assertThat(fileConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFileConfig() throws Exception {
        // Initialize the database
        fileConfigRepository.saveAndFlush(fileConfig);

        int databaseSizeBeforeDelete = fileConfigRepository.findAll().size();

        // Delete the fileConfig
        restFileConfigMockMvc
            .perform(delete(ENTITY_API_URL_ID, fileConfig.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FileConfig> fileConfigList = fileConfigRepository.findAll();
        assertThat(fileConfigList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
