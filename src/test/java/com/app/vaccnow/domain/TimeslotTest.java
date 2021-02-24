package com.app.vaccnow.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.app.vaccnow.web.rest.TestUtil;

public class TimeslotTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Timeslot.class);
        Timeslot timeslot1 = new Timeslot();
        timeslot1.setId("id1");
        Timeslot timeslot2 = new Timeslot();
        timeslot2.setId(timeslot1.getId());
        assertThat(timeslot1).isEqualTo(timeslot2);
        timeslot2.setId("id2");
        assertThat(timeslot1).isNotEqualTo(timeslot2);
        timeslot1.setId(null);
        assertThat(timeslot1).isNotEqualTo(timeslot2);
    }
}
