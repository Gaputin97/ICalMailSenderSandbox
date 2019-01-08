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
import by.iba.bussiness.enrollment.EnrollmentType;
import by.iba.bussiness.enrollment.repository.EnrollmentRepository;
import by.iba.bussiness.invitation_template.InvitationTemplate;
import by.iba.bussiness.meeting.Meeting;
import net.fortuna.ical4j.model.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

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

    public Calendar createCalendarAndSaveAppointment(Learner learner, Meeting meeting, InvitationTemplate invitationTemplate) {
        Calendar calendar = null;
        DateHelper dateHelper = dateHelperDefiner.defineDateHelper(meeting);
        BigInteger meetingId = meeting.getId();

        String email = learner.getEmail();
        EnrollmentType enrollmentType = learner.getEnrollmentType();

        Enrollment enrollment = enrollmentRepository.getByEmailAndParentIdAndType(meetingId, email, enrollmentType);
        Appointment oldAppointment = appointmentRepository.getByMeetingId(meetingId);
        if (oldAppointment == null) {
            Appointment newAppointment = appointmentCreator.createAppointment(meeting, invitationTemplate);
            Calendar calendarInvite = calendarFactory.createInvitationCalendarTemplate(dateHelper, newAppointment, enrollment);
            appointmentRepository.save(newAppointment);
            calendar = calendarInvite;
        } else {
            if (enrollment == null) {
                if (enrollmentChecker.wasChangedStatus(learner, meetingId)) {
                    Calendar calendarCancel = calendarFactory.createCancelCalendarTemplate(dateHelper, oldAppointment, enrollment);
                    calendar = calendarCancel;
                } else {
                    Calendar calendarInvite = calendarFactory.createInvitationCalendarTemplate(dateHelper, oldAppointment, enrollment);
                    calendar = calendarInvite;
                }
            } else {
                Appointment updatedAppointment = appointmentHandler.getUpdatedAppointment(meeting, invitationTemplate);
                if ((updatedAppointment.getUpdateIndex() == 0 && updatedAppointment.getRescheduleIndex() == 0) ||
                        (updatedAppointment.getRescheduleIndex() > oldAppointment.getRescheduleIndex() ||
                                updatedAppointment.getUpdateIndex() > oldAppointment.getUpdateIndex())) {
                    Calendar calendarUpdate = calendarFactory.createInvitationCalendarTemplate(dateHelper, updatedAppointment, enrollment);
                    appointmentRepository.save(updatedAppointment);
                    calendar = calendarUpdate;
                }
            }
        }
        return calendar;
    }

}
