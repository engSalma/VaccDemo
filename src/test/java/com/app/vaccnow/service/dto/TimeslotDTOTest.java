package com.app.vaccnow.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.app.vaccnow.web.rest.TestUtil;

public class TimeslotDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TimeslotDTO.class);
        TimeslotDTO timeslotDTO1 = new TimeslotDTO();
        timeslotDTO1.setId("id1");
        TimeslotDTO timeslotDTO2 = new TimeslotDTO();
        assertThat(timeslotDTO1).isNotEqualTo(timeslotDTO2);
        timeslotDTO2.setId(timeslotDTO1.getId());
        assertThat(timeslotDTO1).isEqualTo(timeslotDTO2);
        timeslotDTO2.setId("id2");
        assertThat(timeslotDTO1).isNotEqualTo(timeslotDTO2);
        timeslotDTO1.setId(null);
        assertThat(timeslotDTO1).isNotEqualTo(timeslotDTO2);
    }
}
