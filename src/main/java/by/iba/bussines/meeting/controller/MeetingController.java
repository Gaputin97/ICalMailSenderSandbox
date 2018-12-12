package by.iba.bussines.meeting.controller;

import by.iba.bussines.meeting.model.Meeting;
import by.iba.bussines.meeting.service.v1.MeetingServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class MeetingController {

    private MeetingServiceImpl meetingService;

    @Autowired
    public MeetingController(MeetingServiceImpl meetingService) {
        this.meetingService = meetingService;
    }

    @ApiOperation(value = "Get meeting by id from meeting service", response = Meeting.class)
    @RequestMapping(value = "/meeting/get", method = RequestMethod.POST)
    @ResponseBody
    public Meeting getMeetingById(@RequestParam(value = "id") String id, HttpServletRequest request) {
        return meetingService.getMeetingById(request, id);
    }

    @ApiOperation(value = "Get all meetings from meeting service", response = Meeting[].class)
    @RequestMapping(value = "/meeting/getAll", method = RequestMethod.POST)
    @ResponseBody
    public List<Meeting> getAllMeetings(HttpServletRequest request) {
        return meetingService.getAllMeetings(request);
    }
}