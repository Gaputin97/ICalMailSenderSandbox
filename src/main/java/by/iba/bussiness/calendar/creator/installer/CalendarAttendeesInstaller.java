package by.iba.bussiness.calendar.creator.installer;

import by.iba.bussiness.enrollment.Enrollment;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.parameter.PartStat;
import net.fortuna.ical4j.model.parameter.Role;
import net.fortuna.ical4j.model.parameter.Rsvp;
import net.fortuna.ical4j.model.property.Attendee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

@org.springframework.stereotype.Component
public class CalendarAttendeesInstaller {
    private static final Logger logger = LoggerFactory.getLogger(CalendarAttendeesInstaller.class);

    public Calendar addAttendeeToCalendar(Enrollment enrollment, Calendar calendar) {
        String email = enrollment.getUserEmail();
        CalendarComponent vEvent = calendar.getComponent(Component.VEVENT);
        Attendee attendee = new Attendee(URI.create("mailto:" + email));
        attendee.getParameters().add(Rsvp.FALSE);
        attendee.getParameters().add(Role.REQ_PARTICIPANT);
        attendee.getParameters().add(PartStat.ACCEPTED);
        vEvent.getProperties().add(attendee);
        return calendar;
    }
}