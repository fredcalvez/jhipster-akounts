package com.akounts.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FileImportTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileImport.class);
        FileImport fileImport1 = new FileImport();
        fileImport1.setId(1L);
        FileImport fileImport2 = new FileImport();
        fileImport2.setId(fileImport1.getId());
        assertThat(fileImport1).isEqualTo(fileImport2);
        fileImport2.setId(2L);
        assertThat(fileImport1).isNotEqualTo(fileImport2);
        fileImport1.setId(null);
        assertThat(fileImport1).isNotEqualTo(fileImport2);
    }
}
