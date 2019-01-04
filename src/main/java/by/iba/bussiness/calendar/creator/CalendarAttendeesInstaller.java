package by.iba.bussiness.calendar.creator;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.calendar.CalendarFactory;
import by.iba.bussiness.calendar.attendee.Learner;
import by.iba.bussiness.calendar.date.DateHelperDefiner;
import by.iba.bussiness.calendar.date.model.DateHelper;
import by.iba.bussiness.enrollment.EnrollmentType;
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

    @Autowired
    public CalendarAttendeesInstaller(DateHelperDefiner dateHelperDefiner, CalendarFactory calendarFactory) {
        this.dateHelperDefiner = dateHelperDefiner;
        this.calendarFactory = calendarFactory;
    }

    public List<Calendar> createCalendarList(List<Learner> learners, Appointment appointment, String calendarUid) {
        List<Calendar> calendarList = new ArrayList<>();
        DateHelper dateHelper = dateHelperDefiner.definerDateHelper(appointment);

        for (Learner learner : learners) {
            Calendar calendar;
            if (learner.getEnrollmentType() == EnrollmentType.CONFIRMED) {
                Calendar calendarInvite = calendarFactory.createInvitationCalendarTemplate(dateHelper, appointment, calendarUid);
                calendar = calendarInvite;
            } else {
                Calendar calendarCancel = calendarFactory.createCancelCalendarTemplate(dateHelper, appointment, calendarUid);
                calendar = calendarCancel;
            }
            String email = learner.getEmail();
            Attendee attendee = new Attendee(URI.create("mailto:" + email));
            attendee.getParameters().add(Rsvp.FALSE);
            attendee.getParameters().add(Role.REQ_PARTICIPANT);
            attendee.getParameters().add(PartStat.ACCEPTED);

            CalendarComponent event = calendar.getComponent(Component.VEVENT);
            event.getProperties().add(attendee);

            try {
                calendarList.add(new Calendar(calendar));
            } catch (ParseException | IOException | URISyntaxException e) {
                logger.error("Can't create calendar list", e);
                throw new CalendarException("Can't create calendar meeting. Try again later.");
            }
            event.getProperties().remove(attendee);
        }
        return calendarList;
    }
}
