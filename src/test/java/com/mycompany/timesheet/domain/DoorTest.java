package com.mycompany.timesheet.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.timesheet.web.rest.TestUtil;

public class DoorTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Door.class);
        Door door1 = new Door();
        door1.setId(1L);
        Door door2 = new Door();
        door2.setId(door1.getId());
        assertThat(door1).isEqualTo(door2);
        door2.setId(2L);
        assertThat(door1).isNotEqualTo(door2);
        door1.setId(null);
        assertThat(door1).isNotEqualTo(door2);
    }
}
