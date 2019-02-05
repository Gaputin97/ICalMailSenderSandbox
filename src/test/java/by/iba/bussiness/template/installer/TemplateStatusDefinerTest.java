package by.iba.bussiness.template.installer;

import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.enrollment.status.EnrollmentStatus;
import by.iba.bussiness.template.TemplateType;
import org.junit.Assert;
import org.junit.Test;

public class TemplateStatusDefinerTest {
    private TemplateStatusDefiner templateStatusDefiner = new TemplateStatusDefiner();

    @Test
    public void testInstallTypeWhenEnrollmentStatusIsCancelled() {
        Enrollment enrollment = new Enrollment();
        enrollment.setStatus(EnrollmentStatus.CANCELLED.toString());

        String expectedTemplateType = templateStatusDefiner.defineTemplateType(enrollment, 5);

        Assert.assertEquals(expectedTemplateType, TemplateType.CANCELLATION.name());
    }

    @Test
    public void testInstallTypeWhenEnrollmentCalendarVersionIsNull() {
        Enrollment enrollment = new Enrollment();
        enrollment.setStatus(EnrollmentStatus.CONFIRMED.toString());
        enrollment.setCalendarVersion(null);

        String expectedTemplateType = templateStatusDefiner.defineTemplateType(enrollment, 5);

        Assert.assertEquals(expectedTemplateType, TemplateType.INVITATION.name());
    }

    @Test
    public void testInstallTypeWhenMaximumAppointmentIndexMoreThenCalendarVersion() {
        Enrollment enrollment = new Enrollment();
        enrollment.setStatus(EnrollmentStatus.CONFIRMED.toString());
        enrollment.setCalendarVersion("10");

        String expectedTemplateType = templateStatusDefiner.defineTemplateType(enrollment, 15);

        Assert.assertEquals(expectedTemplateType, TemplateType.UPDATE.toString());
    }

    @Test
    public void testInstallTypeWhenMaximumAppointmentIndexLessThenCalendarVersion() {
        Enrollment enrollment = new Enrollment();
        enrollment.setStatus(EnrollmentStatus.CONFIRMED.toString());
        enrollment.setCalendarVersion("15");

        String expectedTemplateType = templateStatusDefiner.defineTemplateType(enrollment, 10);

        Assert.assertNull(expectedTemplateType);
    }
}

