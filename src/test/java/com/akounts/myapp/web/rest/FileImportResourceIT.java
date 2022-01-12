package com.akounts.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.akounts.myapp.IntegrationTest;
import com.akounts.myapp.domain.FileImport;
import com.akounts.myapp.domain.enumeration.Status;
import com.akounts.myapp.repository.FileImportRepository;
import com.akounts.myapp.service.dto.FileImportDTO;
import com.akounts.myapp.service.mapper.FileImportMapper;
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
 * Integration tests for the {@link FileImportResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FileImportResourceIT {

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.ToDo;
    private static final Status UPDATED_STATUS = Status.Doing;

    private static final Instant DEFAULT_REVIEW_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REVIEW_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_FILE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_FILE_PATH = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/file-imports";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FileImportRepository fileImportRepository;

    @Autowired
    private FileImportMapper fileImportMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFileImportMockMvc;

    private FileImport fileImport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FileImport createEntity(EntityManager em) {
        FileImport fileImport = new FileImport()
            .fileName(DEFAULT_FILE_NAME)
            .status(DEFAULT_STATUS)
            .reviewDate(DEFAULT_REVIEW_DATE)
            .filePath(DEFAULT_FILE_PATH);
        return fileImport;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FileImport createUpdatedEntity(EntityManager em) {
        FileImport fileImport = new FileImport()
            .fileName(UPDATED_FILE_NAME)
            .status(UPDATED_STATUS)
            .reviewDate(UPDATED_REVIEW_DATE)
            .filePath(UPDATED_FILE_PATH);
        return fileImport;
    }

    @BeforeEach
    public void initTest() {
        fileImport = createEntity(em);
    }

    @Test
    @Transactional
    void createFileImport() throws Exception {
        int databaseSizeBeforeCreate = fileImportRepository.findAll().size();
        // Create the FileImport
        FileImportDTO fileImportDTO = fileImportMapper.toDto(fileImport);
        restFileImportMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fileImportDTO)))
            .andExpect(status().isCreated());

        // Validate the FileImport in the database
        List<FileImport> fileImportList = fileImportRepository.findAll();
        assertThat(fileImportList).hasSize(databaseSizeBeforeCreate + 1);
        FileImport testFileImport = fileImportList.get(fileImportList.size() - 1);
        assertThat(testFileImport.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testFileImport.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testFileImport.getReviewDate()).isEqualTo(DEFAULT_REVIEW_DATE);
        assertThat(testFileImport.getFilePath()).isEqualTo(DEFAULT_FILE_PATH);
    }

    @Test
    @Transactional
    void createFileImportWithExistingId() throws Exception {
        // Create the FileImport with an existing ID
        fileImport.setId(1L);
        FileImportDTO fileImportDTO = fileImportMapper.toDto(fileImport);

        int databaseSizeBeforeCreate = fileImportRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFileImportMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fileImportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FileImport in the database
        List<FileImport> fileImportList = fileImportRepository.findAll();
        assertThat(fileImportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFileImports() throws Exception {
        // Initialize the database
        fileImportRepository.saveAndFlush(fileImport);

        // Get all the fileImportList
        restFileImportMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileImport.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].reviewDate").value(hasItem(DEFAULT_REVIEW_DATE.toString())))
            .andExpect(jsonPath("$.[*].filePath").value(hasItem(DEFAULT_FILE_PATH)));
    }

    @Test
    @Transactional
    void getFileImport() throws Exception {
        // Initialize the database
        fileImportRepository.saveAndFlush(fileImport);

        // Get the fileImport
        restFileImportMockMvc
            .perform(get(ENTITY_API_URL_ID, fileImport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fileImport.getId().intValue()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.reviewDate").value(DEFAULT_REVIEW_DATE.toString()))
            .andExpect(jsonPath("$.filePath").value(DEFAULT_FILE_PATH));
    }

    @Test
    @Transactional
    void getNonExistingFileImport() throws Exception {
        // Get the fileImport
        restFileImportMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFileImport() throws Exception {
        // Initialize the database
        fileImportRepository.saveAndFlush(fileImport);

        int databaseSizeBeforeUpdate = fileImportRepository.findAll().size();

        // Update the fileImport
        FileImport updatedFileImport = fileImportRepository.findById(fileImport.getId()).get();
        // Disconnect from session so that the updates on updatedFileImport are not directly saved in db
        em.detach(updatedFileImport);
        updatedFileImport.fileName(UPDATED_FILE_NAME).status(UPDATED_STATUS).reviewDate(UPDATED_REVIEW_DATE).filePath(UPDATED_FILE_PATH);
        FileImportDTO fileImportDTO = fileImportMapper.toDto(updatedFileImport);

        restFileImportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fileImportDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fileImportDTO))
            )
            .andExpect(status().isOk());

        // Validate the FileImport in the database
        List<FileImport> fileImportList = fileImportRepository.findAll();
        assertThat(fileImportList).hasSize(databaseSizeBeforeUpdate);
        FileImport testFileImport = fileImportList.get(fileImportList.size() - 1);
        assertThat(testFileImport.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testFileImport.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testFileImport.getReviewDate()).isEqualTo(UPDATED_REVIEW_DATE);
        assertThat(testFileImport.getFilePath()).isEqualTo(UPDATED_FILE_PATH);
    }

    @Test
    @Transactional
    void putNonExistingFileImport() throws Exception {
        int databaseSizeBeforeUpdate = fileImportRepository.findAll().size();
        fileImport.setId(count.incrementAndGet());

        // Create the FileImport
        FileImportDTO fileImportDTO = fileImportMapper.toDto(fileImport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFileImportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fileImportDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fileImportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FileImport in the database
        List<FileImport> fileImportList = fileImportRepository.findAll();
        assertThat(fileImportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFileImport() throws Exception {
        int databaseSizeBeforeUpdate = fileImportRepository.findAll().size();
        fileImport.setId(count.incrementAndGet());

        // Create the FileImport
        FileImportDTO fileImportDTO = fileImportMapper.toDto(fileImport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileImportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fileImportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FileImport in the database
        List<FileImport> fileImportList = fileImportRepository.findAll();
        assertThat(fileImportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFileImport() throws Exception {
        int databaseSizeBeforeUpdate = fileImportRepository.findAll().size();
        fileImport.setId(count.incrementAndGet());

        // Create the FileImport
        FileImportDTO fileImportDTO = fileImportMapper.toDto(fileImport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileImportMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fileImportDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FileImport in the database
        List<FileImport> fileImportList = fileImportRepository.findAll();
        assertThat(fileImportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFileImportWithPatch() throws Exception {
        // Initialize the database
        fileImportRepository.saveAndFlush(fileImport);

        int databaseSizeBeforeUpdate = fileImportRepository.findAll().size();

        // Update the fileImport using partial update
        FileImport partialUpdatedFileImport = new FileImport();
        partialUpdatedFileImport.setId(fileImport.getId());

        partialUpdatedFileImport.reviewDate(UPDATED_REVIEW_DATE).filePath(UPDATED_FILE_PATH);

        restFileImportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFileImport.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFileImport))
            )
            .andExpect(status().isOk());

        // Validate the FileImport in the database
        List<FileImport> fileImportList = fileImportRepository.findAll();
        assertThat(fileImportList).hasSize(databaseSizeBeforeUpdate);
        FileImport testFileImport = fileImportList.get(fileImportList.size() - 1);
        assertThat(testFileImport.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testFileImport.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testFileImport.getReviewDate()).isEqualTo(UPDATED_REVIEW_DATE);
        assertThat(testFileImport.getFilePath()).isEqualTo(UPDATED_FILE_PATH);
    }

    @Test
    @Transactional
    void fullUpdateFileImportWithPatch() throws Exception {
        // Initialize the database
        fileImportRepository.saveAndFlush(fileImport);

        int databaseSizeBeforeUpdate = fileImportRepository.findAll().size();

        // Update the fileImport using partial update
        FileImport partialUpdatedFileImport = new FileImport();
        partialUpdatedFileImport.setId(fileImport.getId());

        partialUpdatedFileImport
            .fileName(UPDATED_FILE_NAME)
            .status(UPDATED_STATUS)
            .reviewDate(UPDATED_REVIEW_DATE)
            .filePath(UPDATED_FILE_PATH);

        restFileImportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFileImport.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFileImport))
            )
            .andExpect(status().isOk());

        // Validate the FileImport in the database
        List<FileImport> fileImportList = fileImportRepository.findAll();
        assertThat(fileImportList).hasSize(databaseSizeBeforeUpdate);
        FileImport testFileImport = fileImportList.get(fileImportList.size() - 1);
        assertThat(testFileImport.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testFileImport.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testFileImport.getReviewDate()).isEqualTo(UPDATED_REVIEW_DATE);
        assertThat(testFileImport.getFilePath()).isEqualTo(UPDATED_FILE_PATH);
    }

    @Test
    @Transactional
    void patchNonExistingFileImport() throws Exception {
        int databaseSizeBeforeUpdate = fileImportRepository.findAll().size();
        fileImport.setId(count.incrementAndGet());

        // Create the FileImport
        FileImportDTO fileImportDTO = fileImportMapper.toDto(fileImport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFileImportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fileImportDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fileImportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FileImport in the database
        List<FileImport> fileImportList = fileImportRepository.findAll();
        assertThat(fileImportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFileImport() throws Exception {
        int databaseSizeBeforeUpdate = fileImportRepository.findAll().size();
        fileImport.setId(count.incrementAndGet());

        // Create the FileImport
        FileImportDTO fileImportDTO = fileImportMapper.toDto(fileImport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileImportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fileImportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FileImport in the database
        List<FileImport> fileImportList = fileImportRepository.findAll();
        assertThat(fileImportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFileImport() throws Exception {
        int databaseSizeBeforeUpdate = fileImportRepository.findAll().size();
        fileImport.setId(count.incrementAndGet());

        // Create the FileImport
        FileImportDTO fileImportDTO = fileImportMapper.toDto(fileImport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileImportMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fileImportDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FileImport in the database
        List<FileImport> fileImportList = fileImportRepository.findAll();
        assertThat(fileImportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFileImport() throws Exception {
        // Initialize the database
        fileImportRepository.saveAndFlush(fileImport);

        int databaseSizeBeforeDelete = fileImportRepository.findAll().size();

        // Delete the fileImport
        restFileImportMockMvc
            .perform(delete(ENTITY_API_URL_ID, fileImport.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FileImport> fileImportList = fileImportRepository.findAll();
        assertThat(fileImportList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
