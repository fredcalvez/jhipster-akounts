package com.akounts.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FileImportDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileImportDTO.class);
        FileImportDTO fileImportDTO1 = new FileImportDTO();
        fileImportDTO1.setId(1L);
        FileImportDTO fileImportDTO2 = new FileImportDTO();
        assertThat(fileImportDTO1).isNotEqualTo(fileImportDTO2);
        fileImportDTO2.setId(fileImportDTO1.getId());
        assertThat(fileImportDTO1).isEqualTo(fileImportDTO2);
        fileImportDTO2.setId(2L);
        assertThat(fileImportDTO1).isNotEqualTo(fileImportDTO2);
        fileImportDTO1.setId(null);
        assertThat(fileImportDTO1).isNotEqualTo(fileImportDTO2);
    }
}
