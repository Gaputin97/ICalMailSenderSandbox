package by.iba.bussiness.calendar.creator.installer;

import by.iba.bussiness.appointment.AppointmentCreator;
import by.iba.bussiness.appointment.repository.AppointmentRepository;
import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.calendar.attendee.Learner;
import by.iba.bussiness.calendar.creator.AppointmentCalendarCreator;
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
    private AppointmentCalendarCreator appointmentCalendarCreator;
    private AppointmentRepository appointmentRepository;
    private AppointmentCreator appointmentCreator;

    @Autowired
    public CalendarAttendeesInstaller(AppointmentCalendarCreator appointmentCalendarCreator,
                                      AppointmentRepository appointmentRepository,
                                      AppointmentCreator appointmentCreator) {
        this.appointmentCalendarCreator = appointmentCalendarCreator;
        this.appointmentRepository = appointmentRepository;
        this.appointmentCreator = appointmentCreator;
    }


    public List<Calendar> installCalendarList(List<Learner> learners, Appointment appointment) {
        List<Calendar> calendarList = new ArrayList<>();
        BigInteger meetingId = appointment.getMeetingId();
        Calendar calendar;
        for (Learner learner : learners) {
            String email = learner.getEmail();
            calendar = appointmentCalendarCreator.createCalendar(learner, appointment);
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