package by.iba.bussiness.sender.service.manager;

import by.iba.bussiness.sender.service.InstallationEventSender;
import net.fortuna.ical4j.model.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MultiplyCalendarSender {
    @Autowired
    InstallationEventSender installationEventSender;

    public void sendToEndUser(List<Calendar> calendarList) {
        for (Calendar calendar : calendarList) {
            installationEventSender.sendCalendarToRecipient(calendar);
        }
    }
}
