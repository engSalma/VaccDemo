package com.app.vaccnow.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.app.vaccnow.web.rest.TestUtil;

public class ScheduleDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScheduleDTO.class);
        ScheduleDTO scheduleDTO1 = new ScheduleDTO();
        scheduleDTO1.setId("id1");
        ScheduleDTO scheduleDTO2 = new ScheduleDTO();
        assertThat(scheduleDTO1).isNotEqualTo(scheduleDTO2);
        scheduleDTO2.setId(scheduleDTO1.getId());
        assertThat(scheduleDTO1).isEqualTo(scheduleDTO2);
        scheduleDTO2.setId("id2");
        assertThat(scheduleDTO1).isNotEqualTo(scheduleDTO2);
        scheduleDTO1.setId(null);
        assertThat(scheduleDTO1).isNotEqualTo(scheduleDTO2);
    }
}
