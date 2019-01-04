package by.iba.bussiness.appointment;

import by.iba.bussiness.appointment.repository.AppointmentRepository;
import by.iba.bussiness.invitation_template.InvitationTemplate;
import by.iba.bussiness.meeting.Meeting;
import by.iba.bussiness.owner.Owner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppointmentHandler {
    private static final Logger logger = LoggerFactory.getLogger(AppointmentHandler.class);
    private AppointmentRepository appointmentRepository;
    private AppointmentCreator appointmentCreator;

    @Autowired
    public AppointmentHandler(AppointmentRepository appointmentRepository, AppointmentCreator appointmentCreator) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentCreator = appointmentCreator;
    }

    public Appointment updateAndGetAppointment(Meeting updatedMeeting, InvitationTemplate updatedTemplate) {
        Appointment tempAppointment = appointmentCreator.createAppointment(updatedMeeting, updatedTemplate);
        Appointment sourceAppointment = appointmentRepository.getByMeetingId(tempAppointment.getMeetingId());
        Appointment appointment = null;

        String oldAppointmentDescription = sourceAppointment.getDescription();
        String tempAppointmentDescription = tempAppointment.getDescription();

        String oldAppointmentLocation = sourceAppointment.getLocation();
        String tempAppointmentLocation = tempAppointment.getLocation();

        Owner oldAppointmentOwner = sourceAppointment.getOwner();
        Owner tempAppointmentOwner = tempAppointment.getOwner();

        int maximumIndex;
        if (sourceAppointment.hashCode() == tempAppointment.hashCode()) {
            appointment = sourceAppointment;
        } else if (!sourceAppointment.getTimeSlots().equals(tempAppointment.getTimeSlots())) {
            appointment = tempAppointment;
            maximumIndex = getMaximumIndex(sourceAppointment);
            tempAppointment.setRescheduleIndex(++maximumIndex);
        } else if (!oldAppointmentDescription.equals(tempAppointmentDescription) ||
                (!oldAppointmentLocation.equals(tempAppointmentLocation)) ||
                (!oldAppointmentOwner.equals(tempAppointmentOwner))) {
            appointment = tempAppointment;
            maximumIndex = getMaximumIndex(sourceAppointment);
            tempAppointment.setUpdateIndex(++maximumIndex);
        }
        appointment.setId(sourceAppointment.getId());
        appointmentRepository.save(appointment);
        return appointment;
    }

    private int getMaximumIndex(Appointment sourceAppointment) {
        int updateIndex = sourceAppointment.getUpdateIndex();
        int rescheduleIndex = sourceAppointment.getRescheduleIndex();
        return updateIndex > rescheduleIndex ? updateIndex : rescheduleIndex;
    }
}
