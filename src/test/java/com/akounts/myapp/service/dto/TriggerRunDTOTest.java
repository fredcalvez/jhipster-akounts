package com.akounts.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TriggerRunDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TriggerRunDTO.class);
        TriggerRunDTO triggerRunDTO1 = new TriggerRunDTO();
        triggerRunDTO1.setId(1L);
        TriggerRunDTO triggerRunDTO2 = new TriggerRunDTO();
        assertThat(triggerRunDTO1).isNotEqualTo(triggerRunDTO2);
        triggerRunDTO2.setId(triggerRunDTO1.getId());
        assertThat(triggerRunDTO1).isEqualTo(triggerRunDTO2);
        triggerRunDTO2.setId(2L);
        assertThat(triggerRunDTO1).isNotEqualTo(triggerRunDTO2);
        triggerRunDTO1.setId(null);
        assertThat(triggerRunDTO1).isNotEqualTo(triggerRunDTO2);
    }
}
