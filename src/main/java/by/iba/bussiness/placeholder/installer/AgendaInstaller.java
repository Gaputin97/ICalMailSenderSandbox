package by.iba.bussiness.placeholder.installer;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.calendar.session.Session;
import by.iba.bussiness.calendar.session.SessionParser;
import by.iba.bussiness.meeting.Meeting;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.List;

@Component
public class AgendaInstaller {
    private SessionParser sessionParser;
    private Configuration freeMarkerConfiguration;

    public AgendaInstaller(SessionParser sessionParser, Configuration freeMarkerConfiguration) {
        this.sessionParser = sessionParser;
        this.freeMarkerConfiguration = freeMarkerConfiguration;
    }

    public String installMeetingAgenda(Meeting meeting) {
        String agenda = null;
        try {
            List<Session> sessions = sessionParser.timeSlotListToSessionList(meeting.getTimeSlots());
            Appointment appointmentHelper = new Appointment();
            appointmentHelper.setSessionList(sessions);
            appointmentHelper.setTimeZone(meeting.getTimeZone());
            Template messageTemplate = freeMarkerConfiguration.getTemplate("agenda.html");
            agenda = FreeMarkerTemplateUtils.processTemplateIntoString(messageTemplate, appointmentHelper);
        } catch (TemplateException | IOException e) {
            // TODO (throw ili net)
        }
        return agenda;
    }
}
