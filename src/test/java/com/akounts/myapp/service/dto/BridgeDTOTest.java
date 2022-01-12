package com.akounts.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BridgeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BridgeDTO.class);
        BridgeDTO bridgeDTO1 = new BridgeDTO();
        bridgeDTO1.setId(1L);
        BridgeDTO bridgeDTO2 = new BridgeDTO();
        assertThat(bridgeDTO1).isNotEqualTo(bridgeDTO2);
        bridgeDTO2.setId(bridgeDTO1.getId());
        assertThat(bridgeDTO1).isEqualTo(bridgeDTO2);
        bridgeDTO2.setId(2L);
        assertThat(bridgeDTO1).isNotEqualTo(bridgeDTO2);
        bridgeDTO1.setId(null);
        assertThat(bridgeDTO1).isNotEqualTo(bridgeDTO2);
    }
}
