package by.iba.bussiness.kakoetoimya;

import by.iba.bussiness.calendar.learner.Learner;
import by.iba.bussiness.enrollment.EnrollRequestWrapper;
import by.iba.bussiness.kakoetoimya.service.ImyaService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class ImyaController {
    private ImyaService imyaService;

    @Autowired
    public ImyaController(ImyaService imyaService) {
        this.imyaService = imyaService;
    }

    @ApiOperation(value = "Enroll users and send notifications. ")
    @RequestMapping(value = "/imya/imya", method = RequestMethod.GET)
    public ImyaResponseStatus sendCalendar(@RequestBody EnrollRequestWrapper enrollRequestWrapper,
                                           HttpServletRequest request) {
        String meetingId = enrollRequestWrapper.getMeetingId();
        List<Learner> learners = enrollRequestWrapper.getLearners();
        return imyaService.imyaMethoda(request, meetingId, learners);
    }
}
