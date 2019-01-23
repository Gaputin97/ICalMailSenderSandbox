package by.iba.bussiness.enroll.service.v1;

import by.iba.bussiness.calendar.learner.Learner;
import by.iba.bussiness.enroll.service.EnrollService;
import by.iba.bussiness.enroll.EnrollLearnerStatus;
import by.iba.bussiness.enrollment.EnrollmentsInstaller;
import by.iba.bussiness.meeting.Meeting;
import by.iba.bussiness.meeting.service.MeetingService;
import by.iba.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class EnrollServiceImpl implements EnrollService {
    private static final Logger logger = LoggerFactory.getLogger(EnrollServiceImpl.class);
    private MeetingService meetingService;
    private EnrollmentsInstaller enrollmentsInstaller;

    @Autowired
    public EnrollServiceImpl(MeetingService meetingService, EnrollmentsInstaller enrollmentsInstaller) {
        this.meetingService = meetingService;
        this.enrollmentsInstaller = enrollmentsInstaller;
    }

    @Override
    public List<EnrollLearnerStatus> enrollLearners(HttpServletRequest request,
                                                    String meetingId,
                                                    List<Learner> learners) {
        Meeting meeting = meetingService.getMeetingById(request, meetingId);
        String invitationTemplateKey = meeting.getInvitationTemplate();
        if (invitationTemplateKey.isEmpty()) {
            logger.error("Can't enroll learners to this event, cause can't find some invitation template by meeting id: " + meetingId);
            throw new ServiceException("Meeting " + meetingId + " doesn't have learner invitation template");
        }
        return enrollmentsInstaller.installEnrollmentsByLearners(learners, meetingId);
    }
}
