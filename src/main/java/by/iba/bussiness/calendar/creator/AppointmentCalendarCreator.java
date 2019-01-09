package by.iba.bussiness.calendar.creator;


import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.appointment.AppointmentCreator;
import by.iba.bussiness.appointment.AppointmentHandler;
import by.iba.bussiness.appointment.repository.AppointmentRepository;
import by.iba.bussiness.calendar.CalendarFactory;
import by.iba.bussiness.calendar.attendee.Learner;
import by.iba.bussiness.calendar.date.DateHelperDefiner;
import by.iba.bussiness.calendar.date.model.DateHelper;
import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.enrollment.EnrollmentChecker;
import by.iba.bussiness.enrollment.EnrollmentStatus;
import by.iba.bussiness.enrollment.repository.EnrollmentRepository;
import by.iba.bussiness.meeting.timeslot.TimeSlot;
import net.fortuna.ical4j.model.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;

@Component
public class AppointmentCalendarCreator {
    private EnrollmentRepository enrollmentRepository;
    private AppointmentRepository appointmentRepository;
    private AppointmentCreator appointmentCreator;
    private CalendarFactory calendarFactory;
    private EnrollmentChecker enrollmentChecker;
    private AppointmentHandler appointmentHandler;
    private DateHelperDefiner dateHelperDefiner;

    @Autowired
    public AppointmentCalendarCreator(EnrollmentRepository enrollmentRepository,
                                      AppointmentRepository appointmentRepository,
                                      AppointmentCreator appointmentCreator,
                                      CalendarFactory calendarFactory,
                                      EnrollmentChecker enrollmentChecker,
                                      AppointmentHandler appointmentHandler,
                                      DateHelperDefiner dateHelperDefiner) {
        this.enrollmentRepository = enrollmentRepository;
        this.appointmentRepository = appointmentRepository;
        this.appointmentCreator = appointmentCreator;
        this.calendarFactory = calendarFactory;
        this.enrollmentChecker = enrollmentChecker;
        this.appointmentHandler = appointmentHandler;
        this.dateHelperDefiner = dateHelperDefiner;
    }

    public Calendar createCalendar(Learner learner, Appointment appointment) {
        Calendar calendar;
        List<TimeSlot> timeSlots = appointment.getTimeSlots();
        BigInteger meetingId = appointment.getMeetingId();
        DateHelper dateHelper = dateHelperDefiner.defineDateHelper(timeSlots, meetingId);
        String email = learner.getEmail();
        String enrollmentStatus = learner.getEnrollmentStatus();

        Enrollment enrollment = enrollmentRepository.getByEmailAndParentIdAndType(meetingId, email, enrollmentStatus);
        if (learner.getEnrollmentStatus().equals(EnrollmentStatus.CANCELLED)) {
            Calendar calendarCancel = calendarFactory.createCancelCalendarTemplate(dateHelper, appointment, enrollment);
            calendar = calendarCancel;
        } else {
            Calendar calendarInvite = calendarFactory.createInvitationCalendarTemplate(dateHelper, appointment, enrollment);
            calendar = calendarInvite;
        }
        return calendar;
    }

}
