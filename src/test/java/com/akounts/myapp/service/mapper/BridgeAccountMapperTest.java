package com.akounts.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BridgeAccountMapperTest {

    private BridgeAccountMapper bridgeAccountMapper;

    @BeforeEach
    public void setUp() {
        bridgeAccountMapper = new BridgeAccountMapperImpl();
    }
}
