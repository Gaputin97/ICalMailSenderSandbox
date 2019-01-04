package by.iba.bussiness.appointment.repository;

import by.iba.bussiness.appointment.Appointment;
import org.bson.types.ObjectId;

public interface AppointmentRepository {
    Appointment getByMeetingId(ObjectId code);
    Appointment save(Appointment appointment);
    Appointment update(Appointment appointment);
}
