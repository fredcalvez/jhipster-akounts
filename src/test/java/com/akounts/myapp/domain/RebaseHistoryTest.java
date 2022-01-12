package com.akounts.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RebaseHistoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RebaseHistory.class);
        RebaseHistory rebaseHistory1 = new RebaseHistory();
        rebaseHistory1.setId(1L);
        RebaseHistory rebaseHistory2 = new RebaseHistory();
        rebaseHistory2.setId(rebaseHistory1.getId());
        assertThat(rebaseHistory1).isEqualTo(rebaseHistory2);
        rebaseHistory2.setId(2L);
        assertThat(rebaseHistory1).isNotEqualTo(rebaseHistory2);
        rebaseHistory1.setId(null);
        assertThat(rebaseHistory1).isNotEqualTo(rebaseHistory2);
    }
}
