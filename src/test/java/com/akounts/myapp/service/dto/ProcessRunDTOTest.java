package com.akounts.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProcessRunDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcessRunDTO.class);
        ProcessRunDTO processRunDTO1 = new ProcessRunDTO();
        processRunDTO1.setId(1L);
        ProcessRunDTO processRunDTO2 = new ProcessRunDTO();
        assertThat(processRunDTO1).isNotEqualTo(processRunDTO2);
        processRunDTO2.setId(processRunDTO1.getId());
        assertThat(processRunDTO1).isEqualTo(processRunDTO2);
        processRunDTO2.setId(2L);
        assertThat(processRunDTO1).isNotEqualTo(processRunDTO2);
        processRunDTO1.setId(null);
        assertThat(processRunDTO1).isNotEqualTo(processRunDTO2);
    }
}
