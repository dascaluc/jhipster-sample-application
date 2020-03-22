package com.mycompany.timesheet.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.timesheet.web.rest.TestUtil;

public class AbsenceTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AbsenceType.class);
        AbsenceType absenceType1 = new AbsenceType();
        absenceType1.setId(1L);
        AbsenceType absenceType2 = new AbsenceType();
        absenceType2.setId(absenceType1.getId());
        assertThat(absenceType1).isEqualTo(absenceType2);
        absenceType2.setId(2L);
        assertThat(absenceType1).isNotEqualTo(absenceType2);
        absenceType1.setId(null);
        assertThat(absenceType1).isNotEqualTo(absenceType2);
    }
}
