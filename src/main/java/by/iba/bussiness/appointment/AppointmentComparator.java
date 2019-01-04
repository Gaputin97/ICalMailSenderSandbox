package by.iba.bussiness.appointment;

import by.iba.bussiness.appointment.repository.AppointmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AppointmentComparator {
    private static final Logger logger = LoggerFactory.getLogger(AppointmentComparator.class);

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Appointment comparateAppointments(Appointment newAppointment) {
        Appointment oldAppointment = appointmentRepository.getByMeetingId(newAppointment.getMeetingId());

        Appointment appointment = null;
        if(oldAppointment.hashCode() == newAppointment.hashCode()) {
            appointment = oldAppointment;
        } else if (oldAppointment.getTimeSlots().hashCode() == newAppointment.getTimeSlots().hashCode()) {
            appointment = newAppointment;
            int rescheduleIndex = newAppointment.getRescheduleIndex();
            newAppointment.setRescheduleIndex(rescheduleIndex++);
        }

        return appointment;
    }
}
