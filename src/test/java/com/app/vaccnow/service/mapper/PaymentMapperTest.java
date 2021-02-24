package com.app.vaccnow.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PaymentMapperTest {

    private PaymentMapper paymentMapper;

    @BeforeEach
    public void setUp() {
        paymentMapper = new PaymentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(paymentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(paymentMapper.fromId(null)).isNull();
    }
}
