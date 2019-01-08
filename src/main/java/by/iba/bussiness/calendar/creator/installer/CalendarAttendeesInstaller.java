package by.iba.bussiness.calendar.creator.installer;

import by.iba.bussiness.calendar.attendee.Learner;
import by.iba.bussiness.calendar.creator.AppointmentCalendarCreator;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Component
public class CalendarAttendeesInstaller {
    private static final Logger logger = LoggerFactory.getLogger(CalendarAttendeesInstaller.class);
    @Autowired
    private AppointmentCalendarCreator appointmentCalendarCreator;

    public List<Calendar> installCalendarListAndSaveAppointments(List<Learner> learners, Meeting meeting, InvitationTemplate invitationTemplate) {
        List<Calendar> calendarList = new ArrayList<>();
        for (Learner learner : learners) {
            String email = learner.getEmail();
            Calendar calendar = appointmentCalendarCreator.createCalendarAndSaveAppointment(learner, meeting, invitationTemplate);
            addAttendeeToCalendar(email, calendar);
            try {
                calendarList.add(new Calendar(calendar));
            } catch (ParseException | IOException | URISyntaxException e) {
                logger.error("Can't create calendar list", e);
                throw new CalendarException("Can't create calendar meeting. Try again later.");
            }
        }
        return calendarList;
    }

    private void addAttendeeToCalendar(String email, Calendar calendar) {
        CalendarComponent vEvent = calendar.getComponent(Component.VEVENT);
        Attendee attendee = new Attendee(URI.create("mailto:" + email));
        attendee.getParameters().add(Rsvp.FALSE);
        attendee.getParameters().add(Role.REQ_PARTICIPANT);
        attendee.getParameters().add(PartStat.ACCEPTED);
        vEvent.getProperties().add(attendee);
    }
}