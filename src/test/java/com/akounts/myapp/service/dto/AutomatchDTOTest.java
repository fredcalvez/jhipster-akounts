package com.akounts.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AutomatchDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AutomatchDTO.class);
        AutomatchDTO automatchDTO1 = new AutomatchDTO();
        automatchDTO1.setId(1L);
        AutomatchDTO automatchDTO2 = new AutomatchDTO();
        assertThat(automatchDTO1).isNotEqualTo(automatchDTO2);
        automatchDTO2.setId(automatchDTO1.getId());
        assertThat(automatchDTO1).isEqualTo(automatchDTO2);
        automatchDTO2.setId(2L);
        assertThat(automatchDTO1).isNotEqualTo(automatchDTO2);
        automatchDTO1.setId(null);
        assertThat(automatchDTO1).isNotEqualTo(automatchDTO2);
    }
}
