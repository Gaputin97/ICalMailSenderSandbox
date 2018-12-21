package by.iba.bussiness.sender.service;

import by.iba.bussiness.status.send.CalendarSendingStatus;
import net.fortuna.ical4j.model.Calendar;

public interface InstallationEventSender {
    CalendarSendingStatus sendCalendarToRecipient(Calendar calendar);
}
