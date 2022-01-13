package com.akounts.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RebaseHistoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RebaseHistoryDTO.class);
        RebaseHistoryDTO rebaseHistoryDTO1 = new RebaseHistoryDTO();
        rebaseHistoryDTO1.setId(1L);
        RebaseHistoryDTO rebaseHistoryDTO2 = new RebaseHistoryDTO();
        assertThat(rebaseHistoryDTO1).isNotEqualTo(rebaseHistoryDTO2);
        rebaseHistoryDTO2.setId(rebaseHistoryDTO1.getId());
        assertThat(rebaseHistoryDTO1).isEqualTo(rebaseHistoryDTO2);
        rebaseHistoryDTO2.setId(2L);
        assertThat(rebaseHistoryDTO1).isNotEqualTo(rebaseHistoryDTO2);
        rebaseHistoryDTO1.setId(null);
        assertThat(rebaseHistoryDTO1).isNotEqualTo(rebaseHistoryDTO2);
    }
}
