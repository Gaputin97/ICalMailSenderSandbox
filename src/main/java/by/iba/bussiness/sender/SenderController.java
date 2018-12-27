package by.iba.bussiness.sender.controller;

import by.iba.bussiness.calendar.attendee.model.Attendee;
import by.iba.bussiness.sender.service.v1.SenderServiceImpl;
import by.iba.bussiness.status.send.CalendarResponseStatus;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
public class SenderController {
    private final static Logger logger = LoggerFactory.getLogger(SenderController.class);
    private static final String NAME_OF_FILE = "Calendar.ics";
    @Autowired
    private SenderServiceImpl senderServiceImpl;

    @ApiOperation(value = "Send calendar templates for non existing recipients")
    @RequestMapping(value = "/send/meeting/{meetingId}", method = RequestMethod.POST)
    public CalendarResponseStatus sendInvitationTemplatesToRecipients(@PathVariable String meetingId,
                                                                      @RequestBody List<Attendee> attendees,
                                                                      HttpServletRequest request) {
        return senderServiceImpl.sendMeeting(request, meetingId, attendees);
    }


//    @RequestMapping(path = "/download", method = RequestMethod.GET)
//    public ResponseEntity<InputStreamResource> download() {
//        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
//
//        UploadF
//        return ResponseEntity.ok()
//                .headers(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + NAME_OF_FILE)
//                .contentType(MediaType.parseMediaType("text/calendar"))
//                .body(resource);
//    }
}
