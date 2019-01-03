package by.iba.bussiness.meeting;

import by.iba.bussiness.meeting.service.MeetingService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class MeetingController {
    @Autowired
    private MeetingService meetingService;

    @ApiOperation(value = "Get meeting by id from meeting service", response = Meeting.class)
    @RequestMapping(value = "/meeting/get/{id}", method = RequestMethod.GET)
    public Meeting getMeetingById(@PathVariable(value = "id") String id, HttpServletRequest request) {
        return meetingService.getMeetingById(request, id);
    }

    @ApiOperation(value = "Get all fields from meeting service", response = Meeting[].class)
    @RequestMapping(value = "/meeting/get", method = RequestMethod.GET)
    public List<Meeting> getAllMeetings(HttpServletRequest request) {
        return meetingService.getAllMeetings(request);
    }
}