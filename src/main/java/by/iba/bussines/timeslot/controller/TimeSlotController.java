package by.iba.bussines.timeslot.controller;

import by.iba.bussines.timeslot.model.TimeSlot;
import by.iba.bussines.timeslot.service.v1.TimeSlotServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class TimeSlotController {

    private TimeSlotServiceImpl timeSlotService;

    @Autowired
    public TimeSlotController(TimeSlotServiceImpl timeSlotService) {
        this.timeSlotService = timeSlotService;
    }

    @ApiOperation(value = "Get time slots of meeting", response = TimeSlot[].class)
    @RequestMapping(value = "/timeslot/getByMeetingId", method = RequestMethod.POST)
    public List<TimeSlot> getMeetingTimeSlot(@RequestParam(value = "meetingId") String meetingId, HttpServletRequest request) {
        return timeSlotService.getMeetingTimeSlots(request, meetingId);
    }
}
