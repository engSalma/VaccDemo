package com.app.vaccnow.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AppConfigurationMapperTest {

    private AppConfigurationMapper appConfigurationMapper;

    @BeforeEach
    public void setUp() {
        appConfigurationMapper = new AppConfigurationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(appConfigurationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(appConfigurationMapper.fromId(null)).isNull();
    }
}
