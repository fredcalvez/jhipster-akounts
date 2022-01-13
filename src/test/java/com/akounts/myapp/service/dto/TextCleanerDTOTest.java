package com.akounts.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TextCleanerDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TextCleanerDTO.class);
        TextCleanerDTO textCleanerDTO1 = new TextCleanerDTO();
        textCleanerDTO1.setId(1L);
        TextCleanerDTO textCleanerDTO2 = new TextCleanerDTO();
        assertThat(textCleanerDTO1).isNotEqualTo(textCleanerDTO2);
        textCleanerDTO2.setId(textCleanerDTO1.getId());
        assertThat(textCleanerDTO1).isEqualTo(textCleanerDTO2);
        textCleanerDTO2.setId(2L);
        assertThat(textCleanerDTO1).isNotEqualTo(textCleanerDTO2);
        textCleanerDTO1.setId(null);
        assertThat(textCleanerDTO1).isNotEqualTo(textCleanerDTO2);
    }
}
