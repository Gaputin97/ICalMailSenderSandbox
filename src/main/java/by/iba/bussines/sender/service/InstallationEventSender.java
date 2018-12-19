package by.iba.bussines.sender.service;

import by.iba.bussines.status.send.CalendarSendingStatus;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.property.Method;

public interface InstallationEventSender {
    CalendarSendingStatus sendCalendarToRecipients(String[] recipientList, Calendar calendar, Method method);
}
