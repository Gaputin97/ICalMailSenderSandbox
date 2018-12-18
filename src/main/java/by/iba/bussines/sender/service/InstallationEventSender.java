package by.iba.bussines.sender.service;

import by.iba.bussines.sender.service.method.Method;
import by.iba.bussines.status.send.CalendarSendingStatus;
import net.fortuna.ical4j.model.Calendar;

public interface InstallationEventSender {
    CalendarSendingStatus sendCalendarToRecipients(String[] recipientList, Calendar calendar, Method method);
}
