package by.iba.bussiness.sender.controller;

import by.iba.bussiness.sender.service.v1.SenderServiceImpl;
import by.iba.bussiness.calendar.email.Email;
import by.iba.bussiness.status.send.CalendarSendingStatus;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
public class SenderController {
    private final static Logger logger = LoggerFactory.getLogger(SenderController.class);
    @Autowired
    private SenderServiceImpl senderServiceImpl;

    @ApiOperation(value = "Send calendar templates for non existing recipients")
    @RequestMapping(value = "/send/meeting/{meetingId}", method = RequestMethod.POST)
    public CalendarSendingStatus sendInvitationTemplatesToRecipients(@PathVariable String meetingId,
                                                                     @RequestBody Email email,
                                                                     HttpServletRequest request) {
        senderServiceImpl.sendMeeting(request, meetingId, email);
        logger.info("Calendar templates was successfully sanded");
        return new CalendarSendingStatus("All templates was successfully sanded");
    }


    @RequestMapping(path = "/download", method = RequestMethod.GET)
    public ResponseEntity<Resource> download(String param) throws IOException {

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("text/calendar"))
                .body(resource);
    }
}
