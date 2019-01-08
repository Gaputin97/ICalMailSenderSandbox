package by.iba.bussiness.appointment;

import by.iba.bussiness.appointment.repository.AppointmentRepository;
import by.iba.bussiness.invitation_template.InvitationTemplate;
import by.iba.bussiness.meeting.Meeting;
import by.iba.bussiness.owner.Owner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

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

    public Appointment getUpdatedAppointment(Meeting updatedMeeting, InvitationTemplate updatedTemplate) {
        Appointment newAppointment = appointmentCreator.createAppointment(updatedMeeting, updatedTemplate);
        Appointment sourceAppointment = appointmentRepository.getByMeetingId(updatedMeeting.getId());

        String oldAppointmentDescription = sourceAppointment.getDescription();
        String newAppointmentDescription = newAppointment.getDescription();

        String oldAppointmentLocation = sourceAppointment.getLocation();
        String newAppointmentLocation = newAppointment.getLocation();

        Owner oldAppointmentOwner = sourceAppointment.getOwner();
        Owner newAppointmentOwner = newAppointment.getOwner();

        BigInteger sourceId = sourceAppointment.getId();
        int sourceUpdatedIndex = sourceAppointment.getUpdateIndex();
        int sourceRescheduledIndex = sourceAppointment.getRescheduleIndex();

        int maximumIndex;

        if (sourceAppointment.equals(newAppointment)) {
            newAppointment = sourceAppointment;
        } else {
            maximumIndex = getMaximumIndex(sourceAppointment);
            if (!sourceAppointment.getTimeSlots().equals(newAppointment.getTimeSlots())) {
                newAppointment.setUpdateIndex(sourceUpdatedIndex);
                newAppointment.setRescheduleIndex(++maximumIndex);
            } else if (!oldAppointmentDescription.equals(newAppointmentDescription) ||
                    (!oldAppointmentLocation.equals(newAppointmentLocation)) ||
                    (!oldAppointmentOwner.equals(newAppointmentOwner))) {
                newAppointment.setRescheduleIndex(sourceRescheduledIndex);
                newAppointment.setUpdateIndex(++maximumIndex);
            }
        }
        newAppointment.setId(sourceId);
        appointmentRepository.save(newAppointment);
        return newAppointment;
    }

    public int getMaximumIndex(Appointment sourceAppointment) {
        int updateIndex = sourceAppointment.getUpdateIndex();
        int rescheduleIndex = sourceAppointment.getRescheduleIndex();
        return updateIndex > rescheduleIndex ? updateIndex : rescheduleIndex;
    }
}
