package com.akounts.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BridgeRunDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BridgeRunDTO.class);
        BridgeRunDTO bridgeRunDTO1 = new BridgeRunDTO();
        bridgeRunDTO1.setId(1L);
        BridgeRunDTO bridgeRunDTO2 = new BridgeRunDTO();
        assertThat(bridgeRunDTO1).isNotEqualTo(bridgeRunDTO2);
        bridgeRunDTO2.setId(bridgeRunDTO1.getId());
        assertThat(bridgeRunDTO1).isEqualTo(bridgeRunDTO2);
        bridgeRunDTO2.setId(2L);
        assertThat(bridgeRunDTO1).isNotEqualTo(bridgeRunDTO2);
        bridgeRunDTO1.setId(null);
        assertThat(bridgeRunDTO1).isNotEqualTo(bridgeRunDTO2);
    }
}
