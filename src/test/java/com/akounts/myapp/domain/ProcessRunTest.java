package com.akounts.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProcessRunTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcessRun.class);
        ProcessRun processRun1 = new ProcessRun();
        processRun1.setId(1L);
        ProcessRun processRun2 = new ProcessRun();
        processRun2.setId(processRun1.getId());
        assertThat(processRun1).isEqualTo(processRun2);
        processRun2.setId(2L);
        assertThat(processRun1).isNotEqualTo(processRun2);
        processRun1.setId(null);
        assertThat(processRun1).isNotEqualTo(processRun2);
    }
}
