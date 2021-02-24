package com.app.vaccnow.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.app.vaccnow.web.rest.TestUtil;

public class AppConfigurationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppConfigurationDTO.class);
        AppConfigurationDTO appConfigurationDTO1 = new AppConfigurationDTO();
        appConfigurationDTO1.setId("id1");
        AppConfigurationDTO appConfigurationDTO2 = new AppConfigurationDTO();
        assertThat(appConfigurationDTO1).isNotEqualTo(appConfigurationDTO2);
        appConfigurationDTO2.setId(appConfigurationDTO1.getId());
        assertThat(appConfigurationDTO1).isEqualTo(appConfigurationDTO2);
        appConfigurationDTO2.setId("id2");
        assertThat(appConfigurationDTO1).isNotEqualTo(appConfigurationDTO2);
        appConfigurationDTO1.setId(null);
        assertThat(appConfigurationDTO1).isNotEqualTo(appConfigurationDTO2);
    }
}
