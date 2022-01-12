package com.akounts.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BridgeAccountDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BridgeAccountDTO.class);
        BridgeAccountDTO bridgeAccountDTO1 = new BridgeAccountDTO();
        bridgeAccountDTO1.setId(1L);
        BridgeAccountDTO bridgeAccountDTO2 = new BridgeAccountDTO();
        assertThat(bridgeAccountDTO1).isNotEqualTo(bridgeAccountDTO2);
        bridgeAccountDTO2.setId(bridgeAccountDTO1.getId());
        assertThat(bridgeAccountDTO1).isEqualTo(bridgeAccountDTO2);
        bridgeAccountDTO2.setId(2L);
        assertThat(bridgeAccountDTO1).isNotEqualTo(bridgeAccountDTO2);
        bridgeAccountDTO1.setId(null);
        assertThat(bridgeAccountDTO1).isNotEqualTo(bridgeAccountDTO2);
    }
}
