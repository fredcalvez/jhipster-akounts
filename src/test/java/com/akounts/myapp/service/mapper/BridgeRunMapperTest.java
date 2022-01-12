package com.akounts.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BridgeRunMapperTest {

    private BridgeRunMapper bridgeRunMapper;

    @BeforeEach
    public void setUp() {
        bridgeRunMapper = new BridgeRunMapperImpl();
    }
}
