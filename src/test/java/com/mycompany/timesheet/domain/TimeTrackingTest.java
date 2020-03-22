package com.mycompany.timesheet.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.timesheet.web.rest.TestUtil;

public class TimeTrackingTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TimeTracking.class);
        TimeTracking timeTracking1 = new TimeTracking();
        timeTracking1.setId(1L);
        TimeTracking timeTracking2 = new TimeTracking();
        timeTracking2.setId(timeTracking1.getId());
        assertThat(timeTracking1).isEqualTo(timeTracking2);
        timeTracking2.setId(2L);
        assertThat(timeTracking1).isNotEqualTo(timeTracking2);
        timeTracking1.setId(null);
        assertThat(timeTracking1).isNotEqualTo(timeTracking2);
    }
}
