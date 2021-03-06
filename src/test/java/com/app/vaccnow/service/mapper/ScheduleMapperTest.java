package com.app.vaccnow.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ScheduleMapperTest {

    private ScheduleMapper scheduleMapper;

    @BeforeEach
    public void setUp() {
        scheduleMapper = new ScheduleMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(scheduleMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(scheduleMapper.fromId(null)).isNull();
    }
}
