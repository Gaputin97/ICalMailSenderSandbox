package by.iba.bussines.timeslot.service;

import by.iba.bussines.timeslot.model.TimeSlot;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface TimeSlotService {
    List<TimeSlot> getMeetingTimeSlots(HttpServletRequest request, String meetingId);
}
