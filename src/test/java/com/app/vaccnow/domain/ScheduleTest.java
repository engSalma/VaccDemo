package com.app.vaccnow.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.app.vaccnow.web.rest.TestUtil;

public class ScheduleTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Schedule.class);
        Schedule schedule1 = new Schedule();
        schedule1.setId("id1");
        Schedule schedule2 = new Schedule();
        schedule2.setId(schedule1.getId());
        assertThat(schedule1).isEqualTo(schedule2);
        schedule2.setId("id2");
        assertThat(schedule1).isNotEqualTo(schedule2);
        schedule1.setId(null);
        assertThat(schedule1).isNotEqualTo(schedule2);
    }
}
