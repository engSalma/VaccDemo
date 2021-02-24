package com.app.vaccnow.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TimeslotMapperTest {

    private TimeslotMapper timeslotMapper;

    @BeforeEach
    public void setUp() {
        timeslotMapper = new TimeslotMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(timeslotMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(timeslotMapper.fromId(null)).isNull();
    }
}
