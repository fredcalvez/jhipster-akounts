package com.akounts.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TriggerRunTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TriggerRun.class);
        TriggerRun triggerRun1 = new TriggerRun();
        triggerRun1.setId(1L);
        TriggerRun triggerRun2 = new TriggerRun();
        triggerRun2.setId(triggerRun1.getId());
        assertThat(triggerRun1).isEqualTo(triggerRun2);
        triggerRun2.setId(2L);
        assertThat(triggerRun1).isNotEqualTo(triggerRun2);
        triggerRun1.setId(null);
        assertThat(triggerRun1).isNotEqualTo(triggerRun2);
    }
}
