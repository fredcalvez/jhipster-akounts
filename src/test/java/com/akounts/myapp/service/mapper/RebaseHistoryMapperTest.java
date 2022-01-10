package com.akounts.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RebaseHistoryMapperTest {

    private RebaseHistoryMapper rebaseHistoryMapper;

    @BeforeEach
    public void setUp() {
        rebaseHistoryMapper = new RebaseHistoryMapperImpl();
    }
}
