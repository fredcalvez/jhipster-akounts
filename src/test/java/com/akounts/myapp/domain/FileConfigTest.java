package com.akounts.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FileConfigTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileConfig.class);
        FileConfig fileConfig1 = new FileConfig();
        fileConfig1.setId(1L);
        FileConfig fileConfig2 = new FileConfig();
        fileConfig2.setId(fileConfig1.getId());
        assertThat(fileConfig1).isEqualTo(fileConfig2);
        fileConfig2.setId(2L);
        assertThat(fileConfig1).isNotEqualTo(fileConfig2);
        fileConfig1.setId(null);
        assertThat(fileConfig1).isNotEqualTo(fileConfig2);
    }
}
