package by.iba.bussiness.calendar.creator.installer;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.appointment.AppointmentComparator;
import by.iba.bussiness.appointment.AppointmentCreator;
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
import net.fortuna.ical4j.model.CalendarException;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.parameter.PartStat;
import net.fortuna.ical4j.model.parameter.Role;
import net.fortuna.ical4j.model.parameter.Rsvp;
import net.fortuna.ical4j.model.property.Attendee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Component
public class CalendarAttendeesInstaller {
    private static final Logger logger = LoggerFactory.getLogger(CalendarAttendeesInstaller.class);
    private DateHelperDefiner dateHelperDefiner;
    private CalendarFactory calendarFactory;
    private EnrollmentRepository enrollmentRepository;
    private EnrollmentChecker enrollmentChecker;
    private AppointmentCreator appointmentCreator;
    private AppointmentRepository appointmentRepository;
    private AppointmentComparator appointmentComparator;

    @Autowired
    public CalendarAttendeesInstaller(DateHelperDefiner dateHelperDefiner,
                                      CalendarFactory calendarFactory,
                                      EnrollmentRepository enrollmentRepository,
                                      EnrollmentChecker enrollmentChecker, AppointmentCreator appointmentCreator, AppointmentRepository appointmentRepository, AppointmentComparator appointmentComparator) {
        this.dateHelperDefiner = dateHelperDefiner;
        this.calendarFactory = calendarFactory;
        this.enrollmentRepository = enrollmentRepository;
        this.enrollmentChecker = enrollmentChecker;
        this.appointmentCreator = appointmentCreator;
        this.appointmentRepository = appointmentRepository;
        this.appointmentComparator = appointmentComparator;
    }

    public List<Calendar> createCalendarList(List<Learner> learners, Meeting meeting, InvitationTemplate invitationTemplate) {
        List<Calendar> calendarList = new ArrayList<>();
        DateHelper dateHelper = dateHelperDefiner.defineDateHelper(meeting);
        BigInteger meetingId = meeting.getId();
        for (Learner learner : learners) {
            String email = learner.getEmail();
            EnrollmentType enrollmentType = learner.getEnrollmentType();
            Calendar calendar = null;
            Enrollment enrollment = enrollmentRepository.getByEmailAndParentIdAndType(meetingId, email, enrollmentType);
            Appointment appointment = appointmentRepository.getByMeetingId(meetingId);
            Appointment newAppointment;
            if (appointment == null) {
                newAppointment = appointmentCreator.createAppointment(meeting, invitationTemplate);
                Calendar calendarInvite = calendarFactory.createInvitationCalendarTemplate(dateHelper, newAppointment, enrollment);
                calendar = calendarInvite;
            } else {
                if (enrollment == null) {
                    if (enrollmentChecker.wasChangedStatus(learner, meetingId)) {
                        Calendar calendarCancel = calendarFactory.createCancelCalendarTemplate(dateHelper, appointment, enrollment);
                        calendar = calendarCancel;
                    } else {
                        Calendar calendarInvite = calendarFactory.createInvitationCalendarTemplate(dateHelper, appointment, enrollment);
                        calendar = calendarInvite;
                    }
                } else {
                    Appointment updatedAppointment = appointmentComparator.comparateAppointments(appointment);
                    if (!(updatedAppointment.getRescheduleIndex() <= appointment.getRescheduleIndex()
                            && updatedAppointment.getUpdateIndex() <= appointment.getUpdateIndex())) {
                        Calendar calendarInvite = calendarFactory.createInvitationCalendarTemplate(dateHelper, updatedAppointment, enrollment);
                        calendar = calendarInvite;
                    }
                }
            }
            addAttendeToCalendar(email, calendar);
            try {
                calendarList.add(new Calendar(calendar));
            } catch (ParseException | IOException | URISyntaxException e) {
                logger.error("Can't create calendar list", e);
                throw new CalendarException("Can't create calendar meeting. Try again later.");
            }
        }
        return calendarList;
    }

    public void addAttendeToCalendar(String email, Calendar calendar) {
        CalendarComponent vEvent = calendar.getComponent(Component.VEVENT);
        Attendee attendee = new Attendee(URI.create("mailto:" + email));
        attendee.getParameters().add(Rsvp.FALSE);
        attendee.getParameters().add(Role.REQ_PARTICIPANT);
        attendee.getParameters().add(PartStat.ACCEPTED);
        vEvent.getProperties().add(attendee);

    }
}
