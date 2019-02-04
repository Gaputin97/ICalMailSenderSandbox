package by.iba.bussiness.notification.service.v1;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.appointment.AppointmentCreator;
import by.iba.bussiness.appointment.AppointmentDeterminer;
import by.iba.bussiness.appointment.handler.AppointmentIndexesUpdater;
import by.iba.bussiness.appointment.repository.AppointmentRepository;
import by.iba.bussiness.calendar.session.Session;
import by.iba.bussiness.facade.ComplexTemplateSenderFacade;
import by.iba.bussiness.facade.SimpleCalendarSenderFacade;
import by.iba.bussiness.invitation_template.InvitationTemplate;
import by.iba.bussiness.invitation_template.service.InvitationTemplateService;
import by.iba.bussiness.meeting.Meeting;
import by.iba.bussiness.meeting.service.MeetingService;
import by.iba.bussiness.meeting.type.MeetingLocationType;
import by.iba.bussiness.meeting.type.MeetingType;
import by.iba.bussiness.meeting.type.MeetingTypeDefiner;
import by.iba.bussiness.notification.service.NotificationService;
import by.iba.bussiness.placeholder.replacer.TemplatePlaceHolderReplacer;
import by.iba.bussiness.placeholder.PlaceHoldersInstaller;
import by.iba.bussiness.notification.NotificationResponseStatus;
import by.iba.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);
    private MeetingService meetingService;
    private InvitationTemplateService invitationTemplateService;
    private AppointmentDeterminer appointmentDeterminer;
    private MeetingTypeDefiner meetingTypeDefiner;
    private ComplexTemplateSenderFacade complexTemplateSenderFacade;
    private SimpleCalendarSenderFacade simpleCalendarSenderFacade;
    private AppointmentRepository appointmentRepository;
    private PlaceHoldersInstaller placeHoldersInstaller;
    private TemplatePlaceHolderReplacer templatePlaceHolderReplacer;
    private AppointmentCreator appointmentCreator;
    private AppointmentIndexesUpdater indexesUpdater;

    @Autowired
    public NotificationServiceImpl(MeetingService meetingService,
                                   InvitationTemplateService invitationTemplateService,
                                   AppointmentDeterminer appointmentDeterminer,
                                   MeetingTypeDefiner meetingTypeDefiner,
                                   ComplexTemplateSenderFacade complexTemplateSenderFacade,
                                   SimpleCalendarSenderFacade simpleCalendarSenderFacade,
                                   AppointmentRepository appointmentRepository,
                                   PlaceHoldersInstaller placeHoldersInstaller,
                                   TemplatePlaceHolderReplacer templatePlaceHolderReplacer,
                                   AppointmentCreator appointmentCreator,
                                   AppointmentIndexesUpdater indexesUpdater) {
        this.meetingService = meetingService;
        this.invitationTemplateService = invitationTemplateService;
        this.appointmentDeterminer = appointmentDeterminer;
        this.meetingTypeDefiner = meetingTypeDefiner;
        this.complexTemplateSenderFacade = complexTemplateSenderFacade;
        this.simpleCalendarSenderFacade = simpleCalendarSenderFacade;
        this.appointmentRepository = appointmentRepository;
        this.placeHoldersInstaller = placeHoldersInstaller;
        this.templatePlaceHolderReplacer = templatePlaceHolderReplacer;
        this.appointmentCreator = appointmentCreator;
        this.indexesUpdater = indexesUpdater;
    }

    @Override
    public List<NotificationResponseStatus> sendCalendarToAllEnrollmentsOfMeeting(HttpServletRequest request, String meetingId) {

        Meeting meeting = meetingService.getMeetingById(request, meetingId);
        if (meeting == null) {

            logger.info("Can't find meeting in ec3 with meetingId: " + meetingId);
            throw new ServiceException("Can't find meeting with id " + meetingId);
        }

        String invitationTemplateKey = meeting.getInvitationTemplate();
        if (invitationTemplateKey.isEmpty()) {
            logger.error("Can't enroll learners to this event, cause can't find some invitation template by meeting id: " + meetingId);
            throw new ServiceException("Meeting " + meetingId + " doesn't have learner invitation template");
        }
        InvitationTemplate invitationTemplateWithoutPlaceHolders = invitationTemplateService.getInvitationTemplateByCode(request, invitationTemplateKey);
        Map<String, String> placeHoldersMap = placeHoldersInstaller.installPlaceHoldersMap(meeting);
        MeetingLocationType meetingLocationType = MeetingLocationType.valueOf(meeting.getType());
        InvitationTemplate invitationTemplateWithPlaceHolders = templatePlaceHolderReplacer.replaceTemplatePlaceHolders(
                placeHoldersMap,
                invitationTemplateWithoutPlaceHolders,
                meetingLocationType);
        meeting.setPlainDescription("Plain description");

        Appointment currentAppointment = appointmentRepository.getByMeetingId(new BigInteger(meetingId));
        Appointment newAppointmentWithoutIndexes = appointmentCreator.createAppointmentWithMainFields(meeting, invitationTemplateWithPlaceHolders);
        Appointment newAppointment;
        if (currentAppointment == null) {
            newAppointment = appointmentRepository.save(newAppointmentWithoutIndexes);
        } else {
            Appointment newAppointmentWithIndexes = indexesUpdater.updateIndexesBasedOnSessionsDifferences(newAppointmentWithoutIndexes, currentAppointment);
            Appointment determinedAppointment = appointmentDeterminer.determineNewAppointmentByIndexes(newAppointmentWithIndexes, currentAppointment);
            newAppointment = appointmentRepository.save(determinedAppointment);
        }

        List<Session> newAppSessions = newAppointment.getSessionList();
        MeetingType newAppointmentMeetingType = meetingTypeDefiner.defineMeetingType(newAppSessions);
        List<NotificationResponseStatus> notificationResponseStatusList;

        if (newAppointmentMeetingType.equals(MeetingType.SIMPLE)) {
            notificationResponseStatusList = simpleCalendarSenderFacade.sendCalendar(newAppointment, currentAppointment);
        } else {
            notificationResponseStatusList = complexTemplateSenderFacade.sendTemplate(newAppointment, currentAppointment);
        }
        return notificationResponseStatusList;
    }
}
