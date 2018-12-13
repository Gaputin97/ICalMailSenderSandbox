package by.iba.bussines.time.slot.service;

import by.iba.bussines.time.slot.model.TimeSlot;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface TimeSlotService {
    List<TimeSlot> getMeetingTimeSlots(HttpServletRequest request, String meetingId);
}
