package by.iba.bussiness.appointment;

import by.iba.bussiness.appointment.repository.AppointmentRepository;
import by.iba.bussiness.invitation_template.service.InvitationTemplateService;
import by.iba.bussiness.meeting.service.MeetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AppointmentComparator {
    private static final Logger logger = LoggerFactory.getLogger(AppointmentComparator.class);
    private MeetingService meetingService;
    private InvitationTemplateService invitationTemplateService;
    private AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentComparator(MeetingService meetingService,
                                 InvitationTemplateService invitationTemplateService,
                                 AppointmentRepository appointmentRepository) {
        this.meetingService = meetingService;
        this.invitationTemplateService = invitationTemplateService;
        this.appointmentRepository = appointmentRepository;
    }


}
