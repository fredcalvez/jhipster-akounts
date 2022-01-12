package com.akounts.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BridgeUserMapperTest {

    private BridgeUserMapper bridgeUserMapper;

    @BeforeEach
    public void setUp() {
        bridgeUserMapper = new BridgeUserMapperImpl();
    }
}
