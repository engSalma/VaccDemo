package com.app.vaccnow.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.app.vaccnow.web.rest.TestUtil;

public class AppConfigurationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppConfiguration.class);
        AppConfiguration appConfiguration1 = new AppConfiguration();
        appConfiguration1.setId("id1");
        AppConfiguration appConfiguration2 = new AppConfiguration();
        appConfiguration2.setId(appConfiguration1.getId());
        assertThat(appConfiguration1).isEqualTo(appConfiguration2);
        appConfiguration2.setId("id2");
        assertThat(appConfiguration1).isNotEqualTo(appConfiguration2);
        appConfiguration1.setId(null);
        assertThat(appConfiguration1).isNotEqualTo(appConfiguration2);
    }
}
