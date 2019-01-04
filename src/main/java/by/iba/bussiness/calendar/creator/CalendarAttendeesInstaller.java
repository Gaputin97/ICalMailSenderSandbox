package by.iba.bussiness.calendar.creator;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.calendar.CalendarFactory;
import by.iba.bussiness.calendar.attendee.Learner;
import by.iba.bussiness.calendar.date.DateHelperDefiner;
import by.iba.bussiness.calendar.date.model.DateHelper;
import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.enrollment.EnrollmentChecker;
import by.iba.bussiness.enrollment.EnrollmentType;
import by.iba.bussiness.enrollment.repository.EnrollmentRepository;
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

    @Autowired
    public CalendarAttendeesInstaller(DateHelperDefiner dateHelperDefiner,
                                      CalendarFactory calendarFactory,
                                      EnrollmentRepository enrollmentRepository,
                                      EnrollmentChecker enrollmentChecker) {
        this.dateHelperDefiner = dateHelperDefiner;
        this.calendarFactory = calendarFactory;
        this.enrollmentRepository = enrollmentRepository;
        this.enrollmentChecker = enrollmentChecker;
    }

    public List<Calendar> createCalendarList(List<Learner> learners, Appointment appointment) {
        List<Calendar> calendarList = new ArrayList<>();
        DateHelper dateHelper = dateHelperDefiner.definerDateHelper(appointment);
        BigInteger meetingId = appointment.getMeetingId();

        for (Learner learner : learners) {
            String email = learner.getEmail();
            EnrollmentType enrollmentType = learner.getEnrollmentType();
            Calendar calendar;
            Enrollment enrollment = enrollmentRepository.getByEmailAndParentIdAndType(meetingId, email, enrollmentType);

            if (enrollment == null) {
                if (enrollmentChecker.wasChangedStatus(learner, meetingId)) {
                    Calendar calendarCancel = calendarFactory.createCancelCalendarTemplate(dateHelper, appointment, enrollment);
                    calendar = calendarCancel;
                } else {

                }
                }
                if (learner.getEnrollmentType() == EnrollmentType.CONFIRMED) {

                } else {

                }
                String email = learner.getEmail();
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

        public void addAttendeToCalendar (String email, Calendar calendar){
            CalendarComponent vEvent = calendar.getComponent(Component.VEVENT);
            Attendee attendee = new Attendee(URI.create("mailto:" + email));
            attendee.getParameters().add(Rsvp.FALSE);
            attendee.getParameters().add(Role.REQ_PARTICIPANT);
            attendee.getParameters().add(PartStat.ACCEPTED);
            vEvent.getProperties().add(attendee);

        }
    }
