package com.akounts.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BridgeMapperTest {

    private BridgeMapper bridgeMapper;

    @BeforeEach
    public void setUp() {
        bridgeMapper = new BridgeMapperImpl();
    }
}
