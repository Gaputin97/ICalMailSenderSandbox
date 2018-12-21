package by.iba.bussiness.sender.manager;

import by.iba.bussiness.sender.set_upper.InstallationEventSenderImpl;
import net.fortuna.ical4j.model.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MultiplyCalendarSender {
    @Autowired
    InstallationEventSenderImpl installationEventSender;

    public void sendToEndUser(List<Calendar> calendarList) {
        for (Calendar calendar : calendarList) {
            installationEventSender.sendCalendarToRecipient(calendar);
        }
    }
}
