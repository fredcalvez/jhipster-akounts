package com.akounts.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BridgeTransactionMapperTest {

    private BridgeTransactionMapper bridgeTransactionMapper;

    @BeforeEach
    public void setUp() {
        bridgeTransactionMapper = new BridgeTransactionMapperImpl();
    }
}
